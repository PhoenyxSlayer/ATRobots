package com.redteam.engine.core.lighting;

import org.joml.Vector3f;

public class PointLight {

    private Vector3f color, position;
    private float intensity, constant, linear, exponent;

    public PointLight(Vector3f color, Vector3f position, float intensity, float constant, float linear, float exponent) {
        this.color = color;
        this.position = position;
        this.intensity = intensity;
        this.constant = constant;
        this.linear = linear;
        this.exponent = exponent;
    }

    public PointLight(Vector3f color, Vector3f position, float intensity) {
        this(color, position, intensity, 1, 0, 0);
    }

    public Vector3f getColor() {
        return color;
    }

    @SuppressWarnings("unused")
    public void setColor(Vector3f color) {
        this.color = color;
    }

    public Vector3f getPosition() {
        return position;
    }

    public void setPosition(Vector3f position) {
        this.position = position;
    }

    public float getIntensity() {
        return intensity;
    }

    @SuppressWarnings("unused")
    public void setIntensity(float intensity) {
        this.intensity = intensity;
    }

    public float getConstant() {
        return constant;
    }

    @SuppressWarnings("unused")
    public void setConstant(float constant) {
        this.constant = constant;
    }

    public float getLinear() {
        return linear;
    }

    @SuppressWarnings("unused")
    public void setLinear(float linear) {
        this.linear = linear;
    }

    public float getExponent() {
        return exponent;
    }

    @SuppressWarnings("unused")
    public void setExponent(float exponent) {
        this.exponent = exponent;
    }
}
