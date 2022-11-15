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
    Vector3f originalPos = new Vector3f (0.0f,0.0f,0.0f);
    Vector3f originalRot = new Vector3f (0.0f,0.0f,0.0f);

    TestHittableEntityClass testEntity = new TestHittableEntityClass("tank", null, originalPos, originalRot, 1.0f, 5.0f);

    Vector3f updatedPos = new Vector3f(65.0f, 3.0f, 21.0f);
    Vector3f updatedRot = new Vector3f(44.0f, 72.0f, 10.0f);

    // CONSTRUCTOR TEST
    @Test
    void testConstructor() {
        assertEquals("tank", testEntity.getID());
        assertEquals(originalPos, testEntity.getPos());
        assertEquals(originalRot, testEntity.getRotation());
        assertEquals(1.0f, testEntity.getScale());
        assertEquals(5.0f, testEntity.getHitBoxScale());

        TestHittableEntityClass secondEntity = new TestHittableEntityClass("secondTank", null, updatedPos, updatedRot, 2.0f, 6.0f);

        assertEquals("secondTank", secondEntity.getID());
        assertEquals(updatedPos, secondEntity.getPos());
        assertEquals(updatedRot, secondEntity.getRotation());
        assertEquals(2.0f, secondEntity.getScale());
        assertEquals(6.0f, secondEntity.getHitBoxScale());
    }


}