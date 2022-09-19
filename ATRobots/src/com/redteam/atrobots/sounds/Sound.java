package com.redteam.atrobots.sounds;

import java.io.File;
import javax.sound.sampled.*;

public class Sound {
    public static void source(String s) throws Exception {
        File file = new File(s);
        Clip clip = AudioSystem.getClip();
        
        AudioInputStream ais = AudioSystem.getAudioInputStream(file);
        clip.open(ais);
        clip.start();
    }
}