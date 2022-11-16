package com.redteam.engine.core;

import java.nio.ByteBuffer;
import java.nio.FloatBuffer;
import java.nio.IntBuffer;
import java.util.ArrayList;
import java.util.List;

import org.joml.Vector2f;
import org.joml.Vector3f;
import org.joml.Vector3i;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL15;
import org.lwjgl.opengl.GL20;
import org.lwjgl.opengl.GL30;
import org.lwjgl.stb.STBImage;
import org.lwjgl.system.MemoryStack;

import com.redteam.engine.core.rendering.Model;
import com.redteam.engine.utils.Utils;

public class ObjectLoader {
	private final List<Integer> VAOs = new ArrayList<>();
	private final List<Integer> VBOs = new ArrayList<>();
	private final List<Integer> textures = new ArrayList<>();

	public Model loadOBJModel(String fileName) {
		List<String> lines = Utils.readAllLines(fileName);

		List<Vector3f> vertices = new ArrayList<>();
		List<Vector3f> normals = new ArrayList<>();
		List<Vector2f> textures = new ArrayList<>();
		List<Vector3i> faces = new ArrayList<>();

		for(String line : lines) {
			String[] tokens = line.split("\\s+");
			switch (tokens[0]) {
				case "v" -> {
					// vertices
					Vector3f verticesVec = new Vector3f(
							Float.parseFloat(tokens[1]),
							Float.parseFloat(tokens[2]),
							Float.parseFloat(tokens[3])
					);
					vertices.add(verticesVec);
				}
				case "vt" -> {
					// vertex textures
					Vector2f textureVec = new Vector2f(
							Float.parseFloat(tokens[1]),
							Float.parseFloat(tokens[2])
					);
					textures.add(textureVec);
				}
				case "vn" -> {
					// vertex normals
					Vector3f normalsVec = new Vector3f(
							Float.parseFloat(tokens[1]),
							Float.parseFloat(tokens[2]),
							Float.parseFloat(tokens[3])
					);
					normals.add(normalsVec);
				}
				case "f" -> {
					// faces
					processFace(tokens[1], faces);
					processFace(tokens[2], faces);
					processFace(tokens[3], faces);
				}
				default -> {
				}
			}
		}

		List<Integer> indices = new ArrayList<>();
		float[] verticesArr = new float[vertices.size() * 3];
		int i = 0;
		for(Vector3f pos : vertices) {
			verticesArr[i * 3] = pos.x;
			verticesArr[i * 3 + 1] = pos.y;
			verticesArr[i * 3 + 2] = pos.z;
			i++;
		}

		float[] texCoordinateArr = new float[vertices.size() * 2];
		float[] normalArr = new float[vertices.size() * 3];

		for(Vector3i face : faces) {
			processVertex(face.x, face.y, face.z, textures, normals, indices, texCoordinateArr, normalArr);
		}

		int[] indicesArr = indices.stream().mapToInt((Integer v) -> v).toArray();

		return loadModel(verticesArr, texCoordinateArr, normalArr, indicesArr);
	}

	private static void processVertex(int pos, int texCoordinate, int normal, List<Vector2f> texCoordinateList,
									  List<Vector3f> normalList, List<Integer> indicesList,
									  float[] texCoordinateArr, float[] normalArr) {
		indicesList.add(pos);

		if(texCoordinate >= 0) {
			Vector2f texCoordinateVec = texCoordinateList.get(texCoordinate);
			texCoordinateArr[pos * 2] = texCoordinateVec.x;
			texCoordinateArr[pos * 2 + 1] = 1 - texCoordinateVec.y;
		}

		if(normal >= 0) {
			Vector3f normalVec = normalList.get(normal);
			normalArr[pos * 3] = normalVec.x;
			normalArr[pos * 3 + 1] = normalVec.y;
			normalArr[pos * 3 + 2] = normalVec.z;
		}
	}

