package com.redteam.engine.game.entities;

import com.redteam.engine.core.Engine;
import com.redteam.engine.core.ObjectLoader;
import com.redteam.engine.core.entity.Entity;
import com.redteam.engine.core.entity.HittableEntity;
import com.redteam.engine.core.rendering.Model;
import com.redteam.engine.core.rendering.Texture;
import com.redteam.engine.game.debug.DebugMode;
import com.redteam.engine.game.main.ATRobots;
import org.joml.Vector3f;
import org.lwjgl.glfw.GLFW;
import org.lwjgl.glfw.GLFWKeyCallback;
import org.lwjgl.glfw.GLFWVidMode;
import org.lwjgl.opengl.GL11;

import static com.redteam.engine.utils.Constants.*;
import static org.lwjgl.glfw.GLFW.*;

public class TankEntity extends HittableEntity {

	private final Vector3f baseRotation;
	private final Vector3f turretRotation = new Vector3f(0, 0, 0);

	private static final ObjectLoader loader = new ObjectLoader();

	private Model top;
	private Model base;

	private String color = "";

	@SuppressWarnings("unused")
	public TankEntity(String id, Vector3f basePosition, Vector3f baseRotation, String color) {
		super(id, setTankModel("/models/tankBot.obj", color), basePosition, baseRotation, 1, 5f);
		this.baseRotation = baseRotation;
		this.color = color;


		top = setTankModel("/models/tankTop.obj", color);
		base = setTankModel("/models/tankBot.obj", color);
	}

	public TankEntity(String id, Vector3f basePosition, Vector3f baseRotation) {
		super(id, setTankModel("/models/tankBot.obj", "textures/Camo.jpg"), basePosition, baseRotation, 1, 5f);
		this.baseRotation = baseRotation;

		top = setTankModel("/models/tankTop.obj", "textures/Camo.jpg");
		base = setTankModel("/models/tankBot.obj", "textures/Camo.jpg");
	}

	public TankEntity(String id, Model base, Model top, Vector3f basePosition, Vector3f baseRotation) {
		super(id, base, basePosition, baseRotation, 1, 5f);
		this.baseRotation = baseRotation;

		this.top = top;
		this.base = base;
	}

	public void collision(Entity entity) {
		if (entity instanceof BulletEntity) {
			entity.remove();
			// TODO : REDUCES HEALTH
		}
		// TODO : CREATE MINE COLLISION
		if (entity instanceof TankEntity) {
			// TODO : PREVENT TANKS GOING THROUGH EACH OTHER
		}
	}

	private int health = 100;
	@Override
	public void debugCollision(Entity entity) {
		if (entity instanceof BulletEntity) {
			if (!entity.getID().startsWith(getID())) {
				entity.remove();
				System.out.println(getID() + " Hit!");
				if (health > 0) {
					health -= 10;
				}
				System.out.println("Health: " + health);
				if (health == 0) {
					remove();
				}
			}
		} else if (entity instanceof com.redteam.engine.game.entities.TankEntity) {
			// TODO : MAKE IT SO IT DOESN'T GO THROUGH EACH OTHER
		}
	}

	@Override
	public void gameTick() {
		// TODO : REAL-GAMES GAME TICK IMPLEMENTATION
		// THIS IS WHERE FUNCTION CONVERSION FROM .AT2 CODE WILL GO
	}


	// debugGameTick Variables

	private final Vector3f movement = new Vector3f(0, 0, 0),
			bulletPos = new Vector3f(0, 0, 0);

	private float tankSpeed,
			tankAngle,
			turretAngle;

	private boolean tankMoving = false;

	@Override
	public void debugGameTick() {

		if (!DebugMode.isSpectator()) {
			// MAKES SURE ENTITY IS IN BORDER
			outOfBorder();

			// WASD Movement
			tankControls();
			setRotation(0, tankAngle, 0);
			incPos(movement.x, 0, movement.z);
			movement.zero();

			// Turret Movement
			turretControls();
			setTurretRotation(0, turretAngle, 0);

			// TODO : IMPLEMENT ADDING KEYSTROKES FROM OTHER CLASSES/FUNCTIONS TO THIS
			// PERFORMS ALL KEY TOGGLES + SHOOTING
			debugKeyMappings();

			// Camera Moving w/ Tank
			DebugMode.camera.setPosition(getPos().x, getPos().y + 50f, getPos().z);
			DebugMode.camera.setRotation(90.0f, 0, 0);

			// Tank Idle/Moving Sounds
			tankSounds();
		}
	}

