package com.redteam.atrobots.screens;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;

import com.redteam.atrobots.ATRobots.State;
import com.redteam.atrobots.Window;
import com.redteam.atrobots.sounds.Sound;

public class StartScreen implements Screen {
	private JPanel p;
	private Window w;
	private JButton playButton = new JButton("PLAY");
	private JButton quitButton = new JButton("QUIT");
	private JButton R1B = new JButton("R1"),
					R2B = new JButton("R2"),
					R3B = new JButton("R3"),
					R4B = new JButton("R4"),
					R5B = new JButton("R5"),
					R6B = new JButton("R6"),
					RemR1 = new JButton(),
					RemR2 = new JButton(),
					RemR3 = new JButton(),
					RemR4 = new JButton(),
					RemR5 = new JButton(),
					RemR6 = new JButton();
	private JPanel settingsPanel;
	private JLabel logo = new JLabel();
	private Window newWin;
	
	
		// JPanel attach - the panel which you'd like to attach your other panel to
		private JPanel createPanel(JPanel attach,
								   int x1, int y1, int x2, int y2,
								   Color c) {
			JPanel panel = new JPanel();
			panel.setBounds(x1,y1,x2,y2);
	    	panel.setBackground(c);
	    	attach.add(panel);
	    	return panel;
		}
		
		// String text - the text you'd like your button to say
		private void createRobotButton(JButton b, int x1, int y1, int x2, int y2) {
			b.setBounds(x1, y1, x2, y2);
			b.setBackground(Color.LIGHT_GRAY);
			b.setFont(new Font("TimesRoman", Font.BOLD, 13));
			b.setFocusable(false);
			settingsPanel.add(b);
		}
		
		private void robotButtonInput(JButton robotButton) {
			robotButton.addActionListener(new ActionListener() {
	 	       public void actionPerformed(ActionEvent e) {
	 	             if(e.getSource() == robotButton) {
	 	            	 w.remove(p);
	 	            	 try {
	 	         			Sound.source("src/com/redteam/atrobots/sounds/button_click.wav");
	 	         		 } catch (Exception ex) {
	 	         			// TODO Auto-generated catch block
	 	         			ex.printStackTrace();
	 	         		 }
	 	            	 w.updateScreen(State.SETTINGS);
	 	              }
	 	       }
			});
		}
		
		@Override
		public void grabWindow(Window w) { this.w = w; }
		
	    @Override 
	    public void update() {}
	
