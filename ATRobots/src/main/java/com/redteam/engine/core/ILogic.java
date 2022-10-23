package com.redteam.engine.core;

public interface ILogic {
	
	void init() throws Exception;
	
	void input();
	
	void update(double interval, MouseInput mouseInput);
	
	void render();
	
	void cleanup();
	
}