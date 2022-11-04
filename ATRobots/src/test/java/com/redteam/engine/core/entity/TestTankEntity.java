package com.redteam.engine.core.entity;

import static org.junit.jupiter.api.Assertions.*;

import org.joml.Vector3f;
import org.junit.jupiter.api.Test;

class TestTankEntity {
	
	Vector3f originalPos = new Vector3f(0,0,0);
	Vector3f originalRotation = new Vector3f(0,0,0);
	
	TankEntity tank = new TankEntity("tank", originalPos, originalRotation, 1f);
	
	
	Vector3f updatedPos = new Vector3f(50,20,10);
	Vector3f updatedRotation = new Vector3f(10,1,23);
	
	
	// TankEntity CONSTRUCTOR TEST
	@Test
	void testConstructor() {
		fail("Not yet implemented");
	}
	
	// TANK POSITIONING TEST
	
	@Test
	void testIncPos() {
		tank.setPos(originalPos.x, originalPos.y, originalPos.z);
		
		assertEquals(0.0f, tank.getPos().x, 0.01f);
		assertEquals(0.0f, tank.getPos().y, 0.01f);
		assertEquals(0.0f, tank.getPos().z, 0.01f);
		
		tank.incPos(updatedPos.x, updatedPos.y, updatedPos.z);
		
		assertEquals(50.0f, tank.getPos().x, 0.01f);
		assertEquals(20.0f, tank.getPos().y, 0.01f);
		assertEquals(10.0f, tank.getPos().z, 0.01f);
		
		
		// SPECIAL TO INCPOS
		tank.incPos(30.0f, 20.0f, 1.0f);
		
		assertEquals(80.0f, tank.getPos().x, 0.01f);
		assertEquals(40.0f, tank.getPos().y, 0.01f);
		assertEquals(11.0f, tank.getPos().z, 0.01f);
	}
	
	@Test
	void testSetPos() {
		tank.setPos(originalPos.x, originalPos.y, originalPos.z);
		
		assertEquals(0.0f, tank.getPos().x, 0.01f);
		assertEquals(0.0f, tank.getPos().y, 0.01f);
		assertEquals(0.0f, tank.getPos().z, 0.01f);
		
		tank.setPos(updatedPos.x, updatedPos.y, updatedPos.z);
		
		assertEquals(50.0f, tank.getPos().x, 0.01f);
		assertEquals(20.0f, tank.getPos().y, 0.01f);
		assertEquals(10.0f, tank.getPos().z, 0.01f);
		
		// SPECIAL TO SETPOS
		tank.setPos(30.0f, 20.0f, 1.0f);
				
		assertEquals(30.0f, tank.getPos().x, 0.01f);
		assertEquals(20.0f, tank.getPos().y, 0.01f);
		assertEquals(1.0f, tank.getPos().z, 0.01f);
	}
	
	// TANK ROTATION TEST
	
	@Test
	void testIncRotation() {
		tank.setRotation(originalRotation.x, originalRotation.y, originalRotation.z);
		
		assertEquals(0.0f, tank.getRotation().x, 0.01f);
		assertEquals(0.0f, tank.getRotation().y, 0.01f);
		assertEquals(0.0f, tank.getRotation().z, 0.01f);
		
		tank.incRotation(updatedRotation.x, updatedRotation.y, updatedRotation.z);
		
		assertEquals(10.0f, tank.getRotation().x, 0.01f);
		assertEquals(1.0f, tank.getRotation().y, 0.01f);
		assertEquals(23.0f, tank.getRotation().z, 0.01f);
		
		// SPECIAL TO INCROTATION
		tank.incRotation(30.0f, 20.0f, 1.0f);
				
		assertEquals(40.0f, tank.getRotation().x, 0.01f);
		assertEquals(21.0f, tank.getRotation().y, 0.01f);
		assertEquals(24.0f, tank.getRotation().z, 0.01f);
	}
	
	@Test
	void testSetRotation() {
		fail("Not yet implemented");
	}
	
	// BASE ROTATION TEST
	
	@Test
	void testIncBaseRotation() {
		fail("Not yet implemented");
	}
	
	@Test
	void testSetBaseRotation() {
		fail("Not yet implemented");
	}
	
	
	// TURRET ROTATION TEST
	
	@Test
	void testIncTurretRotation() {
		fail("Not yet implemented");
	}
	
	@Test
	void testSetTurretRotation() {
		fail("Not yet implemented");
	}
}
