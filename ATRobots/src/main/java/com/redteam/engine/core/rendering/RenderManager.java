package com.redteam.engine.core.rendering;

import com.redteam.engine.core.Camera;
import com.redteam.engine.core.Shader;
import com.redteam.engine.core.Window;
import com.redteam.engine.core.entity.Entity;
import com.redteam.engine.core.terrain.Terrain;
import com.redteam.engine.core.lighting.DirectionalLight;
import com.redteam.engine.core.lighting.PointLight;
import com.redteam.engine.core.lighting.SpotLight;
import com.redteam.engine.utils.Consts;
import org.lwjgl.opengl.GL11;

import com.redteam.engine.game.main.ATRobots;

import java.util.ArrayList;
import java.util.List;

import static org.lwjgl.opengl.GL11.glViewport;

public class RenderManager {
	
	private final Window window;
	private EntityRender entityRenderer;
	private TerrainRender terrainRenderer;
	
	public RenderManager() {
		window = ATRobots.getWindow();
	}
	
	public void init() throws Exception {
		entityRenderer = new EntityRender();
		terrainRenderer = new TerrainRender();
		entityRenderer.init();
		terrainRenderer.init();
	}

	public static void renderLights(PointLight[] pointLights, SpotLight[] spotLights,
							 DirectionalLight directionalLight, Shader shader) {
		shader.setUniform("ambientLight", Consts.AMBIENT_LIGHT);
		shader.setUniform("specularPower", Consts.SPECULAR_POWER);
		int numLights = spotLights != null ? spotLights.length : 0;
		for(int i = 0; i < numLights; i++) {
			shader.setUniform("spotLights", spotLights[i], i);
		}

		numLights = pointLights != null ? pointLights.length : 0;
		for(int i = 0; i < numLights; i++) {
			shader.setUniform("pointLights", pointLights[i], i);
		}
		shader.setUniform("directionalLight", directionalLight);
	}

	public static void renderLights(DirectionalLight directionalLight, Shader shader) {
		shader.setUniform("ambientLight", Consts.AMBIENT_LIGHT);
		shader.setUniform("specularPower", Consts.SPECULAR_POWER);
		shader.setUniform("directionalLight", directionalLight);
	}

	@SuppressWarnings("unused")
	public void render(Camera camera, DirectionalLight directionalLight, PointLight[] pointLights, SpotLight[] spotLights) {
		clear();

		if(window.isResize()) {
			glViewport(0, 0, window.getWidth(), window.getHeight());
			window.setResize(true);
		}

		entityRenderer.render(camera, pointLights, spotLights, directionalLight);
		terrainRenderer.render(camera, pointLights, spotLights, directionalLight);
	}

	public void render(Camera camera, DirectionalLight directionalLight) {
		clear();

		if(window.isResize()) {
			glViewport(0, 0, window.getWidth(), window.getHeight());
			window.setResize(true);
		}

		entityRenderer.render(camera, directionalLight);
		terrainRenderer.render(camera, directionalLight);
	}

	public void processEntity(Entity entity) {
		List<Entity> entityList = entityRenderer.getEntities().get(entity.getModel());
		if(entityList != null)
			entityList.add(entity);
		else {
			List<Entity> newEntityList = new ArrayList<>();
			newEntityList.add(entity);
			entityRenderer.getEntities().put(entity.getModel(), newEntityList);
		}
	}

	public void processTerrain(Terrain terrain) {
		terrainRenderer.getTerrain().add(terrain);
	}
	
	public void clear() {
		GL11.glClear(GL11.GL_COLOR_BUFFER_BIT | GL11.GL_DEPTH_BUFFER_BIT);
	}
	
	public void cleanup() {
		entityRenderer.cleanup();
		terrainRenderer.cleanup();
	}
}