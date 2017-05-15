/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Vue;

import Controleur.ButtonToken;
import Modele.Arbitre;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;

/**
 *
 * @author brignone
 */
public class PaneToken {
    Arbitre arbitre;
    GridPane leftGrid;
    GridPane rightGrid;
    
    public PaneToken (Arbitre arbitre) {
        this.arbitre = arbitre;
    }
    
    public GridPane getRightPane() {
        if (rightGrid != null)
            return rightGrid;
        else {
            createRight();
            return rightGrid;
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
        bBee.setOnAction(new ButtonToken(ButtonToken.BEE_BUTTON, lBee, arbitre.joueur(1)));
        bBee.setOnAction(new ButtonToken(ButtonToken.BEE_BUTTON, lBee, arbitre.joueur(1)));
        bBee.setOnAction(new ButtonToken(ButtonToken.BEE_BUTTON, lBee, arbitre.joueur(1)));
        bBee.setOnAction(new ButtonToken(ButtonToken.BEE_BUTTON, lBee, arbitre.joueur(1)));
        bBee.setOnAction(new ButtonToken(ButtonToken.BEE_BUTTON, lBee, arbitre.joueur(1)));
        bBee.setOnAction(new ButtonToken(ButtonToken.BEE_BUTTON, lBee, arbitre.joueur(1)));
        bBee.setOnAction(new ButtonToken(ButtonToken.BEE_BUTTON, lBee, arbitre.joueur(1)));
        
        
    }
}
