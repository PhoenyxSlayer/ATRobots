package com.redteam.engine.game;

import com.redteam.engine.core.Engine;
import com.redteam.engine.core.Window;
import com.redteam.engine.utils.Consts;
import com.redteam.engine.core.entity.Robot;

public class ATRobots {
	private static Robot robotName = new Robot();
	private static Window window;
	private static TestGame game;
	

	// Game States
	public enum State
	{
	    START, RUNNING;
	}
	
	// Main function
	public static void main(String[] args) throws Exception {
			if(robotName.getFile()) {
			window = new Window(Consts.TITLE, 1600, 900, false);
			game = new TestGame();
			Engine engine = new Engine();
			engine.start();
		}			
	}
		
	public static Window getWindow() { 
		return window;
	}
	
	public static TestGame getGame() {
		return game;
	}
	
	public Robot getRobot() {
		return robotName;
	}
}