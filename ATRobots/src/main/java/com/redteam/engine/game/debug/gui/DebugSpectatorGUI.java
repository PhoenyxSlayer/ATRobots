package com.redteam.engine.game.debug.gui;

import imgui.ImGui;
import org.joml.Vector3f;

@SuppressWarnings("unused")
public class DebugSpectatorGUI implements iDebugGUI {

	private boolean spectator;

	public void init() {
		ImGui.begin("Spectator");
	}

	public void update() {
		ImGui.text("Spectator: " + String.valueOf(spectator));
	}

	public void close() {
		ImGui.end();
	}

	// EXTRA FUNCTIONS

	public void setSpectator(Boolean spectator) {
		this.spectator = spectator;
	}
}
