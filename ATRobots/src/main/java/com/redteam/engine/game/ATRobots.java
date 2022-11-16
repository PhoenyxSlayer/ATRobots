package com.redteam.engine.game;

import com.redteam.engine.core.Engine;
import com.redteam.engine.core.Window;
import com.redteam.engine.core.entity.Robot;
import com.redteam.engine.core.gui.MainMenu;
import com.redteam.engine.utils.Consts;

public class ATRobots {
	private static Robot robotName = new Robot();
	private static Window window = new Window(Consts.TITLE, 1600, 900, true);
	private static TestGame game;
	
	// Main function
	public static void main(String[] args) throws Exception{
		new MainMenu(Consts.TITLE, 800, 450);
	}
	
	public static void run() throws Exception {
		if(robotName.compile()) {
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