package com.redteam.engine.core.gui;

import com.redteam.engine.core.entity.Entity;

import imgui.ImGui;

public class DebugGUI {
	private String totalBulletsRemoved = "";
	public void init() {
		return;
	}
	
	public void passDeletedBullet(int bullet, Entity entity) {
		totalBulletsRemoved += "BULLET <" + bullet + "> BEING REMOVED AT <" + entity.getPos().x + ", "
				+ entity.getPos().y + ", "
				+ entity.getPos().z + ">\n";
		return;
	}
	
	public void showBulletDebug() {
		if(totalBulletsRemoved != "") {
			ImGui.begin("BULLET DEBUG");
			ImGui.text(totalBulletsRemoved);
			ImGui.end();
		}
		return;
	}

	
	public void coords(float x, float y, float z) {
		ImGui.begin("Coordinates");
		ImGui.text("X: " + x + "\nY: " + y + "\nZ: " + z);
		ImGui.end();
		return;
	}
	
	public void spectator(boolean spectator) {
		ImGui.begin("Spectator Mode");
		ImGui.text("Spectator: " + spectator);
		ImGui.end();
		return;
	}
}
