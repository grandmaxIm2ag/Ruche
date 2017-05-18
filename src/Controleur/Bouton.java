/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controleur;

import Modele.Arbitres.*;
import Vue.Interface;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;



/**
 *
 * @author grandmax
 */
public class Bouton implements EventHandler<ActionEvent>{
    int value;
    Arbitre arbitre;

    /**
     *
     */
    public final static int BOUTON_NOUVELLE_PARTIE = 0;

    /**
     *
     */
    public final static int BOUTON_DO = 1;

    /**
     *
     */
    public final static int BOUTON_UNDO = 2;

    /**
     *
     */
    public final static int BOUTON_SAUVEGARDER = 3;

    /**
     *
     */
    public final static int BOUTON_AIDE = 4;

    /**
     *
     */
    public final static int BOUTON_CHARGER = 5;

    /**
     *
     */
    public final static int BOUTON_MENU = 6;

    /**
     *
     */
    public final static int BOUTON_COMMENCER = 7;

    /**
     *
     */
    public final static int BOUTON_QUITTER = 7;

    /**
     *
     */
    public final static int BOUTON_CONFIG = 8;

    /**
     *
     */
    public final static int BOUTON_CREDITS = 9;

    /**
     *
     */
    public final static int BOUTON_NOUVELLE_PARTIE_COMMENCER = 10;
    /**
     *
     */
    public final static int BOUTON_PAUSE = 11;
    
    
    /**
     *
     * @param type
     * @param arbitre
     */
    public Bouton (int type, Arbitre arbitre) {
        value = type;
        this.arbitre = arbitre;
    }
    
    /**
     *
     * @param t
     */
    @Override
    public void handle(ActionEvent t) {
        switch(value) {
            case BOUTON_MENU:
                Interface.goMenu();
                break;
            case BOUTON_QUITTER:
                System.exit(0);
                break;
            case BOUTON_NOUVELLE_PARTIE:
                Interface.goNewGame();
                break;
            case BOUTON_CONFIG:
                Interface.goConfig();
                break;
            case BOUTON_CREDITS:
                Interface.goCredits();
                break;
            case BOUTON_NOUVELLE_PARTIE_COMMENCER:
                Interface.nouvelArbitre();
                break;
            
            case BOUTON_CHARGER:
                Interface.goLoadGame();
                break;
            case BOUTON_SAUVEGARDER:
                arbitre.sauvegarder("testS");
                break;
            case BOUTON_UNDO:
                arbitre.precedent();
                break;
            case BOUTON_DO:
                arbitre.refaire();
                break;
            case BOUTON_PAUSE:
                Interface.pause();
                break;
            default:
                break;
        }
    }
    
}
