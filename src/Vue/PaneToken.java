/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Vue;

import Controleur.ButtonToken;
import Modele.Arbitres.*;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;

/**
 *
 * @author brignone
 */
public class PaneToken {
    private Arbitre arbitre;
    private GridPane leftGrid;
    private GridPane rightGrid;
    Label[][] l;
    private static PaneToken INSTANCE = null;
    
    private PaneToken (Arbitre arbitre) {
        this.arbitre = arbitre;
        l = new Label[2][8];
    }
    
    public static PaneToken getInstance(Arbitre arbitre) {
        if (INSTANCE != null)
            return INSTANCE;
        else {
            INSTANCE = new PaneToken (arbitre);
            return INSTANCE;
        }
    }
    
    public GridPane getRightPane() {
        if (rightGrid != null)
            return rightGrid;
        else {
            createRight();
            return rightGrid;
        }
    }
    
    public GridPane getLeftPane() {
        if (leftGrid != null)
            return leftGrid;
        else {
            createLeft();
            return leftGrid;
        }
    }
    
    private void createRight () {
        rightGrid = new GridPane ();
        rightGrid.setHgap(30);
        rightGrid.setVgap(20);
        
        Button bBee = new Button("Bee");
        Button bBeetle = new Button("Beetle");
        Button bGrasshopper = new Button("Grasshopper");
        Button bAnt = new Button("Ant");
        Button bSpider = new Button("Spider");
        Button bLadybug = new Button("Ladubug");
        Button bMoskito = new Button("Moskito");
        Button bWoudlose = new Button("Wousloose");
        
        Label lBee = new Label();
        Label lBeetle = new Label();
        Label lGrasshopper = new Label();
        Label lAnt = new Label();
        Label lSpider = new Label();
        Label lLadybug = new Label();
        Label lMoskito = new Label();
        Label lWoudlose = new Label();
        
        lBee.setText("" + arbitre.joueur(1).pion(0));
        lBeetle.setText("" + arbitre.joueur(1).pion(1));
        lGrasshopper.setText("" + arbitre.joueur(1).pion(2));
        lAnt.setText("" + arbitre.joueur(1).pion(3));
        lSpider.setText("" + arbitre.joueur(1).pion(4));
        lLadybug.setText("" + arbitre.joueur(1).pion(5));
        lMoskito.setText("" + arbitre.joueur(1).pion(6));
        lWoudlose.setText("" + arbitre.joueur(1).pion(7));
        
        bBee.setOnAction(new ButtonToken(ButtonToken.BEE_BUTTON, lBee, arbitre.joueur(1)));
        bBeetle.setOnAction(new ButtonToken(ButtonToken.BEE_BUTTON, lBee, arbitre.joueur(1)));
        bGrasshopper.setOnAction(new ButtonToken(ButtonToken.BEE_BUTTON, lBee, arbitre.joueur(1)));
        bAnt.setOnAction(new ButtonToken(ButtonToken.BEE_BUTTON, lBee, arbitre.joueur(1)));
        bSpider.setOnAction(new ButtonToken(ButtonToken.BEE_BUTTON, lBee, arbitre.joueur(1)));
        bLadybug.setOnAction(new ButtonToken(ButtonToken.BEE_BUTTON, lBee, arbitre.joueur(1)));
        bMoskito.setOnAction(new ButtonToken(ButtonToken.BEE_BUTTON, lBee, arbitre.joueur(1)));
        bWoudlose.setOnAction(new ButtonToken(ButtonToken.BEE_BUTTON, lBee, arbitre.joueur(1)));
        
        rightGrid.add(bBee, 0, 0);
        rightGrid.add(lBee, 1, 0);
        
        rightGrid.add(lBeetle, 0, 1);
        rightGrid.add(bBeetle, 1, 1);
        
        rightGrid.add(bGrasshopper, 0, 2);
        rightGrid.add(lGrasshopper, 1, 2);
        
        rightGrid.add(lAnt, 0, 3);
        rightGrid.add(bAnt, 1, 3);
        
        rightGrid.add(bSpider, 0, 4);
        rightGrid.add(lSpider, 1, 4);
        
        rightGrid.add(lLadybug, 0, 5);
        rightGrid.add(bLadybug, 1, 5);
        
        rightGrid.add(bMoskito, 0, 6);
        rightGrid.add(lMoskito, 1, 6);
        
        rightGrid.add(lWoudlose, 0, 7);
        rightGrid.add(bWoudlose, 1, 7);
        
        l[0][0] = lBee;
        l[0][1] = lBeetle;
        l[0][2] = lGrasshopper;
        l[0][3] = lAnt;
        l[0][4] = lSpider;
        l[0][5] = lLadybug;
        l[0][6] = lMoskito;
        l[0][7] = lWoudlose;
        
    }
    
