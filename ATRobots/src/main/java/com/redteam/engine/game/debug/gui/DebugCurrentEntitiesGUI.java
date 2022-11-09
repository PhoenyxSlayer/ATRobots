package com.redteam.engine.game.debug.gui;

import com.redteam.engine.core.entity.Entity;
import imgui.ImGui;

import java.util.HashSet;

@SuppressWarnings("unused")
public class DebugCurrentEntitiesGUI implements iDebugGUI {

	private HashSet<Entity> entities = new HashSet<>();

	public void init() {
		ImGui.begin("Current Entities");
	}

	public void update() {
		if(entities != null) {
			String currentEntities = "";
			for (Entity ent : entities) {
				currentEntities += "\nID: "+ ent.getID();
				currentEntities += "\nPos:"
								+	"\nX: " + ((int) (ent.getPos().x*100)) / 100.
								+	"\nY: " + ((int) (ent.getPos().y*100)) / 100.
								+	"\nZ: " + ((int) (ent.getPos().z*100)) / 100.
								+	"\n";
			}
			ImGui.text("Current Entities:\n " + currentEntities);
		}
	}

	public void close() {
		ImGui.end();
	}

	// EXTRA FUNCTIONS

	public void setEntities(HashSet<Entity> entities) {
		this.entities = entities;
	}
}
