/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controleur;

import Modele.Arbitres.Arbitre;
import Vue.PaneToken;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.scene.control.ToggleButton;

/**
 *
 * @author brignone
 */
public class AideListener implements ChangeListener<Number> {
    ToggleButton[][] b;
    Arbitre arbitre;
    
    public AideListener (ToggleButton [][] tb, Arbitre arbitre) {
        b = tb;
        this.arbitre = arbitre;
    }

    @Override
    public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
        int i = arbitre.jCourant();
        int j = newValue.intValue();
        
    }
    
}