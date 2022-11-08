package com.redteam.engine.core.lighting;

import org.joml.Vector3f;

public class DirectionalLight {

    Vector3f color, direction;
    private float intensity;

    public DirectionalLight(Vector3f color, Vector3f direction, float intensity) {
        this.color = color;
        this.direction = direction;
        this.intensity = intensity;
    }

    public Vector3f getColor() {
        return color;
    }

    @SuppressWarnings("unused")
    public void setColor(Vector3f color) {
        this.color = color;
    }

    public Vector3f getDirection() {
        return direction;
    }

    @SuppressWarnings("unused")
    public void setDirection(Vector3f direction) {
        this.direction = direction;
    }

    public float getIntensity() {
        return intensity;
    }

    @SuppressWarnings("unused")
    public void setIntensity(float intensity) {
        this.intensity = intensity;
    }
}
