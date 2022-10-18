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
					   removedBullet = 10;
	
	private static float bulletAngle = 0.0f;
	
	private static Entity bulletEntity;
	
	private static long time,
						cooldownTime = 250,
						lastAttack = 0;
	
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
		// 0, 0, -400f is center of terrain^^
		entities.add(new Entity(bulletModel, new Vector3f(0,0,-5f), new Vector3f(0,0,0), 0));

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
	}
	
	public static void setTankPos(float x, float z, float y) {
			entities.set(0,new Entity(tankTopModel, new Vector3f(x,1.3f,z), new Vector3f(0,y,0), 1));
			entities.set(1,new Entity(tankBotModel, new Vector3f(x,1.3f,z), new Vector3f(0,y,0), 1));
		return;
	}
	
	public static void tankDirect(float x, float z) {
		if(!spectator) {
			if((window.isKeyPressed(GLFW.GLFW_KEY_W) && window.isKeyPressed(GLFW.GLFW_KEY_A))) {
				entities.set(1,new Entity(tankBotModel, new Vector3f(x,1.3f,z), new Vector3f(0,225,0), 1));
				entities.set(0,new Entity(tankTopModel, new Vector3f(x,1.3f,z), new Vector3f(0,225,0), 1));
			}
			else if((window.isKeyPressed(GLFW.GLFW_KEY_W) && window.isKeyPressed(GLFW.GLFW_KEY_D))) {
				entities.set(1,new Entity(tankBotModel, new Vector3f(x,1.3f,z), new Vector3f(0,135,0), 1));
				entities.set(0,new Entity(tankTopModel, new Vector3f(x,1.3f,z), new Vector3f(0,135,0), 1));
			}
			else if((window.isKeyPressed(GLFW.GLFW_KEY_D) && window.isKeyPressed(GLFW.GLFW_KEY_S))) {
				entities.set(1,new Entity(tankBotModel, new Vector3f(x,1.3f,z), new Vector3f(0,45,0), 1));
				entities.set(0,new Entity(tankTopModel, new Vector3f(x,1.3f,z), new Vector3f(0,45,0), 1));
			}
			else if((window.isKeyPressed(GLFW.GLFW_KEY_A) && window.isKeyPressed(GLFW.GLFW_KEY_S))) {
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
		if(!spectator) {
			if((window.isKeyPressed(GLFW.GLFW_KEY_UP) && window.isKeyPressed(GLFW.GLFW_KEY_LEFT))) 
				entities.set(0,new Entity(tankTopModel, new Vector3f(x,1.3f,z), new Vector3f(0,225,0), 1));
			else if((window.isKeyPressed(GLFW.GLFW_KEY_UP) && window.isKeyPressed(GLFW.GLFW_KEY_RIGHT)))
				entities.set(0,new Entity(tankTopModel, new Vector3f(x,1.3f,z), new Vector3f(0,135,0), 1));
			else if((window.isKeyPressed(GLFW.GLFW_KEY_RIGHT) && window.isKeyPressed(GLFW.GLFW_KEY_DOWN)))
				entities.set(0,new Entity(tankTopModel, new Vector3f(x,1.3f,z), new Vector3f(0,45,0), 1));
			else if((window.isKeyPressed(GLFW.GLFW_KEY_LEFT) && window.isKeyPressed(GLFW.GLFW_KEY_DOWN)))
				entities.set(0,new Entity(tankTopModel, new Vector3f(x,1.3f,z), new Vector3f(0,315,0), 1));
			else if(window.isKeyPressed(GLFW.GLFW_KEY_UP))
				entities.set(0,new Entity(tankTopModel, new Vector3f(x,1.3f,z), new Vector3f(0,180,0), 1));
			else if(window.isKeyPressed(GLFW.GLFW_KEY_LEFT)) 
				entities.set(0,new Entity(tankTopModel, new Vector3f(x,1.3f,z), new Vector3f(0,270,0), 1));
			else if(window.isKeyPressed(GLFW.GLFW_KEY_DOWN)) 
				entities.set(0,new Entity(tankTopModel, new Vector3f(x,1.3f,z), new Vector3f(0,0,0), 1));
			else if(window.isKeyPressed(GLFW.GLFW_KEY_RIGHT)) 
				entities.set(0,new Entity(tankTopModel, new Vector3f(x,1.3f,z), new Vector3f(0,90,0), 1));
			if(window.isKeyPressed(GLFW.GLFW_KEY_SPACE)) {
				bulletEntity = new Entity(bulletModel, new Vector3f(x,1.3f,z), new Vector3f(0,getRot(0) - 90,-90), 1);
				time = System.currentTimeMillis();	
				if(time > lastAttack + cooldownTime) {
					bulletNumber = entities.size();
					entities.add(bulletNumber, bulletEntity);
					bulletInside = true;
					lastAttack = time;
				}
			}
		}
		
		for(int bullet = 2; bullet < entities.size(); bullet++) {
			if(bulletInside || (bullet < removedBullet)) {
				bulletAngle = entities.get(bullet).getRotation().y + 90;
				switch((int)bulletAngle) {
				case 0:
					entities.get(bullet).incPos(0, 0, 1);
					break;
				case 45:
					entities.get(bullet).incPos(1, 0, 1);
					break;
				case 90:
					entities.get(bullet).incPos(1, 0, 0);
					break;
				case 135:
					entities.get(bullet).incPos(1, 0, -1);
					break;
				case 180:
					entities.get(bullet).incPos(0, 0, -1);
					break;
				case 225:
					System.out.println("TEST");
					entities.get(bullet).incPos(-1, 0, -1);
					break;
				case 270:
					entities.get(bullet).incPos(-1, 0, 0);
					break;
				case 315:
					entities.get(bullet).incPos(-1, 0, 1);
					break;
				}
				
				if((entities.get(bullet).getPos().x <= -Consts.X_BORDER || entities.get(bullet).getPos().x >= Consts.X_BORDER)
				 ||(entities.get(bullet).getPos().z <= -Consts.Z_BORDER || entities.get(bullet).getPos().z >= 0)) {
					//entities.remove(i + 2);
					//
					System.out.println("BULLET BEING REMOVED" + bullet);
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
	
	public static float getPositionX(float xcord) {
		xcord = entities.get(0).getPos().x;
		return xcord;
	}
	
	public static float getPositionZ(float zcord) {
		zcord =  entities.get(0).getPos().z;
		return zcord;
	}

	@Override
	public void input() {
		cameraInc.set(0,0,0);
		modelInc.set(0,0,0);
		
		if(window.isKeyPressed(GLFW.GLFW_KEY_V)) {
			try {
				Thread.sleep(200);
			} catch (InterruptedException e) {
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
		entities.get(i).incRotation(0.0f, 0.0f, 0.0f);	
	//Rotation Control ^^
		//spotAngle += spotInc * 0.05f;
		//if(spotAngle > 4) {
		//	spotInc = -1;
		//}
		//else if(spotAngle <= -4)
		//	spotInc = 1;

		//double spotAngleRad = Math.toRadians(spotAngle);
		//Vector3f coneDir = spotLights[0].getPointLight().getPosition();
		//coneDir.y = (float) Math.sin(spotAngleRad);
		/*
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
		
		*/
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
	
	public static float getRot(int index) {
		
		return entities.get(index).getRotation().y;
	}
	
	public Model setModel(String modelOBJ, String texture) throws Exception{
		Model model = loader.loadOBJModel(modelOBJ);
		model.setTexture(new Texture(loader.loadTexture(texture)), 1f);
		return model;
	}
}