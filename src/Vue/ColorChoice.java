/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Vue;

import Controleur.ColorButton;
import javafx.geometry.Insets;
import javafx.scene.control.ToggleButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.GridPane;

/**
 *
 * @author brignone
 */
public class ColorChoice {
    private GridPane player1;
    private GridPane player2;
    ToggleButton[][][] b;
    private static ColorChoice INSTANCE = null;
    
    private ColorChoice () {
        b = new ToggleButton[2][3][3];
        createPlayer();
    }
    
    public static ColorChoice getInstance () {
        if (INSTANCE != null)
            return INSTANCE;
        else {
            INSTANCE = new ColorChoice();
            return INSTANCE;
        }
    }
    
    public GridPane getPlayer1 () {
        return player1;
    }
    
    public GridPane getPlayer2 () {
        return player2;
    }
    
    private void createPlayer() {
        player1 = new GridPane();
        player2 = new GridPane();
        ToggleGroup g1 = new ToggleGroup();
        ToggleGroup g2 = new ToggleGroup();
        player1.setHgap(15);
        player1.setVgap(15);
        player1.setPadding(new Insets(10,10,10,10));
        player2.setHgap(15);
        player2.setVgap(15);
        player2.setPadding(new Insets(10,10,10,10));
        for (int k = 0; k < 2; k++) {
            for (int i = 0; i < 3; i++) {
                for (int j = 0; j < 3; j++) {
                    b[k][i][j] = new ToggleButton();
                    b[k][i][j].setMinWidth(50);
                    b[k][i][j].setMinHeight(50);
                    switch (i*3+j) {
                        case 0:
                            b[k][i][j].setStyle("-fx-base: hotpink;");
                            break;
                        case 1:
                            b[k][i][j].setStyle("-fx-base:limegreen;");
                            break;
                        case 2:
                            b[k][i][j].setStyle("-fx-base: whitesmoke;");
                            break;
                        case 3:
                            b[k][i][j].setStyle("-fx-base: orangered;");
                            break;
                        case 4:
                            b[k][i][j].setStyle("-fx-base: steelblue;");
                            break;
                        case 5:
                            b[k][i][j].setStyle("-fx-base: darkgoldenrod;");
                            break;
                        case 6:
                            b[k][i][j].setStyle("-fx-base: darkmagenta;");
                            break;
                        case 7:
                            b[k][i][j].setStyle("-fx-base: mediumblue;");
                            break;
                        case 8:
                            b[k][i][j].setStyle("-fx-base: maroon;");
                            break;
                        default:
                    }
                    switch(k) {
                        case 0:
                            player1.add(b[k][i][j], i, j);
                            b[k][i][j].setToggleGroup(g1);
                            b[k][i][j].setOnAction(new ColorButton(i, j, k));
                            break;
                        case 1:
                            player2.add(b[k][i][j], i, j);
                            b[k][i][j].setToggleGroup(g2);
                            b[k][i][j].setOnAction(new ColorButton(i, j, k));
                            break;
                        default:
                    }
                }
            }
        }
        b[0][0][0].setSelected(true);
        b[1][1][0].setSelected(true);
        b[1][0][0].setDisable(true);
        b[0][1][0].setDisable(true);
        Interface.setColorP1(0);
        Interface.setColorP1(3);
        
    }
    
    public ToggleButton[][][] getTable () {
        return b;
    }
    
    public void enable (int k) {
            for (int i = 0; i < 3; i++) 
                for (int j = 0; j < 3; j++) 
                    b[k][i][j].setDisable(false);
    }
    
}
