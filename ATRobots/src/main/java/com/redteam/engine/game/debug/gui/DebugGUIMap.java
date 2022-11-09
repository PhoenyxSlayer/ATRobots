package com.redteam.engine.game.debug.gui;

import com.redteam.engine.core.Window;
import com.redteam.engine.core.entity.Entity;
import com.redteam.engine.game.main.ATRobots;
import imgui.ImGui;
import imgui.flag.ImGuiConfigFlags;
import org.joml.Vector3f;
import org.lwjgl.glfw.GLFW;

import java.util.HashSet;

public class DebugGUIMap {

    Window window = ATRobots.getWindow();
    private boolean debugMode = false;

    private DebugSpectatorGUI spectatorGUI = new DebugSpectatorGUI();
    private DebugCurrentEntitiesGUI currentEntitiesGUI = new DebugCurrentEntitiesGUI();


    public void spectatorGUI(boolean spectator) {
        spectatorGUI.init();
        spectatorGUI.setSpectator(spectator);
        spectatorGUI.update();
        spectatorGUI.close();
    }

    public void currentEntitiesGUI(HashSet<Entity> entities) {
        currentEntitiesGUI.init();
        currentEntitiesGUI.setEntities(entities);
        currentEntitiesGUI.update();
        currentEntitiesGUI.close();
    }

    public boolean getDebugMode(HashSet<Entity> entities) {
        return debugMode;
    }

    public void updateDebugMode() {
        debugMode = !debugMode;
    }

    public void createImGUIWindows() {
        window.imGuiGlfw.newFrame();
        ImGui.newFrame();
    }

    public void render() {
        ImGui.render();
        window.imGuiGl3.renderDrawData(ImGui.getDrawData());
        if(ImGui.getIO().hasConfigFlags(ImGuiConfigFlags.ViewportsEnable)) {
            final long backupWindowPtr = org.lwjgl.glfw.GLFW.glfwGetCurrentContext();
            ImGui.updatePlatformWindows();
            ImGui.renderPlatformWindowsDefault();
            GLFW.glfwMakeContextCurrent(backupWindowPtr);
        }

    }
}
