/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Son;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.scene.shape.Line;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.DataLine;
import javax.sound.sampled.FloatControl;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.Mixer;
import javax.sound.sampled.UnsupportedAudioFileException;

/**
 *
 * @author brignone
 */

class EnzoLaptopParameterException extends Exception {
    public EnzoLaptopParameterException ()
    {
        super ("Parameter used for Enzo's laptop");
    }
}

public class SoundEngine {

    private static Mixer mixer;
    private static Clip clip;
    private static boolean isMute;
    private static Thread t;
    private static SoundEngine INSTANCE = null;
    
    public static SoundEngine getInstance () {
        if (INSTANCE == null) {
            INSTANCE = new SoundEngine();
            if (!INSTANCE.init())
                INSTANCE = null;
        }
        return INSTANCE;
    }
    
    private static boolean init () {
        Mixer.Info[] mixInfo = AudioSystem.getMixerInfo();
        try {
            if (mixInfo[5].toString().equals("Device [plughw:2,0], version 3.16.0-4-amd64"))
                //throw new EnzoLaptopParameterException();
                mixer = AudioSystem.getMixer(mixInfo[5]);
            else 
                mixer = AudioSystem.getMixer(mixInfo[0]);
        } catch (ArrayIndexOutOfBoundsException  e) {
            System.err.println ("SoundEngine.init() Error - line 57 - " + e.getMessage() + " - set the index to 0");
        }
        for (int i = 0; i < mixInfo.length; i++) 
            System.out.println (i + ". " + mixInfo[i]);
        isMute = false;
        //clipTime = 0;
        try {
            clip = (Clip) mixer.getLine(new DataLine.Info(Clip.class, null));
        } catch (Exception e) {
            System.err.println ("SoundEngine.init() Error - " + e.getMessage());
            return false;
        }
        return true;
    }
    
    public static void play () {
        System.err.println ("SoundEngine.play() - Warning - Musique tempraire");
        //play("game_ambient");
        play ("Sunday_s_Child");
    }
    
    public static float play(String filePath) {
        if (!clip.isActive())
            try {
                InputStream stream = ClassLoader.getSystemClassLoader().getResourceAsStream("Son/"+filePath+".wav");
                AudioInputStream audioStream = AudioSystem.getAudioInputStream(stream);
                clip.open(audioStream);
            } catch (NullPointerException e) {
                System.err.println("SoundEngine.play() - Error - Wrong music file - " + e.getMessage());
                //return false;
            } catch (UnsupportedAudioFileException | IOException | LineUnavailableException e) {
                System.err.println("SoundEngine.play() - Error - Music exception - " + e.getMessage());
                //return false;
            }
        clip.start();
        return clip.getLevel();
    }
    
    public static void volume (Number value) {
        float newVolume = value.floatValue();
        FloatControl gainControl = (FloatControl) clip.getControl(FloatControl.Type.MASTER_GAIN);
        System.out.println("Max : " + gainControl.getMaximum() + "\nMin : " + gainControl.getMinimum() + "\nCurrent : " + gainControl.getValue());
        gainControl.setValue(newVolume != 0 ? -(20-newVolume/5) : gainControl.getMinimum());
    }
    
    public static boolean isInstanciated () {
        return INSTANCE != null;
    }
    
}

   