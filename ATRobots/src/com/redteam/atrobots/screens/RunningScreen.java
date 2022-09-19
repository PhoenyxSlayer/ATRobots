package com.redteam.atrobots.screens;

import java.awt.Canvas;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.image.BufferStrategy;
import java.util.Arrays;
import java.util.Vector;

import com.redteam.atrobots.Window;
import com.redteam.atrobots.objects.Robot;
import com.redteam.atrobots.sounds.Sound;

public class RunningScreen implements Screen {
	private Window w;
	private Canvas canvas = new Canvas();
	private Graphics g;
	private BufferStrategy bs;
	private int robots = 6;
	private int robotWidth = 120,
				robotHeight = (int)(robotWidth / 1.5);
	private int points = 3;
	private Vector<Integer> randX = new Vector<>();
	private float[][] x = new float[robots][points],
				  	  y = new float[robots][points];
	private Robot[] ATRobots = new Robot[robots];
	private boolean[] hitWall = new boolean[robots];
	private boolean running = true;
		
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
	    	// Creating Canvas on which AT-Robots will be drawn over
	    	canvas = new Canvas();
			canvas.setSize(w.getWidth(), w.getHeight());
			canvas.setPreferredSize(new Dimension(w.getWidth(), w.getHeight()));
		    canvas.setMinimumSize(new Dimension(w.getWidth(), w.getHeight()));
		    canvas.setMaximumSize(new Dimension(w.getWidth(), w.getHeight()));
		    canvas.setBackground(Color.BLACK);
		    w.add(canvas);
		    w.pack();
		    
		    for(int i = 0; i < robots; i++) {
		    	// Prevents Spawn Collision
		    	/* +1 is to prevent the chance of randX being 0 in which the
		    	 * triangle would have double width
		    	 */
				randX.add((int)((Math.random()*(w.getHeight() - robotHeight - 1) + 1)));
				for(int j = 0; j < randX.size(); j++) {
					if(i == j) {}
					else {
						while((randX.get(i) >= randX.get(j) - robotHeight) && (randX.get(i) <= randX.get(j) + robotHeight)) {
							randX.set(i, (int)((Math.random()*(1080 - robotHeight))));
							j = 0;
						}
					}
				}
				
				// Sets Spawn
				for(int j = 0; j < points; j++) {
					switch(j) {
					case 0:
						x[i][j] = randX.get(i);
						y[i][j] = randX.get(i);
						break;
					case 1:
						x[i][j] = randX.get(i) + robotWidth;
						y[i][j] = randX.get(i) + (robotHeight / 2);
						break;
					case 2:
						x[i][j] = randX.get(i);
						y[i][j] = randX.get(i) + robotHeight;
						break;
					}
				}
				ATRobots[i] = new Robot(x[i], y[i], points);
				//ATRobots[i].setSpeed((float) 0.5);
			}
		    
		    Arrays.fill(hitWall, false);
		    
		    while(running == true) {	
		    	for(int i = 0; i < robots; i++) {
		    		// Detects Right Wall Collision
		    		if((int)x[i][1] == 1920) {
		    			try {
	 	         			Sound.source("src/com/redteam/atrobots/sounds/wall_hit.wav");
	 	         		} catch (Exception ex) {
	 	         			ex.printStackTrace();
	 	         		}
		    			hitWall[i] = true;
		    			x[i][1] = x[i][0] - robotWidth;
		    		}
		    		
		    		// Detects Left Wall Collision
		    		else if((int)x[i][1] == 0) {
		    			try {
	 	         			Sound.source("src/com/redteam/atrobots/sounds/wall_hit.wav");
	 	         		} catch (Exception ex) {
	 	         			ex.printStackTrace();
	 	         		}
			    		hitWall[i] = false;
			    		x[i][1] = x[i][0] + robotWidth;
			    	}
		    	}
		    	
		    	// Movement
		    	for(int i = 0; i < robots; i++)
		    		ATRobots[i].tick(x[i], points, hitWall[i]);
		    	
		    	if(bs == null) canvas.createBufferStrategy(3);
				
				bs = canvas.getBufferStrategy();
				g = bs.getDrawGraphics();
				
				g.clearRect(-200, -200, w.getWidth()+200, w.getHeight()+200);
				
				for(int i = 0; i < robots; i++) {
					ATRobots[i].paintComponent(g);
				}
				
				bs.show();
				g.dispose();
			}
			
	    }
	
	    @Override
	    public void handleInput()
	    {
	    }
}