package com.redteam.atrobots.screens;

import java.awt.Color;
import java.awt.Dimension;

import javax.swing.JPanel;

import com.redteam.atrobots.Window;

public class SettingsScreen implements Screen {
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
			p.setSize(w.getWidth(),w.getHeight());
			p.setPreferredSize(new Dimension(w.getWidth(),w.getHeight()));
			p.setMaximumSize(new Dimension(w.getWidth(),w.getHeight()));
			p.setBackground(Color.GREEN);
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