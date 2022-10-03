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
	
	private Model tankModel, bulletModel;
	
	private boolean spectator = false;
	
	private float cameraSpeed;

	Vector3f cameraInc, modelInc;
	
	public TestGame() {
		renderer = new RenderManager();
		window = ATRobots.getWindow();
		loader = new ObjectLoader();
		camera = new Camera();
		cameraInc = new Vector3f(0,0,0);
		cameraInc.set(0,0,0);
		modelInc = new Vector3f(0,0,0);
		lightAngle = -90;
	}
	
	@Override
	public void init() throws Exception {
		renderer.init();
		
		bulletModel = setModel("/models/bulletFixed.obj", "textures/bullet.png");
		tankModel = setModel("/models/tank.obj", "textures/grassblock.png");
		terrains = new ArrayList<>();
		Terrain terrain = new Terrain(new Vector3f(-400,-1,-800), loader, new Material(new Texture(loader.loadTexture("textures/checkerboard.png")), 0.1f));
		//Terrain terrain2 = new Terrain(new Vector3f(-800,-1,-800), loader, new Material(new Texture(loader.loadTexture("textures/checkerboard.png")), 0.1f));
		terrains.add(terrain); //terrains.add(terrain2);

		entities = new ArrayList<>();
		entities.add(new Entity(tankModel, new Vector3f(-5f,0,-5f), new Vector3f(0,0,0), 1));
		entities.add(new Entity(bulletModel, new Vector3f(0,0,-5f), new Vector3f(0,0,0), 1));

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
		
		camera.setPosition(0, 50, 0);
		camera.setRotation(90, 0, 0);
	}

	@Override
	public void input() {
		cameraInc.set(0,0,0);
		modelInc.set(0,0,0);
		
		if(window.isKeyPressed(GLFW.GLFW_KEY_V)) {
			try {
				Thread.sleep(200);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			spectator = spectator ? false: true;
		}
		
		if(!spectator) {
			cameraSpeed = Consts.CAMERA_STEP;
			camera.setPosition(entities.get(0).getPos().x,50f,entities.get(0).getPos().z);
			camera.setRotation(90, 0, 0);
			if(window.isKeyPressed(GLFW.GLFW_KEY_W)) {
				cameraInc.z = -1;
				modelInc.z = -1;
			}
			if(window.isKeyPressed(GLFW.GLFW_KEY_S)) {
				cameraInc.z = 1;
				modelInc.z = 1;
			}
	
			if(window.isKeyPressed(GLFW.GLFW_KEY_A)) {
				cameraInc.x = -1;
				modelInc.x = -1;
			}
			if(window.isKeyPressed(GLFW.GLFW_KEY_D)) {
				cameraInc.x = 1;
				modelInc.x = 1;
			}
		} else {
			cameraSpeed = (Consts.CAMERA_STEP * 5);
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
		}
		/*
		float lightPos = spotLights[0].getPointLight().getPosition().z;
		if(window.isKeyPressed((GLFW.GLFW_KEY_N)))
			spotLights[0].getPointLight().getPosition().z = lightPos + 0.1f;
		if(window.isKeyPressed((GLFW.GLFW_KEY_M)))
			spotLights[0].getPointLight().getPosition().z = lightPos - 0.1f;
			
		*/
	}

	@Override
	public void update(float interval, MouseInput mouseInput) {
		camera.movePosition(cameraInc.x * cameraSpeed,
				  			cameraInc.y * cameraSpeed,
							cameraInc.z * cameraSpeed);
		
		entities.get(0).incPos(modelInc.x * cameraSpeed,
							   modelInc.y * cameraSpeed,
	  					       modelInc.z * cameraSpeed);
		
		if(mouseInput.isRightButtonPress() && spectator) {
			Vector2f rotVec = mouseInput.getDisplVec();
			camera.moveRotation(rotVec.x * Consts.MOUSE_SENSITIVITY, rotVec.y * Consts.MOUSE_SENSITIVITY, 0);
		}
		for(int i = 0; i < entities.size(); i++)
			entities.get(i).incRotation(0.0f, 0.5f, 0.0f);
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
	
	public Model getTankModel() {
		return tankModel;
	}
	
	public Model getBulletModel() {
		return bulletModel;
	}
	
	public Model setModel(String modelOBJ, String texture) throws Exception{
		Model model = loader.loadOBJModel(modelOBJ);
		model.setTexture(new Texture(loader.loadTexture(texture)), 1f);
		return model;
	}
}