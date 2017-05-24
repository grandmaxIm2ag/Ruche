/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controleur;

import Vue.ColorChoice;
import Vue.Interface;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.ToggleButton;

/**
 *
 * @author brignone
 */
public class ColorButton implements EventHandler<ActionEvent> {
    public static final int HOTPINK = 0;
    public static final int LIMEGREEN = 1;
    public static final int WHITESMOKE = 3;
    public static final int ORANGERED = 4;
    public static final int STEELBLUE = 5;
    public static final int DARKGOLDENROD = 6;
    public static final int DARKMAGENTA = 7;
    public static final int MIDNIGHTBLUE = 8;
    public static final int MAROON = 9;
    
    private final int color;
    private final int player;
    private final int i;
    private final int j;

    public ColorButton (int i, int j, int player) {
        this.color = i*3+j;
        this.player = player;
        this.i = i;
        this.j = j;
    }
    
    @Override
    public void handle(ActionEvent event) {
        if (((ToggleButton)event.getSource()).isSelected()) {
            switch (player) {
                case 0:
                    Interface.setColorP1(color);
                    System.out.println("k="+player);
                    (ColorChoice.getInstance()).enable(1);
                    ((ColorChoice.getInstance()).getTable())[1][i][j].setDisable(true);
                    break;
                case 1:
                    Interface.setColorP2(color);
                    System.out.println("k="+player);
                    (ColorChoice.getInstance()).enable(0);
                    ((ColorChoice.getInstance()).getTable())[0][i][j].setDisable(true);
                    break;
                default:
            }
        }
    }
    
}
