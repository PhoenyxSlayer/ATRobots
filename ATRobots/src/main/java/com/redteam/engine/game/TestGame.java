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

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;

public class TestGame implements ILogic{
	
	private final RenderManager renderer;
	private final ObjectLoader loader;
	public static Window window;
	
	private static List<Entity> entities;
	private List<Terrain> terrains;

	private Camera camera;

	//private float lightAngle, spotAngle, spotInc;
	private DirectionalLight directionalLight;
	private PointLight[] pointLights;
	private SpotLight[] spotLights;
	
	public static Model tankTopModel, tankBotModel, bulletModel;
	
	private static boolean spectator = false,
						   bulletInside;
	
	private float cameraSpeed;

	Vector3f cameraInc, modelInc;
	
	private static int bulletNumber,
					   removedBullet = 10,
					   entityCount;
	
	private static float bulletAngle = 0.0f,
						 angle = 0;
	
	private static Entity bulletEntity;
	
	public TestGame() {
		renderer = new RenderManager();
		window = ATRobots.getWindow();
		loader = new ObjectLoader();
		camera = new Camera();
		cameraInc = new Vector3f(0,0,0);
		cameraInc.set(0,0,0);
		modelInc = new Vector3f(0,0,0);
		//lightAngle = -90;
	}
	
	@Override
	public void init() throws Exception {
		renderer.init();
		
		bulletModel = setModel("/models/bulletFixed.obj", "textures/bullet.png");
		
		tankTopModel = setModel("/models/tankTop.obj", "textures/Camo.jpg");
		tankBotModel = setModel("/models/tankBot.obj", "textures/Camo.jpg");
		terrains = new ArrayList<>();
		Terrain terrain = new Terrain(new Vector3f(-Consts.X_BORDER,0,-Consts.Z_BORDER), loader, new Material(new Texture(loader.loadTexture("textures/concrete.jpg")), 0.1f));
		//Terrain terrain2 = new Terrain(new Vector3f(-800,-1,-800), loader, new Material(new Texture(loader.loadTexture("textures/checkerboard.png")), 0.1f));
		terrains.add(terrain); //terrains.add(terrain2);

		entities = new ArrayList<>();
		entities.add(new Entity(tankTopModel, new Vector3f(0f,1.3f,-(float)Consts.Z_BORDER / 2), new Vector3f(0,0,0), 1));
		entities.add(new Entity(tankBotModel, new Vector3f(0f,1.3f,-(float)Consts.Z_BORDER / 2), new Vector3f(0,0,0), 1));
		

		float lightIntensity = 1.0f;
		// point light
		Vector3f lightPosition = new Vector3f(-0.5f,-0.5f,-3.2f);
		Vector3f lightColor = new Vector3f(1,1,1); 
		//PointLight pointLight = new PointLight(lightColor, lightPosition, lightIntensity, 0, 0, 1);

		// spot light
		//Vector3f coneDir = new Vector3f(0,0,1);
		//float cutoff = (float) Math.cos(Math.toRadians(180));
		//SpotLight spotLight = new SpotLight(new PointLight(lightColor, new Vector3f(0,0,1f),
												// lightIntensity, 0, 0 ,1), coneDir, cutoff);

		//SpotLight spotLight1 = new SpotLight(new PointLight(lightColor, lightPosition, lightIntensity,
		//											0, 0, 1), coneDir, cutoff);
		//spotLight1.getPointLight().setPosition(new Vector3f(0.5f, 0.5f, -3.6f));

		// directional light
		lightPosition = new Vector3f(-1, -10, 0);
		lightColor = new Vector3f(1,1,1);
		directionalLight = new DirectionalLight(lightColor, lightPosition, lightIntensity);

		//pointLights = new PointLight[]{pointLight};
		//spotLights = new SpotLight[]{spotLight, spotLight1};
		
		camera.setPosition(entities.get(0).getPos().x, entities.get(0).getPos().y + 50f, entities.get(0).getPos().z);
		camera.setRotation(90f, 0f, 0f);
		
		
		// BORDER CHECK
		
		
		entityCount = entities.size();
	}
	
