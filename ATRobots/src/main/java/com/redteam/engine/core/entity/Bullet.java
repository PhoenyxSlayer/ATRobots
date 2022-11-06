package com.redteam.engine.core.entity;

import com.redteam.engine.core.Engine;
import com.redteam.engine.core.ObjectLoader;
import com.redteam.engine.game.TestGame;
import com.redteam.engine.utils.Consts;

import org.joml.Vector3f;

public class Bullet extends HittableEntity {

	private static ObjectLoader loader = new ObjectLoader();

	private Model bulletModel;

	public Bullet(String id, Vector3f pos, Vector3f rotation) {
		super(id, setModel("/models/bullet.obj", "textures/bullet.png"), pos, rotation, 1, 2f);

		bulletModel = setModel("/models/bullet.obj", "textures/bullet.png");
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
	
	public void gameTick() {
		// TODO : REAL-GAMES GAME TICK IMPLEMENTATION
	}

	@Override
	public void debugCollision(Entity entity) {
	}

	float bulletSpeed = (float) (Consts.BULLET_SPEED * Engine.tick());;
	
	public void debugGameTick() {
		bulletSpeed = (float) (Consts.BULLET_SPEED * Engine.tick());
		if((getPos().x <= -Consts.X_BORDER || getPos().x >= Consts.X_BORDER)
		|| (getPos().z <= -Consts.Z_BORDER || getPos().z >= 0))
		{
			TestGame.gameTickRemoval();
			return;
		}

		switch((int)getRotation().y) {
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

		return;
		
	}

}
