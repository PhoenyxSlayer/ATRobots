package com.redteam.engine.core;

import com.redteam.engine.utils.Consts;
import org.joml.Matrix4f;
import org.lwjgl.glfw.GLFW;
import org.lwjgl.glfw.GLFWErrorCallback;
import org.lwjgl.glfw.GLFWVidMode;
import org.lwjgl.openal.AL;
import org.lwjgl.openal.ALC;
import static org.lwjgl.openal.ALC10.*;
import org.lwjgl.openal.ALCCapabilities;
import org.lwjgl.openal.ALCapabilities;
import org.lwjgl.opengl.GL;
import org.lwjgl.opengl.GL11;

import org.lwjgl.system.MemoryUtil;

//https://www.glfw.org/docs/latest/window_guide.html
public class Window	
{
	private final String title;
	
	private int width, height;
	private long window,
				 audioContext,
				 audioDevice;
	
	private boolean resize, vSync;
	
	private final Matrix4f projectionMatrix;
	
	// Window Constructor
	public Window(String title, int width, int height, boolean vSync) {
		this.title = title; this.width = width; this.height = height; this.vSync = vSync;
		projectionMatrix = new Matrix4f();
		
	}
	
	public void init() {
		// Gets information in case of an error
		GLFWErrorCallback.createPrint(System.err).set();
		
		// If GLFW has yet to be initialized
		if(!GLFW.glfwInit())
			throw new IllegalStateException("ERR: Unable to initialize GLFW");
		
		GLFW.glfwDefaultWindowHints();
		// Makes window not yet visible
		GLFW.glfwWindowHint(GLFW.GLFW_VISIBLE, GL11.GL_FALSE);
		// Makes window resizable
		GLFW.glfwWindowHint(GLFW.GLFW_RESIZABLE, GL11.GL_TRUE);
		// Specifies the client API version that the created context must be compatible with
		GLFW.glfwWindowHint(GLFW.GLFW_CONTEXT_VERSION_MAJOR, 3);
		GLFW.glfwWindowHint(GLFW.GLFW_CONTEXT_VERSION_MINOR, 2);
		// Specifies which OpenGL profile to create the context for
		GLFW.glfwWindowHint(GLFW.GLFW_OPENGL_PROFILE, GLFW.GLFW_OPENGL_CORE_PROFILE);
		// Specifies whether the OpenGL context should be forward-compatible
		GLFW.glfwWindowHint(GLFW.GLFW_OPENGL_FORWARD_COMPAT, GL11.GL_TRUE);
		
		// Incase width or height is equal to zero
		boolean maximised = false;
		if( (width == 0) || (height == 0) ) {
			width = 100; height = 100;
			GLFW.glfwWindowHint(GLFW.GLFW_MAXIMIZED, GLFW.GLFW_TRUE);
			maximised = true;
		}
		
		window = GLFW.glfwCreateWindow(width, height, title, MemoryUtil.NULL, MemoryUtil.NULL);
		// If the window is empty
		if(window == MemoryUtil.NULL)
			throw new RuntimeException("ERR: Failed to create GLFW Window");
		
		// Gets updated values incase windows is resized
		GLFW.glfwSetFramebufferSizeCallback(window, (window, width, height) -> {
			this.width = width;
			this.height = height;
			this.setResize(true);
		});
		
		// Centers the window
		if(maximised)
			GLFW.glfwMaximizeWindow(window);
		else {
			GLFWVidMode vidMode = GLFW.glfwGetVideoMode(GLFW.glfwGetPrimaryMonitor());
			GLFW.glfwSetWindowPos(window, (vidMode.width() - width) / 2,
										  (vidMode.height() - height) / 2);
		}
		
		// Makes the OpenGL context of the specified window current on the calling thread
		GLFW.glfwMakeContextCurrent(window);
		
		// Syncs to monitors refresh rate
		if(isvSync())
			GLFW.glfwSwapInterval(1);
		
		// Makes window visible
		GLFW.glfwShowWindow(window);
		
		// Initialize the audio device
		String defaultDeviceName = alcGetString(0, ALC_DEFAULT_DEVICE_SPECIFIER);
		audioDevice = alcOpenDevice(defaultDeviceName);
		
		int[] attributes = {0};
		audioContext = alcCreateContext(audioDevice, attributes);
		alcMakeContextCurrent(audioContext);
		
		ALCCapabilities alcCapabilities = ALC.createCapabilities(audioDevice);
		ALCapabilities alCapabilities = AL.createCapabilities(alcCapabilities);
		
		if(!alCapabilities.OpenAL10) {
			assert false : "Audio library not supported.";
		}
		
		/* LWJGL detects the context that is current in the current thread,
		 * creates the GLCapabilities instance and makes the OpenGL
		 * bindings available for use.
		 */
		GL.createCapabilities();
		
		// Window color to black
		GL11.glClearColor(0.0f, 0.0f, 0.0f, 0.0f);
		
		// Tests the depth value of a fragment against the content of the depth buffer
		GL11.glEnable(GL11.GL_DEPTH_TEST);
		/*
		 * Once the fragment shader has processed the fragment a
		 * so called stencil test is executed that, just like the
		 * depth test, has the option to discard fragments.
		 */
		GL11.glEnable(GL11.GL_STENCIL_TEST);
		// Insides of objects won't be rendered
		GL11.glEnable(GL11.GL_CULL_FACE);
		GL11.glCullFace(GL11.GL_BACK);
	}
	
	public void update() {
		GLFW.glfwSwapBuffers(window);
		// Tells OpenGL to start rendering all the objects put in a queue
		GLFW.glfwPollEvents();
		return;
	}
	
	public void cleanup() {
		alcDestroyContext(audioContext);
		alcCloseDevice(audioDevice);
		GLFW.glfwDestroyWindow(window);
		return;
	}
	
	public void setClearColour(float r, float g, float b, float a) {
		GL11.glClearColor(r, g, b, a);
		return;
	}
	
	public boolean isKeyPressed(int keycode) {
		return GLFW.glfwGetKey(window, keycode) == GLFW.GLFW_PRESS;
	}
	
	public boolean windowShouldClose() {
		return GLFW.glfwWindowShouldClose(window);
	}
	
	public boolean isResize() {
		return resize;
	}
	
	public String getTitle() {
		return title;
	}
	
	public void setTitle(String title) {
		GLFW.glfwSetWindowTitle(window, title);
	}
	
	public boolean isvSync() {
		return vSync;
	}
	
	public void setResize(boolean resize) {
		this.resize = resize;
		return;
	}
	
	public int getWidth() {
		return width;
	}
	
	public int getHeight() {
		return height;
	}
	
	public long getWindowHandle() {
		return window;
	}
	
	public Matrix4f getProjectionMatrix() {
		return projectionMatrix;
	}
	
	public Matrix4f updateProjectionMatrix() {
		float aspectRatio = (float) width / height;
		return projectionMatrix.setPerspective(Consts.FOV, aspectRatio, Consts.Z_NEAR, Consts.Z_FAR);
	}
	
	public Matrix4f updateProjectionMatrix(Matrix4f matrix, int width, int height) {
		float aspectRatio = (float) width / height;
		return matrix.setPerspective(Consts.FOV, aspectRatio, Consts.Z_NEAR, Consts.Z_FAR);
	}
}