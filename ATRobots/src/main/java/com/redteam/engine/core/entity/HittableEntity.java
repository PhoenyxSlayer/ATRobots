package com.redteam.engine.core.entity;

import com.redteam.engine.game.TestGame;
import static com.redteam.engine.utils.Consts.*;

import org.joml.Vector3f;

public class HittableEntity extends Entity {
	
	private float hitboxScale;
	    
	private Vector3f[] hitBox;

	public HittableEntity(String id, Model model, Vector3f pos, Vector3f rotation, float scale, float hitboxScale) {
		super(id, model, pos, rotation, scale);
		this.hitboxScale = hitboxScale;
		formCube();
	}
	
	// ALL BELOW HITBOX CODE
    
    private void formCube() {
		
		float halfSideLength = hitboxScale / 2f;
		
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
		for(int i = 0; i < 8; i++) {
			collide &= (pos.x - hitBox[i].x <= hitboxScale) && (pos.x - hitBox[i].x >= -hitboxScale) && 
					   (pos.y - hitBox[i].y <= hitboxScale) && (pos.y - hitBox[i].y >= -hitboxScale) &&
					   (pos.z - hitBox[i].z <= hitboxScale) && (pos.z - hitBox[i].z >= -hitboxScale);
		}
		return collide;
	}
	
	public boolean passThrough(HittableEntity entity) {
		boolean collide = true;
		if(entity != this) {				// Makes sure it's not passing itself
			for(int i = 0; i < 8; i++) {
				collide &= (entity.getBox()[i].x - hitBox[i].x <= hitboxScale) && (entity.getBox()[i].x - hitBox[i].x >= -hitboxScale) && 
						   (entity.getBox()[i].y - hitBox[i].y <= hitboxScale) && (entity.getBox()[i].y - hitBox[i].y >= -hitboxScale) &&
						   (entity.getBox()[i].z - hitBox[i].z <= hitboxScale) && (entity.getBox()[i].z - hitBox[i].z >= -hitboxScale);
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
		return hitboxScale;
	}

	public void collision(Entity entity){ return; }
	public void debugCollision(Entity entity) { return; }

	public void gameTick() { return; }
	public void debugGameTick() { return; }


	// Collision Detection

	Object entitiiesArray[];
	
	public boolean collisionCheck() {
		entitiiesArray = TestGame.entities.toArray();
		formCube();
		for(int i = 0; i < entitiiesArray.length; i++) {
			if((Entity)entitiiesArray[i] instanceof HittableEntity) {
				for(int j = i; j < entitiiesArray.length; j++) {
					if(passThrough((HittableEntity)entitiiesArray[j])) {
						((HittableEntity)entitiiesArray[i]).debugCollision((Entity)entitiiesArray[j]);
						((HittableEntity)entitiiesArray[j]).debugCollision((Entity)entitiiesArray[i]);
						return true;
					}
				}
			}
		}
		return false;
	}

	public boolean inBorder() {
		if((getPos().x <= -X_BORDER || getPos().x >= X_BORDER)
		|| (getPos().z <= -Z_BORDER || getPos().z >= 0))
		{
			return false;
		}
		return true;
	}
}
