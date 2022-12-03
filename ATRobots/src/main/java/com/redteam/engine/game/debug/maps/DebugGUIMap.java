package com.redteam.engine.game.debug.maps;

import com.redteam.engine.core.Window;
import com.redteam.engine.core.entity.Entity;
import com.redteam.engine.game.debug.gui.DebugCurrentEntitiesGUI;
import com.redteam.engine.game.debug.gui.DebugEventsGUI;
import com.redteam.engine.game.debug.gui.DebugSpectatorGUI;
import com.redteam.engine.game.main.ATRobots;
import imgui.ImGui;
import imgui.flag.ImGuiConfigFlags;
import org.lwjgl.glfw.GLFW;

import java.util.HashSet;

public class DebugGUIMap {

    Window window = ATRobots.getWindow();
    private boolean debugMode = false;

    private final DebugSpectatorGUI spectatorGUI = new DebugSpectatorGUI();
    private final DebugCurrentEntitiesGUI currentEntitiesGUI = new DebugCurrentEntitiesGUI();

    private final DebugEventsGUI currentEventsGUI = new DebugEventsGUI();


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

    public void eventsGUI() {
        currentEventsGUI.init();
        currentEventsGUI.update();
        currentEventsGUI.close();
    }

    public void addEvent(String event) {
        currentEventsGUI.addEvent(event);
    }


    public boolean getDebugMode() {
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
        if(getDebugMode()) {
            window.imGuiGl3.renderDrawData(ImGui.getDrawData());
            if (ImGui.getIO().hasConfigFlags(ImGuiConfigFlags.ViewportsEnable)) {
                final long backupWindowPtr = org.lwjgl.glfw.GLFW.glfwGetCurrentContext();
                ImGui.updatePlatformWindows();
                ImGui.renderPlatformWindowsDefault();
                GLFW.glfwMakeContextCurrent(backupWindowPtr);
            }
        }

    }
}
