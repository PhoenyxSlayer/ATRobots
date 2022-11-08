package com.redteam.engine.game.entities;

import com.redteam.engine.core.Engine;
import com.redteam.engine.core.ObjectLoader;
import com.redteam.engine.core.entity.Entity;
import com.redteam.engine.core.entity.HittableEntity;
import com.redteam.engine.core.rendering.Model;
import com.redteam.engine.core.rendering.Texture;
import org.joml.Vector3f;

import static com.redteam.engine.utils.Constants.BULLET_SPEED;

public class BulletEntity extends HittableEntity {

	private static final ObjectLoader loader = new ObjectLoader();

	private final Model bulletModel;

	private boolean isMoving;

	public BulletEntity(String id, Vector3f pos, Vector3f rotation, boolean moving) {
		super(id, setModel(), pos, rotation, 1, 3f);

		bulletModel = setModel();
		isMoving = moving;
	}

	public BulletEntity(String id, Model bulletModel, Vector3f pos, Vector3f rotation, boolean moving) {
		super(id, bulletModel, pos, rotation, 1, 3f);

		this.bulletModel = bulletModel;
		isMoving = moving;
	}

	private static Model setModel() {
		Model model = loader.loadOBJModel("/models/bullet.obj");
		try {
			model.setTexture(new Texture(loader.loadTexture("textures/bullet.png")), 1f);
			return model;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	@SuppressWarnings("unused")
	public Model getBulletModel() { return bulletModel; }
	@SuppressWarnings("unused")
	public boolean getIfMoving() {
		return isMoving;
	}
	@SuppressWarnings("unused")
	public void setMoving(boolean move) {
		isMoving = move;
	}

	@Override
	public void collision(Entity entity) {
	}

	@Override
	public void debugCollision(Entity entity) {
	}

	@Override
	public void gameTick() {
		// TODO : REAL-GAMES GAME TICK IMPLEMENTATION
	}
	
	@Override
	public void debugGameTick() {
		if(outOfBorder()) {
			remove();
		}
		
		if(isMoving) {
			float bulletSpeed = (float) (BULLET_SPEED * Engine.tick());
			switch ((int) (getRotation().y + 90)) {
				case 0 -> incPos(0, 0, bulletSpeed);
				case 45 -> incPos(bulletSpeed, 0, bulletSpeed);
				case 90 -> incPos(bulletSpeed, 0, 0);
				case 135 -> incPos(bulletSpeed, 0, -bulletSpeed);
				case 180 -> incPos(0, 0, -bulletSpeed);
				case 225 -> incPos(-bulletSpeed, 0, -bulletSpeed);
				case 270 -> incPos(-bulletSpeed, 0, 0);
				case 315 -> incPos(-bulletSpeed, 0, bulletSpeed);
			}
		}
	}
}
