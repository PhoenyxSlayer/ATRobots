package com.redteam.atrobots.screens;

import javax.swing.JPanel;

import com.redteam.atrobots.Window;

public class StartScreen implements Screen {
	private JPanel p;
	private Window w;
		
		@Override
		public void grabWindow(Window w) { this.w = w; }
	    
		@Override
	    public void update()
	    {
	    	// TODO
	    }
	
	    @Override
	    public void draw()
	    {
	    	// TODO
	    }
	
	    @Override
	    public void handleInput()
	    {
	    	// TODO
	    }
}