    private void createLeft () {
        leftGrid = new GridPane ();
        leftGrid.setHgap(30);
        leftGrid.setVgap(20);
        
        Button bBee = new Button("Bee");
        Button bBeetle = new Button("Beetle");
        Button bGrasshopper = new Button("Grasshopper");
        Button bAnt = new Button("Ant");
        Button bSpider = new Button("Spider");
        Button bLadybug = new Button("Ladubug");
        Button bMoskito = new Button("Moskito");
        Button bWoudlose = new Button("Wousloose");
        
        Label lBee = new Label();
        Label lBeetle = new Label();
        Label lGrasshopper = new Label();
        Label lAnt = new Label();
        Label lSpider = new Label();
        Label lLadybug = new Label();
        Label lMoskito = new Label();
        Label lWoudlose = new Label();
        
        lBee.setText("" + arbitre.joueur(1).pion(0));
        lBeetle.setText("" + arbitre.joueur(1).pion(1));
        lGrasshopper.setText("" + arbitre.joueur(1).pion(2));
        lAnt.setText("" + arbitre.joueur(1).pion(3));
        lSpider.setText("" + arbitre.joueur(1).pion(4));
        lLadybug.setText("" + arbitre.joueur(1).pion(5));
        lMoskito.setText("" + arbitre.joueur(1).pion(6));
        lWoudlose.setText("" + arbitre.joueur(1).pion(7));
        
        bBee.setOnAction(new ButtonToken(ButtonToken.BEE_BUTTON, lBee, arbitre.joueur(1)));
        bBeetle.setOnAction(new ButtonToken(ButtonToken.BEE_BUTTON, lBee, arbitre.joueur(1)));
        bGrasshopper.setOnAction(new ButtonToken(ButtonToken.BEE_BUTTON, lBee, arbitre.joueur(1)));
        bAnt.setOnAction(new ButtonToken(ButtonToken.BEE_BUTTON, lBee, arbitre.joueur(1)));
        bSpider.setOnAction(new ButtonToken(ButtonToken.BEE_BUTTON, lBee, arbitre.joueur(1)));
        bLadybug.setOnAction(new ButtonToken(ButtonToken.BEE_BUTTON, lBee, arbitre.joueur(1)));
        bMoskito.setOnAction(new ButtonToken(ButtonToken.BEE_BUTTON, lBee, arbitre.joueur(1)));
        bWoudlose.setOnAction(new ButtonToken(ButtonToken.BEE_BUTTON, lBee, arbitre.joueur(1)));
        
        leftGrid.add(bBee, 0, 0);
        leftGrid.add(lBee, 1, 0);
        
        leftGrid.add(lBeetle, 0, 1);
        leftGrid.add(bBeetle, 1, 1);
        
        leftGrid.add(bGrasshopper, 0, 2);
        leftGrid.add(lGrasshopper, 1, 2);
        
        leftGrid.add(lAnt, 0, 3);
        leftGrid.add(bAnt, 1, 3);
        
        leftGrid.add(bSpider, 0, 4);
        leftGrid.add(lSpider, 1, 4);
        
        leftGrid.add(lLadybug, 0, 5);
        leftGrid.add(bLadybug, 1, 5);
        
        leftGrid.add(bMoskito, 0, 6);
        leftGrid.add(lMoskito, 1, 6);
        
        leftGrid.add(lWoudlose, 0, 7);
        leftGrid.add(bWoudlose, 1, 7);
        
        l[1][0] = lBee;
        l[1][1] = lBeetle;
        l[1][2] = lGrasshopper;
        l[1][3] = lAnt;
        l[1][4] = lSpider;
        l[1][5] = lLadybug;
        l[1][6] = lMoskito;
        l[1][7] = lWoudlose;
        
    }
    
    public void update () {
        for (int i = 0; i < 2; i++) {
            for (int j = 0; j < 8; j++) {
                l[i][j].setText("" + arbitre.joueur(i).pion(j));
            }
        }
    }
}
