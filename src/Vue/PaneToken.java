/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Vue;

import Controleur.ButtonToken;
import Modele.Arbitres.*;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ToggleButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.effect.DropShadow;
import javafx.scene.effect.GaussianBlur;
import javafx.scene.image.Image;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Rectangle;

/**
 *
 * @author brignone
 */
public class PaneToken {
    private Arbitre arbitre;
    private GridPane leftGrid;
    private GridPane rightGrid;
    private StackPane right;
    private StackPane left;
    Label[][] l;
    ToggleButton[][] b;
    private static PaneToken INSTANCE = null;
    GaussianBlur leftBlur;
    GaussianBlur rightBlur;
    
    private PaneToken (Arbitre arbitre) {
        this.arbitre = arbitre;
        l = new Label[2][8];
        b = new ToggleButton[2][8];
        leftBlur = new GaussianBlur();
        rightBlur = new GaussianBlur();
        leftBlur.setRadius(0);
        //rightBlur.setRadius(10);
        rightBlur.setRadius(0);
    }
    
    /**
     *
     * @param arbitre
     * @return
     */
    public static PaneToken getInstance(Arbitre arbitre) {
        if (INSTANCE != null)
            return INSTANCE;
        else {
            INSTANCE = new PaneToken (arbitre);
            return INSTANCE;
        }
    }
    
    public static PaneToken getInstance () {
        return INSTANCE;
    }
    
    public void initialize () {
        createLeft();
        createRight();
        for (int i = 0; i < 2; i++) {
            for (int j = 0; j < 8; j++) {
                l[i][j].setText("" + arbitre.joueur(i).pion(j));
                if (arbitre.joueur(i).pion(j) == 0)
                    b[i][j].setDisable(true);
            }
        }
    }
    
    /**
     *
     * @return
     */
    public Pane getRightPane() {
        if (right != null)
            return right;
        else {
            createRight();
            return right;
        }
    }
    
    /**
     *
     * @return
     */
    public Pane getLeftPane() {
        if (left != null)
            return left;
        else {
            createLeft();
            return left;
        }
    }
    
