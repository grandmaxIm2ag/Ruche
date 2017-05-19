/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controleur;

import Son.SoundEngine;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;

/**
 *
 * @author brignone
 */
public class SoundSlider implements ChangeListener<Number> {
    public static final int MUSIC_SLIDER = 0;
    
    private int type;
    
    public SoundSlider (int type) {
        this.type = type;
    }

    @Override
    public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
        if (type == MUSIC_SLIDER && SoundEngine.isInstanciated()) {
            SoundEngine se = SoundEngine.getInstance();
            se.volume(newValue);
        }
    }
    
}
