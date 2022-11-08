package com.redteam.engine.game.debug;

import com.redteam.engine.core.iSoundMapping;

public class DebugSoundMap implements iSoundMapping {
    @Override
    public void init() {
        /* 1st File Source, 2nd Loops?*/
        addSound("src/main/resources/sounds/bullet.ogg", false);
        addSound("src/main/resources/sounds/bloop_x.ogg", false);
        addSound("src/main/resources/sounds/tankIdle.ogg", true);
        addSound("src/main/resources/sounds/tankMove.ogg", true);
    }
}
