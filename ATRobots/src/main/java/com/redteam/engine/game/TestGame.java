package com.redteam.engine.game;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;

import org.joml.Vector2f;
import org.joml.Vector3f;
import org.lwjgl.glfw.GLFW;
import org.lwjgl.glfw.GLFWImage;

import com.redteam.engine.core.Camera;
import static com.redteam.engine.core.Engine.*;
import com.redteam.engine.core.ILogic;
import com.redteam.engine.core.MouseInput;
import com.redteam.engine.core.ObjectLoader;
import com.redteam.engine.core.Window;
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

import images.image_parser;
import sounds.Sound;

public class TestGame implements ILogic{
	
	private final RenderManager renderer;
	private final ObjectLoader loader;

	private static List<Entity> entities;
	private List<Terrain> terrains;

	private Camera camera;

	private DirectionalLight directionalLight;
	private PointLight[] pointLights;
	private SpotLight[] spotLights;
	
	private float cameraSpeed;

	Vector3f cameraInc, modelInc;
	
	public static Window window;
	
	public static Model tankTopModel, tankBotModel, bulletModel;
	
	private static boolean spectator = false,
						   bulletInside;
	
	private static int bulletNumber,
					   removedBullet = bulletNumber + 1,
					   entityCount;
	
	private static float tankAngle = 0.0f,
						 turretAngle = 0.0f,
						 bulletAngle = 0.0f;
	
	private static Entity bulletEntity;
	
	private static Map<String, Sound> sounds = new HashMap<>();
	
	private static float tankSpeed = (float) (Consts.MOVEMENT_SPEED * tick()),
						 bulletSpeed = (float) (Consts.BULLET_SPEED * tick());
	
	private final image_parser icon = image_parser.load_image("src/main/resources/images/test.png");
	
	
	public TestGame() {
		renderer = new RenderManager();
		window = ATRobots.getWindow();
		loader = new ObjectLoader();
		camera = new Camera();
		cameraInc = new Vector3f(0,0,0);
		cameraInc.set(0,0,0);
		modelInc = new Vector3f(0,0,0);
		
		return;
	}
	
	@Override
	public void init() throws Exception {
		renderer.init();
		
		GLFWImage iconGLFW = GLFWImage.malloc();
		GLFWImage.Buffer iconBF = GLFWImage.malloc(1);
        iconGLFW.set(icon.get_width(), icon.get_heigh(), icon.get_image());
        iconBF.put(0, iconGLFW);
        GLFW.glfwSetWindowIcon(window.getWindowHandle(), iconBF);
		
		bulletModel = setModel("/models/bullet.obj", "textures/bullet.png");
		
		tankTopModel = setModel("/models/tankTop.obj", "textures/Camo.jpg");
		tankBotModel = setModel("/models/tankBot.obj", "textures/Camo.jpg");
		terrains = new ArrayList<>();
		Terrain terrain = new Terrain(new Vector3f(-Consts.X_BORDER,0,-Consts.Z_BORDER), loader, new Material(new Texture(loader.loadTexture("textures/concrete.jpg")), 0.1f));
		terrains.add(terrain);

		entities = new ArrayList<>();
		entities.add(new Entity(tankTopModel, new Vector3f(0f,1.3f,-(float)Consts.Z_BORDER / 2), new Vector3f(0,0,0), 1));
		entities.add(new Entity(tankBotModel, new Vector3f(0f,1.3f,-(float)Consts.Z_BORDER / 2), new Vector3f(0,0,0), 1));
		
		float lightIntensity = 1.0f;
		Vector3f lightPosition = new Vector3f(-0.5f,-0.5f,-3.2f);
		Vector3f lightColor = new Vector3f(1,1,1); 
		lightPosition = new Vector3f(-1, -10, 0);
		lightColor = new Vector3f(1,1,1);
		directionalLight = new DirectionalLight(lightColor, lightPosition, lightIntensity);
		
		camera.setPosition(entities.get(0).getPos().x, entities.get(0).getPos().y + 50f, entities.get(0).getPos().z);
		camera.setRotation(90f, 0f, 0f);
		
		addSound("src/main/resources/sounds/bloop_x.ogg", false);
		
		entityCount = entities.size();
		
		return;
	}
	
