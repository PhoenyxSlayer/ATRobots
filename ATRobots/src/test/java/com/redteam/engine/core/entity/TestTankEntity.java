package com.redteam.engine.core.entity;

import static org.junit.jupiter.api.Assertions.*;

import com.redteam.engine.game.entities.TankEntity;
import org.joml.Vector3f;
import org.junit.jupiter.api.Test;

class TestTankEntity {
	Vector3f originalPos = new Vector3f(0,0,0);
	Vector3f originalRotation = new Vector3f(0,0,0);
	Vector3f ogBaseRotation = new Vector3f(0,0,0);
	Vector3f ogTurretRotation = new Vector3f(0,0,0);
	
	TankEntity tank = new TankEntity("tank", null, null, originalPos, originalRotation);
	
	
	Vector3f updatedPos = new Vector3f(50,20,10);
	Vector3f updatedRotation = new Vector3f(10,1,23);
	Vector3f updatedBaseRotate = new Vector3f(21, 73, 106);
	Vector3f updatedTurretRotate = new Vector3f(67, 49, 92);

	// TankEntity CONSTRUCTOR TEST
	@Test
	void testConstructor() {
		TankEntity firstTank = new TankEntity("tank", null, null, originalPos, originalRotation);

		assertEquals("tank", firstTank.getID());
		assertEquals(originalPos, firstTank.getPos());
		assertEquals(originalRotation, firstTank.getRotation());

		TankEntity secondTank = new TankEntity("newTank", null, null, updatedPos, updatedRotation);

		assertEquals("newTank", secondTank.getID());
		assertEquals(updatedPos, secondTank.getPos());
		assertEquals(updatedRotation, secondTank.getRotation());
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

	// TANK POSITIONING TEST
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

	// TANK ROTATION TEST
	@Test
	void testSetRotation() {
		tank.setRotation(originalRotation.x, originalRotation.y, originalRotation.z);
		
		assertEquals(0.0f, tank.getRotation().x, 0.01f);
		assertEquals(0.0f, tank.getRotation().y, 0.01f);
		assertEquals(0.0f, tank.getRotation().z, 0.01f);
		
		tank.setRotation(updatedRotation.x, updatedRotation.y, updatedRotation.z);
		
		assertEquals(10.0f, tank.getRotation().x, 0.01f);
		assertEquals(1.0f, tank.getRotation().y, 0.01f);
		assertEquals(23.0f, tank.getRotation().z, 0.01f);
		
		//SPECIAL TO SETROTATION
		tank.setRotation(45.0f, 16.0f, 76.0f);
		
		assertEquals(45.0f, tank.getRotation().x, 0.01f);
		assertEquals(16.0f, tank.getRotation().y, 0.01f);
		assertEquals(76.0f, tank.getRotation().z, 0.01f);
	}
	
	// BASE ROTATION TEST
	@Test
	void testIncBaseRotation() {
		tank.setBaseRotation(ogBaseRotation.x, ogBaseRotation.y, ogBaseRotation.z);
		
		assertEquals(0.0f, tank.getBaseRotation().x, 0.01f);
		assertEquals(0.0f, tank.getBaseRotation().y, 0.01f);
		assertEquals(0.0f, tank.getBaseRotation().z, 0.01f);
		
		tank.incBaseRotation(updatedBaseRotate.x, updatedBaseRotate.y, updatedBaseRotate.z);
		
		assertEquals(21.0f, tank.getBaseRotation().x, 0.01f);
		assertEquals(73.0f, tank.getBaseRotation().y, 0.01f);
		assertEquals(106.0f, tank.getBaseRotation().z, 0.01f);
		
		// SPECIAL TO INCBASEROTATION
		tank.incBaseRotation(9.0f, 2.0f, 4.0f);
		
		assertEquals(30.0f, tank.getBaseRotation().x, 0.01f);
		assertEquals(75.0f, tank.getBaseRotation().y, 0.01f);
		assertEquals(110.0f, tank.getBaseRotation().z, 0.01f);
	}

	// BASE ROTATION TEST
	@Test
	void testSetBaseRotation() {
		tank.setBaseRotation(ogBaseRotation.x, ogBaseRotation.y, ogBaseRotation.z);
		
		assertEquals(0.0f, tank.getBaseRotation().x, 0.01f);
		assertEquals(0.0f, tank.getBaseRotation().y, 0.01f);
		assertEquals(0.0f, tank.getBaseRotation().z, 0.01f);
		
		tank.setBaseRotation(updatedBaseRotate.x, updatedBaseRotate.y, updatedBaseRotate.z);
		
		assertEquals(21.0f, tank.getBaseRotation().x, 0.01f);
		assertEquals(73.0f, tank.getBaseRotation().y, 0.01f);
		assertEquals(106.0f, tank.getBaseRotation().z, 0.01f);
		
		//SPECIAL TO SETBASEROTATION
		tank.setBaseRotation(91.0f, 32.0f, 7.0f);
		
		assertEquals(91.0f, tank.getBaseRotation().x, 0.01f);
		assertEquals(32.0f, tank.getBaseRotation().y, 0.01f);
		assertEquals(7.0f, tank.getBaseRotation().z, 0.01f);
	}

	// TURRET ROTATION TEST
	@Test
	void testIncTurretRotation() {
		tank.setTurretRotation(ogTurretRotation.x, ogTurretRotation.y, ogTurretRotation.z);
		
		assertEquals(0.0f, tank.getTurretRotation().x, 0.01f);
		assertEquals(0.0f, tank.getTurretRotation().y, 0.01f);
		assertEquals(0.0f, tank.getTurretRotation().z, 0.01f);
		
		tank.incTurretRotation(updatedTurretRotate.x, updatedTurretRotate.y, updatedTurretRotate.z);
		
		assertEquals(67.0f, tank.getTurretRotation().x, 0.01f);
		assertEquals(49.0f, tank.getTurretRotation().y, 0.01f);
		assertEquals(92.0f, tank.getTurretRotation().z, 0.01f);
		
		// SPECIAL TO INCTURRETROTATION
		tank.incTurretRotation(10.0f, 31.0f, 28.0f);
		
		assertEquals(77.0f, tank.getTurretRotation().x, 0.01f);
		assertEquals(80.0f, tank.getTurretRotation().y, 0.01f);
		assertEquals(120.0f, tank.getTurretRotation().z, 0.01f);
	}

	// TURRET ROTATION TEST
	@Test
	void testSetTurretRotation() {
		tank.setTurretRotation(ogTurretRotation.x, ogTurretRotation.y, ogTurretRotation.z);
		
		assertEquals(0.0f, tank.getTurretRotation().x, 0.01f);
		assertEquals(0.0f, tank.getTurretRotation().y, 0.01f);
		assertEquals(0.0f, tank.getTurretRotation().z, 0.01f);
		
		tank.setTurretRotation(updatedTurretRotate.x, updatedTurretRotate.y, updatedTurretRotate.z);
		
		assertEquals(67.0f, tank.getTurretRotation().x, 0.01f);
		assertEquals(49.0f, tank.getTurretRotation().y, 0.01f);
		assertEquals(92.0f, tank.getTurretRotation().z, 0.01f);
		
		// SPECIAL TO SETTURRETROTATION
		tank.setTurretRotation(100.0f, 13.0f, 69.0f);
		
		assertEquals(100.0f, tank.getTurretRotation().x, 0.01f);
		assertEquals(13.0f, tank.getTurretRotation().y, 0.01f);
		assertEquals(69.0f, tank.getTurretRotation().z, 0.01f);
	}
}
