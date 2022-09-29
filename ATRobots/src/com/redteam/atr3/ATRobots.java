package com.redteam.atr3;

public class ATRobots {
	public static final int WIDTH = 1280, HEIGHT = 720, FPS = 60;
	public static Window window = new Window(WIDTH, HEIGHT, FPS, "AT-Robots");
	
	// Game States
	public enum State
	{
	    START, RUNNING;
	}
	// Main function
	public static void main(String[] args) {
		window.create();
		
		while(!window.closed()) {
			if(window.isUpdating()) {
				window.update();
				
				window.swapBuffers();
			}
		}
		window.stop();
	}
}