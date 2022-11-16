package com.redteam.engine.core.entity;

import static org.junit.jupiter.api.Assertions.*;

import com.redteam.engine.core.rendering.Model;
import org.joml.Vector3f;
import org.junit.jupiter.api.Test;

/* https://docs.google.com/document/d/1PEnyHnWt1y7VdEA1bnkRN30tYblDDacImu-bdZJjqKs/edit?usp=sharing
 *  -> Link to Design Doc
 */

// DUE TO HITTABLE ENTITY BEING ABSTRACT WE NEED TO MAKE A SUBCLASS TO TEST IT
class TestHittableEntityClass extends HittableEntity {
    public TestHittableEntityClass(String id, Model model, Vector3f pos, Vector3f rotation, float scale, float hitBoxScale) {
        super(id, model, pos, rotation, scale, hitBoxScale);
    }
    protected void collision(Entity entity) {}
    protected void debugCollision(Entity entity) {}

    public void gameTick() {}
    public void debugGameTick() {}
}

class TestHittableEntity {
    Vector3f originalPos = new Vector3f(0.0f,0.0f,0.0f);
    Vector3f originalRot = new Vector3f(0.0f,0.0f,0.0f);
    Vector3f updatedPos = new Vector3f(65.0f, 3.0f, 21.0f);
    Vector3f updatedRot = new Vector3f(44.0f, 72.0f, 10.0f);

    TestHittableEntityClass testEntity = new TestHittableEntityClass("tank", null, originalPos, originalRot, 1.0f, 5.0f);
    TestHittableEntityClass secondEntity = new TestHittableEntityClass("secondTank", null, updatedPos, updatedRot, 1.0f, 5.0f);

    Vector3f[] testHitBox;

    // CONSTRUCTOR TEST
    @Test
    void testConstructor() {
        assertEquals("tank", testEntity.getID());
        assertEquals(originalPos, testEntity.getPos());
        assertEquals(originalRot, testEntity.getRotation());
        assertEquals(1.0f, testEntity.getScale());
        assertEquals(5.0f, testEntity.getHitBoxScale());

        assertEquals("secondTank", secondEntity.getID());
        assertEquals(updatedPos, secondEntity.getPos());
        assertEquals(updatedRot, secondEntity.getRotation());
        assertEquals(1.0f, secondEntity.getScale());
        assertEquals(5.0f, secondEntity.getHitBoxScale());
    }

    // FORM CUBE TEST
    @Test
    void testFormCube() {
        testHitBox = new Vector3f[]{
                // front face
                new Vector3f(-2.5f,2.5f,2.5f), // top left
                new Vector3f(2.5f,2.5f,2.5f), // top right
                new Vector3f(2.5f, -2.5f, 2.5f), // bottom right
                new Vector3f(-2.5f, -2.5f, 2.5f), // bottom left

                // back face
                new Vector3f(-2.5f,2.5f,-2.5f), // top left
                new Vector3f(2.5f,2.5f,-2.5f), // top right
                new Vector3f(2.5f, -2.5f, -2.5f), // bottom right
                new Vector3f(-2.5f, -2.5f, -2.5f) // bottom left
        };

        assertTrue(new Vector3f(-2.5f,2.5f,2.5f).equals(testHitBox[0]));
        assertTrue(new Vector3f(2.5f,2.5f,2.5f).equals(testHitBox[1]));
        assertFalse(new Vector3f(2.6f,-2.5f,2.5f).equals(testHitBox[2]));
        assertTrue(new Vector3f(-2.5f,-2.5f,2.5f).equals(testHitBox[3]));

        assertFalse(new Vector3f(-45.0f,2.5f,-2.5f).equals(testHitBox[4]));
        assertFalse(new Vector3f(2.5f,10.0f,-2.5f).equals(testHitBox[5]));
        assertTrue(new Vector3f(2.5f,-2.5f,-2.5f).equals(testHitBox[6]));
        assertFalse(new Vector3f(-24.0f,-2.5f,9.0f).equals(testHitBox[7]));
    }

    // PASS THROUGH TEST
    @Test
    void testPassThrough() {
        testEntity.setPos(0,0,0);

        Vector3f testPos = new Vector3f(-2.5f, 1.2f, 0.7f);
        assertTrue(testEntity.passThrough(testPos));

        testPos = new Vector3f(-5.0f, 1.2f, 0.7f);
        assertFalse(testEntity.passThrough(testPos));
    }

    // SECOND PASS THROUGH TEST
    @Test
    void testPassThrough2() {
        testEntity.setPos(0,0,0);
        testEntity.formCube();
        secondEntity.setPos(-2.3f, 1.2f, 0.7f);
        secondEntity.formCube();
        assertTrue(testEntity.passThrough(secondEntity));

        secondEntity.setPos(10.0f,2.0f,3.0f);
        secondEntity.formCube();
        assertFalse(testEntity.passThrough(secondEntity));
    }
}