package com.redteam.engine.game.entities;

import com.redteam.engine.core.entity.*;
import com.redteam.engine.core.rendering.Model;
import com.redteam.engine.core.rendering.Texture;
import com.redteam.engine.game.main.ATRobots;
import com.redteam.engine.game.debug.DebugMode;
import com.redteam.engine.core.Engine;
import com.redteam.engine.core.ObjectLoader;

import static com.redteam.engine.utils.Consts.*;

import static org.lwjgl.glfw.GLFW.*;

import org.joml.Vector3f;
import org.lwjgl.glfw.GLFWKeyCallback;

public class TankEntity extends HittableEntity {
	
	private final Vector3f baseRotation;
	private final Vector3f turretRotation = new Vector3f(0,0,0);
	
	private static final ObjectLoader loader = new ObjectLoader();

	private final Model top;
	private final Model base;

	// TODO : MAKE TANKS HAVE RANDOMIZED TEXTURES

	public TankEntity(String id, Vector3f basePosition, Vector3f baseRotation) {
		super(id, setModel("/models/tankBot.obj"), basePosition, baseRotation, 1, 5f);
		this.baseRotation = baseRotation;

		top = setModel("/models/tankTop.obj");
	    base = setModel("/models/tankBot.obj");
	}

	public TankEntity(String id, Model base, Model top, Vector3f basePosition, Vector3f baseRotation) {
		super(id, base, basePosition, baseRotation, 1, 5f);
		this.baseRotation = baseRotation;

		this.top = top;
		this.base = base;
	}

