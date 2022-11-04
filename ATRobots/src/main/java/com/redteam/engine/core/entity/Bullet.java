package com.redteam.engine.core.entity;

import com.redteam.engine.game.TestGame;

import org.joml.Vector3f;

public class Bullet extends HittableEntity {

	public Bullet(String id, Model model, Vector3f pos, Vector3f rotation, float scale, float hitboxScale) {
		super(id, model, pos, rotation, scale, hitboxScale);
		// TODO Auto-generated constructor stub
	}
	
	public void gameTick() {
		
	}
	
	public void debugGameTick() {
		//TestGame.removeEntity(this);
	}

}
