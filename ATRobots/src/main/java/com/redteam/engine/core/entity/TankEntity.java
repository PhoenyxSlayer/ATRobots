package com.redteam.engine.core.entity;

import org.joml.Vector3f;

public class TankEntity extends HittableEntity {
	private Model[] tank;
	private Vector3f turretRotation;
	private Model top, base;

	public TankEntity(String id, Model top, Model base, Vector3f pos, Vector3f rotation, float scale, float hitboxScale) {
		super(id, base, pos, rotation, scale, hitboxScale);
		this.top = top;
		this.base = base;
		tank = new Model[] {top, base};
	}
	
	public void incRotationTurret(float y) {
		this.turretRotation.y += y;
	}
	
	public void setRotationTurret(float y) {
		this.turretRotation.y = y;
	}
	
	public Model[] getTank() {
		return tank;
	}
	
	public Model getTop() {
		return top;
	}
	
	public Model getBase() {
		return base;
	}
}