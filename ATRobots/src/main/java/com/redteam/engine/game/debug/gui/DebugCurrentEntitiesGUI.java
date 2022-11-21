package com.redteam.engine.game.debug.gui;

import com.redteam.engine.core.entity.Entity;
import imgui.ImGui;

import java.util.HashSet;

@SuppressWarnings("unused")
public class DebugCurrentEntitiesGUI implements iDebugGUI {

	private HashSet<Entity> entities = new HashSet<>();

	public void init() {
		ImGui.begin("Current Entities ");
	}

	public void update() {
		if(entities != null) {
			StringBuilder currentEntities = new StringBuilder();
			for (Entity ent : entities) {
				currentEntities.append("\nID: ").append(ent.getID());
				currentEntities.append("\nPos:" )
							   .append("\nX: ").append(((int) (ent.getPos().x * 100)) / 100.)
							   .append("\nY: ").append(((int) (ent.getPos().y * 100)) / 100.)
							   .append("\nZ: ").append(((int) (ent.getPos().z * 100)) / 100.)
							   .append("\n");
			}
			ImGui.text("Current Entities " + entities.size() + ":\n" + currentEntities);
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
