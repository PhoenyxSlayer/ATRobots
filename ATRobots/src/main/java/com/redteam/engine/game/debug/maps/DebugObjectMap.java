package com.redteam.engine.game.debug.maps;

import com.redteam.engine.core.entity.HittableEntity;
import com.redteam.engine.core.iObjMapping;
import com.redteam.engine.core.lighting.DirectionalLight;
import com.redteam.engine.core.rendering.Material;
import com.redteam.engine.core.rendering.Texture;
import com.redteam.engine.core.terrain.Terrain;
import com.redteam.engine.game.debug.DebugMode;
import com.redteam.engine.game.entities.MineEntity;
import com.redteam.engine.game.entities.TankEntity;
import com.redteam.engine.utils.Constants;
import org.joml.Vector3f;


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
        ));

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