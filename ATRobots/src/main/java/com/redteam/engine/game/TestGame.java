package com.redteam.engine.game;

import java.io.File;
import java.io.IOException;
import java.util.*;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;

import org.joml.Vector2f;
import org.joml.Vector3f;

import static com.redteam.engine.utils.Consts.CAMERA_STEP;
import static org.lwjgl.glfw.GLFW.*;
import org.lwjgl.glfw.GLFWKeyCallback;

import com.redteam.engine.core.Camera;
import static com.redteam.engine.core.Engine.*;
import com.redteam.engine.core.ILogic;
import com.redteam.engine.core.MouseInput;
import com.redteam.engine.core.ObjectLoader;
import com.redteam.engine.core.Window;
import com.redteam.engine.core.entity.Entity;
import com.redteam.engine.core.entity.HittableEntity;
import com.redteam.engine.core.entity.Material;
import com.redteam.engine.core.entity.Model;
import com.redteam.engine.core.entity.TankEntity;
import com.redteam.engine.core.entity.Texture;
import com.redteam.engine.core.entity.terrain.Terrain;
import com.redteam.engine.core.lighting.DirectionalLight;
import com.redteam.engine.core.rendering.RenderManager;
import com.redteam.engine.utils.Consts;

import com.redteam.engine.core.rendering.image_parser;
import com.redteam.engine.core.sound.Sound;

public class TestGame implements ILogic{
	
	private final RenderManager renderer;
	public final ObjectLoader loader;

	public static Set<Entity> entities = new HashSet<Entity>();
	public static Set<Terrain> terrains = new HashSet<Terrain>();

	public static Camera camera;

	private DirectionalLight directionalLight;

	private final Vector3f cameraInc = new Vector3f(0,0,0);

	public static Window window;
	
	public static Model tankModel;
	
	private static boolean spectator = false;
	
	public static int entityCount;

	private static final Map<String, Sound> sounds = new HashMap<>();
	
	private float cameraSpeed = (float)((CAMERA_STEP) * tick());
	
	private final image_parser icon = image_parser.load_image("src/main/resources/images/test.png");
	
	public TestGame() {
		renderer = new RenderManager();
		window = ATRobots.getWindow();
		loader = new ObjectLoader();
		camera = new Camera();
	}
	
	@Override
	public void init() throws Exception {
		renderer.init();
		window.updateLogo(icon);

		// Model Rendering
		tankModel = setModel("/models/tank.obj", "textures/Camo.jpg");

		// Terrain Adding
		Terrain terrain = new Terrain(new Vector3f(-Consts.X_BORDER,0,-Consts.Z_BORDER), loader, new Material(new Texture(loader.loadTexture("textures/concrete.jpg")), 0.1f));
		terrains.add(terrain);

		// Entity Adding
		entities.add(new TankEntity("tank",										// ID
									new Vector3f(70f,1.3f,-50f),				// POSITION
									new Vector3f(0,0,0),					// ROTATION
									1										// SCALE
									));

		entities.add(new HittableEntity("dummyTank",							// ID
											tankModel,								// MODEL
											new Vector3f(50f,1.3f,-50f),		// POSITION
											new Vector3f(0,0,0),			// ROTATION
											1,								// SCALE
											5f							// HITBOX SCALE
											));
		
		// Light for the Map
		// 1st Argument Light Color, 2nd Light Positioning, 3rd Light Intensity
		directionalLight = new DirectionalLight(new Vector3f(1,1,1), new Vector3f(-1, -10, 0), 1.0f);

	}

	private GLFWKeyCallback keyCallback;

