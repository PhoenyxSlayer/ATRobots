package com.redteam.engine.game.main;

import com.redteam.engine.core.Engine;
import com.redteam.engine.core.Window;
import com.redteam.engine.core.entity.Robot;
import com.redteam.engine.game.MainMenu;
import com.redteam.engine.game.debug.DebugMode;
import com.redteam.engine.utils.Constants;

public class ATRobots {
	private static Robot robotName = new Robot();
	public static Window window = new Window(Constants.TITLE, 1600, 900, true);
	private static DebugMode game;


	// Main function
	public static void main(String[] args) throws Exception{
		new MainMenu(Constants.TITLE, 800, 450);
	}

	public static void run() throws Exception {
			game = new DebugMode();
			Engine engine = new Engine();
			engine.start();
	}
		
	public static Window getWindow() { 
		return window;
	}
	
	public static DebugMode getGame() {
		return game;
	}

	@SuppressWarnings("unused")
	public Robot getRobot() {
		return robotName;
	}
}