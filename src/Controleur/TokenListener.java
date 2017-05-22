/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controleur;

import Vue.PaneToken;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.scene.control.ToggleButton;

/**
 *
 * @author brignone
 */
public class TokenListener implements ChangeListener<Boolean> {
    ToggleButton b;
    private int i;
    private int j;
    
    public TokenListener (ToggleButton tb, int i, int j) {
        b = tb;
        this.i = i;
        this.j = j;
    }
    
    @Override
    public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
        //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
        if (!newValue)
            (PaneToken.getInstance()).setDefaultBackground(b, i, j);
    }
    
}
