package com.redteam.engine.game;

import com.redteam.engine.core.*;
import com.redteam.engine.core.entity.Entity;
import com.redteam.engine.core.entity.Material;
import com.redteam.engine.core.entity.Model;
import com.redteam.engine.core.entity.Texture;

import com.redteam.engine.core.entity.terrain.Terrain;
import com.redteam.engine.core.lighting.DirectionalLight;
import com.redteam.engine.core.lighting.PointLight;
import com.redteam.engine.core.lighting.SpotLight;
import com.redteam.engine.core.rendering.RenderManager;
import com.redteam.engine.utils.Consts;
import org.joml.Vector2f;
import org.joml.Vector3f;
import org.lwjgl.glfw.GLFW;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class TestGame implements ILogic {
	
	private final RenderManager renderer;
	private final ObjectLoader loader;
	private final Window window;
	
	private List<Entity> entities;
	private List<Terrain> terrains;

	private Camera camera;

	private float lightAngle, spotAngle = 0, spotInc = 1;
	private DirectionalLight directionalLight;
	private PointLight[] pointLights;
	private SpotLight[] spotLights;

	Vector3f cameraInc;
	
	public TestGame() {
		renderer = new RenderManager();
		window = ATRobots.getWindow();
		loader = new ObjectLoader();
		camera = new Camera();
		cameraInc = new Vector3f(0,0,0);
		lightAngle = -90;
	}
	
	@Override
	public void init() throws Exception {
		renderer.init();

		Model model = loader.loadOBJModel("/models/tank.obj");
		model.setTexture(new Texture(loader.loadTexture("textures/grassblock.png")), 1f);

		terrains = new ArrayList<>();
		Terrain terrain = new Terrain(new Vector3f(0,-1,-800), loader, new Material(new Texture(loader.loadTexture("textures/blue.png")), 0.1f));
		Terrain terrain2 = new Terrain(new Vector3f(-800,-1,-800), loader, new Material(new Texture(loader.loadTexture("textures/checkerboard.png")), 0.1f));
		terrains.add(terrain); terrains.add(terrain2);

		entities = new ArrayList<>();
		Random rnd = new Random();
		for(int i = 0; i < 200; i++) {
			float x = rnd.nextFloat() * 100 - 50;
			float y = rnd.nextFloat() * 100 - 50;
			float z = rnd.nextFloat() * -300;
			entities.add(new Entity(model, new Vector3f(x,y,z),
					new Vector3f(rnd.nextFloat() * 180, rnd.nextFloat() * 180, 0), 1));
		}
		entities.add(new Entity(model, new Vector3f(0,0,-2f), new Vector3f(0,0,0), 1));

		float lightIntensity = 1.0f;
		// point light
		Vector3f lightPosition = new Vector3f(-0.5f,-0.5f,-3.2f);
		Vector3f lightColor = new Vector3f(1,1,1);
		PointLight pointLight = new PointLight(lightColor, lightPosition, lightIntensity, 0, 0, 1);

		// spot light
		Vector3f coneDir = new Vector3f(0,0,1);
		float cutoff = (float) Math.cos(Math.toRadians(180));
		SpotLight spotLight = new SpotLight(new PointLight(lightColor, new Vector3f(0,0,1f),
												 lightIntensity, 0, 0 ,1), coneDir, cutoff);

		SpotLight spotLight1 = new SpotLight(new PointLight(lightColor, lightPosition, lightIntensity,
													0, 0, 1), coneDir, cutoff);
		spotLight1.getPointLight().setPosition(new Vector3f(0.5f, 0.5f, -3.6f));

		// directional light
		lightPosition = new Vector3f(-1, -10, 0);
		lightColor = new Vector3f(1,1,1);
		directionalLight = new DirectionalLight(lightColor, lightPosition, lightIntensity);

		pointLights = new PointLight[]{pointLight};
		spotLights = new SpotLight[]{spotLight, spotLight1};
	}

	@Override
	public void input() {
		cameraInc.set(0,0,0);
		if(window.isKeyPressed(GLFW.GLFW_KEY_W))
			cameraInc.z = -1;
		if(window.isKeyPressed(GLFW.GLFW_KEY_S))
			cameraInc.z = 1;

		if(window.isKeyPressed(GLFW.GLFW_KEY_A))
			cameraInc.x = -1;
		if(window.isKeyPressed(GLFW.GLFW_KEY_D))
			cameraInc.x = 1;

		if(window.isKeyPressed(GLFW.GLFW_KEY_LEFT_CONTROL))
			cameraInc.y = -1;
		if(window.isKeyPressed(GLFW.GLFW_KEY_SPACE))
			cameraInc.y = 1;

		float lightPos = spotLights[0].getPointLight().getPosition().z;
		if(window.isKeyPressed((GLFW.GLFW_KEY_N)))
			spotLights[0].getPointLight().getPosition().z = lightPos + 0.1f;
		if(window.isKeyPressed((GLFW.GLFW_KEY_M)))
			spotLights[0].getPointLight().getPosition().z = lightPos - 0.1f;
	}

	@Override
	public void update(float interval, MouseInput mouseInput) {
		camera.movePosition(cameraInc.x * Consts.CAMERA_STEP,
				  			cameraInc.y * Consts.CAMERA_STEP,
							cameraInc.z * Consts.CAMERA_STEP);

		if(mouseInput.isRightButtonPress()) {
			Vector2f rotVec = mouseInput.getDisplVec();
			camera.moveRotation(rotVec.x * Consts.MOUSE_SENSITIVITY, rotVec.y * Consts.MOUSE_SENSITIVITY, 0);
		}

		//entity.incRotation(0.0f, 0.25f, 0.0f);
		spotAngle += spotInc * 0.05f;
		if(spotAngle > 4) {
			spotInc = -1;
		}
		else if(spotAngle <= -4)
			spotInc = 1;

		double spotAngleRad = Math.toRadians(spotAngle);
		Vector3f coneDir = spotLights[0].getPointLight().getPosition();
		coneDir.y = (float) Math.sin(spotAngleRad);

		lightAngle += 0.5f;
		if(lightAngle > 90) {
			directionalLight.setIntensity(0);
			if(lightAngle >= 360)
				lightAngle = -90;
		} else if (lightAngle <= -80 || lightAngle >= 80) {
			float factor = 1 - (Math.abs(lightAngle) - 80) / 10.0f;
			directionalLight.setIntensity(factor);
			directionalLight.getColor().y = Math.max(factor, 0.9f);
			directionalLight.getColor().z = Math.max(factor, 0.5f);
		} else {
			directionalLight.setIntensity(1);
			directionalLight.getColor().x = 1;
			directionalLight.getColor().y = 1;
			directionalLight.getColor().z = 1;
		}
		double angRad = Math.toRadians(lightAngle);
		directionalLight.getDirection().x = (float) Math.sin(angRad);
		directionalLight.getDirection().y = (float) Math.cos(angRad);

		for(Entity entity : entities) {
			renderer.processEntity(entity);
		}

		for(Terrain terrain : terrains) {
			renderer.processTerrain(terrain);
		}
	}

	@Override
	public void render() { renderer.render(camera, directionalLight, pointLights, spotLights); }

	@Override
	public void cleanup() {
		renderer.cleanup();
		loader.cleanup();
	}
	
}