package com.redteam.atr3;

import org.lwjgl.glfw.GLFWKeyCallback;
import static org.lwjgl.glfw.GLFW.*;

// Must call "glfwPollEvents();" before and "keyCallback.release();" after

public class Input extends GLFWKeyCallback {
	@Override
	public void invoke(long window, int key, int scancode, int action, int mods) {
		if (key == GLFW_KEY_W) {
			// UP
		}
		
		else if (key == GLFW_KEY_A) {
			// LEFT
		}
		
		else if (key == GLFW_KEY_S) {
			// DOWN
		}
		
		else if (key == GLFW_KEY_D) {
			// RIGHT
		}
	}
}