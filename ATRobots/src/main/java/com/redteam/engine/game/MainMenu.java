package com.redteam.engine.game;

import com.redteam.engine.core.entity.Robot;

import imgui.ImGui;
import imgui.flag.ImGuiCol;
import imgui.flag.ImGuiWindowFlags;

public class MainMenu {
	private Robot robot = new Robot();

	protected void showMenu() {
		ImGui.getStyle().setColor(ImGuiCol.Button, 20, 60, 220, 255);
		ImGui.getStyle().setColor(ImGuiCol.ButtonHovered, 220, 0, 0, 255);
		ImGui.getStyle().setColor(ImGuiCol.ButtonActive, 20, 60, 220, 255);
		//ImGui.getStyle().setWindowPadding(20, 20);
		ImGui.begin("Main Menu", ImGuiWindowFlags.NoTitleBar);
		if(ImGui.button("Play", 100, 50)) {
			if(robot.compile()) {
				
			}
		}
		ImGui.end();
	}
}