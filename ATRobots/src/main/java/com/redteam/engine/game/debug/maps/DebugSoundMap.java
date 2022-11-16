package com.redteam.engine.game.debug.maps;

import com.redteam.engine.core.iSoundMapping;
import com.redteam.engine.core.sound.Sound;

import javax.sound.sampled.*;
import java.io.File;
import java.io.IOException;

public class DebugSoundMap implements iSoundMapping {
    private boolean sound = true;
    @Override
    public void init() {
        /* 1st File Source, 2nd Loops?*/
        addSound("src/main/resources/sounds/bullet.ogg", false);
        addSound("src/main/resources/sounds/bloop_x.ogg", false);
        addSound("src/main/resources/sounds/tankIdle.ogg", true);
        addSound("src/main/resources/sounds/tankMove.ogg", true);
    }

    // Daniel Implementation of Sound .ogg
    public Sound getSound(String soundFile) {
        File file = new File(soundFile);
        if (sounds.containsKey(file.getAbsolutePath())) {
            return sounds.get(file.getAbsolutePath());
        } else {
            assert false : "Sound file not added '" + soundFile + "'";
        }
        return null;
    }

    // Alex Implementation of Sound .wav
    public void playSound(String soundFile) throws IOException, UnsupportedAudioFileException, LineUnavailableException {
        if(isSoundOn()) {
            File f = new File(soundFile);
            AudioInputStream audioIn = AudioSystem.getAudioInputStream(f.toURI().toURL());
            Clip clip = AudioSystem.getClip();
            clip.open(audioIn);
            clip.start();
        }
    }

    public void setSound() {
        this.sound = !this.sound;
    }

    public boolean isSoundOn() {
        return sound;
    }
}
