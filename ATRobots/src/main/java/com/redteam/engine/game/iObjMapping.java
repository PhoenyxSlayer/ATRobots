package com.redteam.engine.game;

import java.util.HashSet;

import com.redteam.engine.core.entity.Entity;
import com.redteam.engine.core.entity.terrain.Terrain;
import com.redteam.engine.core.lighting.DirectionalLight;

@SuppressWarnings("unused")
public interface iObjMapping {
	
	void entityMap(HashSet<Entity> map);
	
	void terrainMap(HashSet<Terrain> map);
	
	void lightMap(DirectionalLight light);

}
