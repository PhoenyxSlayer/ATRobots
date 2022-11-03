package com.redteam.engine.core.entity;

import com.redteam.engine.game.ATRobots;
import com.redteam.engine.game.TestGame;
import com.redteam.engine.core.Engine;
import static com.redteam.engine.utils.Consts.*;

import static org.lwjgl.glfw.GLFW.*;

import org.joml.Vector3f;



public class TankEntity extends HittableEntity {
	private Model[] tank;
	private Vector3f basePosition,
					 baseRotation,
					 turretPositon,
					 turretRotation = new Vector3f(0,0,0);

	private float tankAngle,
				  turretAngle;
	private Model top, base;

	private float tankSpeed = (float)(MOVEMENT_SPEED * Engine.tick());

	private Vector3f movement = new Vector3f(0,0,0);

	public TankEntity(String id, Model top, Model base, Vector3f basePosition, Vector3f baseRotation, float scale, float hitboxScale) {
		super(id, base, basePosition, baseRotation, scale, hitboxScale);
		this.top = top;
		this.base = base;
		this.basePosition = basePosition;
		this.baseRotation = baseRotation;
		turretPositon = basePosition;
		tank = new Model[] {top, base};
	}

	public void incPos(float x, float y, float z) {
        incBasePosition(x,y,z);
		incTurretPosition(x,y,z);
	}

	public void setPos(float x, float y, float z) {
        setBasePosition(x,y,z);
		setTurretPosition(x,y,z);
	}

	public void incRotation(float x, float y, float z) {
        incBaseRotation(x,y,z);
		incTurretRotation(x,y,z);
	}

	public void setRotation(float x, float y, float z) {
        setBaseRotation(x,y,z);
		setTurretRotation(x,y,z);
	}

	// BASE + TURRET POSITION

	// BASE OF TANK POSITION BEING INCREMENTING
	public void incBasePosition(float x, float y, float z) {
		this.basePosition.x += x;
		this.basePosition.y += y;
		this.basePosition.z += z;
	}

	// BASE OF TANK POSITION BEING SET
	public void setBasePosition(float x, float y, float z) {
		this.basePosition.x = x;
		this.basePosition.y = y;
		this.basePosition.z = z;
	}

	// TURRET OF TANK POSITION BEING INCREMENTING
	public void incTurretPosition(float x, float y, float z) {
		this.turretPositon.x += x;
		this.turretPositon.y += y;
		this.turretPositon.z += z;
	}
	
	// TURRET OF TANK POSITION BEING SET
	public void setTurretPosition(float x, float y, float z) {
		this.turretPositon.x = x;
		this.turretPositon.y = y;
		this.turretPositon.z = z;
	}

	// BASE + TURRET ROTATION
	
	// BASE OF TANK ROTATION BEING INCREMENTING
	public void incBaseRotation(float x, float y, float z) {
		this.baseRotation.x += x;
		this.baseRotation.y += y;
		this.baseRotation.z += z;
	}

	// BASE OF TANK ROTATION BEING SET
	public void setBaseRotation(float x, float y, float z) {
		this.baseRotation.x = x;
		this.baseRotation.y = y;
		this.baseRotation.z = z;
	}

	// TURRET OF TANK ROTATION BEING INCREMENTING
	public void incTurretRotation(float x, float y, float z) {
		this.turretRotation.x += x;
		this.turretRotation.y += y;
		this.turretRotation.z += z;
	}
	
	// TURRET OF TANK ROTATION BEING SET
	public void setTurretRotation(float x, float y, float z) {
		this.turretRotation.x = x;
		this.turretRotation.y = y;
		this.turretRotation.z = z;
	}
	
	public Model[] getTankModels() {
		return tank;
	}
	
	public Model getTop() {
		return top;
	}
	
	public Model getBase() {
		return base;
	}

	public Vector3f getBasePos() {
		return basePosition;
	}

	public Vector3f getTurretPos() {
		return turretPositon;
	}

	public Vector3f getBaseRotation() {
		return baseRotation;
	}

	public Vector3f getTurretRotation() {
		return turretRotation;
	}

	public void gameTick() {
		// TODO
	}

	public void debugGameTick() {
		if(!TestGame.getSpectator()) {
			// SPRINT
			if(ATRobots.window.isKeyPressed(GLFW_KEY_LEFT_SHIFT))
				tankSpeed = ((float) (MOVEMENT_SPEED * Engine.tick()) * 3);
			else
				tankSpeed = (float) (MOVEMENT_SPEED * Engine.tick());

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

			TestGame.camera.setPosition(getBasePos().x, getBasePos().y + 50f, getBasePos().z);
			// TODO
		}
	}
}