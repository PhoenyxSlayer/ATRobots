package com.redteam.engine.core;

import com.redteam.engine.core.sound.Sound;

import javax.sound.sampled.*;
import java.io.File;
import java.io.IOException;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

public interface iSoundMapping {
    Map<String, Sound> sounds = new HashMap<>();

    void init();

    default Sound addSound(String soundFile, boolean loops) {
        File file = new File(soundFile);
        if (sounds.containsKey(file.getAbsolutePath())) {
            return sounds.get(file.getAbsolutePath());
        } else {
            Sound sound = new Sound(file.getAbsolutePath(), loops);
            sounds.put(file.getAbsolutePath(), sound);
            return sound;
        }
    }

    default Collection<Sound> getAllSounds() { return sounds.values(); }

}
