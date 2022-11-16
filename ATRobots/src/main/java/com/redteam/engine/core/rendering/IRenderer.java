package com.redteam.engine.core.rendering;

import com.redteam.engine.core.Camera;
import com.redteam.engine.core.lighting.DirectionalLight;
import com.redteam.engine.core.lighting.PointLight;
import com.redteam.engine.core.lighting.SpotLight;

public interface IRenderer<T> {

    void init() throws Exception;

    void render(Camera camera, PointLight[] pointLights, SpotLight[] spotLights,
                       DirectionalLight directionalLight);

    void render(Camera camera, DirectionalLight directionalLight);

    void bind(Model model);

    void unbind();

    void prepare(T t, Camera camera);

    void cleanup();
}
