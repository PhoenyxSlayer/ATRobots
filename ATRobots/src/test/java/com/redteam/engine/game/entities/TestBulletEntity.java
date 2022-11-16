package com.redteam.engine.game.entities;

import static org.junit.jupiter.api.Assertions.*;

import com.redteam.engine.game.entities.BulletEntity;
import org.joml.Vector3f;
import org.junit.jupiter.api.Test;

class TestBulletEntity {
	Vector3f originalPos = new Vector3f(0, 0, 0);
	Vector3f originalRotation = new Vector3f(0, 0, 0);

	BulletEntity bulletEntity = new BulletEntity("bullet", null, originalPos, originalRotation, false);

	Vector3f updatedPos = new Vector3f(81, 42, 20);
	Vector3f updatedRotation = new Vector3f(39, 105, 17);

	// BULLET CONSTRUCTOR TEST
	@Test
	void testConstructor() {
		assertEquals("bullet", bulletEntity.getID());
		assertEquals(originalPos, bulletEntity.getPos());
		assertEquals(originalRotation, bulletEntity.getRotation());
		assertFalse(bulletEntity.getIfMoving());

		BulletEntity newBulletEntity = new BulletEntity("secondBullet", null, updatedPos, updatedRotation, true);

		assertEquals("secondBullet", newBulletEntity.getID());
		assertEquals(updatedPos, newBulletEntity.getPos());
		assertEquals(updatedRotation, newBulletEntity.getRotation());
		assertTrue(newBulletEntity.getIfMoving());
	}

	// BULLET MOVING TEST
	@Test
	void testSetMoving() {
		assertFalse(bulletEntity.getIfMoving());

		bulletEntity.setMoving(true);

		assertTrue(bulletEntity.getIfMoving());
	}
}
