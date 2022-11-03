package com.redteam.engine.core.entity;

import org.joml.Vector3f;

public class HittableEntity extends Entity {
	
	private float hitboxScale;
	    
	private Vector3f[] hitBox;

	public HittableEntity(String id, Model model, Vector3f pos, Vector3f rotation, float scale, float hitboxScale) {
		super(id, model, pos, rotation, scale);
		this.hitboxScale = hitboxScale;
	}
	
	@Override
	public void incPos(float x, float y, float z) {
		super.incPos(x, y, z);
		formCube();
	}
	
	@Override
	public void setPos(float x, float y, float z) {
		super.setPos(x, y, z);
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
		if(collide) {
			System.out.println("VECTOR3F COLLISION AT X: " + pos.x + " Y: " + pos.y + " Z: " + pos.z +
							   "\nWITH ENTITY: " + getID());
			return true;
		}
		return false;
	}
	
	public boolean passThrough(HittableEntity entity) {
		boolean collide = true;
		for(int i = 0; i < 8; i++) {
			collide &= (entity.getBox()[i].x - hitBox[i].x <= hitboxScale) && (entity.getBox()[i].x - hitBox[i].x >= -hitboxScale) && 
					   (entity.getBox()[i].y - hitBox[i].y <= hitboxScale) && (entity.getBox()[i].y - hitBox[i].y >= -hitboxScale) &&
					   (entity.getBox()[i].z - hitBox[i].z <= hitboxScale) && (entity.getBox()[i].z - hitBox[i].z >= -hitboxScale);
		}
		if(collide) {
			System.out.println("BOX COLLISION AT X: " + entity.getPos().x + " Y: " + entity.getPos().y + " Z: " + entity.getPos().z +
							   "\nWITH ENTITY: " + getID());
			return true;
		}
		return false;
		
	}
	
	public Vector3f[] getBox() {
		return hitBox;
	}
	
	public float getHitBoxScale() {
		return hitboxScale;
	}

	public void gameTick() {
		// TODO
	}

	public void debugGameTick() {
		// TODO
    }
}
