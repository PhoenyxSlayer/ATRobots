package com.redteam.engine.core.entity;

import com.redteam.engine.game.ATRobots;
import com.redteam.engine.game.TestGame;
import com.redteam.engine.core.Engine;
import com.redteam.engine.core.ObjectLoader;

import static com.redteam.engine.utils.Consts.*;

import static org.lwjgl.glfw.GLFW.*;

import org.joml.Vector3f;
import org.lwjgl.glfw.GLFWKeyCallback;

public class TankEntity extends HittableEntity {
	
	private Vector3f baseRotation,
					 turretRotation = new Vector3f(0,0,0);
	
	private static ObjectLoader loader = new ObjectLoader();

	private Model top,
				  base;

	private float tankSpeed = (float)(MOVEMENT_SPEED * Engine.tick());

	// TODO : MAKE TANKS HAVE RANDOMIZED TEXTURES

	public TankEntity(String id, Vector3f basePosition, Vector3f baseRotation) {
		super(id, setModel("/models/tankBot.obj", "textures/Camo.jpg"), basePosition, baseRotation, 1, 5f);
		this.baseRotation = baseRotation;

		top = setModel("/models/tankTop.obj", "textures/Camo.jpg");
	    base = setModel("/models/tankBot.obj", "textures/Camo.jpg");
	}

	private static Model setModel(String modelOBJ, String texture) {
		Model model = loader.loadOBJModel(modelOBJ);
		try {
			model.setTexture(new Texture(loader.loadTexture(texture)), 1f);
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
		this.baseRotation.add(new Vector3f(x, y, z));
	}
	
	// TURRET OF TANK ROTATION BEING INCREMENTING
	public void incTurretRotation(float x, float y, float z) {
		this.turretRotation.add(new Vector3f(x, y, z));
	}

	// BASE OF TANK ROTATION BEING SET
	public void setBaseRotation(float x, float y, float z) {
		this.baseRotation = new Vector3f(x,y,z);
	}
	
	// TURRET OF TANK ROTATION BEING SET
	public void setTurretRotation(float x, float y, float z) {
		this.turretRotation = new Vector3f(x,y,z);
	}
	
	public Model getTop() { return top; }
	
	public Model getBase() { return base; }
	
	public Vector3f getBaseRotation() { return baseRotation; }

	public Vector3f getTurretRotation() { return turretRotation; }

	@Override
	public void collision(Entity entity) {
		// TODO : REAL-GAMES IMPLEMENTATION OF COLLISION
		return;
	}
	int i = 0;
	@Override
	public void debugCollision(Entity entity) {
		//System.out.println(entity.getID());
		
		if(entity.getID().trim().equals("bullet")) {
			System.out.println("i've been hit " + i++);
			TestGame.findAndRemoveEntity(entity);
			// TODO : REDUCES HEALTH
		}
		else if(entity instanceof TankEntity) {
			// TODO : MAKE IT SO IT DOESN'T GO THROUGH EACHOTHER
		}
		return;
	}

	@Override
	public void gameTick() {
		// TODO : REAL-GAMES GAME TICK IMPLEMENTATION
		// THIS IS WHERE FUNCTION CONVERSION FROM .AT2 CODE WILL GO
	}
	

	// debugGameTick Variables
	
	private Vector3f pushBack = new Vector3f(0,0,0),
					 movement = new Vector3f(0,0,0),
					 bulletPos = new Vector3f(0,0,0);
	
	private float tankAngle,
	  			  turretAngle;

	GLFWKeyCallback keyCallback;

	@Override
	public void debugGameTick() {
		
		if(!TestGame.getSpectator()) {
			// SPEED/SPRINT
			if(ATRobots.window.isKeyPressed(GLFW_KEY_LEFT_SHIFT))
				tankSpeed = ((float) (MOVEMENT_SPEED * Engine.tick()) * 3);
			else
				tankSpeed = (float) (MOVEMENT_SPEED * Engine.tick());

			// MAKES SURE ENTITY IS IN BORDER
			if(!inBorder()) {
				pushBack = getPos();
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
							TestGame.updateSpectator();
						if(!TestGame.getSpectator()) {
							if(key == GLFW_KEY_SPACE) {
								bulletPos.x = getPos().x;
								bulletPos.z = getPos().z;
								switch((int)turretAngle) {
									case 0:
										bulletPos.z += 5.3f;
										break;
									case 45:
										bulletPos.x += 4.2f;
										bulletPos.z += 4.2f;
										break;
									case 90:
										bulletPos.x += 5.3f;
										break;
									case 135:
										bulletPos.x += 4.2f;
										bulletPos.z -= 4.2f;
										break;
									case 180:
										bulletPos.z -= 5.3f;
										break;
									case 225:
										bulletPos.x -= 4.2f;
										bulletPos.z -= 4.2f;
										break;
									case 270:
										bulletPos.x -= 5.3f;
										break;
									case 315:
										bulletPos.x -= 4.2f;
										bulletPos.z += 4.2f;
										break;
								}
								TestGame.addAdditionalEntity(
								new Bullet(
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
							return;
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

			TestGame.camera.setPosition(getPos().x, getPos().y + 50f, getPos().z);
			TestGame.camera.setRotation(90.0f, 0, 0);
		}
	}
}