    private void createRight () {
        right = new StackPane();
        right.setAlignment(Pos.TOP_CENTER );
        Rectangle centerRect = new Rectangle();
        centerRect.setOpacity(0.25);
        centerRect.widthProperty().bind(right.widthProperty());
        centerRect.heightProperty().bind(right.heightProperty());
        centerRect.setArcWidth(20);
        centerRect.setArcHeight(20);
        centerRect.setFill(Color.BLACK);
        DropShadow shadow = new DropShadow();
        centerRect.setEffect(shadow);
        
        rightGrid = new GridPane ();
        rightGrid.setHgap(30);
        rightGrid.setVgap(20);
        rightGrid.setEffect(rightBlur);
        
        ToggleGroup group = new ToggleGroup();
        
        ToggleButton bBee = new ToggleButton("Bee");
        ToggleButton bBeetle = new ToggleButton("Beetle");
        ToggleButton bGrasshopper = new ToggleButton("Grasshopper");
        ToggleButton bAnt = new ToggleButton("Ant");
        ToggleButton bSpider = new ToggleButton("Spider");
        ToggleButton bLadybug = new ToggleButton("Ladubug");
        ToggleButton bMoskito = new ToggleButton("Moskito");
        ToggleButton bWoodlouse = new ToggleButton("Woodlouse");
        
        bBee.setBackground(new Background(new BackgroundFill(new ImagePattern(new Image(ClassLoader.getSystemClassLoader().getResourceAsStream("Images/Boutons/bee_red.png"))), CornerRadii.EMPTY, Insets.EMPTY)));
        bBee.setMaxWidth(50);
        bBee.setMaxHeight(50);
        bBee.setMinWidth(50);
        bBee.setMinHeight(50);
        
        bBeetle.setBackground(new Background(new BackgroundFill(new ImagePattern(new Image(ClassLoader.getSystemClassLoader().getResourceAsStream("Images/Boutons/beetle_red.png"))), CornerRadii.EMPTY, Insets.EMPTY)));
        bBeetle.setMaxWidth(50);
        bBeetle.setMaxHeight(50);
        bBeetle.setMinWidth(50);
        bBeetle.setMinHeight(50);
        
        bGrasshopper.setBackground(new Background(new BackgroundFill(new ImagePattern(new Image(ClassLoader.getSystemClassLoader().getResourceAsStream("Images/Boutons/grasshopper_red.png"))), CornerRadii.EMPTY, Insets.EMPTY)));
        bGrasshopper.setMaxWidth(50);
        bGrasshopper.setMaxHeight(50);
        bGrasshopper.setMinWidth(50);
        bGrasshopper.setMinHeight(50);
        
        bAnt.setBackground(new Background(new BackgroundFill(new ImagePattern(new Image(ClassLoader.getSystemClassLoader().getResourceAsStream("Images/Boutons/ant_red.png"))), CornerRadii.EMPTY, Insets.EMPTY)));
        bAnt.setMaxWidth(50);
        bAnt.setMaxHeight(50);
        bAnt.setMinWidth(50);
        bAnt.setMinHeight(50);
        
        bSpider.setBackground(new Background(new BackgroundFill(new ImagePattern(new Image(ClassLoader.getSystemClassLoader().getResourceAsStream("Images/Boutons/spider_red.png"))), CornerRadii.EMPTY, Insets.EMPTY)));
        bSpider.setMaxWidth(50);
        bSpider.setMaxHeight(50);
        bSpider.setMinWidth(50);
        bSpider.setMinHeight(50);
        
        bLadybug.setBackground(new Background(new BackgroundFill(new ImagePattern(new Image(ClassLoader.getSystemClassLoader().getResourceAsStream("Images/Boutons/ladybug_red.png"))), CornerRadii.EMPTY, Insets.EMPTY)));
        bLadybug.setMaxWidth(50);
        bLadybug.setMaxHeight(50);
        bLadybug.setMinWidth(50);
        bLadybug.setMinHeight(50);
        
        bMoskito.setBackground(new Background(new BackgroundFill(new ImagePattern(new Image(ClassLoader.getSystemClassLoader().getResourceAsStream("Images/Boutons/moskito_red.png"))), CornerRadii.EMPTY, Insets.EMPTY)));
        bMoskito.setMaxWidth(50);
        bMoskito.setMaxHeight(50);
        bMoskito.setMinWidth(50);
        bMoskito.setMinHeight(50);
        
        bWoodlouse.setBackground(new Background(new BackgroundFill(new ImagePattern(new Image(ClassLoader.getSystemClassLoader().getResourceAsStream("Images/Boutons/woodlouse_red.png"))), CornerRadii.EMPTY, Insets.EMPTY)));
        bWoodlouse.setMaxWidth(50);
        bWoodlouse.setMaxHeight(50);
        bWoodlouse.setMinWidth(50);
        bWoodlouse.setMinHeight(50);
                
        bBee.setToggleGroup(group);
        bBeetle.setToggleGroup(group);
        bGrasshopper.setToggleGroup(group);
        bAnt.setToggleGroup(group);
        bSpider.setToggleGroup(group);
        bLadybug.setToggleGroup(group);
        bMoskito.setToggleGroup(group);
        bWoodlouse.setToggleGroup(group);
        
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
        
        bBee.setOnAction(new ButtonToken(ButtonToken.BEE_BUTTON, lBee, arbitre.joueur(1), arbitre));
        bBeetle.setOnAction(new ButtonToken(ButtonToken.BEETLE_BUTTON, lBee, arbitre.joueur(1), arbitre));
        bGrasshopper.setOnAction(new ButtonToken(ButtonToken.GRASSHOPPER_BUTTON, lBee, arbitre.joueur(1), arbitre));
        bAnt.setOnAction(new ButtonToken(ButtonToken.ANT_BUTTON, lBee, arbitre.joueur(1), arbitre));
        bSpider.setOnAction(new ButtonToken(ButtonToken.SPIDER_BUTTON, lBee, arbitre.joueur(1), arbitre));
        bLadybug.setOnAction(new ButtonToken(ButtonToken.LADYBUG_BUTTON, lBee, arbitre.joueur(1), arbitre));
        bMoskito.setOnAction(new ButtonToken(ButtonToken.MOSKITO_BUTTON, lBee, arbitre.joueur(1), arbitre));
        bWoodlouse.setOnAction(new ButtonToken(ButtonToken.WOODLOUSE_BUTTON, lBee, arbitre.joueur(1), arbitre));
        
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
        rightGrid.add(bWoodlouse, 1, 7);
        
        l[0][0] = lBee;
        l[0][1] = lBeetle;
        l[0][2] = lGrasshopper;
        l[0][3] = lAnt;
        l[0][4] = lSpider;
        l[0][5] = lLadybug;
        l[0][6] = lMoskito;
        l[0][7] = lWoudlose;
        
        b[0][0] = bBee;
        b[0][1] = bBeetle;
        b[0][2] = bGrasshopper;
        b[0][3] = bAnt;
        b[0][4] = bSpider;
        b[0][5] = bLadybug;
        b[0][6] = bMoskito;
        b[0][7] = bWoodlouse;
        
        right.getChildren().addAll(centerRect, rightGrid);
        
    }
    
