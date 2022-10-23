package com.redteam.engine.core;

import java.io.IOException;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;

import org.lwjgl.glfw.GLFW;
import org.lwjgl.glfw.GLFWErrorCallback;

import com.redteam.engine.game.ATRobots;
import com.redteam.engine.utils.Consts;


public class Engine {
	
	public static final long NANOSECOND = 1000000000L;
	
	private static int fps;
	public static final float framerate = Consts.FPS;
	private static float frametime = 1.0f / framerate;
	public static float currentFrameTime = 0;
	private boolean isRunning;
	
	private Window window;
	private MouseInput mouseInput;
	private GLFWErrorCallback errorCallback;
	private ILogic gameLogic;
	
	private static double lastFrame;
	public static double delta = getDelta();
	
	private void init() throws Exception {
		GLFW.glfwSetErrorCallback(errorCallback = GLFWErrorCallback.createPrint(System.err));
		window = ATRobots.getWindow();
		gameLogic = ATRobots.getGame();
		mouseInput = new MouseInput();
		window.init();
		gameLogic.init();
		mouseInput.init();
	}
	
	public void start() throws Exception {
		init();
		if(isRunning)
			return;
		run();
	}
	
	public void run() throws IOException, UnsupportedAudioFileException, LineUnavailableException {
		this.isRunning = true;
		int frames = 0;
		long frameCounter = 0;
		// Returns the current value of the system timer, in nanoseconds.
		long lastTime = System.nanoTime();
		double unprocessedTime = 0;
			
		while(isRunning) {	
			delta = getDelta();
			input();
			boolean render = false;
			long startTime = System.nanoTime();
			long passedTime = startTime - lastTime;
			lastTime = startTime;
			
			unprocessedTime += passedTime / (double)NANOSECOND;
			frameCounter += passedTime;
			
			while(unprocessedTime > frametime) {
				
				render = true;
				unprocessedTime -= frametime;
				
				if(window.windowShouldClose())
					stop();
				
				if(frameCounter >= NANOSECOND) {
					setFps(frames);
					currentFrameTime = 1.0f/fps;
					window.setTitle(Consts.TITLE + " Engine FPS: " + getFps());
					frames = 0;
					frameCounter = 0;
				}
			}
			
			if(render) {
				update(frametime);
				render();
				frames++;
			}
		}
		cleanup();	
	}
	
	private void stop() {
		if(!isRunning)
			return;
		isRunning = false;
	}
	
	private void input() {
		mouseInput.input();
		gameLogic.input();
	}
	
	private void render() {
		gameLogic.render();
		window.update();
	}
	
	private void update(float interval) { gameLogic.update(interval, mouseInput); }
	
	private void cleanup() {
		window.cleanup();
		gameLogic.cleanup();
		errorCallback.free();
		GLFW.glfwTerminate();
	}
	
	public static int getFps() {
		return fps;
	}
	
	public static void setFps(int fps) {
		Engine.fps = fps;
	}
	
	private static double getDelta() {
		double currentTime = GLFW.glfwGetTime();
		double delta = (double) currentTime - (double) lastFrame;
		lastFrame = GLFW.glfwGetTime();
		return delta;
	}
}