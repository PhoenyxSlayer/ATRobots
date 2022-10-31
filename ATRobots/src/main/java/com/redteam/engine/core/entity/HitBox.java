package com.redteam.engine.core.entity;
import org.joml.Vector3f;

public class HitBox {
	//private HashMap<Integer, Vector3f> cube = new HashMap<String, Vector3f>();
	private Entity entity;
	private Vector3f entityPosition = new Vector3f();
	private float edgeLength = 0f;
	Vector3f vertices[];
	
	public HitBox(Entity entity, float edgeLength) throws Exception {
		if(entity != null) {
			this.entity = entity;
			entityPosition = entity.getPos();
			this.edgeLength = edgeLength;
			formCube();
		}
		else {
			throw new Exception("Entity: " + entity + " is null");
		}
		return;
	}
	
	public void updateHitBox(Entity entity) {
		formCube();
		return;
	}
	
	private void formCube() {
		
		float halfSideLength = edgeLength / 2f;
		
		vertices = new Vector3f[]{
				
				// front face
				new Vector3f( entityPosition.x - halfSideLength,
							  entityPosition.y + halfSideLength,
							  entityPosition.z + halfSideLength ), // top left
				
				new Vector3f( entityPosition.x + halfSideLength,
							  entityPosition.y + halfSideLength,
							  entityPosition.z + halfSideLength), // top right
				
				new Vector3f( entityPosition.x + halfSideLength,
							  entityPosition.y - halfSideLength,
							  entityPosition.z + halfSideLength), // bottom right
				
				new Vector3f( entityPosition.x - halfSideLength,
						 	  entityPosition.y - halfSideLength,
						 	  entityPosition.z + halfSideLength), // bottom left
				
				
				// back face
				new Vector3f( entityPosition.x - halfSideLength,
							  entityPosition.y + halfSideLength,
							  entityPosition.z - halfSideLength), // top left
				
				new Vector3f( entityPosition.x + halfSideLength,
							  entityPosition.y + halfSideLength,
							  entityPosition.z - halfSideLength), // top right
				
				new Vector3f( entityPosition.x + halfSideLength,
							  entityPosition.y - halfSideLength,
							  entityPosition.z - halfSideLength), // bottom right
				
				new Vector3f( entityPosition.x - halfSideLength,
							  entityPosition.y - halfSideLength,
							  entityPosition.z - halfSideLength), // bottom left
				
				
				// left face
				// top left (top left front face)
				// top right (top left back face)
				// bottom right (bottom left back face)
				// bottom left (bottom left front face)
				
				
				// right face
				// top left (top right front face)
				// top right (top right back face)
				// bottom right (bottom right back face)
				// bottom left (bottom right front face)
				
				
				// top face
				// top left (top left front face)
				// top right (top left back face)
				// bottom right (top right back face)
				// bottom left (top right front face)
				
				
				// bottom face
				// top left (bottom left front face)
				// top right (bottom left back face)
				// bottom right (bottom right back face)
				// bottom left (bottom right front face)
		};
	}
	
	public boolean passThrough(Entity entity) {
		boolean collide = true;
		for(int i = 0; i < 8; i++) {
			collide &= (entity.getPos().x - vertices[i].x <= edgeLength) && (entity.getPos().x - vertices[i].x >= -edgeLength) && 
					   (entity.getPos().y - vertices[i].y <= edgeLength) && (entity.getPos().y - vertices[i].y >= -edgeLength) &&
					   (entity.getPos().z - vertices[i].z <= edgeLength) && (entity.getPos().z - vertices[i].z >= -edgeLength);
		}
		if(collide) {
			System.out.println("VECTOR3F COLLISION AT X: " + entity.getPos().x + " Y: " + entity.getPos().y + " Z: " + entity.getPos().z);
			return true;
		}
		return false;
	}
	
	public boolean passThrough(HitBox box) {
		boolean collide = true;
		for(int i = 0; i < 8; i++) {
			collide &= (box.getBox()[i].x - vertices[i].x <= edgeLength) && (box.getBox()[i].x - vertices[i].x >= -edgeLength) && 
					   (box.getBox()[i].y - vertices[i].y <= edgeLength) && (box.getBox()[i].y - vertices[i].y >= -edgeLength) &&
					   (box.getBox()[i].z - vertices[i].z <= edgeLength) && (box.getBox()[i].z - vertices[i].z >= -edgeLength);
		}
		if(collide) {
			System.out.println("BOX COLLISION AT X: " + getEntity().getPos().x + " Y: " + getEntity().getPos().y + " Z: " + getEntity().getPos().z +
							   "\nWITH ENTITY AT X: " + box.getEntity().getPos().x + " Y: " + box.getEntity().getPos().y + " Z: " + box.getEntity().getPos().z);
			return true;
		}
		return false;
		
	}
	
	public Vector3f[] getBox() {
		return vertices;
	}
	
	public float getEdgeLength() {
		return edgeLength;
	}
	
	public Entity getEntity() {
		return entity;
	}

}