package com.redteam.engine.core.entity;

import com.redteam.engine.core.rendering.Model;
import com.redteam.engine.game.debug.DebugMode;
import com.redteam.engine.game.entities.BulletEntity;
import org.joml.Vector3f;

/* https://docs.google.com/document/d/1PEnyHnWt1y7VdEA1bnkRN30tYblDDacImu-bdZJjqKs/edit?usp=sharing
 *  -> Link to Design Doc
 */

public class HittableEntity extends Entity {

	private float hitBoxScale;

	private Vector3f[] hitBox;

	public HittableEntity(String id, Model model, Vector3f pos, Vector3f rotation, float scale, float hitBoxScale) {
		super(id, model, pos, rotation, scale);
		this.hitBoxScale = hitBoxScale;
		formCube();
	}

	// ALL BELOW HIT BOX CODE

	protected void formCube() {

		float halfSideLength = hitBoxScale / 2f;

		hitBox = new Vector3f[]{
				// front face
				new Vector3f(getPos().x - halfSideLength,
						getPos().y + halfSideLength,
						getPos().z + halfSideLength), // top left

				new Vector3f(getPos().x + halfSideLength,
						getPos().y + halfSideLength,
						getPos().z + halfSideLength), // top right

				new Vector3f(getPos().x + halfSideLength,
						getPos().y - halfSideLength,
						getPos().z + halfSideLength), // bottom right

				new Vector3f(getPos().x - halfSideLength,
						getPos().y - halfSideLength,
						getPos().z + halfSideLength), // bottom left


				// back face
				new Vector3f(getPos().x - halfSideLength,
						getPos().y + halfSideLength,
						getPos().z - halfSideLength), // top left

				new Vector3f(getPos().x + halfSideLength,
						getPos().y + halfSideLength,
						getPos().z - halfSideLength), // top right

				new Vector3f(getPos().x + halfSideLength,
						getPos().y - halfSideLength,
						getPos().z - halfSideLength), // bottom right

				new Vector3f(getPos().x - halfSideLength,
						getPos().y - halfSideLength,
						getPos().z - halfSideLength), // bottom left
		};
	}

	public boolean passThrough(Vector3f pos) {
		boolean collide = true;
		if (pos != getPos()) {
			for (int i = 0; i < 8; i++) {
				collide &= (pos.x - hitBox[i].x <= hitBoxScale)
						&& (pos.x - hitBox[i].x >= -hitBoxScale) &&

						(pos.y - hitBox[i].y <= hitBoxScale)
						&& (pos.y - hitBox[i].y >= -hitBoxScale) &&

						(pos.z - hitBox[i].z <= hitBoxScale)
						&& (pos.z - hitBox[i].z >= -hitBoxScale);
			}
		} else {
			collide = false;
		}
		return collide;
	}

	public boolean passThrough(HittableEntity entity) {
		boolean collide = true;
		if (entity != this) {                // Makes sure it's not passing itself
			for (int i = 0; i < 8; i++) {
				collide &= (entity.getBox()[i].x - hitBox[i].x <= hitBoxScale)
						&& (entity.getBox()[i].x - hitBox[i].x >= -hitBoxScale) &&

						(entity.getBox()[i].y - hitBox[i].y <= hitBoxScale)
						&& (entity.getBox()[i].y - hitBox[i].y >= -hitBoxScale) &&

						(entity.getBox()[i].z - hitBox[i].z <= hitBoxScale)
						&& (entity.getBox()[i].z - hitBox[i].z >= -hitBoxScale);
			}
		} else {
			collide = false;
		}

		return collide;

	}

	public Vector3f[] getBox() {
		return hitBox;
	}

	public float getHitBoxScale() {
		return hitBoxScale;
	}

	@SuppressWarnings("unused")
	public void setHitBoxScale(float hitBoxScale) {
		this.hitBoxScale = hitBoxScale;
	}

	@SuppressWarnings("unused")
	public void collision(Entity entity) {
	}

	int i = 0;
	int health = 100;

	public void debugCollision(Entity entity) {
		if (entity instanceof BulletEntity) {
			entity.remove();
			i++;
			if (i % 2 == 0) {
				DebugMode.setModel("/models/tank.obj", "textures/base/cyan.jpg");
			} else {
				DebugMode.setModel("/models/tank.obj", "textures/base/red.jpg");
			}
			System.out.println(getID() + " Hit!");
			if (health > 0) { health -= 10; }
			System.out.println("Health: " + health);
			if (health == 0) { remove(); }
		}
	}

	// Collision Detection
	@SuppressWarnings("unused")
	public void mainCollisionCheck() {
		/* TODO : ADD REAL GAME IMPLEMENTATION
			*	HINT: YOU ONLY HAVE TO CHANGE THE ARRAY FOR WHATEVER OBJECT
			*		  YOU'RE USING FOR THE REAL GAME
			*/
		Object[] entitiesArray = DebugMode.objectMap.entityMap().toArray();
		checkForCollision(entitiesArray);
	}

	public void debugCollisionCheck() {
		Object[] entitiesArray = DebugMode.objectMap.entityMap().toArray();
		checkForCollision(entitiesArray);
	}
	public void checkForCollision(Object[] entityArray) {
		Object[] entitiesArray = DebugMode.objectMap.entityMap().toArray();
		formCube();
		for (Object ent : entitiesArray) {
			assert ent instanceof HittableEntity;
			if ((passThrough((HittableEntity) ent)) ||
					(passThrough(((Entity) ent).getPos()))) {
				this.debugCollision((Entity) ent);
				((HittableEntity) ent).debugCollision(this);
				return;
			}
		}
	}

	@Override
	public void gameTick() {}

	@Override
	public void debugGameTick() {}
}