	public static void setTankPos(float x, float z, float y) {
			entities.set(0,new Entity(tankTopModel, new Vector3f(x,1.3f,z), new Vector3f(0,y,0), 1));
			entities.set(1,new Entity(tankBotModel, new Vector3f(x,1.3f,z), new Vector3f(0,y,0), 1));
		return;
	}
	
	public static void tankDirect(float x, float z) {
		if(!spectator) {
			if((window.isKeyPressed(GLFW.GLFW_KEY_W) & window.isKeyPressed(GLFW.GLFW_KEY_A))) {
				entities.set(1,new Entity(tankBotModel, new Vector3f(x,1.3f,z), new Vector3f(0,225,0), 1));
				entities.set(0,new Entity(tankTopModel, new Vector3f(x,1.3f,z), new Vector3f(0,225,0), 1));
			}
			else if((window.isKeyPressed(GLFW.GLFW_KEY_W) & window.isKeyPressed(GLFW.GLFW_KEY_D))) {
				entities.set(1,new Entity(tankBotModel, new Vector3f(x,1.3f,z), new Vector3f(0,135,0), 1));
				entities.set(0,new Entity(tankTopModel, new Vector3f(x,1.3f,z), new Vector3f(0,135,0), 1));
			}
			else if((window.isKeyPressed(GLFW.GLFW_KEY_D) & window.isKeyPressed(GLFW.GLFW_KEY_S))) {
				entities.set(1,new Entity(tankBotModel, new Vector3f(x,1.3f,z), new Vector3f(0,45,0), 1));
				entities.set(0,new Entity(tankTopModel, new Vector3f(x,1.3f,z), new Vector3f(0,45,0), 1));
			}
			else if((window.isKeyPressed(GLFW.GLFW_KEY_A) & window.isKeyPressed(GLFW.GLFW_KEY_S))) {
				entities.set(1,new Entity(tankBotModel, new Vector3f(x,1.3f,z), new Vector3f(0,315,0), 1));
				entities.set(0,new Entity(tankTopModel, new Vector3f(x,1.3f,z), new Vector3f(0,315,0), 1));
			}
			else if(window.isKeyPressed(GLFW.GLFW_KEY_W)) {
				entities.set(1,new Entity(tankBotModel, new Vector3f(x,1.3f,z), new Vector3f(0,180,0), 1));
				entities.set(0,new Entity(tankTopModel, new Vector3f(x,1.3f,z), new Vector3f(0,180,0), 1));
			}
			else if(window.isKeyPressed(GLFW.GLFW_KEY_A)) {
				entities.set(1,new Entity(tankBotModel, new Vector3f(x,1.3f,z), new Vector3f(0,270,0), 1));
				entities.set(0,new Entity(tankTopModel, new Vector3f(x,1.3f,z), new Vector3f(0,270,0), 1));
			}
			else if(window.isKeyPressed(GLFW.GLFW_KEY_S)) {
				entities.set(1,new Entity(tankBotModel, new Vector3f(x,1.3f,z), new Vector3f(0,0,0), 1));
				entities.set(0,new Entity(tankTopModel, new Vector3f(x,1.3f,z), new Vector3f(0,0,0), 1));
			}
			else if(window.isKeyPressed(GLFW.GLFW_KEY_D)) {
				entities.set(1,new Entity(tankBotModel, new Vector3f(x,1.3f,z), new Vector3f(0,90,0), 1));
				entities.set(0,new Entity(tankTopModel, new Vector3f(x,1.3f,z), new Vector3f(0,90,0), 1));
			}
		}
		return;
	}
	