	private static Model setModel(String modelOBJ) {
		Model model = loader.loadOBJModel(modelOBJ);
		try {
			model.setTexture(new Texture(loader.loadTexture("textures/Camo.jpg")), 1f);
			return model;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
	
	@Override
	public void incRotation(float x, float y, float z) {
        incBaseRotation(x,y,z);
		incTurretRotation(x,y,z);
	}
	
	@Override
	public void setRotation(float x, float y, float z) {
        setBaseRotation(x,y,z);
		setTurretRotation(x,y,z);
	}

	// BASE + TURRET ROTATION
	
	// BASE OF TANK ROTATION BEING INCREMENTING
	public void incBaseRotation(float x, float y, float z) {
		super.incRotation(x,y,z);
	}
	
	// TURRET OF TANK ROTATION BEING INCREMENTING
	public void incTurretRotation(float x, float y, float z) {
		this.turretRotation.add(new Vector3f(x, y, z));
	}

	// BASE OF TANK ROTATION BEING SET
	public void setBaseRotation(float x, float y, float z) {
		super.setRotation(x,y,z);
	}
	
	// TURRET OF TANK ROTATION BEING SET
	public void setTurretRotation(float x, float y, float z) {
		this.turretRotation.set(x,y,z);
	}
	
	public Model getTop() { return top; }
	
	public Model getBase() { return base; }
	
	public Vector3f getBaseRotation() { return baseRotation; }

	public Vector3f getTurretRotation() { return turretRotation; }

	@Override
	public void collision(Entity entity) {
		// TODO : REAL-GAMES IMPLEMENTATION OF COLLISION
	}
	int i = 0;
	@Override
	public void debugCollision(Entity entity) {
		//System.out.println(entity.getID());
		
		if(entity.getID().trim().equals("bullet")) {
			System.out.println("i've been hit " + i++);
			DebugMode.findAndRemoveEntity(entity);
			// TODO : REDUCES HEALTH
		}
		else if(entity instanceof TankEntity) {
			// TODO : MAKE IT SO IT DOESN'T GO THROUGH EACH OTHER
		}
	}

	@Override
	public void gameTick() {
		// TODO : REAL-GAMES GAME TICK IMPLEMENTATION
		// THIS IS WHERE FUNCTION CONVERSION FROM .AT2 CODE WILL GO
	}
	

	// debugGameTick Variables

	private final Vector3f movement = new Vector3f(0,0,0);
	private final Vector3f bulletPos = new Vector3f(0,0,0);
	
	private float tankAngle,
	  			  turretAngle;

	GLFWKeyCallback keyCallback;

	@Override
	public void debugGameTick() {
		
		if(!DebugMode.isSpectator()) {
			// SPEED/SPRINT
			float tankSpeed;
			if(ATRobots.window.isKeyPressed(GLFW_KEY_LEFT_SHIFT))
				tankSpeed = ((float) (MOVEMENT_SPEED * Engine.tick()) * 3);
			else
				tankSpeed = (float) (MOVEMENT_SPEED * Engine.tick());

			// MAKES SURE ENTITY IS IN BORDER
			if(outOfBorder()) {
				Vector3f pushBack = getPos();
				if(getPos().x > X_BORDER && getPos().z > 0 )
					pushBack.add(-tankSpeed, 0, -tankSpeed);
				else if(getPos().x > X_BORDER && getPos().z < -Z_BORDER )
					pushBack.add(-tankSpeed, 0, tankSpeed);
				else if(getPos().x < -X_BORDER && getPos().z > 0 )
					pushBack.add(tankSpeed, 0, -tankSpeed);
				else if(getPos().x < -X_BORDER && getPos().z < -Z_BORDER )
					pushBack.add(tankSpeed, 0, tankSpeed);
				else if(getPos().x > X_BORDER)
					pushBack.add(-tankSpeed, 0, 0);
				else if(getPos().x < -X_BORDER)
					pushBack.add(tankSpeed, 0, -tankSpeed);
				else if(getPos().z > 0)
					pushBack.add(0, 0, -tankSpeed);
				else if(getPos().z < -Z_BORDER)
					pushBack.add(0 , 0, tankSpeed);
				
				setPos(pushBack.x, pushBack.y, pushBack.z);
			}

			// SHOOTING

			// TODO : IMPLEMENT ADDING KEYSTROKES FROM OTHER CLASSES/FUNCTIONS TO THIS
			keyCallback = new GLFWKeyCallback() {
				@Override
				public void invoke(long window, int key, int scancode, int action, int mods) {
					if(action == GLFW_PRESS) {

						if(key == GLFW_KEY_V)
							DebugMode.updateSpectator();
						if(!DebugMode.isSpectator()) {
							if(key == GLFW_KEY_SPACE) {
								bulletPos.x = getPos().x;
								bulletPos.z = getPos().z;
								switch ((int) turretAngle) {
									case 0 ->   bulletPos.z += 5.3f;
									case 45 -> {
											    bulletPos.x += 4.2f;
											    bulletPos.z += 4.2f;
									}
									case 90 ->  bulletPos.x += 5.3f;
									case 135 -> {
										        bulletPos.x += 4.2f;
											    bulletPos.z -= 4.2f;
									}
									case 180 -> bulletPos.z -= 5.3f;
									case 225 -> {
												bulletPos.x -= 4.2f;
												bulletPos.z -= 4.2f;
									}
									case 270 -> bulletPos.x -= 5.3f;
									case 315 -> {
												bulletPos.x -= 4.2f;
												bulletPos.z += 4.2f;
									}
								}
								DebugMode.addAdditionalEntity(
								new BulletEntity(
								"bullet",											// ID
									new Vector3f(bulletPos.x,2.55f,bulletPos.z),		// POSITION
									new Vector3f(0,turretAngle -90, -90),	 		// ROTATION
									true										// IS IT MOVING?
								));
							}
						}
					}
					if (action == GLFW_RELEASE) {
						if(key == GLFW_KEY_ESCAPE) {
							System.out.println("EXITING");
							glfwSetWindowShouldClose(window, true);
						}
						
					}
				}
			};

			glfwSetKeyCallback(ATRobots.window.getWindowHandle(), keyCallback);

			// MOVING + ROTATION OF TANK
			if(ATRobots.window.isKeyPressed(GLFW_KEY_W)) {
				tankAngle = 180;
				movement.z = -tankSpeed;				// MOVES UP
			}
			if(ATRobots.window.isKeyPressed(GLFW_KEY_A)) {
				tankAngle = 270;
				movement.x = -tankSpeed;				// MOVES LEFT
			}
			if(ATRobots.window.isKeyPressed(GLFW_KEY_S)) {
				tankAngle = 0;
				movement.z = tankSpeed;					// MOVES DOWN
			}
			if(ATRobots.window.isKeyPressed(GLFW_KEY_D)) {
				tankAngle = 90;
				movement.x = tankSpeed;					// MOVES RIGHT
			}
			if(ATRobots.window.isKeyPressed(GLFW_KEY_W) & ATRobots.window.isKeyPressed(GLFW_KEY_A)) {
				tankAngle = 225;
				movement.x = -tankSpeed;				// MOVES UP-LEFT
				movement.z = -tankSpeed;
			}
			if(ATRobots.window.isKeyPressed(GLFW_KEY_W) & ATRobots.window.isKeyPressed(GLFW_KEY_D)) {
				tankAngle = 135;
				movement.x = tankSpeed;					// MOVES UP-RIGHT
				movement.z = -tankSpeed;
			}
			if(ATRobots.window.isKeyPressed(GLFW_KEY_D) & ATRobots.window.isKeyPressed(GLFW_KEY_S)) {
				tankAngle = 45;
				movement.x = tankSpeed;					// MOVES DOWN-RIGHT
				movement.z = tankSpeed;
			}
			if(ATRobots.window.isKeyPressed(GLFW_KEY_A) & ATRobots.window.isKeyPressed(GLFW_KEY_S)) {
				tankAngle = 315;
				movement.x = -tankSpeed;				// MOVES DOWN-LEFT
				movement.z = tankSpeed;
			}

			incPos(movement.x, 0, movement.z);
			movement.zero();

			setRotation(0, tankAngle, 0);

			// ROTATION OF TURRET

			turretAngle = getBaseRotation().y;

			if(ATRobots.window.isKeyPressed(GLFW_KEY_I))
				turretAngle = 180;
			if(ATRobots.window.isKeyPressed(GLFW_KEY_J))
				turretAngle = 270;
			if(ATRobots.window.isKeyPressed(GLFW_KEY_L))
				turretAngle = 90;
			if(ATRobots.window.isKeyPressed(GLFW_KEY_K))
				turretAngle = 0;
			if(ATRobots.window.isKeyPressed(GLFW_KEY_I) & ATRobots.window.isKeyPressed(GLFW_KEY_J))
				turretAngle = 225;
			if(ATRobots.window.isKeyPressed(GLFW_KEY_I) & ATRobots.window.isKeyPressed(GLFW_KEY_L))
				turretAngle = 135;
			if(ATRobots.window.isKeyPressed(GLFW_KEY_L) & ATRobots.window.isKeyPressed(GLFW_KEY_K))
				turretAngle = 45;
			if(ATRobots.window.isKeyPressed(GLFW_KEY_J) & ATRobots.window.isKeyPressed(GLFW_KEY_K))
				turretAngle = 315;

			setTurretRotation(0, turretAngle, 0);

			DebugMode.camera.setPosition(getPos().x, getPos().y + 50f, getPos().z);
			DebugMode.camera.setRotation(90.0f, 0, 0);
		}
	}
}