package com.redteam.engine.core.entity;

import com.redteam.engine.core.Engine;
import com.redteam.engine.core.ObjectLoader;
import com.redteam.engine.game.TestGame;

import static com.redteam.engine.utils.Consts.*;

import org.joml.Vector3f;

public class Bullet extends HittableEntity {

	private static ObjectLoader loader = new ObjectLoader();

	private Model bulletModel;

	private boolean isMoving;

	private float bulletSpeed = (float) (BULLET_SPEED * Engine.tick());;

	public Bullet(String id, Vector3f pos, Vector3f rotation, boolean moving) {
		super(id, setModel("/models/untitled.obj", "textures/bullet.png"), pos, rotation, 1, 3f);

		bulletModel = setModel("/models/untitled.obj", "textures/bullet.png");

		isMoving = moving;
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

	public Model getBulletModel() { return bulletModel; }

	public boolean getIfMoving() {
		return isMoving;
	}

	public void setMoving(boolean move) {
		isMoving = move;
		return;
	}

	@Override
	public void collision(Entity entity) {
		return;
	}

	@Override
	public void debugCollision(Entity entity) {
		return;
	}

	@Override
	public void gameTick() {
		// TODO : REAL-GAMES GAME TICK IMPLEMENTATION
	}
	
	@Override
	public void debugGameTick() {
		if(!inBorder()) {
			TestGame.entityIteratorRemoval();
		}
		
		if(isMoving) {
			bulletSpeed = (float) (BULLET_SPEED * Engine.tick());
			switch((int)(getRotation().y + 90)) {
				case 0:
					incPos(0, 0, bulletSpeed);
					break;
				case 45:
					incPos(bulletSpeed, 0, bulletSpeed);
					break;
				case 90:
					incPos(bulletSpeed, 0, 0);
					break;
				case 135:
					incPos(bulletSpeed, 0, -bulletSpeed);
					break;
				case 180:
					incPos(0, 0, -bulletSpeed);
					break;
				case 225:
					incPos(-bulletSpeed, 0, -bulletSpeed);
					break;
				case 270:
					incPos(-bulletSpeed, 0, 0);
					break;
				case 315:
					incPos(-bulletSpeed, 0, bulletSpeed);
					break;
				default:
					incPos(-bulletSpeed, 0, -bulletSpeed);
					break;
			}
		}

		return;
		
	}
}
