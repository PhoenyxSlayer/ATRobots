package com.redteam.engine.core.entity;

import static org.junit.jupiter.api.Assertions.*;

import org.joml.Vector3f;
import org.junit.jupiter.api.Test;

class TestTankEntity {
	
	Vector3f originalPos = new Vector3f(0,0,0);
	Vector3f originalRotation = new Vector3f(0,0,0);
	Vector3f ogBaseRotation = new Vector3f(0,0,0);
	Vector3f ogTurretRotation = new Vector3f(0,0,0);
	
	TankEntity tank = new TankEntity("tank", originalPos, originalRotation);
	
	
	Vector3f updatedPos = new Vector3f(50,20,10);
	Vector3f updatedRotation = new Vector3f(10,1,23);
	Vector3f updatedBaseRotate = new Vector3f(37, 145, 8);
	Vector3f updatedTurretRotate = new Vector3f(101,3,134);
	
	
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
		tank.setRotation(originalRotation.x, originalRotation.y, originalRotation.z);
		
		assertEquals(0.0f, tank.getRotation().x, 0.01f);
		assertEquals(0.0f, tank.getRotation().y, 0.01f);
		assertEquals(0.0f, tank.getRotation().z, 0.01f);
		
		tank.setRotation(updatedRotation.x, updatedRotation.y, updatedRotation.z);
		
		assertEquals(10.0f, tank.getRotation().x, 0.01f);
		assertEquals(1.0f, tank.getRotation().x, 0.01f);
		assertEquals(23.0f, tank.getRotation().x, 0.01f);
		
		// SPECIAL TO SETROTATION
		tank.setRotation(30.0f, 10.0f, 5.0f);
		
		assertEquals(30.0f, tank.getRotation().x, 0.01f);
		assertEquals(10.0f, tank.getRotation().x, 0.01f);
		assertEquals(5.0f, tank.getRotation().x, 0.01f);
	}
	
	// BASE ROTATION TEST
	
	@Test
	void testIncBaseRotation() {
		tank.setBaseRotation(ogBaseRotation.x, ogBaseRotation.y, ogBaseRotation.z);
		
		assertEquals(0.0f, tank.getBaseRotation().x, 0.01f);
		assertEquals(0.0f, tank.getBaseRotation().y, 0.01f);
		assertEquals(0.0f, tank.getBaseRotation().z, 0.01f);
		
		tank.incBaseRotation(updatedBaseRotate.x, updatedBaseRotate.y, updatedBaseRotate.z);
		
		assertEquals(37.0f, tank.getBaseRotation().x, 0.01f);
		assertEquals(145.0f, tank.getBaseRotation().y, 0.01f);
		assertEquals(8.0f, tank.getBaseRotation().z, 0.01f);
		
		// SPECIAL TO INCBASEROTATION
		tank.incBaseRotation(3.0f, 5.0f, 42.0f);
		
		assertEquals(40.0f, tank.getBaseRotation().x, 0.01f);
		assertEquals(150.0f, tank.getBaseRotation().y, 0.01f);
		assertEquals(50.0f, tank.getBaseRotation().z, 0.01f);
	}
	
	@Test
	void testSetBaseRotation() {
		tank.setBaseRotation(ogBaseRotation.x, ogBaseRotation.y, ogBaseRotation.z);
		
		assertEquals(0.0f, tank.getBaseRotation().x, 0.01f);
		assertEquals(0.0f, tank.getBaseRotation().y, 0.01f);
		assertEquals(0.0f, tank.getBaseRotation().z, 0.01f);
		
		tank.setBaseRotation(updatedBaseRotate.x, updatedBaseRotate.y, updatedBaseRotate.z);
		
		assertEquals(37.0f, tank.getBaseRotation().x, 0.01f);
		assertEquals(145.0f, tank.getBaseRotation().y, 0.01f);
		assertEquals(8.0f, tank.getBaseRotation().z, 0.01f);
		
		// SPECIAL TO SETBASEROTATION
		tank.setBaseRotation(12.0f, 104.0f, 65.0f);
		
		assertEquals(12.0f, tank.getBaseRotation().x, 0.01f);
		assertEquals(104.0f, tank.getBaseRotation().y, 0.01f);
		assertEquals(65.0f, tank.getBaseRotation().z, 0.01f);
	}
	
	
	// TURRET ROTATION TEST
	
	@Test
	void testIncTurretRotation() {
		tank.setTurretRotation(ogTurretRotation.x, ogTurretRotation.y, ogTurretRotation.z);
		
		assertEquals(0.0f, tank.getTurretRotation().x, 0.01f);
		assertEquals(0.0f, tank.getTurretRotation().y, 0.01f);
		assertEquals(0.0f, tank.getTurretRotation().z, 0.01f);
		
		tank.incTurretRotation(updatedTurretRotate.x, updatedTurretRotate.y, updatedTurretRotate.z);
		
		assertEquals(101.0f, tank.getTurretRotation().x, 0.01f);
		assertEquals(3.0f, tank.getTurretRotation().y, 0.01f);
		assertEquals(134.0f, tank.getTurretRotation().z, 0.01f);
		
		// SPECIAL TO INCTURRETROTATION
		tank.incTurretRotation(9.0f, 7.0f, 6.0f);
		
		assertEquals(110.0f, tank.getTurretRotation().x, 0.01f);
		assertEquals(10.0f, tank.getTurretRotation().y, 0.01f);
		assertEquals(140.0f, tank.getTurretRotation().z, 0.01f);
	}
	
	@Test
	void testSetTurretRotation() {
		tank.setTurretRotation(ogTurretRotation.x, ogTurretRotation.y, ogTurretRotation.z);
		
		assertEquals(0.0f, tank.getTurretRotation().x, 0.01f);
		assertEquals(0.0f, tank.getTurretRotation().y, 0.01f);
		assertEquals(0.0f, tank.getTurretRotation().z, 0.01f);
		
		tank.setTurretRotation(updatedTurretRotate.x, updatedTurretRotate.y, updatedTurretRotate.z);
		
		assertEquals(101.0f, tank.getTurretRotation().x, 0.01f);
		assertEquals(3.0f, tank.getTurretRotation().y, 0.01f);
		assertEquals(134.0f, tank.getTurretRotation().z, 0.01f);
		
		// SPECIAL TO SETTURRETROTATION
		tank.setTurretRotation(11.0f, 7.0f, 14.0f);
		
		assertEquals(11.0f, tank.getTurretRotation().x, 0.01f);
		assertEquals(7.0f, tank.getTurretRotation().y, 0.01f);
		assertEquals(14.0f, tank.getTurretRotation().z, 0.01f);
	}
}
