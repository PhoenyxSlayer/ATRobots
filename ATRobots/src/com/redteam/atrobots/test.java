package com.redteam.atrobots;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import javax.swing.JPanel;

public class test {
	public static class RectDraw extends JPanel {
		  /**
		 * 
		 */
		private static final long serialVersionUID = 1L;

		public void paintComponent(Graphics g) {
		    super.paintComponent(g);  
		    g.drawRect(230,80,10,10);  
		    g.setColor(Color.RED);  
		    g.fillRect(230,80,10,10);  
		  }

		  public Dimension getPreferredSize() {
		    return new Dimension(getWidth(), getHeight()); // appropriate constants
		  }
	}
}