    private void createLeft () {
        left = new StackPane();
        left.setAlignment(Pos.TOP_CENTER );
        Rectangle centerRect = new Rectangle();
        centerRect.setOpacity(0.25);
        centerRect.widthProperty().bind(left.widthProperty());
        centerRect.heightProperty().bind(left.heightProperty());
        centerRect.setArcWidth(20);
        centerRect.setArcHeight(20);
        centerRect.setFill(Color.BLACK);
        DropShadow shadow = new DropShadow();
        centerRect.setEffect(shadow);
        leftGrid = new GridPane ();
        leftGrid.setHgap(30);
        leftGrid.setVgap(20);
        leftGrid.setEffect(leftBlur);
        
        ToggleGroup group = new ToggleGroup();
        
        ToggleButton bBee = new ToggleButton("Bee");
        ToggleButton bBeetle = new ToggleButton("Beetle");
        ToggleButton bGrasshopper = new ToggleButton("Grasshopper");
        ToggleButton bAnt = new ToggleButton("Ant");
        ToggleButton bSpider = new ToggleButton("Spider");
        ToggleButton bLadybug = new ToggleButton("Ladubug");
        ToggleButton bMoskito = new ToggleButton("Moskito");
        ToggleButton bWoodlouse = new ToggleButton("Woodlouse");
        
        bBee.setBackground(new Background(new BackgroundFill(new ImagePattern(new Image(ClassLoader.getSystemClassLoader().getResourceAsStream("Images/Boutons/bee_red.png"))), CornerRadii.EMPTY, Insets.EMPTY)));
        bBee.setMaxWidth(50);
        bBee.setMaxHeight(50);
        bBee.setMinWidth(50);
        bBee.setMinHeight(50);
        
        bBeetle.setBackground(new Background(new BackgroundFill(new ImagePattern(new Image(ClassLoader.getSystemClassLoader().getResourceAsStream("Images/Boutons/beetle_red.png"))), CornerRadii.EMPTY, Insets.EMPTY)));
        bBeetle.setMaxWidth(50);
        bBeetle.setMaxHeight(50);
        bBeetle.setMinWidth(50);
        bBeetle.setMinHeight(50);
        
        bGrasshopper.setBackground(new Background(new BackgroundFill(new ImagePattern(new Image(ClassLoader.getSystemClassLoader().getResourceAsStream("Images/Boutons/grasshopper_red.png"))), CornerRadii.EMPTY, Insets.EMPTY)));
        bGrasshopper.setMaxWidth(50);
        bGrasshopper.setMaxHeight(50);
        bGrasshopper.setMinWidth(50);
        bGrasshopper.setMinHeight(50);
        
        bAnt.setBackground(new Background(new BackgroundFill(new ImagePattern(new Image(ClassLoader.getSystemClassLoader().getResourceAsStream("Images/Boutons/ant_red.png"))), CornerRadii.EMPTY, Insets.EMPTY)));
        bAnt.setMaxWidth(50);
        bAnt.setMaxHeight(50);
        bAnt.setMinWidth(50);
        bAnt.setMinHeight(50);
        
        bSpider.setBackground(new Background(new BackgroundFill(new ImagePattern(new Image(ClassLoader.getSystemClassLoader().getResourceAsStream("Images/Boutons/spider_red.png"))), CornerRadii.EMPTY, Insets.EMPTY)));
        bSpider.setMaxWidth(50);
        bSpider.setMaxHeight(50);
        bSpider.setMinWidth(50);
        bSpider.setMinHeight(50);
        
        bLadybug.setBackground(new Background(new BackgroundFill(new ImagePattern(new Image(ClassLoader.getSystemClassLoader().getResourceAsStream("Images/Boutons/ladybug_red.png"))), CornerRadii.EMPTY, Insets.EMPTY)));
        bLadybug.setMaxWidth(50);
        bLadybug.setMaxHeight(50);
        bLadybug.setMinWidth(50);
        bLadybug.setMinHeight(50);
        
        bMoskito.setBackground(new Background(new BackgroundFill(new ImagePattern(new Image(ClassLoader.getSystemClassLoader().getResourceAsStream("Images/Boutons/moskito_red.png"))), CornerRadii.EMPTY, Insets.EMPTY)));
        bMoskito.setMaxWidth(50);
        bMoskito.setMaxHeight(50);
        bMoskito.setMinWidth(50);
        bMoskito.setMinHeight(50);
        
        bWoodlouse.setBackground(new Background(new BackgroundFill(new ImagePattern(new Image(ClassLoader.getSystemClassLoader().getResourceAsStream("Images/Boutons/woodlouse_red.png"))), CornerRadii.EMPTY, Insets.EMPTY)));
        bWoodlouse.setMaxWidth(50);
        bWoodlouse.setMaxHeight(50);
        bWoodlouse.setMinWidth(50);
        bWoodlouse.setMinHeight(50);
        
        //bMoskito
        
        bBee.setToggleGroup(group);
        bBeetle.setToggleGroup(group);
        bGrasshopper.setToggleGroup(group);
        bAnt.setToggleGroup(group);
        bSpider.setToggleGroup(group);
        bLadybug.setToggleGroup(group);
        bMoskito.setToggleGroup(group);
        bWoodlouse.setToggleGroup(group);
        
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
        
        bBee.setOnAction(new ButtonToken(ButtonToken.BEE_BUTTON, lBee, arbitre.joueur(1), arbitre));
        bBeetle.setOnAction(new ButtonToken(ButtonToken.BEETLE_BUTTON, lBee, arbitre.joueur(1), arbitre));
        bGrasshopper.setOnAction(new ButtonToken(ButtonToken.GRASSHOPPER_BUTTON, lBee, arbitre.joueur(1), arbitre));
        bAnt.setOnAction(new ButtonToken(ButtonToken.ANT_BUTTON, lBee, arbitre.joueur(1), arbitre));
        bSpider.setOnAction(new ButtonToken(ButtonToken.SPIDER_BUTTON, lBee, arbitre.joueur(1), arbitre));
        bLadybug.setOnAction(new ButtonToken(ButtonToken.LADYBUG_BUTTON, lBee, arbitre.joueur(1), arbitre));
        bMoskito.setOnAction(new ButtonToken(ButtonToken.MOSKITO_BUTTON, lBee, arbitre.joueur(1), arbitre));
        bWoodlouse.setOnAction(new ButtonToken(ButtonToken.WOODLOUSE_BUTTON, lBee, arbitre.joueur(1), arbitre));
        
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
        leftGrid.add(bWoodlouse, 1, 7);
        
        l[1][0] = lBee;
        l[1][1] = lBeetle;
        l[1][2] = lGrasshopper;
        l[1][3] = lAnt;
        l[1][4] = lSpider;
        l[1][5] = lLadybug;
        l[1][6] = lMoskito;
        l[1][7] = lWoudlose;
        
        b[1][0] = bBee;
        b[1][1] = bBeetle;
        b[1][2] = bGrasshopper;
        b[1][3] = bAnt;
        b[1][4] = bSpider;
        b[1][5] = bLadybug;
        b[1][6] = bMoskito;
        b[1][7] = bWoodlouse;
        
        left.getChildren().addAll(centerRect, leftGrid);
    }
    
