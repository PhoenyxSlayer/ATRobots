package com.redteam.atrobots;

import java.util.HashMap;
import java.util.Map;

import javax.swing.JFrame;
import com.redteam.atrobots.ATRobots.*;
import com.redteam.atrobots.screens.Screen;

public class Window extends JFrame{
	
	private static final long serialVersionUID = 6286634599679800388L;
	
	// Begin state with Start Screen
	private State state = State.START;
	
	// Hash maps for the Screen States
	
	// SOURCE: https://stackoverflow.com/questions/34560139/how-can-i-create-a-start-screen-for-my-java-game
	private Map<State, Screen> screens = new HashMap<>();
	
	// JFrame Window Creation
	public Window(String title, int width, int height, ATRobots main) {
		super (title);
		// JFrame Formatting
		setSize(width, height);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setVisible(true);
		setResizable(false);
		setLocationRelativeTo(null);
	}
	
	// Adds Screens Classes w/ what state their associated w/ to HashMap
	public void addScreen(State state, Screen screen) {
        screens.put(state, screen);
    }
	
	// Use this function to change the game state
	public void updateState(State newState) {
		revalidate();
   	 	repaint();
		state = newState;
		run();
	}
	
	// Grabs current state and runs through the three functions consistently
	public void run() {
		Screen screen = screens.get(state);
		screen.grabWindow(this);
		screen.handleInput();
		screen.update();
        screen.draw();
	}
}
