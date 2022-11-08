package com.redteam.engine.core.gui;

import java.awt.Canvas;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.image.BufferStrategy;

import javax.swing.ImageIcon;
import javax.swing.JFrame;

import com.redteam.engine.game.ATRobots;

public class MainMenu extends JFrame {
	private static final long serialVersionUID = -4918278784416304615L;
	
	private int width, height;
	
	private ImageIcon icon = new ImageIcon("src/main/resources/images/test.png");
	private ImageIcon play = new ImageIcon("src/main/resources/images/play.png");
	private ImageIcon exit = new ImageIcon("src/main/resources/images/exit.png");
	private ImageIcon bg = new ImageIcon("src/main/resources/images/Unnamed.png");
	private ImageIcon settings = new ImageIcon("src/main/resources/images/settings.png");
	
	private Canvas canvas;
	
	private Graphics g;
	private BufferStrategy bs;
	
	private boolean running = true;
	
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
					ATRobots.setName("run");
					setVisible(false);
					try {
						ATRobots.run();
					} catch (Exception ex) {
						ex.printStackTrace();
					}
					setVisible(true);
				}
				if((e.getX() >= (width/2)-55 && e.getX() <= ((width/2)-55)+128) && (e.getY() >= (height/2)+100 && e.getY() <= (height/2)+150)) {
					System.out.println("Button Clicked");
					running = false;
					dispose();
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
		
		bs.show();
		g.dispose();
	}
}