    /**
     *
     */
    public void update () {
        for (int i = 0; i < 2; i++) {
            for (int j = 0; j < 8; j++) {
                l[i][j].setText("" + arbitre.joueur(i).pion(j));
                if (arbitre.joueur(i).pion(j) == 0)
                    b[i][j].setDisable(true);
            }
        }
        
        switch (arbitre.jCourant()) {
            case 0:
                //eftBlur.setRadius(10);
                rightBlur.setRadius(0);
                break;
            case 1:
                leftBlur.setRadius(0);
                //rightBlur.setRadius(10);
                break;
            default:
        }
        
    }
    
    private void setDefaultBackground (ToggleButton b, int i, int j) {
        String s = "";
        switch (j) {
            case 0:
                s = "bee";
                break;
            case 1:
                s = "beetle";
                break;
            case 2:
                s = "grasshopper";
                break;
            case 3:
                s = "ant";
                break;
            case 4:
                s = "spider";
                break;
            case 5:
                s = "ladybug";
                break;
            case 6:
                s = "moskito";
                break;
            case 7:
                s = "woodlouse";
                break;
            default:
        }
        b.setBackground(new Background(new BackgroundFill(new ImagePattern(new Image(ClassLoader.getSystemClassLoader().getResourceAsStream("Images/Boutons/" + s + "_red.png"))), CornerRadii.EMPTY, Insets.EMPTY)));
    }
    
    public void uncheck () {
        for (int i = 0; i < 2; i++) {
            for (int j = 0; j < 8; j++) {
                b[i][j].setSelected(false);
                setDefaultBackground(b[i][j], i, j);
            }
        }
        
    }
}
