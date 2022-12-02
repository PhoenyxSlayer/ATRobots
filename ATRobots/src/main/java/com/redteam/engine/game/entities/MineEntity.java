package com.redteam.engine.game.entities;

import com.redteam.engine.core.ObjectLoader;
import com.redteam.engine.core.entity.Entity;
import com.redteam.engine.core.entity.HittableEntity;
import com.redteam.engine.core.rendering.Model;
import com.redteam.engine.core.rendering.Texture;
import org.joml.Vector3f;

public class MineEntity extends HittableEntity {

	private static final ObjectLoader loader = new ObjectLoader();

	private final Model mineModel;

	public MineEntity(String id, Vector3f pos, Vector3f rotation) {
		super(id, setMineModel(), pos, rotation, 3, 3f);

		mineModel = setMineModel();
	}

	@SuppressWarnings("unused")
	public MineEntity(String id, Model mineModel, Vector3f pos, Vector3f rotation) {
		super(id, mineModel, pos, rotation, 1, 2f);

		this.mineModel = mineModel;
	}

	private static Model setMineModel() {
		Model model = loader.loadOBJModel("/models/mine.obj");
		try {
			model.setTexture(new Texture(loader.loadTexture("textures/mine.png")), 1f);
			return model;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	@SuppressWarnings("unused")
	public Model getMineModel() { return mineModel; }

	public void collision(Entity entity) {
	}
	public void debugCollision(Entity entity) {}

	public void gameTick() {
		// TODO : REAL-GAMES GAME TICK IMPLEMENTATION
		if(outOfBorder()) {
			remove();
		}
	}

	public void debugGameTick() {
		gameTick();
	}
}
