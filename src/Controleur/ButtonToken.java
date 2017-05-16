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
import javafx.geometry.Insets;
import javafx.scene.control.Label;
import javafx.scene.control.ToggleButton;
import javafx.scene.image.Image;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.scene.paint.ImagePattern;

/**
 *
 * @author brignone
 */
public class ButtonToken implements EventHandler<ActionEvent> {
    int type;
    Label label;
    Joueur player;
    Arbitre arbitre;
    int[] tab;

    /**
     *
     */
    public final static int BEE_BUTTON = 0;

    /**
     *
     */
    public final static int BEETLE_BUTTON = 1;

    /**
     *
     */
    public final static int GRASSHOPPER_BUTTON = 2;

    /**
     *
     */
    public final static int ANT_BUTTON = 3;

    /**
     *
     */
    public final static int SPIDER_BUTTON = 4;

    /**
     *
     */
    public final static int LADYBUG_BUTTON = 5;

    /**
     *
     */
    public final static int MOSKITO_BUTTON = 6;
    public final static int WOODLOUSE_BUTTON = 7;
    
    public ButtonToken (int type, Label label, Joueur player, Arbitre arbitre) {
        this.type = type;
        this.label = label;
        this.player = player;
        this.arbitre = arbitre;
    }
    
    /**
     *
     * @param e
     */
    @Override
    public void handle (ActionEvent e) {
        tab = player.pions();
        String s = "";
        //switch (type) {
            //case BEE_BUTTON:
            switch (type) {
                case BEE_BUTTON:
                    s = "bee";
                    break;
                case BEETLE_BUTTON:
                    s = "beetle";
                    break;
                case GRASSHOPPER_BUTTON:
                    s = "grasshopper";
                    break;
                case ANT_BUTTON:
                    s = "ant";
                    break;
                case SPIDER_BUTTON:
                    s = "spider";
                    break;
                case LADYBUG_BUTTON:
                    s = "ladybug";
                    break;
                case MOSKITO_BUTTON:
                    s = "moskito";
                    break;
                default:
            
                    
            }
                if (((ToggleButton) e.getSource()).isSelected()) {
                    arbitre.initDepot(type);
                    if (type != WOODLOUSE_BUTTON)
                    ((ToggleButton) e.getSource()).setBackground(new Background(new BackgroundFill(new ImagePattern(new Image(ClassLoader.getSystemClassLoader().getResourceAsStream("Images/Boutons/" + s + "_grey.png"))), CornerRadii.EMPTY, Insets.EMPTY)));
                } else {
                    arbitre.plateau().clearAide();
                    if (type != WOODLOUSE_BUTTON)
                    ((ToggleButton) e.getSource()).setBackground(new Background(new BackgroundFill(new ImagePattern(new Image(ClassLoader.getSystemClassLoader().getResourceAsStream("Images/Boutons/"+s+"_red.png"))), CornerRadii.EMPTY, Insets.EMPTY)));
                }
                //break;
            //default:
        //}
    }
}