	public static void turretDirect(float x, float z) {
		angle = entities.get(0).getRotation().y();
		if(!spectator) {
			if((window.isKeyPressed(GLFW.GLFW_KEY_I) & window.isKeyPressed(GLFW.GLFW_KEY_J)))
				angle = 225;
			else if((window.isKeyPressed(GLFW.GLFW_KEY_I) & window.isKeyPressed(GLFW.GLFW_KEY_L)))
				angle = 135;
			else if((window.isKeyPressed(GLFW.GLFW_KEY_L) & window.isKeyPressed(GLFW.GLFW_KEY_K)))
				angle = 45;
			else if((window.isKeyPressed(GLFW.GLFW_KEY_J) & window.isKeyPressed(GLFW.GLFW_KEY_K)))
				angle = 315;
			else if(window.isKeyPressed(GLFW.GLFW_KEY_I))
				angle = 180;
			else if(window.isKeyPressed(GLFW.GLFW_KEY_J))
				angle = 270;
			else if(window.isKeyPressed(GLFW.GLFW_KEY_K))
				angle = 0;
			else if(window.isKeyPressed(GLFW.GLFW_KEY_L))
				angle = 90;
			entities.set(0,new Entity(tankTopModel, new Vector3f(x,1.3f,z), new Vector3f(0,angle,0), 1));
		}
		
		for(int bullet = entityCount; bullet < entities.size(); bullet++) {
			if(bulletInside || (bullet < removedBullet)) {
				bulletAngle = entities.get(bullet).getRotation().y + 90;
				System.out.println("BULLET <" + bullet + "> AT " + entities.get(bullet).getPos().x + ", "
																 + entities.get(bullet).getPos().y + ", "
																 + entities.get(bullet).getPos().z + "> "
																 + " AT ANGLE " + bulletAngle);
				switch((int)bulletAngle) {
				case 0:
					entities.get(bullet).incPos(0, 0, Consts.BULLET_SPEED);
					break;
				case 45:
					entities.get(bullet).incPos(Consts.BULLET_SPEED, 0, Consts.BULLET_SPEED);
					break;
				case 90:
					entities.get(bullet).incPos(Consts.BULLET_SPEED, 0, 0);
					break;
				case 135:
					entities.get(bullet).incPos(Consts.BULLET_SPEED, 0, -Consts.BULLET_SPEED);
					break;
				case 180:
					entities.get(bullet).incPos(0, 0, -Consts.BULLET_SPEED);
					break;
				case 225:
					entities.get(bullet).incPos(-Consts.BULLET_SPEED, 0, -Consts.BULLET_SPEED);
					break;
				case 270:
					entities.get(bullet).incPos(-Consts.BULLET_SPEED, 0, 0);
					break;
				case 315:
					entities.get(bullet).incPos(-Consts.BULLET_SPEED, 0, Consts.BULLET_SPEED);
					break;
				default:
					entities.get(bullet).incPos(-Consts.BULLET_SPEED, 0, -Consts.BULLET_SPEED);
					break;
				}
				
				if((entities.get(bullet).getPos().x < -Consts.X_BORDER || entities.get(bullet).getPos().x > Consts.X_BORDER)
				 ||(entities.get(bullet).getPos().z < -Consts.Z_BORDER || entities.get(bullet).getPos().z > 0)) {
					System.out.println("BULLET <" + bullet + "> BEING REMOVED AT <" + entities.get(bullet).getPos().x + ", "
							+ entities.get(bullet).getPos().y + ", "
							+ entities.get(bullet).getPos().z + ">");
					bulletInside = false;
					entities.remove(bullet);
					removedBullet = bullet;
				}
			}
			else {
				bullet--;
				bulletInside = true;
			}
		}
		return;
	}
	
