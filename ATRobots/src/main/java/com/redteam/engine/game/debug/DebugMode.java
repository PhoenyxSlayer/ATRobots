package com.redteam.engine.game.debug;

import com.redteam.engine.core.*;
import com.redteam.engine.core.entity.Entity;
import com.redteam.engine.core.entity.HittableEntity;
import com.redteam.engine.core.rendering.RenderManager;
import com.redteam.engine.core.rendering.image_parser;
import com.redteam.engine.core.terrain.Terrain;
import com.redteam.engine.game.debug.gui.DebugGUIMap;
import com.redteam.engine.game.entities.TankEntity;
import com.redteam.engine.game.main.ATRobots;
import imgui.ImGui;
import org.joml.Vector2f;
import org.joml.Vector3f;

import java.util.HashSet;
import java.util.Iterator;

import static com.redteam.engine.core.Engine.tick;
import static com.redteam.engine.core.iObjMapping.entities;
import static com.redteam.engine.utils.Constants.CAMERA_STEP;
import static com.redteam.engine.utils.Constants.MOUSE_SENSITIVITY;
import static org.lwjgl.glfw.GLFW.*;

public class DebugMode implements ILogic {
	public static DebugObjectMap objectMap;
	public static DebugSoundMap soundMap;

	public static DebugGUIMap debugGUIMap;

	private static final HashSet<Entity> additionalEntities = new HashSet<>();

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

		while(iGameTick.hasNext()) {
			Entity ent = iGameTick.next();
			if(ent.isRemoved()) {
				iGameTick.remove();
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
		debugGUIMap.createImGUIWindows();

		debugGUIMap.spectatorGUI(isSpectator());
		debugGUIMap.currentEntitiesGUI(entities);

		debugGUIMap.render();

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

		// Terrain Rendering
		for(Terrain ter : objectMap.terrainMap())
			renderer.processTerrain(ter);

		// Renders camera output and lighting
		renderer.render(camera, objectMap.lightMap());
	}

	@Override
	public void cleanup() {
		renderer.cleanup();
		loader.cleanup();
	}

	public static void addAdditionalEntity(Entity ent) { additionalEntities.add(ent); }
	
	public static boolean isSpectator() { return spectator; }
	public static void updateSpectator() {spectator = !spectator; }

	public static ObjectLoader getObjectLoader() { return loader; }
}