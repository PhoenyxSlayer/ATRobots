package com.redteam.engine.core;

import java.util.HashSet;

import com.redteam.engine.core.entity.Entity;
import com.redteam.engine.core.terrain.Terrain;
import com.redteam.engine.core.lighting.DirectionalLight;

@SuppressWarnings("unused")
public interface iObjMapping {
	
	void entityMap(HashSet<Entity> map);
	
	void terrainMap(HashSet<Terrain> map);
	
	void lightMap(DirectionalLight light);

}
