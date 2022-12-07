package com.redteam.engine.game.debug.maps;

import com.redteam.engine.core.Engine;
import com.redteam.engine.core.entity.Entity;
import com.redteam.engine.core.entity.HittableEntity;
import com.redteam.engine.core.iObjMapping;
import com.redteam.engine.core.lighting.DirectionalLight;
import com.redteam.engine.core.rendering.Material;
import com.redteam.engine.core.rendering.Texture;
import com.redteam.engine.core.terrain.Terrain;
import com.redteam.engine.game.debug.DebugMode;
import com.redteam.engine.game.entities.BulletEntity;
import com.redteam.engine.game.entities.MineEntity;
import com.redteam.engine.game.entities.TankEntity;
import com.redteam.engine.game.main.ATRobots;
import com.redteam.engine.utils.Constants;
import org.joml.Vector3f;

import static com.redteam.engine.utils.Constants.MOVEMENT_SPEED;
import static org.lwjgl.glfw.GLFW.GLFW_KEY_LEFT_SHIFT;


public class DebugObjectMap implements iObjMapping {

    // Entity Adding
    @Override
    public void initEntities() {
        addEntity(new TankEntity	(
                                    "tank",                                      // ID
                                        DebugMode.RandomizeTankLocation(),       // POSITION
                                        new Vector3f(0,0,0)                 // ROTATION
                                    ));

        addEntity(new HittableEntity (
                "dummyTank",
                DebugMode.setModel("/models/tank.obj", "textures/base/cyan.jpg"),
                DebugMode.RandomizeTankLocation(),
                new Vector3f(0, DebugMode.RandomizeTankRotation(),0),
                1f,
                5f
        ) {
            private int health = 100;

            @Override
            public void collision(Entity entity) {
                debugCollision(entity);
            }

            @Override
            public void debugCollision(Entity entity) {
                if (entity instanceof BulletEntity) {
                    entity.remove();
                    DebugMode.debugGUIMap.addEvent(getID() + " Hit! w/ " + entity.getID());
                    health -= 10;
                    DebugMode.debugGUIMap.addEvent("Health: " + health);
                }
                else if (entity instanceof MineEntity) {
                    entity.remove();
                    if (DebugMode.soundMap.isSoundOn()) {
                        DebugMode.soundMap.getSound("sounds/explosion.ogg").stop();
                        DebugMode.soundMap.getSound("sounds/explosion.ogg").play();
                    } else {
                        if (DebugMode.soundMap.getSound("sounds/explosion.ogg").isPlaying()) {
                            DebugMode.soundMap.getSound("sounds/explosion.ogg").stop();
                        }
                    }
                    health -= 50;
                    DebugMode.debugGUIMap.addEvent("Health: " + health);
                } else if (entity instanceof HittableEntity) {
                    float tankSpeed;
                    if (ATRobots.window.isKeyPressed(GLFW_KEY_LEFT_SHIFT))
                        tankSpeed = ((float) (MOVEMENT_SPEED * Engine.tick()) * 3);
                    else
                        tankSpeed = (float) (MOVEMENT_SPEED * Engine.tick());
                    Vector3f pushBack = getPos();
                    float pushBackSpeed = tankSpeed / 2;
                    if ((getPos().x > (entity.getPos().x + entity.getScale())) &&
                            (getPos().z > (entity.getPos().z + entity.getScale()))) {
                        pushBack.add((pushBackSpeed), 0, 0);
                        pushBack.add(0, 0, (pushBackSpeed));
                    }
                    else if ((getPos().x < (entity.getPos().x - entity.getScale())) &&
                            (getPos().z < (entity.getPos().z - entity.getScale()))) {
                        pushBack.add(-(pushBackSpeed), 0, 0);
                        pushBack.add(0, 0, -(pushBackSpeed));
                    }
                    else if ((getPos().x > (entity.getPos().x + entity.getScale())) &&
                            (getPos().z < (entity.getPos().z - entity.getScale()))) {
                        pushBack.add((pushBackSpeed), 0, 0);
                        pushBack.add(0, 0, -(pushBackSpeed));
                    }
                    else if ((getPos().x < (entity.getPos().x - entity.getScale())) &&
                            (getPos().z > (entity.getPos().z + entity.getScale()))) {
                        pushBack.add(-(pushBackSpeed), 0, 0);
                        pushBack.add(0, 0, (pushBackSpeed));
                    }
                    else if (getPos().x > (entity.getPos().x + entity.getScale()))
                        pushBack.add((pushBackSpeed), 0, 0);
                    else if (getPos().x < (entity.getPos().x - entity.getScale()))
                        pushBack.add(-(pushBackSpeed), 0, 0);
                    else if (getPos().z > (entity.getPos().z + entity.getScale()))
                        pushBack.add(0, 0, (pushBackSpeed));
                    else if (getPos().z < (entity.getPos().z - entity.getScale()))
                        pushBack.add(0, 0, -(pushBackSpeed));

                    setPos(pushBack.x, pushBack.y, pushBack.z);
                }
            }

            @Override
            public void gameTick() {
                debugGameTick();
            }

            @Override
            public void debugGameTick() {
                if(health <= 0) {
                    DebugMode.debugGUIMap.addEvent(getID() + " down!");
                    remove();
                }
            }
        });

        addEntity(new MineEntity(
                "dummyMine",
                DebugMode.RandomizeTankLocation(),
                new Vector3f(0, DebugMode.RandomizeTankRotation(),0)
        ));

        addEntity(new MineEntity(
                "dummyMine",
                DebugMode.RandomizeTankLocation(),
                new Vector3f(0, DebugMode.RandomizeTankRotation(),0)
        ));
    }

