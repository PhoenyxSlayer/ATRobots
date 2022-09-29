package com.redteam.atr3;

import java.nio.DoubleBuffer;

import org.lwjgl.BufferUtils;
import org.lwjgl.glfw.GLFW;
import org.lwjgl.glfw.GLFWVidMode;

public class Window	
{
	private int width, height;
    private String title;
    private double fps_cap, time, processedTime = 0;
    private long window;
    private boolean[] keys = new boolean[GLFW.GLFW_KEY_LAST];
    private boolean[] mouseButtons = new boolean[GLFW.GLFW_MOUSE_BUTTON_LAST];
    
    public Window(int width, int height, int fps, String title) {
    	this.width = width;
    	this.height = height;
    	this.title = title;
    	fps_cap = fps;
    }
    
    public void create() {
    	if (!GLFW.glfwInit()) {
    		System.err.println("ERROR: COULD NOT INITIALIZE GLFW");
    		System.exit(-1);
    	}
    	GLFW.glfwWindowHint(GLFW.GLFW_VISIBLE, GLFW.GLFW_FALSE);
    	GLFW.glfwWindowHint(GLFW.GLFW_RESIZABLE, GLFW.GLFW_FALSE);
    	
    	window = GLFW.glfwCreateWindow(width, height, title, 0, 0);
    	
    	if (window == 0) {
    		System.err.println("ERR: WINDOW COULD NOT BE CREATED");
    		System.exit(-1);
    	}
    	
    	GLFWVidMode videoMode = GLFW.glfwGetVideoMode(GLFW.glfwGetPrimaryMonitor());
    	
    	// MIGHT BE WRONG
    	GLFW.glfwSetWindowPos(window, (videoMode.width() - width) / 2, (videoMode.height() - height) / 2);
    	
    	GLFW.glfwShowWindow(window);
    	
    	time = getTime();
    }

    public boolean closed() {
    	return GLFW.glfwWindowShouldClose(window);
    }
    
    public void update() {
    	for (int i = 0; i < GLFW.GLFW_KEY_LAST; i++) keys[i] = isKeyDown(i);
    	for (int i = 0; i < GLFW.GLFW_MOUSE_BUTTON_LAST; i++) mouseButtons[i] = isMouseDown(i);
    	GLFW.glfwPollEvents();
    }
    
    public void stop() {
    	GLFW.glfwTerminate();
    }
    
    public void swapBuffers() {
    	GLFW.glfwSwapBuffers(window);
    }
    
    public double getTime() {
    	return (double) System.nanoTime() / (double) 1000000000;
    }
    
    public boolean isKeyDown(int keyCode) {
    	return GLFW.glfwGetKey(window, keyCode) == 1;
    }
    
    public boolean isMouseDown(int mouseButton) {
    	return GLFW.glfwGetMouseButton(window, mouseButton) == 1;
    }
    
    public boolean isKeyPressed(int keyCode) {
    	return isKeyDown(keyCode) && !keys[keyCode];
    }
    
    public boolean isKeyReleased(int keyCode) {
    	return !isKeyDown(keyCode) && keys[keyCode];
    }
    
    public boolean isMousePressed(int mouseButton) {
    	return isMouseDown(mouseButton) && !mouseButtons[mouseButton];
    }
    
    public boolean isMouseReleased(int mouseButton) {
    	return !isMouseDown(mouseButton) && mouseButtons[mouseButton];
    }
    
    public double getMouseX() {
    	DoubleBuffer buffer = BufferUtils.createDoubleBuffer(1);
    	GLFW.glfwGetCursorPos(window, buffer, null);
    	return buffer.get(0);
    }
    
    public double getMouseY() {
    	DoubleBuffer buffer = BufferUtils.createDoubleBuffer(1);
    	GLFW.glfwGetCursorPos(window, null, buffer);
    	return buffer.get(0);
    }
    
    public boolean isUpdating() {
    	double nextTIme = getTime();
    	double passedTime = nextTIme - time;
    	processedTime += passedTime;
    	time = nextTIme;
    	
    	while(processedTime > 1.0/fps_cap) {
    		processedTime -= 1.0/fps_cap;
    		return true;
    	}
    	return false;
    }
    
    public int getWidth() { return width; }
    
    public int getHeight() { return height; }
    
    public String getTitle() { return title; }
    
    public double getFPS() { return fps_cap; }
    
    public long getWindow() { return window; }
}