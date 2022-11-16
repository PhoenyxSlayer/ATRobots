package com.redteam.engine.game;

import java.awt.Canvas;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.util.ArrayList;

import javax.swing.ImageIcon;

public class RobotSelection {
	
	private int width = 800; 
	private int height = 450;
	private ArrayList<String> name = new ArrayList<String>();
	private String r1 = "", r2 = "", r3 = "", r4 = "", r5 = "", r6 = "";
	
	private ImageIcon play = new ImageIcon("src/main/resources/images/play.png");
	private ImageIcon back = new ImageIcon("src/main/resources/images/back.png");
	
	public void render(Graphics g, Canvas c) {
		g.clearRect(-200, -200, width+200, height+200);
		
		g.setColor(new Color(192, 192, 192));
		g.fillRect(100, 111, 35, 25);
		g.fillRect(70, 111, 15, 25);
		
		g.setColor(new Color(0,255,0));
		g.setFont(new Font("TimesRoman", Font.BOLD, 20));
		g.drawString(r1, 140, 132);
		
		g.setColor(new Color(192, 192, 192));
		g.fillRect(100, 179, 35, 25);
		g.fillRect(70, 179, 15, 25);
		
		g.setColor(new Color(255,0,0));
		g.setFont(new Font("TimesRoman", Font.BOLD, 20));
		g.drawString(r2, 140, 200);

		g.setColor(new Color(192, 192, 192));
		g.fillRect(100, 247, 35, 25);
		g.fillRect(70, 247, 15, 25);
		
		g.setColor(new Color(0,0,255));
		g.setFont(new Font("TimesRoman", Font.BOLD, 20));
		g.drawString(r3, 140, 268);
		
		g.setColor(new Color(192, 192, 192));
		g.fillRect(500, 111, 35, 25);
		g.fillRect(470, 111, 15, 25);

		g.setColor(new Color(0,255,255));
		g.setFont(new Font("TimesRoman", Font.BOLD, 20));
		g.drawString(r4, 540, 132);
		
		g.setColor(new Color(192, 192, 192));
		g.fillRect(500, 179, 35, 25);
		g.fillRect(470, 179, 15, 25);

		g.setColor(new Color(255,0,255));
		g.setFont(new Font("TimesRoman", Font.BOLD, 20));
		g.drawString(r5, 540, 200);
		
		g.setColor(new Color(192, 192, 192));
		g.fillRect(500, 247, 35, 25);
		g.fillRect(470, 247, 15, 25);
		
		g.setColor(new Color(255,255,0));
		g.setFont(new Font("TimesRoman", Font.BOLD, 20));
		g.drawString(r6, 540, 268);
		
		g.drawImage(play.getImage(), width-128, height-50, c);
		g.drawImage(back.getImage(), 0, height-50, c);
		
		//g.drawImage(icon.getImage(), 100, 200, canvas);
		g.dispose();
	}
	
	public void setName(ArrayList<String> n) {
		name = n;
		switch(name.size()) {
		case 1:
			r1 = name.get(0);
			break;
		case 2:
			r2 = name.get(1);
			break;
		case 3:
			r3 = name.get(2);
			break;
		case 4:
			r4 = name.get(3);
			break;
		case 5:
			r5 = name.get(4);
			break;
		case 6:
			r6 = name.get(5);
			break;
		default:
			break;
		}
	}
	
	public void clearName(ArrayList<String> n) {
		name = n;
		switch(name.size()) {
		case 5:
			r1 = name.get(0);
			r2 = name.get(1);
			r3 = name.get(2);
			r4 = name.get(3);
			r5 = name.get(4);
			r6 = "";
			break;
		case 4:
			r1 = name.get(0);
			r2 = name.get(1);
			r3 = name.get(2);
			r4 = name.get(3);
			r5 = "";
			break;
		case 3:
			r1 = name.get(0);
			r2 = name.get(1);
			r3 = name.get(2);
			r4 = "";
			break;
		case 2:
			r1 = name.get(0);
			r2 = name.get(1);
			r3 = "";
			break;
		case 1:
			r1 = name.get(0);
			r2 = "";
			break;
		default:
			r1 = "";
			break;
		}
	}
}