	public static void borderCheck(float x, float z) throws IOException, UnsupportedAudioFileException, LineUnavailableException {
		float y = 0;
		if(x > Consts.X_BORDER){
			if((window.isKeyPressed(GLFW.GLFW_KEY_W) && window.isKeyPressed(GLFW.GLFW_KEY_D))) {
				y = 135;
			}
			else if((window.isKeyPressed(GLFW.GLFW_KEY_S) && window.isKeyPressed(GLFW.GLFW_KEY_D))){
				y = 45;
			}
			else if(window.isKeyPressed(GLFW.GLFW_KEY_D)){
				y = 90;
			}
			Engine.playSound("bloop_x.wav");
		 	TestGame.setTankPos(x - Consts.CAMERA_STEP * 2, z, y);
		}
		else if(x < -Consts.X_BORDER) {
			if((window.isKeyPressed(GLFW.GLFW_KEY_W) && window.isKeyPressed(GLFW.GLFW_KEY_A))) {
				y = 225;
			}
			else if((window.isKeyPressed(GLFW.GLFW_KEY_S) && window.isKeyPressed(GLFW.GLFW_KEY_A))){
				y = 315;
			}
			else if(window.isKeyPressed(GLFW.GLFW_KEY_A)){
				y = 270;
			}
			Engine.playSound("bloop_x.wav");
		 	TestGame.setTankPos(x + Consts.CAMERA_STEP * 2, z, y);
		}
		else if(z > 0) {
			if((window.isKeyPressed(GLFW.GLFW_KEY_S) && window.isKeyPressed(GLFW.GLFW_KEY_D))) {
				y = 45;
			}
			else if((window.isKeyPressed(GLFW.GLFW_KEY_S) && window.isKeyPressed(GLFW.GLFW_KEY_A))){
				y = 315;
			}
			else if(window.isKeyPressed(GLFW.GLFW_KEY_S)){
				y = 0;
			}
			Engine.playSound("bloop_x.wav");
		 	TestGame.setTankPos(x, z - Consts.CAMERA_STEP * 2, y);
		}
		else if(z <= -Consts.Z_BORDER) {
			if((window.isKeyPressed(GLFW.GLFW_KEY_W) && window.isKeyPressed(GLFW.GLFW_KEY_A))) {
				y = 225;
			}
			else if((window.isKeyPressed(GLFW.GLFW_KEY_W) && window.isKeyPressed(GLFW.GLFW_KEY_D))){
				y = 135;
			}
			else if(window.isKeyPressed(GLFW.GLFW_KEY_W)){
				y = 180;
			}
			Engine.playSound("bloop_x.wav");
		 	TestGame.setTankPos(x, z + Consts.CAMERA_STEP * 2, y);
		}
	}
	
	public static float getPositionX() {
		return entities.get(0).getPos().x;
	}
	
	public static float getPositionZ() {
		return entities.get(0).getPos().z;
	}
	
	public static float getTurretPositionX() {
		return entities.get(1).getPos().x;
	}
	
	public static float getTurretPositionZ() {
		return entities.get(1).getPos().z;
	}

	@Override
	public void input() {
		cameraInc.set(0,0,0);
		modelInc.set(0,0,0);
		
		GLFW.glfwSetKeyCallback(window.getWindowHandle(), (window, key, scancode, action, mods) -> {
				if(key == GLFW.GLFW_KEY_ESCAPE && action == GLFW.GLFW_RELEASE)
					GLFW.glfwSetWindowShouldClose(window, true);
				if(key == GLFW.GLFW_KEY_V && action == GLFW.GLFW_PRESS)
					spectator = spectator ? false: true;
				if(!spectator) {
					if(key == GLFW.GLFW_KEY_SPACE && action == GLFW.GLFW_PRESS) {
						bulletEntity = new Entity(bulletModel, new Vector3f(getPositionX(),2.55f,getPositionZ()), new Vector3f(0,angle - 90,-90), 1);
						bulletNumber = entities.size();
						entities.add(bulletNumber, bulletEntity);
						bulletInside = true;
					}
				}
		});
		
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

		
		for(Entity entity : entities) {
			renderer.processEntity(entity);
		}

		for(Terrain terrain : terrains) {
			renderer.processTerrain(terrain);
		}
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
		float x = getPositionX(), z = getPositionZ();
		
		
		
		tankDirect(x,z);
		turretDirect(x,z);
		try {
			borderCheck(x,z);
		} catch (IOException | UnsupportedAudioFileException | LineUnavailableException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void render() { renderer.render(camera, directionalLight, pointLights, spotLights); }

	@Override
	public void cleanup() {
		renderer.cleanup();
		loader.cleanup();
	}
	
	public static float getRot(int index) {
		
		return entities.get(index).getRotation().y;
	}
	
	public Model setModel(String modelOBJ, String texture) throws Exception{
		Model model = loader.loadOBJModel(modelOBJ);
		model.setTexture(new Texture(loader.loadTexture(texture)), 1f);
		return model;
	}
}