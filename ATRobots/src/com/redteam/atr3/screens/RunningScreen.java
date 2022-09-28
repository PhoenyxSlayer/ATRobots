package com.redteam.atr3.screens;

import java.awt.Color;
import java.awt.Dimension;

import javax.swing.JPanel;

import com.redteam.atr3.Window;

public class RunningScreen implements Screen {
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
	    	p = new JPanel();
			p.setSize(w.getWidth(),w.getHeight());
			p.setPreferredSize(new Dimension(w.getWidth(),w.getHeight()));
			p.setMaximumSize(new Dimension(w.getWidth(),w.getHeight()));
			p.setBackground(Color.black);
			p.setLayout(null);
			
			w.add(p);
	    }
	
	    @Override
	    public void handleInput()
	    {
	    	// TODO
	    }
}