	    @Override
	    public void draw()
	    {
	    	
	    	// JPanel Formatting`	
			p = new JPanel();
			p.setSize(w.getWidth(),w.getHeight());
			p.setPreferredSize(new Dimension(w.getWidth(),w.getHeight()));
			p.setMaximumSize(new Dimension(w.getWidth(),w.getHeight()));
			p.setBackground(Color.black);
			p.setLayout(null);
	    	
	    	// Left Border Panel
			createPanel(p, 0, 0, 10, w.getHeight(), Color.GRAY);
	    	
	    	// Right Border Panel *needed to be offset*
			createPanel(p, w.getWidth() - 25, 0, 10, w.getHeight(), Color.GRAY);
	    	
			// Top Border Panel
	    	createPanel(p, 0, 0, w.getWidth(), 10, Color.GRAY);
	    	
	    	// Bottom Border Panel *needed to be offset*
			createPanel(p, 0, w.getHeight() - 48, w.getWidth(), 10, Color.GRAY);
	    	
	    	// Settings Panel
			settingsPanel = createPanel(p, 0, 100, w.getWidth(), 310, Color.GRAY);
			settingsPanel.setLayout(null);
			
			// Logo
			logo.setText("AT-Robots Shell v1.0");
			logo.setFont(new Font("TimesRoman", Font.BOLD, 50));
			logo.setBounds((w.getWidth() / 2) - 275, 0, 600, 115);
			logo.setForeground(Color.WHITE);
			p.add(logo);
			
			
			// Panels where the names of the robots will appear
			createPanel(settingsPanel, 140, 25, 480, 70, Color.BLACK);
			createPanel(settingsPanel, 140, 120, 480, 70, Color.BLACK);
			createPanel(settingsPanel, 140, 215, 480, 70, Color.BLACK);
			createPanel(settingsPanel, 750, 25, 480, 70, Color.BLACK);
			createPanel(settingsPanel, 750, 120, 480, 70, Color.BLACK);
			createPanel(settingsPanel, 750, 215, 480, 70, Color.BLACK);
			
			// Buttons that go to settings screen to select an ATRobot
			createRobotButton(R1B, 65, 25, 70, 70);
			createRobotButton(R2B, 65, 120, 70, 70);
			createRobotButton(R3B, 65, 215, 70, 70);
			createRobotButton(R4B, 675, 25, 70, 70);
			createRobotButton(R5B, 675, 120, 70, 70);
			createRobotButton(R6B, 675, 215, 70, 70);
			
			// Removes a robot from their spot
			createRobotButton(RemR1, 50, 25, 10, 70);
			createRobotButton(RemR2, 50, 120, 10, 70);
			createRobotButton(RemR3, 50, 215, 10, 70);
			createRobotButton(RemR4, 660, 25, 10, 70);
			createRobotButton(RemR5, 660, 120, 10, 70);
			createRobotButton(RemR6, 660, 215, 10, 70);
	    	
			playButton.setBounds(20, 20, 400, 75);
			quitButton.setBounds(20, 20, 400, 75);

			playButton.setFont(new Font("TimesRoman", Font.PLAIN, 20));
			quitButton.setFont(new Font("TimesRoman", Font.PLAIN, 20));
			
			playButton.setBackground(Color.GRAY);
			quitButton.setBackground(Color.GRAY);
			
			playButton.setFocusable(false);
			quitButton.setFocusable(false);
			
			
			
			/*
			 * Math for the Width:
			 * (Width of the Screen / 2) - to calculate half of the screen,
			 * (-200) - to subtract by half of the buttons width and add spacing between the buttons
			 * (+250/-250) - to offset the buttons from each other
			 * 
			 * Math for the Height: 
			 * (Height of the Screen) - to find the bottom,
			 * (-130) - to go slightly higher than the bottom
			 */
			playButton.setLocation(((w.getWidth() / 2) - 200) + 250,
									(w.getHeight() - 130));
			quitButton.setLocation(((w.getWidth() / 2) - 200) - 250,
									(w.getHeight() - 130));
			
		    p.add(playButton);
		    p.add(quitButton);
		    w.add(p);
		    
		    w.revalidate();
		    w.repaint();
	    }
	
	    @Override
	    public void handleInput()
	    {	
	    	quitButton.addActionListener(new ActionListener() {
	    	       public void actionPerformed(ActionEvent e) {
	    	             if(e.getSource() == quitButton) {
	    	            	 w.remove(p);
	    	            	 try {
	    	         			Sound.source("src/com/redteam/atrobots/sounds/button_click.wav");
	    	         		} catch (Exception ex) {
	    	         			// TODO Auto-generated catch block
	    	         			ex.printStackTrace();
	    	         		}
	    	            	 w.updateScreen(State.CLOSING);
	    	              }
	    	       }
	    	 });
	    	playButton.addActionListener(new ActionListener() {
	    	       public void actionPerformed(ActionEvent e) {
	    	             if(e.getSource() == playButton) {
	    	            	 w.remove(p);
	    	            	 try {
	    	         			Sound.source("src/com/redteam/atrobots/sounds/button_click.wav");
	    	         		} catch (Exception ex) {
	    	         			// TODO Auto-generated catch block
	    	         			ex.printStackTrace();
	    	         		}
	    	            	 //w.updateScreen(State.RUNNING);
	    	            	 w.dispose();
	    	            	 newWin = new Window("ATROBOTS GAME", 1920, 1080);
		    	         	 newWin.addScreen(State.START, new StartScreen());
		    	         	 newWin.addScreen(State.RUNNING, new RunningScreen());
		    	         	 newWin.addScreen(State.NO_GRAPHICS, new NoGraphicsScreen());
		    	         	 newWin.addScreen(State.SETTINGS, new SettingsScreen());
		    	         	 newWin.addScreen(State.CLOSING, new ClosingScreen());
		    	         	 newWin.updateScreen(State.RUNNING);
	    	              }
	    	       }
	    	 });
	    	robotButtonInput(R1B);
	    	robotButtonInput(R2B);
	    	robotButtonInput(R3B);
	    	robotButtonInput(R4B);
	    	robotButtonInput(R5B);
	    	robotButtonInput(R6B);
	    }
}