package com.redteam.engine.core.rendering;

import com.redteam.engine.utils.Constants;
import org.joml.Vector4f;

public class Material {

    private Vector4f ambientColor, diffuseColor, specularColor;
    private float reflectance;
    private Texture texture;

    public Material() {
        this.ambientColor = Constants.DEFAULT_COLOR;
        this.diffuseColor = Constants.DEFAULT_COLOR;
        this.specularColor = Constants.DEFAULT_COLOR;
        this.texture = null;
        this.reflectance = 0;
    }

    public Material(Vector4f color, float reflectance) {
        this(color, color, color, null, reflectance);
    }

    public Material(Texture texture) {
        this(Constants.DEFAULT_COLOR, Constants.DEFAULT_COLOR, Constants.DEFAULT_COLOR, texture, 0);
    }

    public Material(Texture texture, float reflectance) {
        this(Constants.DEFAULT_COLOR, Constants.DEFAULT_COLOR, Constants.DEFAULT_COLOR, texture, reflectance);
    }

    public Material(Vector4f color, Texture texture, float reflectance) {
        this(color, color, color, texture, reflectance);
    }

    public Material(Vector4f ambientColor, Vector4f diffuseColor, Vector4f specularColor, Texture texture, float reflectance) {
        this.ambientColor = ambientColor;
        this.diffuseColor = diffuseColor;
        this.specularColor = specularColor;
        this.texture = texture;
        this.reflectance = reflectance;
    }

    public Vector4f getAmbientColor() {
        return ambientColor;
    }
    @SuppressWarnings("unused")
    public void setAmbientColor(Vector4f ambientColor) {
        this.ambientColor = ambientColor;
    }

    public Vector4f getDiffuseColor() {
        return diffuseColor;
    }
    @SuppressWarnings("unused")
    public void setDiffuseColor(Vector4f diffuseColor) {
        this.diffuseColor = diffuseColor;
    }

    public Vector4f getSpecularColor() {
        return specularColor;
    }
    @SuppressWarnings("unused")
    public void setSpecularColor(Vector4f specularColor) {
        this.specularColor = specularColor;
    }

    public float getReflectance() {
        return reflectance;
    }

    public void setReflectance(float reflectance) {
        this.reflectance = reflectance;
    }

    public Texture getTexture() {
        return texture;
    }

    public void setTexture(Texture texture) {
        this.texture = texture;
    }

    public boolean hasTexture() {
        return texture != null;
    }
}
