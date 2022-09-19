package com.redteam.atrobots.screens;

import java.awt.Dimension;
import java.awt.Image;
import java.awt.event.ActionEvent;
import javax.swing.Timer;

import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;

import com.redteam.atrobots.Window;

public class ClosingScreen implements Screen {
	private JPanel p;
	private Window w;
	
		@Override
		public void grabWindow(Window w) { this.w = w; }
		
		@Override // Closes out application after 3 seconds
	    public void update(){ new Timer(3000, (ActionEvent e) -> { System.exit(1); }).start(); }
	
	    @Override
	    public void draw()
	    {
	    	p = new JPanel();
			p.setSize(w.getWidth(),w.getHeight());
			p.setPreferredSize(new Dimension(w.getWidth(),w.getHeight()));
			p.setMaximumSize(new Dimension(w.getWidth(),w.getHeight()));
			p.setLayout(null);
	    	
	    	JLabel label = new JLabel();
	    	label.setIcon(new ImageIcon
	    				 (new ImageIcon("src/com/redteam/atrobots/screens/images.png").getImage().getScaledInstance
	    				 (w.getWidth(), w.getHeight(), Image.SCALE_DEFAULT)));
	    	label.setBounds(0, 0, w.getWidth(), w.getHeight());
	    	
	    	
	    	p.add(label);
	    	label.setVisible(true);
	    	w.add(p);
			p.setVisible(true);
	    }
	
	    @Override
	    public void handleInput(){}
}