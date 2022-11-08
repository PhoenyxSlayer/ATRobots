package com.redteam.engine.core.rendering;

import com.redteam.engine.core.Camera;
import com.redteam.engine.core.Shader;
import com.redteam.engine.core.terrain.Terrain;
import com.redteam.engine.core.lighting.DirectionalLight;
import com.redteam.engine.core.lighting.PointLight;
import com.redteam.engine.core.lighting.SpotLight;
import com.redteam.engine.game.main.ATRobots;
import com.redteam.engine.utils.Consts;
import com.redteam.engine.utils.Transformation;
import com.redteam.engine.utils.Utils;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL20;
import org.lwjgl.opengl.GL30;

import java.util.ArrayList;
import java.util.List;

@SuppressWarnings("rawtypes")
public class TerrainRender implements IRenderer {

    Shader shader;

    private final List<Terrain> terrains;

    public TerrainRender() throws Exception {
        terrains = new ArrayList<>();
        shader = new Shader();
    }

    @Override
    public void init() throws Exception {
        shader.createVertexShader(Utils.loadResource("/shaders/terrain/terrain_vertex.vs"));
        shader.createFragmentShader(Utils.loadResource("/shaders/terrain/terrain_fragment.fs"));
        EntityRender.shaderCreation(shader);
        shader.createPointLightListUniform("pointLights", Consts.MAX_POINT_LIGHTS);
        shader.createSpotLightListUniform("spotLights", Consts.MAX_SPOT_LIGHTS);
    }

    @Override
    public void render(Camera camera, PointLight[] pointLights, SpotLight[] spotLights, DirectionalLight directionalLight) {
        shader.bind();
        shader.setUniform("projectionMatrix", ATRobots.getWindow().updateProjectionMatrix());
        RenderManager.renderLights(pointLights, spotLights, directionalLight, shader);
        drawTerrainList(camera);
    }

    private void drawTerrainList(Camera camera) {
        for(Terrain terrain : terrains) {
            bind(terrain.getModel());
            prepare(terrain, camera);
            GL11.glDrawElements(GL11.GL_TRIANGLES, terrain.getModel().getVertexCount(), GL11.GL_UNSIGNED_INT, 0);
            unbind();
        }
        terrains.clear();
        shader.unbind();
    }

    @Override
    public void render(Camera camera, DirectionalLight directionalLight) {
        shader.bind();
        shader.setUniform("projectionMatrix", ATRobots.getWindow().updateProjectionMatrix());
        RenderManager.renderLights(directionalLight, shader);
        drawTerrainList(camera);
    }

    @Override
    public void bind(Model model) {
        EntityRender.glVertexArrayCreation(model, shader);
    }

    @Override
    public void unbind() {
        GL20.glDisableVertexAttribArray(0);
        GL20.glDisableVertexAttribArray(1);
        GL20.glDisableVertexAttribArray(2);
        GL30.glBindVertexArray(0);
    }

    @Override
    public void prepare(Object terrain, Camera camera) {
        shader.setUniform("textureSampler", 0);
        shader.setUniform("transformationMatrix", Transformation.createTransformationMatrix((Terrain) terrain));
        shader.setUniform("viewMatrix", Transformation.getViewMatrix(camera));
    }

    @Override
    public void cleanup() {
        shader.cleanup();
    }

    public List<Terrain> getTerrain() {
        return terrains;
    }
}
