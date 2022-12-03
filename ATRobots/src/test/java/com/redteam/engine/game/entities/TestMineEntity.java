package com.redteam.engine.game.entities;

import static org.junit.jupiter.api.Assertions.*;

import org.joml.Vector3f;
import org.junit.jupiter.api.Test;

class TestMineEntity {
    Vector3f originalPos = new Vector3f(0, 0, 0);
    Vector3f originalRotation = new Vector3f(0, 0, 0);
    MineEntity mineEntity = new MineEntity("mine", null, originalPos, originalRotation);
    Vector3f updatedPos = new Vector3f(52, 10, 64);
    Vector3f updatedRotation = new Vector3f(27, 86, 2);

    // MINE CONSTRUCTOR TEST
    @Test
    void testConstructor() {
        assertEquals("mine", mineEntity.getID());
        assertEquals(originalPos, mineEntity.getPos());
        assertEquals(originalRotation, mineEntity.getRotation());

        MineEntity newMineEntity = new MineEntity("secondMine", null, updatedPos, updatedRotation);

        assertEquals("secondMine", newMineEntity.getID());
        assertEquals(updatedPos, newMineEntity.getPos());
        assertEquals(updatedRotation, newMineEntity.getRotation());
    }
}
