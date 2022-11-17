package com.redteam.engine.game;

import java.awt.Canvas;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.image.BufferStrategy;
import java.util.ArrayList;

import javax.swing.ImageIcon;
import javax.swing.JFrame;

import com.redteam.engine.core.entity.Robot;
import com.redteam.engine.game.main.ATRobots;

public class MainMenu extends JFrame {
	private static final long serialVersionUID = -4918278784416304615L;
	
	private int width, height;
	private ArrayList<String> rName = new ArrayList<String>();
	
	private ImageIcon icon = new ImageIcon("src/main/resources/images/test.png");
	private ImageIcon play = new ImageIcon("src/main/resources/images/play.png");
	private ImageIcon exit = new ImageIcon("src/main/resources/images/exit.png");
	private ImageIcon bg = new ImageIcon("src/main/resources/images/Unnamed.png");
	private ImageIcon settings = new ImageIcon("src/main/resources/images/settings.png");
	
	private Canvas canvas;
	
	private Graphics g;
	private BufferStrategy bs;
	
	private boolean running = true;
	private boolean rSelect = false;
	private Robot r = new Robot();
	private RobotSelection rs = new RobotSelection();
	
	public MainMenu(String title, int width, int height) {
		this.width = width;
		this.height = height;
		
		setIconImage(icon.getImage());
		setTitle(title);
		setSize(width, height);
		setVisible(true);
		setResizable(false);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setLocationRelativeTo(null);
		
		canvas = new Canvas();
		canvas.setSize(width, height);
		canvas.setPreferredSize(new Dimension(width, height));
	    canvas.setMinimumSize(new Dimension(width, height));
	    canvas.setMaximumSize(new Dimension(width, height));
	    canvas.setBackground(new Color(0,0,0));
	    add(canvas);
	    pack();
	  
	    //init();
	    run();
	}

	public void run() {
		
		canvas.addMouseListener(new MouseListener() {

			@Override
			public void mouseClicked(MouseEvent e) {
				System.out.println("Mouse X: "+e.getX()+"\nMouse Y: "+e.getY()+"");
				if((e.getX() >= (width/2)-55 && e.getX() <= ((width/2)-55)+128) && (e.getY() >= (height/2) && e.getY() <= (height/2)+50)) {
					System.out.println("Button Clicked");
					rSelect = true;
				}
				if((e.getX() >= width-128 && e.getX() <= width) && (e.getY() >= height-50 && e.getY() <= height)) {
					setVisible(false);
					try {
						ATRobots.run();
					} catch (Exception ex) {
						ex.printStackTrace();
					}
					setVisible(true);
				}
				if((e.getX() >= 0 && e.getX() <= 128) && (e.getY() >= height-50 && e.getY() <= height)) {
					rSelect = false;
				}
				if((e.getX() >= (width/2)-55 && e.getX() <= ((width/2)-55)+128) && (e.getY() >= (height/2)+100 && e.getY() <= (height/2)+150)) {
					System.out.println("Button Clicked");
					running = false;
					dispose();
				}
				
				if((e.getX() >= 70 && e.getX() <= 85) && (e.getY() >= 111 && e.getY() <= 136)) {
					rName.remove(0);
					rs.clearName(rName);
				}
				if((e.getX() >= 100 && e.getX() <= 135) && (e.getY() >= 111 && e.getY() <= 136)) {
					r.compile();
					System.out.println(rName);
					rName.add(r.referenceName());
					rs.setName(rName);
				}
				if((e.getX() >= 70 && e.getX() <= 85) && (e.getY() >= 179 && e.getY() <= 204)) {
					rName.remove(1);
					rs.clearName(rName);
				}
				if((e.getX() >= 100 && e.getX() <= 135) && (e.getY() >= 179 && e.getY() <= 204)) {
					r.compile();
					rName.add(r.referenceName());
					rs.setName(rName);
				}
				if((e.getX() >= 70 && e.getX() <= 85) && (e.getY() >= 247 && e.getY() <= 272)) {
					rName.remove(2);
					rs.clearName(rName);
				}
				if((e.getX() >= 100 && e.getX() <= 135) && (e.getY() >= 247 && e.getY() <= 272)) {
					r.compile();
					rName.add(r.referenceName());
					rs.setName(rName);
				}
				if((e.getX() >= 470 && e.getX() <= 485) && (e.getY() >= 111 && e.getY() <= 136)) {
					rName.remove(3);
					rs.clearName(rName);
				}
				if((e.getX() >= 500 && e.getX() <= 535) && (e.getY() >= 111 && e.getY() <= 136)) {
					r.compile();
					rName.add(r.referenceName());
					rs.setName(rName);
				}
				if((e.getX() >= 470 && e.getX() <= 485) && (e.getY() >= 179 && e.getY() <= 204)) {
					rName.remove(4);
					rs.clearName(rName);
				}
				if((e.getX() >= 500 && e.getX() <= 535) && (e.getY() >= 179 && e.getY() <= 204)) {
					r.compile();
					rName.add(r.referenceName());
					rs.setName(rName);
				}
				if((e.getX() >= 470 && e.getX() <= 485) && (e.getY() >= 247 && e.getY() <= 272)) {
					rName.remove(5);
					rs.clearName(rName);
				}
				if((e.getX() >= 500 && e.getX() <= 535) && (e.getY() >= 247 && e.getY() <= 272)) {
					r.compile();
					rName.add(r.referenceName());
					rs.setName(rName);
				}
			}
			
			@Override
			public void mousePressed(MouseEvent e) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void mouseReleased(MouseEvent e) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void mouseEntered(MouseEvent e) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void mouseExited(MouseEvent e) {
				// TODO Auto-generated method stub
				
			}
		});
		while(running) {
			render();
		}
	}
	
	public void render() {
		if(bs == null) canvas.createBufferStrategy(3);
		
		bs = canvas.getBufferStrategy();
		g = bs.getDrawGraphics();
		
		g.clearRect(-200, -200, width+200, height+200);
		g.drawImage(bg.getImage(), 0, 0, canvas);
		
		g.setColor(new Color(255,0,0));
		
		g.drawImage(play.getImage(), (width/2)-55, (height/2), canvas);
		g.drawImage(exit.getImage(), (width/2)-55, (height/2)+100, canvas);
		g.drawImage(settings.getImage(), 0, height-50, canvas);
		//g.drawImage(icon.getImage(), 100, 200, canvas);
		
		if(rSelect) rs.render(g, canvas);
		
		bs.show();
		g.dispose();
	}
}