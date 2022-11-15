package com.redteam.engine.core.entity;

import static org.junit.jupiter.api.Assertions.*;

import com.redteam.engine.core.rendering.Model;
import org.joml.Vector3f;
import org.junit.jupiter.api.Test;

/* https://docs.google.com/document/d/1c6gZt4eIC7wcBEtP2FeN9dnjtGApW9oLxj5VHExmx9c/edit?usp=sharing
 *  -> Link to Design Doc
 */

// DUE TO ENTITY BEING ABSTRACT WE NEED TO MAKE A SUBCLASS TO TEST IT
class TestEntityClass extends Entity {
    public TestEntityClass(String id, Model model, Vector3f pos, Vector3f rotation, float scale) {
        super(id, model, pos, rotation, scale);
    }

    public void gameTick() {}
    public void debugGameTick() {}
}

class TestEntity {
    Vector3f originalPos = new Vector3f(0.0f,0.0f,0.0f);
    Vector3f originalRot = new Vector3f(0.0f,0.0f,0.0f);

    Entity testEntity = new TestEntityClass("tank", null, originalPos, originalRot, 1);

    Vector3f updatedPos = new Vector3f(45.0f, 76.0f, 21.0f);
    Vector3f updatedRot = new Vector3f(79.0f, 5.0f, 83.0f);

    // ENTITY CONSTRUCTOR TEST
    @Test
    void testConstructor() {
        assertEquals("tank", testEntity.getID());
        assertEquals(originalPos, testEntity.getPos());
        assertEquals(originalRot, testEntity.getRotation());
        assertEquals(1, testEntity.getScale());

        Entity secondEntity = new TestEntityClass("secondTank", null, updatedPos, updatedRot, 2);

        assertEquals("secondTank", secondEntity.getID());
        assertEquals(updatedPos, secondEntity.getPos());
        assertEquals(updatedRot, secondEntity.getRotation());
        assertEquals(2, secondEntity.getScale());
    }

    // ENTITY POSITIONING TEST
    @Test
    void testIncPos() {
        testEntity.setPos(originalPos.x, originalPos.y, originalPos.z);

        assertEquals(0.0f, testEntity.getPos().x, 0.01f);
        assertEquals(0.0f, testEntity.getPos().y, 0.01f);
        assertEquals(0.0f, testEntity.getPos().z, 0.01f);

        testEntity.incPos(updatedPos.x, updatedPos.y, updatedPos.z);

        assertEquals(45.0f, testEntity.getPos().x, 0.01f);
        assertEquals(76.0f, testEntity.getPos().y, 0.01f);
        assertEquals(21.0f, testEntity.getPos().z, 0.01f);

        // SPECIAL TO INC POS
        testEntity.incPos(21.0f, 7.0f, 16.0f);

        assertEquals(66.0f, testEntity.getPos().x, 0.01f);
        assertEquals(83.0f, testEntity.getPos().y, 0.01f);
        assertEquals(37.0f, testEntity.getPos().z, 0.01f);
    }

    // ENTITY POSITIONING TEST
    @Test
    void testSetPos() {
        testEntity.setPos(originalPos.x, originalPos.y, originalPos.z);

        assertEquals(0.0f, testEntity.getPos().x, 0.01f);
        assertEquals(0.0f, testEntity.getPos().y, 0.01f);
        assertEquals(0.0f, testEntity.getPos().z, 0.01f);

        testEntity.setPos(updatedPos.x, updatedPos.y, updatedPos.z);

        assertEquals(45.0f, testEntity.getPos().x, 0.01f);
        assertEquals(76.0f, testEntity.getPos().y, 0.01f);
        assertEquals(21.0f, testEntity.getPos().z, 0.01f);

        // SPECIAL TO SET POS
        testEntity.setPos(34.0f, 56.0f, 11.0f);

        assertEquals(34.0f, testEntity.getPos().x, 0.01f);
        assertEquals(56.0f, testEntity.getPos().y, 0.01f);
        assertEquals(11.0f, testEntity.getPos().z, 0.01f);
    }

    // ENTITY ROTATION TEST
    @Test
    void testIncRotation() {
        testEntity.setRotation(originalRot.x, originalRot.y, originalRot.z);

        assertEquals(0.0f, testEntity.getRotation().x, 0.01f);
        assertEquals(0.0f, testEntity.getRotation().y, 0.01f);
        assertEquals(0.0f, testEntity.getRotation().z, 0.01f);

        testEntity.incRotation(updatedRot.x, updatedRot.y, updatedRot.z);

        assertEquals(79.0f, testEntity.getRotation().x, 0.01f);
        assertEquals(5.0f, testEntity.getRotation().y, 0.01f);
        assertEquals(83.0f, testEntity.getRotation().z, 0.01f);

        // SPECIAL TO INC ROTATION
        testEntity.incRotation(67.0f, 22.0f, 95.0f);

        assertEquals(146.0f, testEntity.getRotation().x, 0.01f);
        assertEquals(27.0f, testEntity.getRotation().y, 0.01f);
        assertEquals(178.0f, testEntity.getRotation().z, 0.01f);
    }

    // ENTITY ROTATION TEST
    @Test
    void testSetRotation() {
        testEntity.setRotation(originalRot.x, originalRot.y, originalRot.z);

        assertEquals(0.0f, testEntity.getRotation().x, 0.01f);
        assertEquals(0.0f, testEntity.getRotation().y, 0.01f);
        assertEquals(0.0f, testEntity.getRotation().z, 0.01f);

        testEntity.setRotation(updatedRot.x, updatedRot.y, updatedRot.z);

        assertEquals(79.0f, testEntity.getRotation().x, 0.01f);
        assertEquals(5.0f, testEntity.getRotation().y, 0.01f);
        assertEquals(83.0f, testEntity.getRotation().z, 0.01f);

        // SPECIAL TO SET ROTATION
        testEntity.setRotation(54.0f, 78.0f, 31.0f);

        assertEquals(54.0f, testEntity.getRotation().x, 0.01f);
        assertEquals(78.0f, testEntity.getRotation().y, 0.01f);
        assertEquals(31.0f, testEntity.getRotation().z, 0.01f);
    }

    // REMOVE ENTITY TEST
    @Test
    void testRemove() {
        assertFalse(testEntity.isRemoved());

        testEntity.remove();

        assertTrue(testEntity.isRemoved());
    }

    // OUT OF BORDER TEST
    @Test
    void testOutOfBorder() {
        testEntity.setPos(originalPos.x, originalPos.y, originalPos.z);

        assertFalse(testEntity.outOfBorder());

        testEntity.setPos(10000.0f, 5000.0f, 3000.0f);

        assertTrue(testEntity.outOfBorder());
    }
}