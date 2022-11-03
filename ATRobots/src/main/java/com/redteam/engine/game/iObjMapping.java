package com.redteam.engine.game;

import java.util.HashSet;

import com.redteam.engine.core.entity.Entity;
import com.redteam.engine.core.entity.terrain.Terrain;
import com.redteam.engine.core.lighting.DirectionalLight;

public interface iObjMapping {
	
	public void entityMap(HashSet<Entity> map);
	
	public void terrainMap(HashSet<Terrain> map);
	
	public void lightMap(DirectionalLight light);

}
