package com.redteam.atrobots;

import com.redteam.atrobots.screens.*;

public class ATRobots {
	
	private Window gameWindow;
	
	// Game States
	public enum State
	{
	    START, RUNNING, NO_GRAPHICS, SETTINGS, CLOSING;
	}
	
	// Resolution
	
	
	// Main function
	public static void main(String[] args) {
		new ATRobots();
	}
	
	// Add All Game States
	public ATRobots() {
		gameWindow = new Window("ATRobots", 1280, 720);
		gameWindow.addScreen(State.START, new StartScreen());
		gameWindow.addScreen(State.RUNNING, new RunningScreen());
		gameWindow.addScreen(State.NO_GRAPHICS, new NoGraphicsScreen());
		gameWindow.addScreen(State.SETTINGS, new SettingsScreen());
		gameWindow.addScreen(State.CLOSING, new ClosingScreen());
		gameWindow.run();
	}
}