	private static Model setTankModel(String modelOBJ, String texture) {
		String textureFile = "textures/";

		// TODO : In Blender create a black border outline for all textures for both turret and the base
		// Sets the Turret Model (Depending on the user picked color)
		if (modelOBJ.equals("/models/tankTop.obj")) {
			textureFile += "turret/";
			textureFile = getTextureColor(texture, textureFile);
		}
		// Sets the Base Model (Depending on the user picked color)
		else if (modelOBJ.equals("/models/tankBot.obj")) {
			textureFile += "base/";
			textureFile = getTextureColor(texture, textureFile);
		}
		Model model = loader.loadOBJModel(modelOBJ);
		try {
			model.setTexture(new Texture(loader.loadTexture(textureFile)), 1f);
			return model;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	private static String getTextureColor(String color, String textureFile) {
		switch (color) {
			case "Green", "green", "G", "g" -> textureFile += "green.jpg";
			case "Red", "red", "R", "r" -> textureFile += "red.jpg";
			case "Blue", "blue", "B", "b" -> textureFile += "blue.jpg";
			case "Cyan", "cyan", "C", "c" -> textureFile += "cyan.jpg";
			case "Pink", "pink", "P", "p" -> textureFile += "pink.jpg";
			case "Yellow", "yellow", "Y", "y" -> textureFile += "yellow.jpg";
			default -> textureFile = "textures/Camo.jpg";
		}
		return textureFile;
	}

	@Override
	public void incRotation(float x, float y, float z) {
		incBaseRotation(x, y, z);
		incTurretRotation(x, y, z);
	}

	@Override
	public void setRotation(float x, float y, float z) {
		setBaseRotation(x, y, z);
		setTurretRotation(x, y, z);
	}

	// BASE + TURRET ROTATION

	// BASE OF TANK ROTATION BEING INCREMENTING
	public void incBaseRotation(float x, float y, float z) {
		super.incRotation(x, y, z);
	}

	// TURRET OF TANK ROTATION BEING INCREMENTING
	public void incTurretRotation(float x, float y, float z) {
		this.turretRotation.add(new Vector3f(x, y, z));
	}

	// BASE OF TANK ROTATION BEING SET
	public void setBaseRotation(float x, float y, float z) {
		super.setRotation(x, y, z);
	}

	// TURRET OF TANK ROTATION BEING SET
	public void setTurretRotation(float x, float y, float z) {
		this.turretRotation.set(x, y, z);
	}

	public Model getTop() {
		return top;
	}

	public Model getBase() {
		return base;
	}

	public Vector3f getBaseRotation() {
		return baseRotation;
	}

	public Vector3f getTurretRotation() {
		return turretRotation;
	}

	@SuppressWarnings("unused")
	public String getColor() {
		return color;
	}

	@SuppressWarnings("unused")
	public void setColor(String color) {
		this.color = color;
		top = setTankModel("/models/tankTop.obj", color);
		base = setTankModel("/models/tankBot.obj", color);
	}

	private void tankControls() {
		// By default, it's not moving
		tankMoving = false;

		if (ATRobots.window.isKeyPressed(GLFW_KEY_LEFT_SHIFT))
			tankSpeed = ((float) (MOVEMENT_SPEED * Engine.tick()) * 3);
		else
			tankSpeed = (float) (MOVEMENT_SPEED * Engine.tick());

		// MOVING + ROTATION OF TANK
		if (ATRobots.window.isKeyPressed(GLFW_KEY_W) & ATRobots.window.isKeyPressed(GLFW_KEY_A)) {
			tankMoving = true;
			tankAngle = 225;
			movement.x = -tankSpeed;                // MOVES UP-LEFT
			movement.z = -tankSpeed;
		}
		else if (ATRobots.window.isKeyPressed(GLFW_KEY_W) & ATRobots.window.isKeyPressed(GLFW_KEY_D)) {
			tankMoving = true;
			tankAngle = 135;
			movement.x = tankSpeed;                    // MOVES UP-RIGHT
			movement.z = -tankSpeed;
		}
		else if (ATRobots.window.isKeyPressed(GLFW_KEY_D) & ATRobots.window.isKeyPressed(GLFW_KEY_S)) {
			tankMoving = true;
			tankAngle = 45;
			movement.x = tankSpeed;                    // MOVES DOWN-RIGHT
			movement.z = tankSpeed;
		}
		else if (ATRobots.window.isKeyPressed(GLFW_KEY_A) & ATRobots.window.isKeyPressed(GLFW_KEY_S)) {
			tankMoving = true;
			tankAngle = 315;
			movement.x = -tankSpeed;                // MOVES DOWN-LEFT
			movement.z = tankSpeed;
		}
		else if (ATRobots.window.isKeyPressed(GLFW_KEY_W)) {
			tankMoving = true;
			tankAngle = 180;
			movement.z = -tankSpeed;                // MOVES UP
		}
		else if (ATRobots.window.isKeyPressed(GLFW_KEY_A)) {
			tankMoving = true;
			tankAngle = 270;
			movement.x = -tankSpeed;                // MOVES LEFT
		}
		else if (ATRobots.window.isKeyPressed(GLFW_KEY_S)) {
			tankMoving = true;
			tankAngle = 0;
			movement.z = tankSpeed;                    // MOVES DOWN
		}
		else if (ATRobots.window.isKeyPressed(GLFW_KEY_D)) {
			tankMoving = true;
			tankAngle = 90;
			movement.x = tankSpeed;                    // MOVES RIGHT
		}

	}


	private void turretControls() {
		turretAngle = getBaseRotation().y;

		if (ATRobots.window.isKeyPressed(GLFW_KEY_I) & ATRobots.window.isKeyPressed(GLFW_KEY_J))
			turretAngle = 225;
		else if (ATRobots.window.isKeyPressed(GLFW_KEY_I) & ATRobots.window.isKeyPressed(GLFW_KEY_L))
			turretAngle = 135;
		else if (ATRobots.window.isKeyPressed(GLFW_KEY_L) & ATRobots.window.isKeyPressed(GLFW_KEY_K))
			turretAngle = 45;
		else if (ATRobots.window.isKeyPressed(GLFW_KEY_J) & ATRobots.window.isKeyPressed(GLFW_KEY_K))
			turretAngle = 315;
		else if (ATRobots.window.isKeyPressed(GLFW_KEY_I))
			turretAngle = 180;
		else if (ATRobots.window.isKeyPressed(GLFW_KEY_J))
			turretAngle = 270;
		else if (ATRobots.window.isKeyPressed(GLFW_KEY_L))
			turretAngle = 90;
		else if (ATRobots.window.isKeyPressed(GLFW_KEY_K))
			turretAngle = 0;

	}


	private void tankSounds() {
		if (DebugMode.soundMap.isSoundOn()) {    //
			if (tankMoving) {
				DebugMode.soundMap.getSound("sounds/tankIdle.ogg").stop();
				DebugMode.soundMap.getSound("sounds/tankMove.ogg").play();
			} else {
				DebugMode.soundMap.getSound("sounds/tankMove.ogg").stop();
				DebugMode.soundMap.getSound("sounds/tankIdle.ogg").play();
			}
		} else {
			if (DebugMode.soundMap.getSound("sounds/tankIdle.ogg").isPlaying()) {
				DebugMode.soundMap.getSound("sounds/tankIdle.ogg").stop();
			}
			if (DebugMode.soundMap.getSound("sounds/tankMove.ogg").isPlaying()) {
				DebugMode.soundMap.getSound("sounds/tankMove.ogg").stop();
			}
		}
	}

	@Override
	public boolean outOfBorder() {
		if (super.outOfBorder()) {
			Vector3f pushBack = getPos();
			if (getPos().x > X_BORDER && getPos().z > 0)
				pushBack.add(-tankSpeed, 0, -tankSpeed);
			else if (getPos().x > X_BORDER && getPos().z < -Z_BORDER)
				pushBack.add(-tankSpeed, 0, tankSpeed);
			else if (getPos().x < -X_BORDER && getPos().z > 0)
				pushBack.add(tankSpeed, 0, -tankSpeed);
			else if (getPos().x < -X_BORDER && getPos().z < -Z_BORDER)
				pushBack.add(tankSpeed, 0, tankSpeed);
			else if (getPos().x > X_BORDER)
				pushBack.add(-tankSpeed, 0, 0);
			else if (getPos().x < -X_BORDER)
				pushBack.add(tankSpeed, 0, 0);
			else if (getPos().z > 0)
				pushBack.add(0, 0, -tankSpeed);
			else if (getPos().z < -Z_BORDER)
				pushBack.add(0, 0, tankSpeed);

			setPos(pushBack.x, pushBack.y, pushBack.z);
			return true;
		}
		return false;
	}

	boolean fullscreen = false;

	private void debugKeyMappings() {
		// Enables the Debug GUIs
		// ID
		// POSITION
		// ROTATION
		// IS IT MOVING?
		GLFWKeyCallback keyCallback = new GLFWKeyCallback() {
			@Override
			public void invoke(long window, int key, int scancode, int action, int mods) {
				if (action == GLFW_PRESS) {
					if (key == GLFW_KEY_F11) {
						fullscreen = !fullscreen;
						long monitor;
						GLFWVidMode glfwGetVideoMode = glfwGetVideoMode(glfwGetPrimaryMonitor());
						if(fullscreen) {
							assert glfwGetVideoMode != null;
							monitor = ATRobots.getWindow().getPrimaryMonitor();

							GLFW.glfwSetWindowMonitor(	window,
														monitor,
													0,
													0,
														 glfwGetVideoMode.width(),
													     glfwGetVideoMode.height(),
														 GLFW_DONT_CARE
													 );
							GL11.glViewport(0, 0, glfwGetVideoMode.width(),glfwGetVideoMode.height());
						} else {
							monitor = 0;
							GLFW.glfwSetWindowMonitor(	window,
														monitor,
													(glfwGetVideoMode.width() - WIDTH) / 2,
													(glfwGetVideoMode.height() - HEIGHT) / 2,
														WIDTH,
														HEIGHT,
														GLFW_DONT_CARE
													 );
							GLFW.glfwWindowHint(GLFW.GLFW_MAXIMIZED, GLFW.GLFW_FALSE);
							GL11.glViewport(0, 0, WIDTH, HEIGHT);
						}
					}
					if (key == GLFW_KEY_M) {
						DebugMode.soundMap.setSound();
					}
					if (key == GLFW_KEY_F4)
						DebugMode.debugGUIMap.updateDebugMode();        // Enables the Debug GUIs
					if (key == GLFW_KEY_V)
						DebugMode.updateSpectator();
					if (!DebugMode.isSpectator()) {
						if (key == GLFW_KEY_SPACE) {
							bulletPos.x = getPos().x;
							bulletPos.z = getPos().z;
							switch ((int) turretAngle) {
								case 0 -> bulletPos.z += 4f;
								case 45 -> {
									bulletPos.x += 4f;
									bulletPos.z += 4f;
								}
								case 90 -> bulletPos.x += 4f;
								case 135 -> {
									bulletPos.x += 4f;
									bulletPos.z -= 4f;
								}
								case 180 -> bulletPos.z -= 4f;
								case 225 -> {
									bulletPos.x -= 4f;
									bulletPos.z -= 4f;
								}
								case 270 -> bulletPos.x -= 4f;
								case 315 -> {
									bulletPos.x -= 4f;
									bulletPos.z += 4f;
								}
							}
							if (DebugMode.soundMap.isSoundOn()) {
								DebugMode.soundMap.getSound("sounds/bullet.ogg").stop();
								DebugMode.soundMap.getSound("sounds/bullet.ogg").play();
							} else {
								if (DebugMode.soundMap.getSound("sounds/bullet.ogg").isPlaying()) {
									DebugMode.soundMap.getSound("sounds/bullet.ogg").stop();
								}
							}
							DebugMode.objectMap.addEntity(
									new BulletEntity(
											getID() + "_bullet",                                    // ID
											new Vector3f(bulletPos.x, 2.55f, bulletPos.z),        // POSITION
											new Vector3f(0, turretAngle + 90, 90),        // ROTATION
											true                                                    // IS IT MOVING?
									));
						}
					}
				}
				if (action == GLFW_RELEASE) {
					if (key == GLFW_KEY_ESCAPE) {
						System.out.println("EXITING WINDOW " + ATRobots.getWindow().getTitle());
						glfwSetWindowShouldClose(window, true);
					}

				}
			}
		};
		glfwSetKeyCallback(ATRobots.window.getWindowHandle(), keyCallback);
	}
}