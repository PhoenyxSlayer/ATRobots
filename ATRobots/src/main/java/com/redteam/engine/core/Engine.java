package com.redteam.engine.core;

import javax.sound.sampled.*;
import java.io.File;
import java.io.IOException;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;

import org.lwjgl.glfw.GLFW;
import org.lwjgl.glfw.GLFWErrorCallback;

import com.redteam.engine.game.ATRobots;
import com.redteam.engine.game.TestGame;
import com.redteam.engine.utils.Consts;


public class Engine {
	
	public static final long NANOSECOND = 1000000000L;
	
	private static int fps;
	public static final float framerate = 1000;
	private static float frametime = 1.0f / framerate;
	public static float currentFrameTime = 0;
	private boolean isRunning;
	
	private Window window;
	private MouseInput mouseInput;
	private GLFWErrorCallback errorCallback;
	private ILogic gameLogic;
	
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
		float x = 0;
		float z = 0;
		float y = 0;
		this.isRunning = true;
		int frames = 0;
		long frameCounter = 0;
		// Returns the current value of the system timer, in nanoseconds.
		long lastTime = System.nanoTime();
		double unprocessedTime = 0;
		
			playSound("tank.wav");
			
		while(isRunning) {	
			//if((window.isKeyPressed(GLFW.GLFW_KEY_W)) || (window.isKeyPressed(GLFW.GLFW_KEY_A)) || (window.isKeyPressed(GLFW.GLFW_KEY_S)) || (window.isKeyPressed(GLFW.GLFW_KEY_D))){
				//playSound("TankRunning.wav");
				//}
			
			boolean render = false;
			long startTime = System.nanoTime();
			long passedTime = startTime - lastTime;
			lastTime = startTime;
			
			unprocessedTime += passedTime / (double)NANOSECOND;
			frameCounter += passedTime;
			
			input();
			
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
		//Collision Detection
			x = TestGame.getPositionX(x);
			z = TestGame.getPositionZ(z);
			TestGame.tankDirect(x, z);
			if(x > 400){
					if((window.isKeyPressed(GLFW.GLFW_KEY_W) && window.isKeyPressed(GLFW.GLFW_KEY_D))) {
						y = 135;
					}
					else if((window.isKeyPressed(GLFW.GLFW_KEY_S) && window.isKeyPressed(GLFW.GLFW_KEY_D))){
						y = 45;
					}
					else if(window.isKeyPressed(GLFW.GLFW_KEY_D)){
						y = 90;
					}
					//playSound("bloop_x.wav");
				 	TestGame.setTankPos(x - 0.2f, z, y);
				}
				else if(x < -400) {
					if((window.isKeyPressed(GLFW.GLFW_KEY_W) && window.isKeyPressed(GLFW.GLFW_KEY_A))) {
						y = 225;
					}
					else if((window.isKeyPressed(GLFW.GLFW_KEY_S) && window.isKeyPressed(GLFW.GLFW_KEY_A))){
						y = 315;
					}
					else if(window.isKeyPressed(GLFW.GLFW_KEY_A)){
						y = 270;
					}
				//	playSound("bloop_x.wav");
				 	TestGame.setTankPos(x + 0.2f, z, y);
				}
				else if(z > 0) {
					if((window.isKeyPressed(GLFW.GLFW_KEY_S) && window.isKeyPressed(GLFW.GLFW_KEY_D))) {
						y = 45;
					}
					else if((window.isKeyPressed(GLFW.GLFW_KEY_S) && window.isKeyPressed(GLFW.GLFW_KEY_A))){
						y = 315;
					}
					else if(window.isKeyPressed(GLFW.GLFW_KEY_S)){
						y = 0;
					}
				//	playSound("bloop_x.wav");
				 	TestGame.setTankPos(x, z - 0.2f, y);
				}
				else if(z < -800) {
					if((window.isKeyPressed(GLFW.GLFW_KEY_W) && window.isKeyPressed(GLFW.GLFW_KEY_A))) {
						y = 225;
					}
					else if((window.isKeyPressed(GLFW.GLFW_KEY_W) && window.isKeyPressed(GLFW.GLFW_KEY_D))){
						y = 135;
					}
					else if(window.isKeyPressed(GLFW.GLFW_KEY_W)){
						y = 180;
					}
				//	playSound("bloop_x.wav");
				 	TestGame.setTankPos(x, z + 0.2f, y);
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
	
	void playSound(String soundFile) throws IOException, UnsupportedAudioFileException, LineUnavailableException {
	    File f = new File(soundFile);
	    AudioInputStream audioIn = AudioSystem.getAudioInputStream(f.toURI().toURL());  
	    Clip clip = AudioSystem.getClip();
	    clip.open(audioIn);
	   // if(!clip.isPlaying()) {
	    clip.loop(Clip.LOOP_CONTINUOUSLY);
	}
}