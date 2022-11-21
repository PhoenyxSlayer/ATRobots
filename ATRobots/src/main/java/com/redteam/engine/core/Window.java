package com.redteam.engine.core;

import static org.lwjgl.openal.ALC10.ALC_DEFAULT_DEVICE_SPECIFIER;
import static org.lwjgl.openal.ALC10.alcCloseDevice;
import static org.lwjgl.openal.ALC10.alcCreateContext;
import static org.lwjgl.openal.ALC10.alcDestroyContext;
import static org.lwjgl.openal.ALC10.alcGetString;
import static org.lwjgl.openal.ALC10.alcMakeContextCurrent;
import static org.lwjgl.openal.ALC10.alcOpenDevice;

import org.joml.Matrix4f;
import org.lwjgl.glfw.*;
import org.lwjgl.openal.AL;
import org.lwjgl.openal.ALC;
import org.lwjgl.openal.ALCCapabilities;
import org.lwjgl.openal.ALCapabilities;
import org.lwjgl.opengl.GL;
import org.lwjgl.opengl.GL11;
import org.lwjgl.system.MemoryUtil;

import com.redteam.engine.core.rendering.image_parser;
import com.redteam.engine.utils.Constants;

import imgui.ImGui;
import imgui.gl3.ImGuiImplGl3;
import imgui.glfw.ImGuiImplGlfw;

public class Window {
	public final ImGuiImplGlfw imGuiGlfw = new ImGuiImplGlfw();
	public final ImGuiImplGl3 imGuiGl3 = new ImGuiImplGl3();
	
	private String glslVersion = null;
	private final String title;
	private long windowPtr,
				 audioContext,
				 audioDevice;
	
	private int width, height;
	
	private final boolean vSync;
	private boolean resize;
	
	private final Matrix4f projectionMatrix;
	
	public Window(String title, int width, int height, boolean vSync) {
		this.title = title; this.width = width; this.height = height; this.vSync = vSync;
		projectionMatrix = new Matrix4f();
	}
	
	public void init() {
		initWindow();
		initImGui();
		imGuiGlfw.init(windowPtr, true);
		imGuiGl3.init(glslVersion);
	}
	
	public void cleanup() {
		imGuiGl3.dispose();
		imGuiGlfw.dispose();
		ImGui.destroyContext();
		Callbacks.glfwFreeCallbacks(windowPtr);
		alcDestroyContext(audioContext);
		alcCloseDevice(audioDevice);
		GLFW.glfwDestroyWindow(windowPtr);
	}
	
