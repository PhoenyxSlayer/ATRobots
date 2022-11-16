package com.redteam.engine.game.debug.gui;

import imgui.ImGui;

@SuppressWarnings("unused")
public class DebugSpectatorGUI implements iDebugGUI {

	private boolean spectator;

	public void init() {
		ImGui.begin("Spectator");
	}

	public void update() {
		ImGui.text("Spectator: " + spectator);
	}

	public void close() {
		ImGui.end();
	}

	// EXTRA FUNCTIONS

	public void setSpectator(Boolean spectator) {
		this.spectator = spectator;
	}
}
