package com.redteam.engine.game;

import java.io.BufferedReader;
import java.io.InputStreamReader;

import com.redteam.engine.core.Engine;
import com.redteam.engine.core.Window;
import com.redteam.engine.utils.Consts;
import com.redteam.engine.core.entity.Robot;

public class ATRobots {
	private static Robot robotName;
	private static Window window;
	private static Window mainMenuWindow;
	private static Engine engine;
	private static TestGame game;
	static BufferedReader reader = new BufferedReader(
            new InputStreamReader(System.in));

	// Game States
	public enum State
	{
	    START, RUNNING;
	}
	
	// Current Game State
	public static State state = State.START;
	
	// Main function
	public static void main(String[] args) throws Exception {
		
		run();
				
	}
	
	public static void run() throws Exception
	{
	    switch(state)
	    {
	    case START:
	    	String name = reader.readLine();
	    	if(name.trim().equals("run")) {
		        state = State.RUNNING;
		        run();
	    	}
	        break;
	    case RUNNING:
	    	robotName = new Robot();
	    	if(robotName.compile()) {
		    	window = new Window(Consts.TITLE, 1600, 900, true);
				game = new TestGame();
				Engine engine = new Engine();
				engine.start();
	    	}
	        break;
	    default:
	        throw new RuntimeException("Unknown state: " + state);
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