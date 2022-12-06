package com.redteam.engine.core;

import java.util.HashSet;

import com.redteam.engine.core.entity.Entity;
import com.redteam.engine.core.terrain.Terrain;
import com.redteam.engine.core.lighting.DirectionalLight;
import org.joml.Vector3f;

@SuppressWarnings("unused")
public interface iObjMapping {
	HashSet<Entity> entities = new HashSet<>();
	HashSet<Terrain> terrains = new HashSet<>();
	DirectionalLight light = new DirectionalLight(new Vector3f(1,1,1), new Vector3f(-1, -10, 0), 1.0f);

	default void init() {
		initEntities();
		initTerrains();
		initLights();
	}
	void initEntities();

	void initTerrains();

	void initLights();

	default HashSet<Entity> entityMap() {
		return entities;
	}

	default HashSet<Terrain> terrainMap() {
		return terrains;
	}

	default DirectionalLight lightMap() {
		return light;
	}

	default Entity addEntity(Entity e) {
		entities.add(e);
		return e;
	}

	default void removeEntity(Entity e) {
		entities.remove(e);
	}

	default void addTerrain(Terrain t) {
		terrains.add(t);
	}

	default void removeTerrain(Terrain t) {
		terrains.remove(t);
	}

	default void changeLight(DirectionalLight l) {
		light.equals(l);
	}
}
