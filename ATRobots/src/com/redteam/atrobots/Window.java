package com.redteam.atrobots;

import java.awt.Canvas;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.image.BufferStrategy;

import javax.swing.JFrame;

public class Window extends JFrame{
	private static final long serialVersionUID = 6286634599679800388L;
	
	private int width, height;
	private Canvas canvas;
	
	private Main main;
	
	private Graphics g;
	private BufferStrategy bs;
	
	public Window(String title, int width, int height, Main main) {
		super (title);
		this.main = main;
		
		this.width = width;
		this.height = height;
		
		setSize(width, height);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setVisible(true);
		setResizable(false);
		setLocationRelativeTo(null);
		
		canvas = new Canvas();
		canvas.setSize(width, height);
		canvas.setPreferredSize(new Dimension(width, height));
	    canvas.setMinimumSize(new Dimension(width, height));
	    canvas.setMaximumSize(new Dimension(width, height));
	    canvas.setBackground(Color.BLACK);
	    add(canvas);
	    pack();
	    
	    init();
	    run();
	}
	private void run() {
		while(true) {
			main.tick();
			render();
		}
	}
	
	private void init() {
		main.init();
	}
	private void render() {
		if(bs == null) canvas.createBufferStrategy(3);
		
		bs = canvas.getBufferStrategy();
		g = bs.getDrawGraphics();
		
		g.clearRect(-200, -200, width+200, height+200);
		
		main.render(g);
		
		bs.show();
		g.dispose();
	}
}
