package com.redteam.atr3.screens;

import javax.swing.JPanel;

import com.redteam.atr3.Window;

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