	private void initWindow() {
		// Gets information in case of an error
		GLFWErrorCallback.createPrint(System.err).set();

		// If GLFW has yet to be initialized
		if(!GLFW.glfwInit())
			throw new IllegalStateException("ERR: Unable to initialize GLFW");
		
		GLFW.glfwDefaultWindowHints();
		GLFW.glfwWindowHint(GLFW.GLFW_VISIBLE, GL11.GL_FALSE);
		// Makes window resizable
		GLFW.glfwWindowHint(GLFW.GLFW_RESIZABLE, GL11.GL_FALSE);

		// https://en.wikipedia.org/wiki/OpenGL_Shading_Language
		glslVersion = "#version 150";
		GLFW.glfwWindowHint(GLFW.GLFW_CONTEXT_VERSION_MAJOR, 3);
		GLFW.glfwWindowHint(GLFW.GLFW_CONTEXT_VERSION_MINOR, 2);
		
		// Specifies which OpenGL profile to create the context for
		GLFW.glfwWindowHint(GLFW.GLFW_OPENGL_PROFILE, GLFW.GLFW_OPENGL_CORE_PROFILE);
		
		// Specifies whether the OpenGL context should be forward-compatible
		GLFW.glfwWindowHint(GLFW.GLFW_OPENGL_FORWARD_COMPAT, GL11.GL_TRUE);
		
		// Encase width or height is equal to zero
		boolean maximised = false;
		if( (width == 0) || (height == 0) ) {
			width = 100; height = 100;
			GLFW.glfwWindowHint(GLFW.GLFW_MAXIMIZED, GLFW.GLFW_TRUE);
			maximised = true;
		}
		
		windowPtr = GLFW.glfwCreateWindow(width, height, title, MemoryUtil.NULL, MemoryUtil.NULL);
		
		if(windowPtr == MemoryUtil.NULL)
			throw new RuntimeException("ERR: Failed to create GLFW Window");
		
		GLFW.glfwSetFramebufferSizeCallback(windowPtr, (window, width, height) -> {
			this.width = width;
			this.height = height;
			this.setResize(false);
		});

		// Centers the window
		if(maximised)
			GLFW.glfwMaximizeWindow(windowPtr);
		else {
			GLFWVidMode vidMode = GLFW.glfwGetVideoMode(GLFW.glfwGetPrimaryMonitor());
			assert vidMode != null;
			GLFW.glfwSetWindowPos(windowPtr, (vidMode.width() - width) / 2,
												  (vidMode.height() - height) / 2);
		}
		
		// Makes the OpenGL context of the specified window current on the calling thread
		GLFW.glfwMakeContextCurrent(windowPtr);
		
		// Syncs to monitors refresh rate
		if(isvSync())
			GLFW.glfwSwapInterval(1);
	
		// Makes window visible
		GLFW.glfwShowWindow(windowPtr);
		
		// Initialize the audio device
		String defaultDeviceName = alcGetString(0, ALC_DEFAULT_DEVICE_SPECIFIER);
		audioDevice = alcOpenDevice(defaultDeviceName);
				
		int[] attributes = {0};
		audioContext = alcCreateContext(audioDevice, attributes);
		alcMakeContextCurrent(audioContext);
				
		ALCCapabilities alcCapabilities = ALC.createCapabilities(audioDevice);
		ALCapabilities alCapabilities = AL.createCapabilities(alcCapabilities);

		assert alCapabilities.OpenAL10 : "Audio library not supported.";
		
		/* LWJGL detects the context that is current in the current thread,
		 * creates the GLCapabilities instance and makes the OpenGL
		 * bindings available for use.
		 */
		GL.createCapabilities();
		GL11.glClearColor(0.0f, 0.0f, 0.0f, 0.0f);
		
		// Tests the depth value of a fragment against the content of the depth buffer
		GL11.glEnable(GL11.GL_DEPTH_TEST);
		
		/*
		 * Once the fragment shader has processed the fragment a
		 * so-called stencil test is executed that, just like the
		 * depth test, has the option to discard fragments.
		 */
		GL11.glEnable(GL11.GL_STENCIL_TEST);
		// Insides of objects won't be rendered
		GL11.glEnable(GL11.GL_CULL_FACE);
		GL11.glCullFace(GL11.GL_BACK);
		
	}
	
	private void initImGui() {
		ImGui.createContext();
	}
	
	public void update() {
	}
	
	public void setResize(boolean resize) {
		this.resize = resize;
	}
	
	public boolean isvSync() {
		return vSync;
	}

	@SuppressWarnings("unused")
	public void setClearColour(float r, float g, float b, float a) {
		GL11.glClearColor(r, g, b, a);
	}
	
	public boolean isKeyPressed(int keycode) {
		return GLFW.glfwGetKey(windowPtr, keycode) == GLFW.GLFW_PRESS;
	}

	@SuppressWarnings("unused")
	public boolean isKeyReleased(int keycode) {
		return GLFW.glfwGetKey(windowPtr, keycode) == GLFW.GLFW_RELEASE;
	}
	
	public boolean windowShouldClose() {
		return GLFW.glfwWindowShouldClose(windowPtr);
	}
	
	public boolean isResize() {
		return resize;
	}

	@SuppressWarnings("unused")
	public String getTitle() {
		return title;
	}
	
	public void setTitle(String title) {
		GLFW.glfwSetWindowTitle(windowPtr, title);
	}
	
	public int getWidth() {
		return width;
	}
	
	public int getHeight() {
		return height;
	}
	
	public long getWindowHandle() {
		return windowPtr;
	}
	@SuppressWarnings("unused")
	public Matrix4f getProjectionMatrix() {
		return projectionMatrix;
	}
	
	public Matrix4f updateProjectionMatrix() {
		float aspectRatio = (float) width / height;
		return projectionMatrix.setPerspective(Constants.FOV, aspectRatio, Constants.Z_NEAR, Constants.Z_FAR);
	}

	public long getPrimaryMonitor() {
		return GLFW.glfwGetPrimaryMonitor();
	}

	@SuppressWarnings("unused")
	public Matrix4f updateProjectionMatrix(Matrix4f matrix, int width, int height) {
		float aspectRatio = (float) width / height;
		return matrix.setPerspective(Constants.FOV, aspectRatio, Constants.Z_NEAR, Constants.Z_FAR);
	}

	public void updateLogo(image_parser icon) {
		GLFWImage iconGLFW = GLFWImage.malloc();
		GLFWImage.Buffer iconBF = GLFWImage.malloc(1);
        iconGLFW.set(icon.get_width(), icon.get_height(), icon.get_image());
        iconBF.put(0, iconGLFW);
        GLFW.glfwSetWindowIcon(this.getWindowHandle(), iconBF);
	}
}