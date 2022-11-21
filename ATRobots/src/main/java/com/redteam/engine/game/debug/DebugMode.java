package com.redteam.engine.game.debug;

import com.redteam.engine.core.*;
import com.redteam.engine.core.entity.Entity;
import com.redteam.engine.core.entity.HittableEntity;
import com.redteam.engine.core.rendering.Model;
import com.redteam.engine.core.rendering.RenderManager;
import com.redteam.engine.core.rendering.Texture;
import com.redteam.engine.core.rendering.image_parser;
import com.redteam.engine.core.terrain.Terrain;
import com.redteam.engine.game.debug.maps.DebugGUIMap;
import com.redteam.engine.game.debug.maps.DebugObjectMap;
import com.redteam.engine.game.debug.maps.DebugSoundMap;
import com.redteam.engine.game.entities.TankEntity;
import com.redteam.engine.game.main.ATRobots;
import com.redteam.engine.utils.Constants;
import org.joml.Vector2f;
import org.joml.Vector3f;

import java.util.Iterator;
import java.util.Random;

import static com.redteam.engine.core.Engine.tick;
import static com.redteam.engine.core.iObjMapping.entities;
import static com.redteam.engine.utils.Constants.CAMERA_STEP;
import static com.redteam.engine.utils.Constants.MOUSE_SENSITIVITY;
import static org.lwjgl.glfw.GLFW.*;

public class DebugMode implements ILogic {
	public static DebugObjectMap objectMap;
	public static DebugSoundMap soundMap;
	public static DebugGUIMap debugGUIMap;

	public static Iterator<Entity> iGameTick;

	private final image_parser icon;

	private final RenderManager renderer;
	public static ObjectLoader loader;

	public static Window window;

	public static Camera camera;
	private final Vector3f cameraInc;

	private static boolean spectator;

	public DebugMode() {
		debugGUIMap = new DebugGUIMap();
		objectMap = new DebugObjectMap();
		soundMap = new DebugSoundMap();
		icon = image_parser.load_image("src/main/resources/images/test.png");
		renderer = new RenderManager();
		window = ATRobots.getWindow();
		loader = new ObjectLoader();
		camera = new Camera();
		cameraInc = new Vector3f(0,0,0);
		spectator = false;
	}

	@Override
	public void init() throws Exception {
		window.updateLogo(icon);			// WINDOW ICON
		renderer.init();					// INITIALIZATION OF RENDERER
		objectMap.init();					// ADDS ENTITIES, TERRAIN, AND LIGHTS
		soundMap.init();					// ADDS SOUNDS
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
			camera.moveRotation(rotVec.x * MOUSE_SENSITIVITY, rotVec.y * MOUSE_SENSITIVITY, 0);
		}

		// Camera Updates
		camera.movePosition(cameraInc);
		cameraInc.zero();	// Resets the Vector3F to all zeros

		gameTick(); 		// Updates each entity with their game functionalities(ticks)
	}

	public void gameTick() {
		iGameTick = objectMap.entityMap().iterator();

		// Needs to be an iterator due to removal code during iteration
		while(iGameTick.hasNext()) {
			Entity ent = iGameTick.next();	// Grabs entity value from iterator

			ent.debugGameTick(); 			// Runs through each entity's functionalities

			if(ent instanceof HittableEntity)
				((HittableEntity) ent).debugCollisionCheck();	// Checks Collisions on only HittableEntities and their children

			if(ent.isRemoved()) {                // If it's removal was called
				iGameTick.remove();				 // Remove the entity from the iterator
				objectMap.removeEntity(ent);	 // Remove the entity from the map
			}
		}
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

	@Override
	public void render() {
		// Entity Rendering
		for(Entity ent : objectMap.entityMap()) {
			if(ent instanceof TankEntity) {
				// TankEntity consists of two models; resulting in the need of two entities being rendered
				renderer.processEntity(new Entity("tankBot", ((TankEntity) ent).getBase(), ent.getPos(), ((TankEntity) ent).getBaseRotation(), 1f) {
					// TODO : Implement it so the tank class will pass two models into the renderer instead of this
					public void gameTick() {}
					public void debugGameTick() {}
				});
				renderer.processEntity(new Entity("tankTop", ((TankEntity) ent).getTop(), ent.getPos(), ((TankEntity) ent).getTurretRotation(), 1f) {
					public void gameTick() {}
					public void debugGameTick() {}
				});
			}
			else {
				renderer.processEntity(ent);
			}
		}

		// Terrain Rendering
		for(Terrain ter : objectMap.terrainMap())
			renderer.processTerrain(ter);

		// Renders camera output and lighting
		renderer.render(camera, objectMap.lightMap());


		// Drawing of the GUI
		debugGUIMap.createImGUIWindows();

		debugGUIMap.spectatorGUI(isSpectator());
		debugGUIMap.currentEntitiesGUI(entities);

		debugGUIMap.render();

		glfwSwapBuffers(window.getWindowHandle());
		// Tells OpenGL to start rendering all the objects put in a queue
		glfwPollEvents();
	}

	@Override
	public void cleanup() {
		renderer.cleanup();
		loader.cleanup();
	}

	public static boolean isSpectator() { return spectator; }
	public static void updateSpectator() {spectator = !spectator; }

	public static ObjectLoader getObjectLoader() { return loader; }


	public static Vector3f RandomizeTankLocation () {
		// randomGenerator() * (max - min) + min;
		return new Vector3f(
				(randomGenerator() * (Constants.X_BORDER - -Constants.X_BORDER) + -Constants.X_BORDER),
				1.3f,
				// Min for Z is 0
				(randomGenerator() * (-Constants.Z_BORDER))
		);
	}

	public static float RandomizeTankRotation () {
		// randomGenerator() * (max - min) + min;
		return randomGenerator() * 360;
	}

	private static final Random generator = new Random();
	static float randomGenerator() {
		return generator.nextFloat();
	}

	public static Model setModel(String OBJModel, String texture) {
		Model model = loader.loadOBJModel(OBJModel);
		try {
			model.setTexture(new Texture(loader.loadTexture(texture)), 1f);
			return model;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

}