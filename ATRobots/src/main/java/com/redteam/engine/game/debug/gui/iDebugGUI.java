package com.redteam.engine.game.debug.gui;

import com.redteam.engine.core.Window;
import com.redteam.engine.core.entity.Entity;
import com.redteam.engine.game.main.ATRobots;
import imgui.ImGui;
import imgui.flag.ImGuiConfigFlags;
import org.joml.Vector3f;
import org.lwjgl.glfw.GLFW;

import java.util.HashSet;

import static org.lwjgl.glfw.GLFW.glfwPollEvents;
import static org.lwjgl.glfw.GLFW.glfwSwapBuffers;

public interface iDebugGUI {

    Window window = ATRobots.getWindow();

    void init();

    void update();


    void close();

}
