package com.redteam.atrobots.objects;

import java.awt.Color;
import java.awt.Graphics;

import javax.swing.JPanel;

public class Tanks extends JPanel {
	
	private static final long serialVersionUID = 1L;
	private float[] x = new float[3], 
					y = new float[3];
	private int points = 3;
	private Color c;
	private Robot r;
	private int[] xInt = new int[3],
				yInt = new int[3];
	
	public Tanks(float[] x, float[] y) {
		setPoints(x, y);
	}
	
	public void setPoints(float[] x, float[] y) {
		this.x = x;
		this.y = y;
	}
	
	public void setColor(Color c) {
		this.c = c;
	}
	
	public void tick(){
		r.tick();
	}
	
	@Override
	protected void paintComponent(Graphics g) {
		// Convert to int
		for (int i = 0; i < x.length; i++) {
			xInt[i] = (int)x[i];
            yInt[i] = (int)y[i];
        }
		super.paintComponent(g);
		g.setColor(c);
		g.drawPolygon(xInt, yInt, points);
	}
}