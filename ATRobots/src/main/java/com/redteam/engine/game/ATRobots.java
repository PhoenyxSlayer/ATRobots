package com.redteam.engine.game;

import com.redteam.engine.core.Engine;
import com.redteam.engine.core.Window;
import com.redteam.engine.utils.Consts;
import com.redteam.engine.core.entity.Robot;

public class ATRobots {
	private static final Robot robotName = new Robot();
	public static Window window;
	private static TestGame game;


	// Main function
	public static void main(String[] args) throws Exception {
		//if(robotName.getFile()) {
			window = new Window(Consts.TITLE, 1600, 900, true);
			game = new TestGame();
			Engine engine = new Engine();
			engine.start();
		//}
	}
		
	public static Window getWindow() { 
		return window;
	}
	
	public static TestGame getGame() {
		return game;
	}

	@SuppressWarnings("unused")
	public Robot getRobot() {
		return robotName;
	}
}