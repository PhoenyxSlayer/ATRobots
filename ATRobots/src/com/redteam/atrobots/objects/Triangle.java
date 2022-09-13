/*package com.redteam.atrobots.objects;

import java.awt.Color;
import java.awt.Graphics;

import com.redteam.atrobots.ATRobots;
import com.redteam.atrobots.Window;

public class Triangle {
	
	public Triangle() {
	}
	
	public void triangleMovement() {
		for(int i = 0; i < ATRobots.points; i++) {
			ATRobots.xPos[i] += .1;
			ATRobots.x[i] = (int)ATRobots.xPos[i];
		}
	}
	
	//public void setPoints(int[] x, int[] y) {
		//this.x = x;
		//this.y = y;
	//}
	
	protected void renderTriangle(Graphics g) {
		//if(Window.bs == null)
			//this.createBufferStrategy(3);
			//Window.bs = Window.canvas.getBufferStrategy();
		//
		Window.graphics = Window.bs.getDrawGraphics();
		Window.graphics.clearRect(-200, -200, (ATRobots.getWidth() + 200), (ATRobots.getHeight()+200));
		Window.graphics.setColor(Color.RED);
		Window.graphics.drawPolygon(ATRobots.x, ATRobots.y, 3);
		Window.bs.show();
		Window.graphics.dispose();
		
	}
}*/