package com.redteam.engine.core.entity;

import java.util.HashMap;
import org.joml.Vector3f;

public class HitBox {
	
	private Vector3f pos;
	private float width, length, height;
	private HashMap<String, Vector3f> cube = new HashMap<String, Vector3f>();
	private Vector3f entityPosition = new Vector3f();
	
	public HitBox(Entity entity) throws Exception {
		if(entity != null) {
			entityPosition = entity.getPos();
			formCube(entityPosition);
		}
		else {
			throw new Exception("Entity: " + entity + " is null");
		}
	}
	
	public void formCube(Vector3f coordinate) {
		cube.put("lowerLeftFront", coordinate);
		cube.put("lowerRightFront", coordinate);
		cube.put("upperRightFront", coordinate);
		cube.put("upperLeftFront", coordinate);
		cube.put("lowerLeftBack", coordinate);
		cube.put("lowerRightBack", coordinate);
		cube.put("upperRightBack", coordinate);
		cube.put("upperLeftBack", coordinate);
		return;
	}
	
	public void updateBox(Vector3f pos) {
		formCube(pos);
		return;
	}
	
	public HashMap<String, Vector3f> getPos() { return cube; }
}