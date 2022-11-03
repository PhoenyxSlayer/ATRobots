package com.redteam.engine.game;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.HashSet;

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
import com.redteam.engine.core.entity.TankEntity;
import com.redteam.engine.core.entity.Texture;
import com.redteam.engine.core.entity.terrain.Terrain;
import com.redteam.engine.core.gui.DebugGUI;
import com.redteam.engine.core.lighting.DirectionalLight;
import com.redteam.engine.core.lighting.PointLight;
import com.redteam.engine.core.lighting.SpotLight;
import com.redteam.engine.core.rendering.RenderManager;
import com.redteam.engine.utils.Consts;

import images.image_parser;
import imgui.ImGui;
import imgui.flag.ImGuiConfigFlags;
import sounds.Sound;

public class TestGame implements ILogic{
	
	private final RenderManager renderer;
	private final ObjectLoader loader;

	public Set<Entity> entities = new HashSet<Entity>();
	public Set<Terrain> terrains = new HashSet<Terrain>();

	public static Camera camera;

	private DirectionalLight directionalLight;

	private Vector3f cameraInc = new Vector3f(0,0,0);;
	
	public static Window window;
	
	public static Model tankTopModel, tankBotModel;
	
	private static boolean spectator = false;
	
	private static int bulletNumber;
	
	public static int entityCount;

	private static Entity bulletEntity,
						  passDeletedBulletEntity,
						  passBulletAngleEntity;
	
	private static Map<String, Sound> sounds = new HashMap<>();
	
	private float cameraSpeed = (float)((Consts.CAMERA_STEP) * tick());
	
	private final image_parser icon = image_parser.load_image("src/main/resources/images/test.png");
	
	public TestGame() {
		renderer = new RenderManager();
		window = ATRobots.getWindow();
		loader = new ObjectLoader();
		camera = new Camera();
		camera.setRotation(90f, 0, 0);
		return;
	}
	
	@Override
	public void init() throws Exception {
		renderer.init();

		// Model Rendering
		tankTopModel = setModel("/models/tankTop.obj", "textures/Camo.jpg");
		tankBotModel = setModel("/models/tankBot.obj", "textures/Camo.jpg");

		// Terrain Adding
		Terrain terrain = new Terrain(new Vector3f(-Consts.X_BORDER,0,-Consts.Z_BORDER), loader, new Material(new Texture(loader.loadTexture("textures/concrete.jpg")), 0.1f));
		terrains.add(terrain);

		// Entity Adding
		entities.add(new TankEntity("tank", tankTopModel, tankBotModel, new Vector3f(70f,1.3f,-50f), new Vector3f(0,0,0), 1, 5f));
		entities.add(new Entity("DummyTankTop", tankTopModel, new Vector3f(50f,1.3f,-50f), new Vector3f(0,0,0), 1));
		entities.add(new Entity("DummyTankBottom", tankBotModel, new Vector3f(50f,1.3f,-50f), new Vector3f(0,0,0), 1));
		
		// Light for the Map
		// 1st Argument Light Color, 2nd Light Positioning, 3rd Light Intensity
		directionalLight = new DirectionalLight(new Vector3f(1,1,1), new Vector3f(-1, -10, 0), 1.0f);
		
		return;
	}

	@Override
	public void input() {
		GLFW.glfwSetKeyCallback(window.getWindowHandle(), (window, key, scancode, action, mods) -> {
				if(key == GLFW.GLFW_KEY_ESCAPE && action == GLFW.GLFW_RELEASE) {
					System.out.println("EXITING");
					GLFW.glfwSetWindowShouldClose(window, true);
					return;														// QUITS GAME
				}
				if(key == GLFW.GLFW_KEY_V && action == GLFW.GLFW_PRESS)
					spectator = spectator ? false: true;						// SPECTATOR MODE W/ V PRESS
		});

		if(spectator) {															// SPECTATOR CONTROLS
			cameraSpeed = (float)((Consts.CAMERA_STEP) * tick());
			if(window.isKeyPressed(GLFW.GLFW_KEY_W))
				cameraInc.z = -cameraSpeed;
			if(window.isKeyPressed(GLFW.GLFW_KEY_S))
				cameraInc.z = cameraSpeed;
	
			if(window.isKeyPressed(GLFW.GLFW_KEY_A))
				cameraInc.x = -cameraSpeed;
			if(window.isKeyPressed(GLFW.GLFW_KEY_D))
				cameraInc.x = cameraSpeed;
			
			if(window.isKeyPressed(GLFW.GLFW_KEY_LEFT_CONTROL))
				cameraInc.y = -cameraSpeed;
			if(window.isKeyPressed(GLFW.GLFW_KEY_SPACE))
				cameraInc.y = cameraSpeed;
		}
		return;
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
		
		return;
	}

	@Override
	public void render() {

		GLFW.glfwSwapBuffers(window.getWindowHandle());
		// Tells OpenGL to start rendering all the objects put in a queue
		GLFW.glfwPollEvents();

		// Entity Rendering
		for(Entity entity : entities) {
			if(entity instanceof TankEntity) {
				// TankEntity consists of two models; resulting in the need of two entities being rendered
				renderer.processEntity(new Entity("tankBot", ((TankEntity) entity).getBase(), ((TankEntity) entity).getBasePos(), ((TankEntity) entity).getBaseRotation(), 1f));
				renderer.processEntity(new Entity("tankTop", ((TankEntity) entity).getTop(), ((TankEntity) entity).getTurretPos(), ((TankEntity) entity).getTurretRotation(), 1f));
			} else {
				renderer.processEntity(entity);
			}
			
		}

		// Terrain Rendering
		for(Terrain terrain : terrains) {
			renderer.processTerrain(terrain);
		}

		renderer.render(camera, directionalLight);
		return;
	}

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

	public void addEntity(Entity ent) {
		entities.add(ent); // add the entity to a set of entities
	}

	public void addTerrain(Terrain ent) {
		terrains.add(ent); // add the terrain to a set of terrains
	}

	public void gameTick() {
		for(Entity ent : entities) { // for EVERY entity
			ent.debugGameTick();	 // Updates each entity with their game functionalities(ticks)
		}
	}

	public static boolean getSpectator() {
		return spectator;
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
		if(sounds.containsKey(file.getAbsolutePath())) {
			return sounds.get(file.getAbsolutePath());
		} else {
			Sound sound = new Sound(file.getAbsolutePath(), loops);
			sounds.put(file.getAbsolutePath(), sound);
			return sound;
		}
	}
}