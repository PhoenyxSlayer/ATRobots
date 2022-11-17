package com.redteam.engine.core.entity;

import com.redteam.engine.core.rendering.Model;
import com.redteam.engine.core.rendering.Texture;
import com.redteam.engine.game.debug.DebugMode;
import com.redteam.engine.game.entities.BulletEntity;
import org.joml.Vector3f;

import static com.redteam.engine.utils.Constants.X_BORDER;
import static com.redteam.engine.utils.Constants.Z_BORDER;

public class HittableEntity extends Entity {
	
	private float hitBoxScale;

	private int health = 100;
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
				new Vector3f( getPos().x - halfSideLength,
							  getPos().y + halfSideLength,
							  getPos().z + halfSideLength ), // top left
				
				new Vector3f( getPos().x + halfSideLength,
							  getPos().y + halfSideLength,
							  getPos().z + halfSideLength), // top right
				
				new Vector3f( getPos().x + halfSideLength,
							  getPos().y - halfSideLength,
							  getPos().z + halfSideLength), // bottom right
				
				new Vector3f( getPos().x - halfSideLength,
							  getPos().y - halfSideLength,
							  getPos().z + halfSideLength), // bottom left
				
				
				// back face
				new Vector3f( getPos().x - halfSideLength,
							  getPos().y + halfSideLength,
							  getPos().z - halfSideLength), // top left
				
				new Vector3f( getPos().x + halfSideLength,
							  getPos().y + halfSideLength,
							  getPos().z - halfSideLength), // top right
				
				new Vector3f( getPos().x + halfSideLength,
							  getPos().y - halfSideLength,
							  getPos().z - halfSideLength), // bottom right
				
				new Vector3f( getPos().x - halfSideLength,
							  getPos().y - halfSideLength,
							  getPos().z - halfSideLength), // bottom left
		};
	}

	@SuppressWarnings("unused")
    public boolean passThrough(Vector3f pos) {
		boolean collide = true;
		for(int i = 0; i < 8; i++) {
			collide &= (pos.x - hitBox[i].x <= hitBoxScale) && (pos.x - hitBox[i].x >= -hitBoxScale) &&
					   (pos.y - hitBox[i].y <= hitBoxScale) && (pos.y - hitBox[i].y >= -hitBoxScale) &&
					   (pos.z - hitBox[i].z <= hitBoxScale) && (pos.z - hitBox[i].z >= -hitBoxScale);
		}
		return collide;
	}
	
	public boolean passThrough(HittableEntity entity) {
		boolean collide = true;
		if(entity != this) {				// Makes sure it's not passing itself
			for(int i = 0; i < 8; i++) {
				collide &= (entity.getBox()[i].x - hitBox[i].x <= hitBoxScale) && (entity.getBox()[i].x - hitBox[i].x >= -hitBoxScale) &&
						   (entity.getBox()[i].y - hitBox[i].y <= hitBoxScale) && (entity.getBox()[i].y - hitBox[i].y >= -hitBoxScale) &&
						   (entity.getBox()[i].z - hitBox[i].z <= hitBoxScale) && (entity.getBox()[i].z - hitBox[i].z >= -hitBoxScale);
			} 
		}
		else {
			collide = false;
		}
		
		return collide;
		
	}
	
	public Vector3f[] getBox() {
		return hitBox;
	}

	@SuppressWarnings("unused")
	public float getHitBoxScale() {
		return hitBoxScale;
	}
	public void setHitBoxScale(float hitBoxScale) {
		this.hitBoxScale = hitBoxScale;
	}
	
	@SuppressWarnings("unused")
	public void collision(Entity entity){
	}
	int i = 0;
	public void debugCollision(Entity entity) {
		if(entity instanceof BulletEntity) {
			entity.remove();
			i++;
			if(i % 2 == 0) {
				setModel(DebugMode.setModel("/models/tank.obj", "textures/base/red.jpg"));
			} else {
				setModel(DebugMode.setModel("/models/tank.obj", "textures/base/cyan.jpg"));
			}
			System.out.println("Tank Hit!");
			if(health >= 10) { health -= 10; }
			System.out.println("Health: " + health);
		}
	}

	public void gameTick() {
	}
	public void debugGameTick() {
	}

	// Collision Detection
	@SuppressWarnings("unused")
	public void collisionCheck() {
		/* TODO : ADD REAL GAME IMPLEMENTATION
			HINT: YOU ONLY HAVE TO CHANGE THE TOARRAY FOR WHATEVER OBJECT
				  YOU'RE USING FOR THE REAL GAME
		 */
	}

	public void debugCollisionCheck() {
		Object[] entitiesArray = DebugMode.objectMap.entityMap().toArray();
		formCube();
		for (Object ent : entitiesArray) {
			if (ent instanceof HittableEntity) {
				if(passThrough((HittableEntity) ent) &&
						passThrough(((HittableEntity) ent).getPos())){
					this.debugCollision((HittableEntity) ent);
					((HittableEntity) ent).debugCollision(this);
					return;
				}
			}
		}
	}

	public boolean outOfBorder() {
		return ((getPos().x <= -X_BORDER) || (getPos().x >= X_BORDER))
				|| ((getPos().z <= -Z_BORDER) || (getPos().z >= 0));
	}
}
