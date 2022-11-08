package com.redteam.engine.core.entity;

import com.redteam.engine.core.rendering.Model;
import org.joml.Vector3f;

public class Entity {

	private final String id;
    private final Model model;
    private final Vector3f pos;
    private final Vector3f rotation;
    private final float scale;
    
    public Entity(String id, Model model, Vector3f pos, Vector3f rotation, float scale) {
    	this.id = id;
        this.model = model;
        this.pos = pos;
        this.rotation = rotation;
        this.scale = scale;
    }

    public void incPos(float x, float y, float z) {
        this.pos.x += x;
        this.pos.y += y;
        this.pos.z += z;
    }

    public void setPos(float x, float y, float z) {
        this.pos.x = x;
        this.pos.y = y;
        this.pos.z = z;
    }

    public void incRotation(float x, float y, float z) {
        this.rotation.x += x;
        this.rotation.y += y;
        this.rotation.z += z;
    }

    public void setRotation(float x, float y, float z) {
        this.rotation.x = x;
        this.rotation.y = y;
        this.rotation.z = z;
    }
    
    public String getID() {
    	return id;
    }

    public Model getModel() {
        return model;
    }

    public Vector3f getPos() {
        return pos;
    }

    public Vector3f getRotation() {
        return rotation;
    }

    public float getScale() {
        return scale;
    }
    @SuppressWarnings("unused")
    public void gameTick() {
    }

    public void debugGameTick() {
    }
}
