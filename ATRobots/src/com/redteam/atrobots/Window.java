package com.redteam.atrobots;

import java.util.HashMap;
import java.util.Map;

import javax.swing.JFrame;
import com.redteam.atrobots.ATRobots.*;
import com.redteam.atrobots.screens.Screen;

public class Window extends JFrame{
	
	private static final long serialVersionUID = 6286634599679800388L;
	
	private int width, height;
	
	// Begin state with Start Screen
	private State state = State.START;
	
	// Hash maps for the Screen States
	private Map<State, Screen> screens = new HashMap<>();
	
	// JFrame Window Creation
	public Window(String title, int width, int height) {
		super (title);
		this.width = width;
		this.height = height;
		// JFrame Formatting
		setSize(width, height);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setVisible(true);
		setResizable(false);
		setLocationRelativeTo(null);
	}
	
	public int getWidth() { return width; }
	public int getHeight() { return height; }
	
	// Adds Screens Classes w/ what state their associated w/ to HashMap
	public void addScreen(State state, Screen screen) {
        screens.put(state, screen);
    }
	
	public void updateScreen(State newState) {
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
