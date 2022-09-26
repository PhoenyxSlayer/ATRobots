package com.redteam.atr3.screens;

import com.redteam.atr3.Window;

public interface Screen
{
	void grabWindow(Window w);
    void update();
    void draw();
    void handleInput();
}