    // Terrain Adding
    @Override
    public void initTerrains() {
        addTerrain( new Terrain		(
                                        new Vector3f(-Constants.X_BORDER,0,-Constants.Z_BORDER),	                                                        // POSITION
                                        DebugMode.getObjectLoader(),													                                    // OBJECT LOADER
                                        new Material(new Texture(DebugMode.getObjectLoader().loadTexture("textures/concrete.jpg")), 0.1f)	// TEXTURE
                                    ));
                
      //Outer Terrain
      		//top and bottom of map

            Material borders = new Material(new Texture(DebugMode.getObjectLoader().loadTexture("textures/MineCraftGrass.jpg")), 0.1f);
      		
        	addTerrain(new Terrain(new Vector3f(-Constants.X_BORDER,0,-(Constants.Z_BORDER * 2)), DebugMode.getObjectLoader(), borders));
      		addTerrain(new Terrain(new Vector3f(-Constants.X_BORDER,0,0), DebugMode.getObjectLoader(), borders));

      		//Right side of map
      		addTerrain(new Terrain(new Vector3f(Constants.X_BORDER,0,-Constants.Z_BORDER), DebugMode.getObjectLoader(), borders));
      		addTerrain(new Terrain(new Vector3f(Constants.X_BORDER,0,-(Constants.Z_BORDER * 2)), DebugMode.getObjectLoader(), borders));
      		addTerrain(new Terrain(new Vector3f(Constants.X_BORDER,0,0), DebugMode.getObjectLoader(), borders));
      		
      		//Left Side map
      		addTerrain(new Terrain(new Vector3f(-(Constants.Z_BORDER * 1.5f),0,-(Constants.Z_BORDER * 2)), DebugMode.getObjectLoader(), borders));
      		addTerrain(new Terrain(new Vector3f(-(Constants.Z_BORDER * 1.5f),0,-Constants.Z_BORDER), DebugMode.getObjectLoader(), borders));
      		addTerrain(new Terrain(new Vector3f(-(Constants.Z_BORDER * 1.5f),0,0), DebugMode.getObjectLoader(), borders));
      //Outer Terrain^^*/
    }

    // Light for the Map
    @Override
    public void initLights() {
        // 1st Argument Light Color, 2nd Light Positioning, 3rd Light Intensity
        changeLight(new DirectionalLight(new Vector3f(1,1,1), new Vector3f(-1, -10, 0), 1.0f));
    }
}