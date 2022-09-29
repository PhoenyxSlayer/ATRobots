package com.redteam.atr3;

import org.joml.Vector2f;
import org.lwjgl.glfw.GLFW;

import com.redteam.atr3.Window;
import com.redteam.atr3.Camera;
import com.redteam.atr3.Model;
import com.redteam.atr3.Shader;
import com.redteam.atr3.assets.Arena;

public class Player {
	private Model model;
	//private Texture texture;
	
	public Player() {
		float[] vertices = new float[] {
				-1f, 1f, 0, //Top Left
				1f, 1f, 0, //Top Right
				1f, -1f, 0, // Bottom Right
				-1f, -1f, 0, //Bottom Left
		};
		
		float[] texture = new float[] {
				0,0,
				1,0,
				1,1,
				0,1,
		};
		
		int[] indices = new int[] {
				0,1,2,
				2,3,0
		};
		
		model = new Model(vertices, texture, indices);
		//this.texture = new Texture("test.png");
	}
	
	public void update(float delta, Window window, Camera camera, Arena arena) {
		Vector2f move = new Vector2f();
		
		/*// Move Left
		if (window.getInput().isKeyDown(GLFW.GLFW_KEY_A)) move.add(-10 * delta, 0);
		
		// Move Right
		if (window.getInput().isKeyDown(GLFW.GLFW_KEY_D)) move.add(10 * delta, 0);
		
		// Move Up
		if (window.getInput().isKeyDown(GLFW.GLFW_KEY_W)) move.add(0, 10 * delta);
	
		// Move Down
		if (window.getInput().isKeyDown(GLFW.GLFW_KEY_S)) move.add(0, -10 * delta);
		*/ 
	}
	
	public void render(Shader shader, Camera camera) {
		shader.bind();
		shader.setUniform("sampler", 0);
		shader.setUniform("projection", camera.getProjection());
		//texture.bind(0);
		model.render();
	}
}