	private static void processFace(String token, List<Vector3i> faces) {
		String[] lineToken = token.split("/");
		int length = lineToken.length;
		int pos, coords = -1, normal = -1;
		pos = Integer.parseInt(lineToken[0]) - 1;
		if(length > 1) {
			String textCoordinate = lineToken[1];
			coords = textCoordinate.length() > 0 ? Integer.parseInt(textCoordinate) - 1 : -1;
			if(length > 2)
				normal = Integer.parseInt(lineToken[2]) - 1;
		}
		Vector3i facesVec = new Vector3i(pos, coords, normal);
		faces.add(facesVec);
	}
	
	public Model loadModel(float[] vertices, float[] textureCoords, float[] normals, int[] indices) {
		int id = createVAO();
		storeIndicesBuffer(indices);
		storeDataInAttribList(0, 3, vertices);
		storeDataInAttribList(1, 2, textureCoords);
		storeDataInAttribList(2, 3, normals);
		unbind();
		return new Model(id, indices.length);
	}
	
	public int loadTexture(String filename) {
		int width, height;
		ByteBuffer buffer;
		try(MemoryStack stack = MemoryStack.stackPush()) {
			IntBuffer w = stack.mallocInt(1);
			IntBuffer h = stack.mallocInt(1);
			IntBuffer c = stack.mallocInt(1);
			
			buffer = STBImage.stbi_load(filename, w, h, c, 4);
			if(buffer == null)
				throw new Exception("Image File " + filename + " not loaded " + STBImage.stbi_failure_reason());
			
			width = w.get();
			height = h.get();
		} catch (Exception e) {
			throw new RuntimeException(e);
		}

		int id = GL11.glGenTextures();
		textures.add(id);
		GL11.glBindTexture(GL11.GL_TEXTURE_2D, id);
		GL11.glPixelStorei(GL11.GL_UNPACK_ALIGNMENT, 1);
		GL11.glTexImage2D(GL11.GL_TEXTURE_2D, 0, GL11.GL_RGBA, width, height, 0, GL11.GL_RGBA, GL11.GL_UNSIGNED_BYTE, buffer);
		GL30.glGenerateMipmap(GL11.GL_TEXTURE_2D);
		STBImage.stbi_image_free(buffer);
		return id;
	}
	
	private int createVAO() {
		int id = GL30.glGenVertexArrays();
		VAOs.add(id);
		GL30.glBindVertexArray(id);
		return id;
	}
	
	private void storeIndicesBuffer(int[] indices) {
		int vbo = GL15.glGenBuffers();
		VBOs.add(vbo);
		GL15.glBindBuffer(GL15.GL_ELEMENT_ARRAY_BUFFER, vbo);
		IntBuffer buffer = Utils.storeDataInIntBuffer(indices);
		GL15.glBufferData(GL15.GL_ELEMENT_ARRAY_BUFFER, buffer, GL15.GL_STATIC_DRAW);
	}
	
	private void storeDataInAttribList(int attribNo, int vertexCount, float[] data) {
		int vbo = GL15.glGenBuffers();
		VBOs.add(vbo);
		GL15.glBindBuffer(GL15.GL_ARRAY_BUFFER, vbo);
		FloatBuffer buffer = Utils.storeDataInFloatBuffer(data);
		GL15.glBufferData(GL15.GL_ARRAY_BUFFER, buffer, GL15.GL_STATIC_DRAW);
		GL20.glVertexAttribPointer(attribNo, vertexCount, GL11.GL_FLOAT, false, 0, 0);
		GL15.glBindBuffer(GL15.GL_ARRAY_BUFFER, 0);
	}
	
	private void unbind() {
		GL30.glBindVertexArray(0);
	}
	
	public void cleanup() {
		for(int vao : VAOs)
			GL30.glDeleteVertexArrays(vao);
		for(int vbo : VBOs)
			GL30.glDeleteBuffers(vbo);
		for(int texture : textures)
			GL11.glDeleteTextures(texture);
	}
}