	@Override
	public void input() {

		keyCallback = new GLFWKeyCallback() {
			@Override
			public void invoke(long window, int key, int scancode, int action, int mods) {
				if (action == GLFW_RELEASE) {
					if (key == GLFW_KEY_ESCAPE) {
						System.out.println("EXITING");
						glfwSetWindowShouldClose(window, true);
						// QUITS GAME
					}
					if (key == GLFW_KEY_V) {
						spectator = !spectator;                        // SPECTATOR MODE W/ V PRESS
					}
				}
			}
		};
		glfwSetKeyCallback(ATRobots.window.getWindowHandle(), keyCallback);

		if(spectator) {
			spectatorMovement();
		}
	}
	@Override
	public void update(double interval, MouseInput mouseInput) {
		// Spectator Camera Rotation (Using Right Click)
		if(mouseInput.isRightButtonPress() && spectator) {
			Vector2f rotVec = mouseInput.getDisplVec();
			camera.moveRotation(rotVec.x * Consts.MOUSE_SENSITIVITY, rotVec.y * Consts.MOUSE_SENSITIVITY, 0);
		}

		// Camera Updates
		camera.movePosition( cameraInc.x,
	  						 cameraInc.y,
	  						 cameraInc.z );
		cameraInc.zero();	// Resets the Vector3f to all zeros

		gameTick(); 		// Updates each entity with their game functionalities(ticks)

	}
	@Override
	public void render() {
		glfwSwapBuffers(window.getWindowHandle());
		// Tells OpenGL to start rendering all the objects put in a queue
		glfwPollEvents();
		// Entity Rendering

		for(Iterator<Entity> i = entities.iterator(); i.hasNext();) {
			Entity ent = i.next();
			if(ent instanceof TankEntity) {
				// TankEntity consists of two models; resulting in the need of two entities being rendered
				renderer.processEntity(new Entity("tankBot", ((TankEntity) ent).getBase(), ent.getPos(), ((TankEntity) ent).getBaseRotation(), 1f));
				renderer.processEntity(new Entity("tankTop", ((TankEntity) ent).getTop(), ent.getPos(), ((TankEntity) ent).getTurretRotation(), 1f));
			}
			else {
				renderer.processEntity(ent);
			}
		}

		// Terrain Rendering
		for(Terrain terrain : terrains) {
			renderer.processTerrain(terrain);
		}

		renderer.render(camera, directionalLight);
	}

	@Override
	public void cleanup() {
		renderer.cleanup();
		loader.cleanup();
	}
	
	private Model setModel(String modelOBJ, String texture) throws Exception{
		Model model = loader.loadOBJModel(modelOBJ);
		model.setTexture(new Texture(loader.loadTexture(texture)), 1f);
		return model;
	}

	public void addEntity(Entity ent) {
		entities.add(ent);	 // add the entity to a set of entities
	}
	
	public static void removeEntity(Entity ent) {
		entities.remove(ent); // remove the entity to a set of entities
	}

	public void addTerrain(Terrain ent) {
		terrains.add(ent);	 // add the terrain to a set of terrains
	}
	
	public void removeTerrain(Terrain ent) {
		terrains.add(ent);	 // remove the terrain to a set of terrains
	}

	public static Iterator<Entity> iGameTick;
	public void gameTick() {
		iGameTick = entities.iterator();

		while(iGameTick.hasNext()) {
			Entity ent = iGameTick.next();
			if(ent instanceof HittableEntity) {
				ent.debugGameTick();
				((HittableEntity) ent).collisionCheck();
			} else { // for EVERY entity
				ent.debugGameTick();
			}// Updates each entity with their game functionalities(ticks)
		}
	}

	public static void gameTickRemoval() {
		iGameTick.remove();
	}

	public static boolean getSpectator() {
		return spectator;
	}

	public void spectatorMovement() {
		cameraSpeed = (float)((CAMERA_STEP) * tick());

		if(window.isKeyPressed(GLFW_KEY_W))
			cameraInc.z = -cameraSpeed;
		if(window.isKeyPressed(GLFW_KEY_S))
			cameraInc.z = cameraSpeed;

		if(window.isKeyPressed(GLFW_KEY_A))
			cameraInc.x = -cameraSpeed;
		if(window.isKeyPressed(GLFW_KEY_D))
			cameraInc.x = cameraSpeed;

		if(window.isKeyPressed(GLFW_KEY_LEFT_CONTROL))
			cameraInc.y = -cameraSpeed;
		if(window.isKeyPressed(GLFW_KEY_SPACE))
			cameraInc.y = cameraSpeed;
		return;
	}




	// SOUND RELEATED

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
		if (sounds.containsKey(file.getAbsolutePath())) {
			return sounds.get(file.getAbsolutePath());
		} else {
			Sound sound = new Sound(file.getAbsolutePath(), loops);
			sounds.put(file.getAbsolutePath(), sound);
			return sound;
		}
	}
}