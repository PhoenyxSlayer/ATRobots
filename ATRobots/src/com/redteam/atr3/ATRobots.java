package com.redteam.atr3;

import java.io.IOException;

public class ATRobots {
	private static Robot robotName = new Robot();
	public static final int WIDTH = 1280, HEIGHT = 720, FPS = 60;
	public static Window window = new Window(WIDTH, HEIGHT, FPS, "AT-Robots");
	
	// Game States
	public enum State
	{
	    START, RUNNING;
	}
	// Main function
	public static void main(String[] args) throws IOException {
		robotName.getName();
		if(robotName.equals() == true) {
			robotName.referenceName();
			window.create();
			
			while(!window.closed()) {
				if(window.isUpdating()) {
					window.update();
					
					window.swapBuffers();
				}
			}
			window.stop();
		}
		else {
			System.err.println("COULD NOT FIND FILE");
		}
	}
	
	public Robot getRobot() {
		return robotName;
	}
}