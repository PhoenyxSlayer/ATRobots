package com.redteam.atrobots;

import com.redteam.atrobots.screens.*;

public class ATRobots {
	
	private Window gameWindow;
	
	// Game States
	public enum State
	{
	    START, RUNNING;
	}
	
	// Resolution
	public static int getWidth() { return 1280; }
	public static int getHeight() { return 720; }
	
	// Main function
	public static void main(String[] args) {
		new ATRobots();
	}
	
	// Add All Game States
	public ATRobots() {
		gameWindow = new Window("ATRobots", getWidth(), getHeight(), this);
		gameWindow.addScreen(State.START, new StartScreen());
		gameWindow.addScreen(State.RUNNING, new RunningScreen());
		gameWindow.run();
	}
}