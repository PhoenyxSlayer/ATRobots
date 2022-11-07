package com.redteam.engine.core.entity;

import static org.junit.jupiter.api.Assertions.*;

import org.joml.Vector3f;
import org.junit.jupiter.api.Test;

class TestBulletEntity {
	Vector3f originalPos = new Vector3f(0, 0, 0);
	Vector3f originalRotation = new Vector3f(0, 0, 0);

	Bullet bullet = new Bullet("bullet", null, originalPos, originalRotation, false);

	Vector3f updatedPos = new Vector3f(81, 42, 20);
	Vector3f updatedRotation = new Vector3f(39, 105, 17);

	// BULLET CONSTRUCTOR TEST
	@Test
	void testConstructor() {
		assertEquals("bullet", bullet.getID());
		assertEquals(originalPos, bullet.getPos());
		assertEquals(originalRotation, bullet.getRotation());
		assertFalse(bullet.getIfMoving());

		Bullet secondBullet = new Bullet("secondBullet", null, updatedPos, updatedRotation, true);

		assertEquals("secondBullet", secondBullet.getID());
		assertEquals(updatedPos, secondBullet.getPos());
		assertEquals(updatedRotation, secondBullet.getRotation());
		assertTrue(secondBullet.getIfMoving());
	}

	// BULLET MOVING TEST
	@Test
	void testSetMoving() {
		assertFalse(bullet.getIfMoving());

		bullet.setMoving(true);

		assertTrue(bullet.getIfMoving());
	}
}
