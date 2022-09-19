package com.redteam.atrobots.screens;

import com.redteam.atrobots.Window;

public interface Screen
{
	void grabWindow(Window w);
    void update();
    void draw();
    void handleInput();
}