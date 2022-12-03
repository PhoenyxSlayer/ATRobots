package com.redteam.engine.game.debug.gui;

import imgui.ImGui;

import java.util.Objects;

@SuppressWarnings("unused")
public class DebugEventsGUI implements iDebugGUI {

	private String events = "";

	public void init() {
		ImGui.begin("Events");
	}

	public void update() {
		ImGui.text(events);
	}

	public void close() {
		ImGui.end();
	}

	// EXTRA FUNCTIONS

	public void addEvent(String event) {
		if(event != null) {
			events += event + "\n";
		}
	}
}
