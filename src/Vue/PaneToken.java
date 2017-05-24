/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Vue;

import Controleur.AideListener;
import Controleur.Bouton;
import Controleur.ButtonToken;
import Controleur.TokenListener;
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
import javafx.scene.image.ImageView;
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
    static Label nomAdv;
    private static String colorJ1;
    private static String colorJ2;
    AideListener al;
    private static final String [] colorString = {"hotpink", "limegreen", "whitesmoke", "orangered", "steelblue", "darkgoldenrod", "magenta", "mediumblue", "maroon"};
    
    private PaneToken (Arbitre arbitre) {
        this.arbitre = arbitre;
        l = new Label[2][8];
        b = new ToggleButton[2][8];
        leftBlur = new GaussianBlur();
        rightBlur = new GaussianBlur();
        leftBlur.setRadius(0);
        if (arbitre.type() == FabriqueArbitre.LOCAL_JVJ || arbitre.type() == FabriqueArbitre.LOCAL_JVIA )
            rightBlur.setRadius(10);
        rightBlur.setRadius(0);
        nomAdv = new Label();
        colorJ1 = colorToString(0);
        colorJ2 = colorToString(1);
        
    }
    
    private String colorToString (int p) {
        int c = 0;
        switch (p) {
            case 0:
                c = Interface.getColorP1();
                break;
            case 1:
                c = Interface.getColorP2();
                break;
            default:
        }
        return colorString[c];
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
                //if (arbitre.joueur(i).pion(j) == 0 || arbitre.jCourant()!=i)
                    //b[i][j].setDisable(true);
                b[i][j].selectedProperty().addListener(new TokenListener(b[i][j], i, j));
            }
        }
        al = new AideListener(b, arbitre);
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
        String p = colorToString(0);
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
        
        bBee.setBackground(new Background(new BackgroundFill(new ImagePattern(new Image(ClassLoader.getSystemClassLoader().getResourceAsStream("Images/Boutons/bee_"+p+".png"))), CornerRadii.EMPTY, Insets.EMPTY)));
        bBee.setMaxWidth(50);
        bBee.setMaxHeight(50);
        bBee.setMinWidth(50);
        bBee.setMinHeight(50);
        
        bBeetle.setBackground(new Background(new BackgroundFill(new ImagePattern(new Image(ClassLoader.getSystemClassLoader().getResourceAsStream("Images/Boutons/beetle_"+p+".png"))), CornerRadii.EMPTY, Insets.EMPTY)));
        bBeetle.setMaxWidth(50);
        bBeetle.setMaxHeight(50);
        bBeetle.setMinWidth(50);
        bBeetle.setMinHeight(50);
        
        bGrasshopper.setBackground(new Background(new BackgroundFill(new ImagePattern(new Image(ClassLoader.getSystemClassLoader().getResourceAsStream("Images/Boutons/grasshopper_"+p+".png"))), CornerRadii.EMPTY, Insets.EMPTY)));
        bGrasshopper.setMaxWidth(50);
        bGrasshopper.setMaxHeight(50);
        bGrasshopper.setMinWidth(50);
        bGrasshopper.setMinHeight(50);
        
        bAnt.setBackground(new Background(new BackgroundFill(new ImagePattern(new Image(ClassLoader.getSystemClassLoader().getResourceAsStream("Images/Boutons/ant_"+p+".png"))), CornerRadii.EMPTY, Insets.EMPTY)));
        bAnt.setMaxWidth(50);
        bAnt.setMaxHeight(50);
        bAnt.setMinWidth(50);
        bAnt.setMinHeight(50);
        
        bSpider.setBackground(new Background(new BackgroundFill(new ImagePattern(new Image(ClassLoader.getSystemClassLoader().getResourceAsStream("Images/Boutons/spider_"+p+".png"))), CornerRadii.EMPTY, Insets.EMPTY)));
        bSpider.setMaxWidth(50);
        bSpider.setMaxHeight(50);
        bSpider.setMinWidth(50);
        bSpider.setMinHeight(50);
        
        bLadybug.setBackground(new Background(new BackgroundFill(new ImagePattern(new Image(ClassLoader.getSystemClassLoader().getResourceAsStream("Images/Boutons/ladybug_"+p+".png"))), CornerRadii.EMPTY, Insets.EMPTY)));
        bLadybug.setMaxWidth(50);
        bLadybug.setMaxHeight(50);
        bLadybug.setMinWidth(50);
        bLadybug.setMinHeight(50);
        
        bMoskito.setBackground(new Background(new BackgroundFill(new ImagePattern(new Image(ClassLoader.getSystemClassLoader().getResourceAsStream("Images/Boutons/moskito_"+p+".png"))), CornerRadii.EMPTY, Insets.EMPTY)));
        bMoskito.setMaxWidth(50);
        bMoskito.setMaxHeight(50);
        bMoskito.setMinWidth(50);
        bMoskito.setMinHeight(50);
        
        bWoodlouse.setBackground(new Background(new BackgroundFill(new ImagePattern(new Image(ClassLoader.getSystemClassLoader().getResourceAsStream("Images/Boutons/woodlouse_"+p+".png"))), CornerRadii.EMPTY, Insets.EMPTY)));
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
        lBee.setTextFill(Color.WHITE);
        lBeetle.setTextFill(Color.WHITE);
        lGrasshopper.setTextFill(Color.WHITE);
        lSpider.setTextFill(Color.WHITE);
        lLadybug.setTextFill(Color.WHITE);
        lMoskito.setTextFill(Color.WHITE);
        lWoudlose.setTextFill(Color.WHITE);
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
        
        Label joueur = new Label(arbitre.joueur(Arbitre.J1).nom() );
        joueur.setTextFill(Color.WHITE);
        
        rightGrid.add(joueur, 1,0);
        
        rightGrid.add(bBee, 0, 1);
        rightGrid.add(lBee, 1, 1);
        
        rightGrid.add(lBeetle, 0, 2);
        rightGrid.add(bBeetle, 1, 2);
        
        rightGrid.add(bGrasshopper, 0, 3);
        rightGrid.add(lGrasshopper, 1, 3);
        
        rightGrid.add(lAnt, 0, 4);
        rightGrid.add(bAnt, 1, 4);
        
        rightGrid.add(bSpider, 0, 5);
        rightGrid.add(lSpider, 1, 5);
        
        rightGrid.add(lLadybug, 0, 6);
        rightGrid.add(bLadybug, 1, 6);
        
        rightGrid.add(bMoskito, 0, 7);
        rightGrid.add(lMoskito, 1, 7);
        
        rightGrid.add(lWoudlose, 0, 8);
        rightGrid.add(bWoodlouse, 1, 8);
        /*
        Image imageHelp = new Image(ClassLoader.getSystemClassLoader().getResourceAsStream("Images/Icone/help.png"));
        Button btHelp = new Button();
        btHelp.setOnAction(new Bouton(Bouton.BOUTON_AIDE, arbitre));
        btHelp.setGraphic(new ImageView(imageHelp));
        *
        rightGrid.add(btHelp, 1, 9);
        */
        /*
        Image imageAbd = new Image(ClassLoader.getSystemClassLoader().getResourceAsStream("Images/Icone/white.png"));
        Button btAbd = new Button();
        btAbd.setGraphic(new ImageView(imageAbd));
        
        rightGrid.add(btAbd, 0,9);
        */
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
        
        //bBee.selectedProperty().addListener(listener);
        
        right.getChildren().addAll(centerRect, rightGrid);
        
        //update();
    }
    
    private void createLeft () {
        String p = colorToString(1);
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
        
        bBee.setBackground(new Background(new BackgroundFill(new ImagePattern(new Image(ClassLoader.getSystemClassLoader().getResourceAsStream("Images/Boutons/bee_"+p+".png"))), CornerRadii.EMPTY, Insets.EMPTY)));
        bBee.setMaxWidth(50);
        bBee.setMaxHeight(50);
        bBee.setMinWidth(50);
        bBee.setMinHeight(50);
        
        bBeetle.setBackground(new Background(new BackgroundFill(new ImagePattern(new Image(ClassLoader.getSystemClassLoader().getResourceAsStream("Images/Boutons/beetle_"+p+".png"))), CornerRadii.EMPTY, Insets.EMPTY)));
        bBeetle.setMaxWidth(50);
        bBeetle.setMaxHeight(50);
        bBeetle.setMinWidth(50);
        bBeetle.setMinHeight(50);
        
        bGrasshopper.setBackground(new Background(new BackgroundFill(new ImagePattern(new Image(ClassLoader.getSystemClassLoader().getResourceAsStream("Images/Boutons/grasshopper_"+p+".png"))), CornerRadii.EMPTY, Insets.EMPTY)));
        bGrasshopper.setMaxWidth(50);
        bGrasshopper.setMaxHeight(50);
        bGrasshopper.setMinWidth(50);
        bGrasshopper.setMinHeight(50);
        
        bAnt.setBackground(new Background(new BackgroundFill(new ImagePattern(new Image(ClassLoader.getSystemClassLoader().getResourceAsStream("Images/Boutons/ant_"+p+".png"))), CornerRadii.EMPTY, Insets.EMPTY)));
        bAnt.setMaxWidth(50);
        bAnt.setMaxHeight(50);
        bAnt.setMinWidth(50);
        bAnt.setMinHeight(50);
        
        bSpider.setBackground(new Background(new BackgroundFill(new ImagePattern(new Image(ClassLoader.getSystemClassLoader().getResourceAsStream("Images/Boutons/spider_"+p+".png"))), CornerRadii.EMPTY, Insets.EMPTY)));
        bSpider.setMaxWidth(50);
        bSpider.setMaxHeight(50);
        bSpider.setMinWidth(50);
        bSpider.setMinHeight(50);
        
        bLadybug.setBackground(new Background(new BackgroundFill(new ImagePattern(new Image(ClassLoader.getSystemClassLoader().getResourceAsStream("Images/Boutons/ladybug_"+p+".png"))), CornerRadii.EMPTY, Insets.EMPTY)));
        bLadybug.setMaxWidth(50);
        bLadybug.setMaxHeight(50);
        bLadybug.setMinWidth(50);
        bLadybug.setMinHeight(50);
        
        bMoskito.setBackground(new Background(new BackgroundFill(new ImagePattern(new Image(ClassLoader.getSystemClassLoader().getResourceAsStream("Images/Boutons/moskito_"+p+".png"))), CornerRadii.EMPTY, Insets.EMPTY)));
        bMoskito.setMaxWidth(50);
        bMoskito.setMaxHeight(50);
        bMoskito.setMinWidth(50);
        bMoskito.setMinHeight(50);
        
        bWoodlouse.setBackground(new Background(new BackgroundFill(new ImagePattern(new Image(ClassLoader.getSystemClassLoader().getResourceAsStream("Images/Boutons/woodlouse_"+p+".png"))), CornerRadii.EMPTY, Insets.EMPTY)));
        bWoodlouse.setMaxWidth(50);
        bWoodlouse.setMaxHeight(50);
        bWoodlouse.setMinWidth(50);
        bWoodlouse.setMinHeight(50);
        
        bWoodlouse.setBackground(new Background(new BackgroundFill(new ImagePattern(new Image(ClassLoader.getSystemClassLoader().getResourceAsStream("Images/Boutons/woodlouse_"+p+".png"))), CornerRadii.EMPTY, Insets.EMPTY)));
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
        
        lBee.setTextFill(Color.WHITE);
        lBeetle.setTextFill(Color.WHITE);
        lGrasshopper.setTextFill(Color.WHITE);
        lSpider.setTextFill(Color.WHITE);
        lLadybug.setTextFill(Color.WHITE);
        lMoskito.setTextFill(Color.WHITE);
        lWoudlose.setTextFill(Color.WHITE);
        
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
        
        
        nomAdv.setText(arbitre.joueur(Arbitre.J2).nom() );
        nomAdv.setTextFill(Color.WHITE);
        
        leftGrid.add(nomAdv, 0,0);
        
        leftGrid.add(bBee, 0, 1);
        leftGrid.add(lBee, 1, 1);
        
        leftGrid.add(lBeetle, 0, 2);
        leftGrid.add(bBeetle, 1, 2);
        
        leftGrid.add(bGrasshopper, 0, 3);
        leftGrid.add(lGrasshopper, 1, 3);
        
        leftGrid.add(lAnt, 0, 4);
        leftGrid.add(bAnt, 1, 4);
        
        leftGrid.add(bSpider, 0, 5);
        leftGrid.add(lSpider, 1, 5);
        
        leftGrid.add(lLadybug, 0, 6);
        leftGrid.add(bLadybug, 1, 6);
        
        leftGrid.add(bMoskito, 0, 7);
        leftGrid.add(lMoskito, 1, 7);
        
        leftGrid.add(lWoudlose, 0, 8);
        leftGrid.add(bWoodlouse, 1, 8);
        
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
        //update();
    }
    
    /**
     *
     */
    public void update () {
        setNomAdv(arbitre.joueur(Arbitre.J2).nom());
        for (int i = 0; i < 2; i++) {
            for (int j = 0; j < 8; j++) {
                l[i][j].setText("" + arbitre.joueur(i).pion(j));
                if (arbitre.joueur(i).pion(j) == 0 || arbitre.jCourant()==i)
                    b[i][j].setDisable(true);
                else 
                    b[i][j].setDisable(false);
            }
        }
        //if (arbitre.type() == FabriqueArbitre.LOCAL_JVJ || arbitre.type() == FabriqueArbitre.LOCAL_JVIA ) 
        switch (arbitre.jCourant()) {
            case 0:
                //leftBlur.setRadius(10);
                rightBlur.setRadius(0);
                break;
            case 1:
                leftBlur.setRadius(0);
                //rightBlur.setRadius(10);
                break;
            default:
        }
        
    }
    
    public void setHelpBackground (int i, int j) {
        if (j >= 0 && j < 8) {
            ToggleButton b = this.b[i][j];
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
            b.setBackground(new Background(new BackgroundFill(new ImagePattern(new Image(ClassLoader.getSystemClassLoader().getResourceAsStream("Images/Boutons/" + s + "_rouge.png"))), CornerRadii.EMPTY, Insets.EMPTY)));
        }
    }
    
    public void setDefaultBackground (ToggleButton b, int i, int j) {
        String s = "";
        String p = colorToString(i);
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
        b.setBackground(new Background(new BackgroundFill(new ImagePattern(new Image(ClassLoader.getSystemClassLoader().getResourceAsStream("Images/Boutons/" + s + "_"+p+".png"))), CornerRadii.EMPTY, Insets.EMPTY)));
    }
    
    public void uncheck () {
        for (int i = 0; i < 2; i++) {
            for (int j = 0; j < 8; j++) {
                b[i][j].setSelected(false);
                setDefaultBackground(b[i][j], i, j);
            }
        }
        
    }
    
    public static void setNomAdv(String adv){
        nomAdv.setText(adv);
    }
    
    public static void reset(){
        INSTANCE = null;
    }
}
