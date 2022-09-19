package com.redteam.atrobots.objects;

import java.awt.Color;
import java.awt.Graphics;
import java.util.Random;

public class Robot extends Tanks{
	
	private static final long serialVersionUID = 1L;

	Random rand = new Random();
	
	int r = (int) (((255 - 10) * Math.random()) + 10);
	int g = (int) (((255 - 10) * Math.random()) + 10);
	int b = (int) (((255 - 10) * Math.random()) + 10);
	
	float speed = (float) (((new Random()).nextFloat() * 0.2) * 25);
	
	public Robot(float[] x, float[] y, int points) {
		super(x, y);

		setPoints(x,y);
		setColor(new Color((int)r,g,b));
		
	}
	
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
	}
	
	public void setSpeed (float speed) {
		this.speed = speed;
	}
	
	public void tick (float[] x, int points, boolean wall) {
		for(int i = 0; i < points; i++) {
			if(wall)
				x[i] -= speed;
			else
				x[i] += speed;
		}
	}
}