	private static void setTankPos(float x, float z) {
		float y = getRotationY();
		entities.get(0).setPos(x, 1.3f, z);
		entities.get(0).setRotation(0, y, 0);
		entities.get(1).setPos(x, 1.3f, z);
		entities.get(1).setRotation(0, y, 0);
		return;
	}
	
	private static void turretDirect() {
		for(int bullet = entityCount; bullet < entities.size(); bullet++) {
			if(bulletInside || (bullet < removedBullet)) {
				bulletAngle = entities.get(bullet).getRotation().y + 90;
				System.out.println("BULLET <" + bullet + "> AT " + entities.get(bullet).getPos().x + ", "
																 + entities.get(bullet).getPos().y + ", "
																 + entities.get(bullet).getPos().z + "> "
																 + " AT ANGLE " + bulletAngle);
				switch((int)bulletAngle) {
					case 0:
						entities.get(bullet).incPos(0, 0, bulletSpeed);
						break;
					case 45:
						entities.get(bullet).incPos(bulletSpeed, 0, bulletSpeed);
						break;
					case 90:
						entities.get(bullet).incPos(bulletSpeed, 0, 0);
						break;
					case 135:
						entities.get(bullet).incPos(bulletSpeed, 0, -bulletSpeed);
						break;
					case 180:
						entities.get(bullet).incPos(0, 0, -bulletSpeed);
						break;
					case 225:
						entities.get(bullet).incPos(-bulletSpeed, 0, -bulletSpeed);
						break;
					case 270:
						entities.get(bullet).incPos(-bulletSpeed, 0, 0);
						break;
					case 315:
						entities.get(bullet).incPos(-bulletSpeed, 0, bulletSpeed);
						break;
					default:
						entities.get(bullet).incPos(-bulletSpeed, 0, -bulletSpeed);
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
	
	private static void borderCheck(float x, float z) {
		if((x < -Consts.X_BORDER) || (x > Consts.X_BORDER)
		 ||(z < -Consts.Z_BORDER) || (z > 0)){
			
			if(x > Consts.X_BORDER) {
				x -= tankSpeed;
			}
			else if(x < -Consts.X_BORDER) {
				x += tankSpeed;
			}
			else if(z > 0) {
				z -= tankSpeed;
			}
			else if(z < -Consts.Z_BORDER) {
				z += tankSpeed;
			}
			getSound("src/main/resources/sounds/bloop_x.ogg").play();
			setTankPos(x, z);
		}
		
		return;
	}

	@Override
	public void input() {
		cameraInc.set(0,0,0);
		modelInc.set(0,0,0);
		
		GLFW.glfwSetKeyCallback(window.getWindowHandle(), (window, key, scancode, action, mods) -> {
				if(key == GLFW.GLFW_KEY_ESCAPE && action == GLFW.GLFW_RELEASE) {
					System.out.println("EXITING");
					GLFW.glfwSetWindowShouldClose(window, true);
					return;
				}
				if(key == GLFW.GLFW_KEY_V && action == GLFW.GLFW_PRESS) {
					spectator = spectator ? false: true;
					System.out.println("SPECTATOR: " + spectator);
				}
					
				if(!spectator) {
					if(key == GLFW.GLFW_KEY_SPACE && action == GLFW.GLFW_PRESS) {
						float x = getPositionX(),
							  z = getPositionZ();
						switch((int)turretAngle) {
							case 0:
								z += 4.3f;
								break;
							case 45:
								x += 3.2f;
								z += 3.2f;
								break;
							case 90:
								x += 4.3f;
								break;
							case 135:
								x += 3.2f;
								z -= 3.2f;
								break;
							case 180:
								z -= 4.3f;
								break;
							case 225:
								x -= 3.2f;
								z -= 3.2f;
								break;
							case 270:
								x -= 4.3f;
								break;
							case 315:
								x -= 3.2f;
								z += 3.2f;
								break;
						}
						bulletEntity = new Entity(bulletModel, new Vector3f(x,2.55f,z), new Vector3f(0,turretAngle - 90,-90), 1);
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
			
			// TANK MOVEMENT + ROTATION
			if(window.isKeyPressed(GLFW.GLFW_KEY_W)) {
				tankAngle = 180;
				cameraInc.z = -tankSpeed; modelInc.z = -tankSpeed;
			}
			if(window.isKeyPressed(GLFW.GLFW_KEY_A)) {
				tankAngle = 270;
				cameraInc.x = -tankSpeed; modelInc.x = -tankSpeed;
			}
			if(window.isKeyPressed(GLFW.GLFW_KEY_S)) {
				tankAngle = 0;
				cameraInc.z = tankSpeed; modelInc.z = tankSpeed;
			}
			if(window.isKeyPressed(GLFW.GLFW_KEY_D)) {
				tankAngle = 90;
				cameraInc.x = tankSpeed; modelInc.x = tankSpeed;
			}
			if((window.isKeyPressed(GLFW.GLFW_KEY_W) & window.isKeyPressed(GLFW.GLFW_KEY_A))) {
				tankAngle = 225;
				cameraInc.z = -tankSpeed; modelInc.z = -tankSpeed;
				cameraInc.x = -tankSpeed; modelInc.x = -tankSpeed;
			}
			if((window.isKeyPressed(GLFW.GLFW_KEY_W) & window.isKeyPressed(GLFW.GLFW_KEY_D))) {
				tankAngle = 135;
				cameraInc.z = -tankSpeed; modelInc.z = -tankSpeed;
				cameraInc.x =  tankSpeed; modelInc.x = tankSpeed;
			}
			if((window.isKeyPressed(GLFW.GLFW_KEY_D) & window.isKeyPressed(GLFW.GLFW_KEY_S))) {
				tankAngle = 45;
				cameraInc.x = tankSpeed; modelInc.x = tankSpeed;
				cameraInc.z = tankSpeed; modelInc.z = tankSpeed;
			}
			if((window.isKeyPressed(GLFW.GLFW_KEY_A) & window.isKeyPressed(GLFW.GLFW_KEY_S))) {
				tankAngle = 315;
				cameraInc.x = -tankSpeed; modelInc.x = -tankSpeed;
				cameraInc.z =  tankSpeed; modelInc.z = tankSpeed;
			}
			entities.get(0).setRotation(0, tankAngle, 0);
			entities.get(1).setRotation(0, tankAngle, 0);
			
			// TURRET ROTATION
			turretAngle = entities.get(1).getRotation().y();
			
			if(window.isKeyPressed(GLFW.GLFW_KEY_I))
				turretAngle = 180;
			if(window.isKeyPressed(GLFW.GLFW_KEY_J))
				turretAngle = 270;
			if(window.isKeyPressed(GLFW.GLFW_KEY_L))
				turretAngle = 90;
			if(window.isKeyPressed(GLFW.GLFW_KEY_K))
				turretAngle = 0;
			if((window.isKeyPressed(GLFW.GLFW_KEY_I) & window.isKeyPressed(GLFW.GLFW_KEY_J)))
				turretAngle = 225;
			if((window.isKeyPressed(GLFW.GLFW_KEY_I) & window.isKeyPressed(GLFW.GLFW_KEY_L)))
				turretAngle = 135;
			if((window.isKeyPressed(GLFW.GLFW_KEY_L) & window.isKeyPressed(GLFW.GLFW_KEY_K)))
				turretAngle = 45;
			if((window.isKeyPressed(GLFW.GLFW_KEY_J) & window.isKeyPressed(GLFW.GLFW_KEY_K)))
				turretAngle = 315;
			entities.get(0).setRotation(0, turretAngle, 0);
		} else {
			cameraSpeed = (Consts.CAMERA_STEP * 2);
			if(window.isKeyPressed(GLFW.GLFW_KEY_W))
				cameraInc.z = -tankSpeed;
			if(window.isKeyPressed(GLFW.GLFW_KEY_S))
				cameraInc.z = tankSpeed;
	
			if(window.isKeyPressed(GLFW.GLFW_KEY_A))
				cameraInc.x = -tankSpeed;
			if(window.isKeyPressed(GLFW.GLFW_KEY_D))
				cameraInc.x = tankSpeed;
			
			if(window.isKeyPressed(GLFW.GLFW_KEY_LEFT_CONTROL))
				cameraInc.y = -tankSpeed;
			if(window.isKeyPressed(GLFW.GLFW_KEY_SPACE))
				cameraInc.y = tankSpeed;
		}
		
		return;
	}

	@Override
	public void update(double interval, MouseInput mouseInput) {
		tankSpeed = (float) (Consts.MOVEMENT_SPEED * tick());
		bulletSpeed = (float) (Consts.BULLET_SPEED * tick());
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
		entities.get(1).incPos(modelInc.x * cameraSpeed,
							   modelInc.y * cameraSpeed,
							   modelInc.z * cameraSpeed);

		if(mouseInput.isRightButtonPress() && spectator) {
			Vector2f rotVec = mouseInput.getDisplVec();
			camera.moveRotation(rotVec.x * Consts.MOUSE_SENSITIVITY, rotVec.y * Consts.MOUSE_SENSITIVITY, 0);
		}
		
		turretDirect();
		borderCheck(getPositionX(),getPositionZ());
		
		return;
	}

	@Override
	public void render() { renderer.render(camera, directionalLight, pointLights, spotLights); return; }

	@Override
	public void cleanup() {
		renderer.cleanup();
		loader.cleanup();
		return;
	}
	
	private Model setModel(String modelOBJ, String texture) throws Exception{
		Model model = loader.loadOBJModel(modelOBJ);
		model.setTexture(new Texture(loader.loadTexture(texture)), 1f);
		return model;
	}
	
	private static float getPositionX() {
		return entities.get(1).getPos().x;
	}
	
	@SuppressWarnings("unused")
	private static float getPositionY() {
		return entities.get(1).getPos().y;
	}
	
	private static float getPositionZ() {
		return entities.get(1).getPos().z;
	}
	
	@SuppressWarnings("unused")
	private static float getRotationX() {
		return entities.get(1).getRotation().x;
	}
	
	private static float getRotationY() {
		return entities.get(1).getRotation().y;
	}
	
	@SuppressWarnings("unused")
	private static float getRotationZ() {
		return entities.get(1).getRotation().z;
	}
	
	@SuppressWarnings("unused")
	private static float getTurretPositionX() {
		return entities.get(0).getPos().x;
	}
	
	@SuppressWarnings("unused")
	private static float getTurretPositionY() {
		return entities.get(0).getPos().y;
	}
	
	@SuppressWarnings("unused")
	private static float getTurretPositionZ() {
		return entities.get(0).getPos().z;
	}
	
	@SuppressWarnings("unused")
	private static float getTurretRotationX() {
		return entities.get(0).getRotation().x;
	}
	
	@SuppressWarnings("unused")
	private static float getTurretRotationY() {
		return entities.get(0).getRotation().y;
	}
	
	@SuppressWarnings("unused")
	private static float getTurretRotationZ() {
		return entities.get(0).getRotation().z;
	}
	
	@SuppressWarnings("unused")
	private static void playSound(String soundFile) throws IOException, UnsupportedAudioFileException, LineUnavailableException {
	    File f = new File(soundFile);
	    AudioInputStream audioIn = AudioSystem.getAudioInputStream(f.toURI().toURL());  
	    Clip clip = AudioSystem.getClip();
	    clip.open(audioIn);
	    clip.start();
	}
	
	public static Collection<Sound> getAllSounds() {
		return sounds.values();  
	}
	
	public static Sound getSound(String soundFile) {
		File file = new File(soundFile);
		if(sounds.containsKey(file.getAbsolutePath())) {
			return sounds.get(file.getAbsolutePath());
		} else {
			assert false : "Sound file not added '" + soundFile + "'";
		}
		
		return null;
	}
	
	public static Sound addSound(String soundFile, boolean loops) {
		File file = new File(soundFile);
		if(sounds.containsKey(file.getAbsolutePath())) {
			return sounds.get(file.getAbsolutePath());
		} else {
			Sound sound = new Sound(file.getAbsolutePath(), loops);
			sounds.put(file.getAbsolutePath(), sound);
			return sound;
		}
	}
}