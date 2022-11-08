package com.redteam.engine.game.debug.gui;

import java.util.HashMap;
import java.util.Map;

import com.redteam.engine.core.entity.Entity;

import imgui.ImGui;

@SuppressWarnings("unused")
public class DebugGUI {
	private final HashMap<Integer, String> totalBulletsRemoved = new HashMap<>();
	private final HashMap<Integer, String> totalBulletAngles = new HashMap<>();
	
	private String currTotalBulletsRemoved,
				   currTotalBulletAngles;
	
	public void init() {
	}
	
	public void passDeletedBullet(int bullet, Entity entity) {
		totalBulletsRemoved.clear();
		totalBulletsRemoved.put(bullet, "BULLET <" + bullet + "> BEING REMOVED AT <"
								+ entity.getPos().x + ", "
								+ entity.getPos().y + ", "
								+ entity.getPos().z + ">\n");
	}
	
	public void passBulletAngle(int bullet, float bulletAngle, Entity entity) {
		totalBulletAngles.put(bullet, "BULLET <" + bullet + "> AT "
							  + entity.getPos().x + ", "
						 	  + entity.getPos().y + ", "
						 	  + entity.getPos().z + "> "
						 	  + " AT ANGLE " + bulletAngle + "\n");
	}
	
	public void showDeletedBulletDebug() {
		if(!totalBulletsRemoved.isEmpty()) {
			ImGui.begin("BULLET DEBUG");
			for(Map.Entry<Integer, String> set :
				totalBulletsRemoved.entrySet()) {
				currTotalBulletsRemoved += set.getValue();
			}
			ImGui.text(currTotalBulletsRemoved);
			currTotalBulletsRemoved = "";
			ImGui.end();
		}
	}
	
	public void showBulletAngleDebug() {
		if(!totalBulletAngles.isEmpty()) {
			ImGui.begin("BULLET ANGLE DEBUG");
			for(Map.Entry<Integer, String> set :
				totalBulletAngles.entrySet()) {
				currTotalBulletAngles += set.getValue();
			}
			ImGui.text(currTotalBulletAngles);
			currTotalBulletAngles = "";
			ImGui.end();
		}
	}

	
	public void coords(float x, float y, float z) {
		ImGui.begin("Coordinates");
		ImGui.text("X: " + x + "\nY: " + y + "\nZ: " + z);
		ImGui.end();
	}
	
	public void spectator(boolean spectator) {
		ImGui.begin("Spectator Mode");
		ImGui.text("Spectator: " + spectator);
		ImGui.end();
	}
}
