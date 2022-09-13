package com.redteam.atrobots.screens;

import java.awt.Color;
import java.awt.Dimension;

import javax.swing.JPanel;

import com.redteam.atrobots.ATRobots;
import com.redteam.atrobots.Window;

public class NoGraphicsScreen implements Screen {
	private Window w;
	private JPanel p;
	
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
	    	p = new JPanel();
			p.setSize(ATRobots.getWidth(),ATRobots.getHeight());
			p.setPreferredSize(new Dimension(ATRobots.getWidth(),ATRobots.getHeight()));
			p.setMaximumSize(new Dimension(ATRobots.getWidth(),ATRobots.getHeight()));
			p.setBackground(Color.YELLOW);
			p.setLayout(null);
			
			w.add(p);
			p.setVisible(true);
	    }
	    @Override
	    public void handleInput()
	    {
	        // TODO
	    }
}