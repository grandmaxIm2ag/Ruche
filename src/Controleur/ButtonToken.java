/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controleur;

import Joueurs.Joueur;
import Modele.Arbitres.*;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Label;

/**
 *
 * @author brignone
 */
public class ButtonToken implements EventHandler<ActionEvent> {
    int type;
    Label label;
    Joueur player;
    int[] tab;
    public final static int BEE_BUTTON = 0;
    public final static int BEETLE_BUTTON = 1;
    public final static int GRASSHOPPER_BUTTON = 2;
    public final static int ANT_BUTTON = 3;
    public final static int SPIDER_BUTTON = 4;
    public final static int LADYBUG_BUTTON = 5;
    public final static int MOSKITO_BUTTON = 6;
    public final static int WOUDLOSE_BUTTON = 7;
    
    public ButtonToken (int type, Label label, Joueur player) {
        this.type = type;
        this.label = label;
        this.player = player;
    }
    
    @Override
    public void handle (ActionEvent e) {
        tab = player.pions();
        switch (type) {
            case BEE_BUTTON:
                tab[BEE_BUTTON]--;
                label.setText("" + tab[BEE_BUTTON]);
                break;
            default:
        }
    }
}
