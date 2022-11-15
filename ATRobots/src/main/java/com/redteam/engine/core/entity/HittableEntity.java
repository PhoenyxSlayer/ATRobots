package com.redteam.engine.core.entity;

import com.redteam.engine.core.rendering.Model;
import com.redteam.engine.game.debug.DebugMode;
import org.joml.Vector3f;

public abstract class HittableEntity extends Entity {
	
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

    public boolean passThrough(Vector3f pos) {
		boolean collide = true;
		if(pos != getPos())  {
			for(int i = 0; i < 8; i++) {
				collide &= (pos.x - hitBox[i].x <= hitBoxScale) && (pos.x - hitBox[i].x >= -hitBoxScale) &&
						(pos.y - hitBox[i].y <= hitBoxScale) && (pos.y - hitBox[i].y >= -hitBoxScale) &&
						(pos.z - hitBox[i].z <= hitBoxScale) && (pos.z - hitBox[i].z >= -hitBoxScale);
			}
		} else {
			collide = false;
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

	public float getHitBoxScale() {
		return hitBoxScale;
	}
	@SuppressWarnings("unused")
	public void setHitBoxScale(float hitBoxScale) {
		this.hitBoxScale = hitBoxScale;
	}
	
	@SuppressWarnings("unused")
	protected abstract void collision(Entity entity);

	protected abstract void debugCollision(Entity entity);

	// Collision Detection
	@SuppressWarnings("unused")
	public void mainCollisionCheck() {
		/* TODO : ADD REAL GAME IMPLEMENTATION
		 *	HINT: YOU ONLY HAVE TO CHANGE THE ARRAY FOR WHATEVER OBJECT
		 *		  YOU'RE USING FOR THE REAL GAME
		 */
		Object[] entitiesArray = null; //= DebugMode.objectMap.entityMap().toArray();
		checkForCollision(entitiesArray);
	}

	public void debugCollisionCheck() {
		Object[] entitiesArray = DebugMode.objectMap.entityMap().toArray();
		checkForCollision(entitiesArray);
	}

	private void checkForCollision(Object[] entitiesArray) {
		formCube();
		for (Object ent : entitiesArray) {
			assert ent instanceof HittableEntity;
			if( (passThrough((HittableEntity) ent)) ||
					(passThrough(((Entity) ent).getPos()))) {
				this.debugCollision((Entity) ent);
				((HittableEntity) ent).debugCollision(this);
				return;
			}
		}
	}
}
