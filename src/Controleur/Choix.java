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
    public final static int CHOIX_MODE = 0;
    public final static int CHOIX_PLATEAU = 1;
    public final static int CHOIX_DIFFICULTE = 2;
    
    FabriqueArbitre fabrique;
    int value;

    public Choix(FabriqueArbitre fab, int v){
        fabrique = fab;
        value = v;
    }
    /**
     *
     * @param ov
     * @param t
     * @param t1
     */
    @Override
    public void changed(ObservableValue<? extends Number> ov, Number t, Number t1) {
        fabrique.setInit(value, (int) t1);
    }
    
}
