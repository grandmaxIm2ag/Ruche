/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controleur;

import Modele.Arbitres.*;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;

/**
 *
 * @author grandmax
 */
public class Choix implements ChangeListener<Number>{
    Arbitre a;
    int value;

    /**
     *
     * @param ov
     * @param t
     * @param t1
     */
    @Override
    public void changed(ObservableValue<? extends Number> ov, Number t, Number t1) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
}
