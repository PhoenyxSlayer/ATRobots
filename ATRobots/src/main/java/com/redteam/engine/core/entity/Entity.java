package com.redteam.engine.core.entity;

import com.redteam.engine.core.rendering.Model;
import org.joml.Vector3f;

import static com.redteam.engine.utils.Constants.X_BORDER;
import static com.redteam.engine.utils.Constants.Z_BORDER;

/* https://docs.google.com/document/d/1c6gZt4eIC7wcBEtP2FeN9dnjtGApW9oLxj5VHExmx9c/edit?usp=sharing
 *  -> Link to Design Doc
 */

public abstract class Entity {

	private final String id;
    private final Model model;
    private final Vector3f pos;
    private final Vector3f rotation;
    private float scale;

    private boolean removed;
    
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
    public void setScale(float scale) {
        this.scale = scale;
    }
    @SuppressWarnings("unused")
    public abstract void gameTick();

    public abstract void debugGameTick();

    public void remove() {
        removed = true;
    }

    public boolean isRemoved() {
        return removed;
    }

    public boolean outOfBorder() {
		return ((getPos().x < -X_BORDER) || (getPos().x > X_BORDER))
				|| ((getPos().z < -Z_BORDER) || (getPos().z > 0));
	}
}