package com.redteam.engine.game.debug;

import com.redteam.engine.core.iObjMapping;
import com.redteam.engine.core.lighting.DirectionalLight;
import com.redteam.engine.core.rendering.Material;
import com.redteam.engine.core.rendering.Texture;
import com.redteam.engine.core.terrain.Terrain;
import com.redteam.engine.game.entities.TankEntity;
import com.redteam.engine.utils.Constants;
import org.joml.Vector3f;


public class DebugObjectMap implements iObjMapping {

    // Entity Adding
    @Override
    public void initEntities() {
        addEntity(new TankEntity	(
                                    "tank",                                      // ID
                                        new Vector3f(70f,1.3f,-50f),        // POSITION
                                        new Vector3f(0,0,0)                 // ROTATION
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
    }

    // Light for the Map
    @Override
    public void initLights() {
        // 1st Argument Light Color, 2nd Light Positioning, 3rd Light Intensity
        changeLight(new DirectionalLight(new Vector3f(1,1,1), new Vector3f(-1, -10, 0), 1.0f));
    }
}