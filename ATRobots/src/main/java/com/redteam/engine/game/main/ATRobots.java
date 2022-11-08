package com.redteam.engine.game.main;

import com.redteam.engine.core.Engine;
import com.redteam.engine.core.Window;
import com.redteam.engine.game.debug.DebugMode;
import com.redteam.engine.utils.Consts;
import com.redteam.engine.game.entities.Robot;

public class ATRobots {
	private static final Robot robotName = new Robot();
	public static Window window;
	private static DebugMode game;


	// Main function
	public static void main(String[] args) throws Exception {
		//if(robotName.getFile()) {
			window = new Window(Consts.TITLE, 1600, 900, true);
			game = new DebugMode();
			Engine engine = new Engine();
			engine.start();
		//}
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