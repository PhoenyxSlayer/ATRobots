package com.redteam.engine.game.debug;

import java.io.File;
import java.io.IOException;
import java.util.*;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;

import com.redteam.engine.game.main.ATRobots;
import org.joml.Vector2f;
import org.joml.Vector3f;

import static com.redteam.engine.utils.Constants.CAMERA_STEP;
import static org.lwjgl.glfw.GLFW.*;

import com.redteam.engine.core.Camera;
import static com.redteam.engine.core.Engine.*;
import com.redteam.engine.core.ILogic;
import com.redteam.engine.core.MouseInput;
import com.redteam.engine.core.ObjectLoader;
import com.redteam.engine.core.Window;
import com.redteam.engine.core.entity.Entity;
import com.redteam.engine.core.entity.HittableEntity;
import com.redteam.engine.core.rendering.Material;
import com.redteam.engine.core.rendering.Model;
import com.redteam.engine.game.entities.TankEntity;
import com.redteam.engine.core.rendering.Texture;
import com.redteam.engine.core.terrain.Terrain;
import com.redteam.engine.core.lighting.DirectionalLight;
import com.redteam.engine.core.rendering.RenderManager;
import com.redteam.engine.utils.Constants;

import com.redteam.engine.core.rendering.image_parser;
import com.redteam.engine.core.sound.Sound;

public class DebugMode implements ILogic {
	
	private final RenderManager renderer;
	public static ObjectLoader loader;

	public static DebugObjectMap objectMap = new DebugObjectMap();
	private static HashSet<Entity> additionalEntities = new HashSet<>();

	public static Iterator<Entity> iRenderEntities,
								   iAdditionalEntities;
	public static Iterator<Terrain> iRenderTerrains;

	public static Camera camera;

	private final Vector3f cameraInc = new Vector3f(0,0,0);

	public static Window window;

	private static boolean spectator = false;

	// TODO : MAKE SOUND HAVE ITS OWN MAP FILE
	private static final Map<String, Sound> sounds = new HashMap<>();

	private final image_parser icon = image_parser.load_image("src/main/resources/images/test.png");
	
	public DebugMode() {
		renderer = new RenderManager();
		window = ATRobots.getWindow();
		loader = new ObjectLoader();
		camera = new Camera();
	}
	
	@Override
	public void init() throws Exception {
		renderer.init();					// INITIALIZATION OF RENDERER
		window.updateLogo(icon);			// WINDOW ICON
		objectMap.init();
	}

	@Override
	public void input() {
		if(spectator) {
			spectatorMovement();										// IF V IS PRESSED IT WILL ENABLE/ DISABLE THIS
		}
	}

	@Override
	public void update(double interval, MouseInput mouseInput) {

		// Spectator Camera Rotation (Using Right Click)
		if(mouseInput.isRightButtonPress() && spectator) {
			Vector2f rotVec = mouseInput.getDisplayVec();
			camera.moveRotation(rotVec.x * Constants.MOUSE_SENSITIVITY, rotVec.y * Constants.MOUSE_SENSITIVITY, 0);
		}

		// Camera Updates
		camera.movePosition( cameraInc.x,
	  						 cameraInc.y,
	  						 cameraInc.z );
		cameraInc.zero();	// Resets the Vector3F to all zeros

		gameTick(); 		// Updates each entity with their game functionalities(ticks)

	}

	@Override
	public void render() {
		glfwSwapBuffers(window.getWindowHandle());
		// Tells OpenGL to start rendering all the objects put in a queue
		glfwPollEvents();

		// Entity Rendering
		for(Entity ent : objectMap.entityMap()) {
			if(ent instanceof TankEntity) {
				// TankEntity consists of two models; resulting in the need of two entities being rendered
				renderer.processEntity(new Entity("tankBot", ((TankEntity) ent).getBase(), ent.getPos(), ((TankEntity) ent).getBaseRotation(), 1f));
				renderer.processEntity(new Entity("tankTop", ((TankEntity) ent).getTop(), ent.getPos(), ((TankEntity) ent).getTurretRotation(), 1f));
			}
			else {
				renderer.processEntity(ent);
			}
		}

		iRenderTerrains = objectMap.terrainMap().iterator();

		// Terrain Rendering
		for(Terrain ter : objectMap.terrainMap())
			renderer.processTerrain(ter);

		renderer.render(camera, objectMap.lightMap());
	}

	@Override
	public void cleanup() {
		renderer.cleanup();
		loader.cleanup();
	}

	public void gameTick() {
		iRenderEntities = objectMap.entityMap().iterator();

		while(iRenderEntities.hasNext()) {
			Entity ent = iRenderEntities.next();
			if(ent.isRemoved()) {
				iRenderEntities.remove();
				continue;
			}
			if(ent instanceof HittableEntity) {
				ent.debugGameTick();
				((HittableEntity) ent).debugCollisionCheck();	// Checks Collisions on only HittableEntities and their children
			} else { // for EVERY entity
				ent.debugGameTick();
			}		 // Updates each entity with their game functionalities(ticks)
		}

		for(Entity ent : additionalEntities)
			objectMap.addEntity(ent);

		additionalEntities.clear();
	}

	// *Used for Entity Classes* removes themselves
	public static void entityIteratorRemoval() { iRenderEntities.remove(); }

	public static void addAdditionalEntity(Entity ent) { additionalEntities.add(ent); }
	
	public static boolean isSpectator() { return spectator; }

	public static void updateSpectator() {
		spectator = !spectator;
	}

	public void spectatorMovement() {
		float cameraSpeed = (float) ((CAMERA_STEP) * tick());

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
	}

	public static ObjectLoader getObjectLoader() {
		return loader;
	}


	// SOUND RELATED

	@SuppressWarnings("unused")
	private static void playSound(String soundFile) throws IOException, UnsupportedAudioFileException, LineUnavailableException {
	    File f = new File(soundFile);
	    AudioInputStream audioIn = AudioSystem.getAudioInputStream(f.toURI().toURL());  
	    Clip clip = AudioSystem.getClip();
	    clip.open(audioIn);
	    clip.start();
	}

	@SuppressWarnings("unused")
	public static Collection<Sound> getAllSounds() { return sounds.values(); }

	@SuppressWarnings("unused")
	public static Sound getSound(String soundFile) {
		File file = new File(soundFile);
		if(sounds.containsKey(file.getAbsolutePath())) {
			return sounds.get(file.getAbsolutePath());
		} else {
			assert false : "Sound file not added '" + soundFile + "'";
		}
		
		return null;
	}

	@SuppressWarnings("unused")
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