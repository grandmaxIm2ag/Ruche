/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Vue;


import javafx.application.*;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.layout.BorderPane;
import Modele.Arbitre;
import Joueurs.Joueur;
import Joueurs.Ordinateur;
import Modele.Point;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import javafx.collections.FXCollections;
import javafx.event.*;
import javafx.geometry.Insets;
import javafx.geometry.Orientation;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.Separator;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import static javafx.scene.input.DataFormat.URL;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
//import java.net.URL;
//import javafx.scene.input.DataFormat.URL;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.TilePane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javax.imageio.ImageIO;

/**
 *
 * @author grandmax
 */
public class Interface extends Application{
    public final static int BOUTTON_NOUVEL = 0;
    public final static int BOUTTON_REFAIRE = 1;
    public final static int BOUTTON_ANNULER = 2;
    public final static int BOUTTON_SAUVEGARDER = 3;
    public final static int BOUTON_AIDE = 4;
    public final static int BOUTON_CHARGER = 5;
    public final static int BOUTON_MENU = 6;
    public final static int BOUTON_COMMENCER = 7;
    public final static int BOUTON_QUITTER = 7;

    public final static int SOURIS_PRESSEE = 0;
    public final static int SOURIS_BOUGEE = 1;
    
    public final static int CHOIX_MODE = 0;
    public final static int CHOIX_DIFFICULTE = 1;
    public final static int CHOIX_PLATEAU = 2;
    
    static Arbitre arbitre;
    static BorderPane root;
    static Scene s;
    final static boolean fullScreen = false;
        
    @Override
    public void start(Stage stage) throws Exception {


        //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
        stage.setTitle("Ruche");
        if (fullScreen) {
            s = new Scene(root);
            stage.setFullScreen(true);
        } else {
            s = new Scene(root, 800, 600);
        }
        
        Canvas c = new Canvas(200, 100);
        GraphicsContext gc;
        gc = c.getGraphicsContext2D();
        StackPane paneTop= new StackPane(c);
        paneTop.setPadding(new Insets(20,20,20,20));
        gc.setFill(Color.ORANGE);
        gc.setStroke(Color.WHEAT);
        double [][] coords = hex_corner(100, 50, 50);
        gc.fillPolygon(coords[0], coords[1], 6);
        gc.strokePolygon(coords[0], coords[1], 6);
        gc.setFill(Color.ORANGERED);
        gc.setFont(Font.font("Tahoma", FontWeight.NORMAL, 33));
        gc.fillText("Hive", 70, 60);
        paneTop.setAlignment(Pos.CENTER);
        root.setTop(paneTop);
        
        stage.setScene(s);
        goMenu();
        stage.show();
    }

    public static void creer(String[] args, Arbitre a) {
        root = new BorderPane();
        arbitre = a;
        launch(args);
        
    }
    
    public static void goPartie() {
        //root.setLeft(new Pane());
        root.setBottom(new Pane());
        Canvas c = new Canvas (500, 500);
        //StackPane stack = new StackPane(c);
        Pane stack = new Pane(c);
        root.setCenter(stack);
        //stack.setAlignment(Pos.TOP_CENTER);
        /*
        createBee(c, 250, 250, 50);
        createBeetle(c, 350, 250, 50);
        createGrasshopper(c, 450, 250, 50);
        createLadybug(c, 150, 250, 50);
        createSpider(c, 200, 350, 50);
        createAnt(c, 300, 350, 50);
        createMoskito(c, 400, 350, 50);
        createWoodlouse(c, 300, 150, 50);
        */
        root.setCenter(stack);
        
        c.widthProperty().bind(stack.widthProperty());
        c.heightProperty().bind(stack.heightProperty().subtract(50));
        
        VBox box = new VBox();
        box.setAlignment(Pos.TOP_CENTER);
        box.setPadding(new Insets(20,10,20,10));
        box.setSpacing(20);
        root.setLeft(box);
        Button btPrec = new Button("Précédent");
        Button btSuiv = new Button("Suivant");
        Button btSave = new Button ("Sauvegarder");
        Button btMenu = new Button("Menu principal");
        btPrec.setMaxWidth(150);
        btSuiv.setMaxWidth(150);
        btSave.setMaxWidth(150);
        btMenu.setMaxWidth(150);
        btMenu.setOnAction(new EventHandler<ActionEvent>() {
        
            @Override
            public void handle(ActionEvent event) {
                goMenu();
            }
        });
        
        GridPane bPion = new GridPane();
        bPion.setHgap(10);
        Separator sep = new Separator();
        sep.setOrientation(Orientation.VERTICAL);
        bPion.add(pionTableau(), 0, 0);
        bPion.add(sep, 1, 0);
        bPion.add(pionTableau(), 2, 0);
        root.setRight(bPion);
        
        box.getChildren().addAll(btPrec, btSuiv, btSave, btMenu);
        
        Animation anim = new Animation(arbitre, c);
        anim.start();

    }
    
    public static double pythagorelol (double a) {
        return Math.sqrt(Math.pow(a, 2) - Math.pow(a/2, 2));
    }
    
    public static double pymoins1 (double a) {
    return Math.cos(Math.PI/180*30)/a;
}
    
    public static Canvas pionTableau() {
        Canvas c = new Canvas(75,375);
        GraphicsContext gc = c.getGraphicsContext2D();
        double x, y;
        x = 25;
        y = 50;
        createBee(c, x, y, 25);
        x += pythagorelol(25);
        y = y+25+25/2;
        createBeetle(c,x, y, 25);
        x -= pythagorelol(25);
        y = y+25+25/2;
        createGrasshopper(c, x, y, 25);
        x += pythagorelol(25);
        y = y+25+25/2;
        createLadybug(c, x, y, 25);
        x -= pythagorelol(25);
        y = y+25+25/2;
        createSpider(c, x, y, 25);
        x += pythagorelol(25);
        y = y+25+25/2;
        createAnt(c, x, y, 25);
        x -= pythagorelol(25);
        y = y+25+25/2;
        createMoskito(c, x, y, 25);
        x += pythagorelol(25);
        y = y+25+25/2;
        createWoodlouse(c, x, y, 25);
        return c;
    }
    
    public static void goMenu(){
        root.setRight(new Pane());
        root.setLeft(new Pane());
        root.setBottom(new Pane());
        
        GridPane grid = new GridPane();
        grid.setHgap(10);
        grid.setVgap(20);
        VBox box = new VBox();
        root.setCenter(box);
        grid.setAlignment(Pos.CENTER);
        
        Button btNouveau = new Button("Nouvelle partie");
        Button btCharger = new Button("Charger partie");
        Button btConfig = new Button("Configuration");
        Button btCredits = new Button("Credits");
        Button btQuit = new Button("Quitter");

        btQuit.setOnAction(new EventHandler<ActionEvent>() {

            @Override
            public void handle(ActionEvent event) {
                System.exit(0);
            }
        });
        
        btNouveau.setOnAction(new EventHandler<ActionEvent>() {
            
            @Override
            public void handle (ActionEvent event) {
                goPartie();
            }
        });
        
        btConfig.setOnAction(new EventHandler<ActionEvent>() {
            
            @Override
            public void handle(ActionEvent event) {
                goConfig();
            }
        });
        
        btCredits.setOnAction(new EventHandler<ActionEvent>() {
            
            @Override
            public void handle(ActionEvent event) {
                goCredits();
            }
        });
        
        btNouveau.setMaxWidth(150);
        btCharger.setMaxWidth(150);
        btConfig.setMaxWidth(150);
        btCredits.setMaxWidth(150);
        box.setAlignment(Pos.TOP_CENTER);
        box.setSpacing(30);
        
        ChoiceBox cbDifficulte = new ChoiceBox(FXCollections.observableArrayList("Facile", "Normal", "Difficile"));
        ChoiceBox cbMode = new ChoiceBox(FXCollections.observableArrayList("Contre IA", "Contre Humain"));
        ChoiceBox cbSave = new ChoiceBox(FXCollections.observableArrayList("Jean contre pierre", "Jean contre IA", "Jean contre IA(2)"));
        
        cbDifficulte.setMaxWidth(150);
        cbMode.setMaxWidth(150);
        cbSave.setMaxWidth(150);
        cbDifficulte.getSelectionModel().selectFirst();
        cbMode.getSelectionModel().selectFirst();
        cbSave.getSelectionModel().selectFirst();
        
        Label lDifficulte = new Label("Difficulté :");
        Label lMode = new Label("Mode :");
        Label lSave = new Label("Sauvegarde :");
        
        grid.add(btNouveau, 0, 0);
        grid.add(btCharger, 0, 1);
        grid.add(btConfig, 0, 2);
        grid.add(lDifficulte, 3, 0);
        grid.add(cbDifficulte, 4, 0);
        grid.add(lMode, 7, 0);
        grid.add(cbMode, 8, 0);
        grid.add(lSave, 3, 1);
        grid.add(cbSave, 4, 1);
        grid.add(btCredits, 0, 3);
        
        
        
        Canvas c = titreSect("Menu");
        
        box.getChildren().addAll(c, grid, btQuit);
        
    }
    
    
    public static double[][] hex_corner (double x, double y, double rayon) {
        double [][] coords = new double[2][6];
        double angle_deg;
        double angle_rad;
        for (int i = 0; i < 6; i++) {
            angle_deg = 60*i + 30;
            angle_rad = Math.PI / 180 * angle_deg;
            coords[0][i] = x + rayon * Math.cos(angle_rad);
            coords[1][i] = y + rayon * Math.sin(angle_rad);
        }
        return coords;
    }
    
    public void goFin(String gagnant){
        
    }
    
    public static void goConfig () {
        VBox box = new VBox();
        box.setAlignment(Pos.TOP_CENTER);
        box.setSpacing(30);
        root.setCenter(box);
        
        Canvas c = titreSect("Config");
        
        GridPane grid = new GridPane();
        grid.setHgap(10);
        grid.setVgap(20);
        grid.setAlignment(Pos.CENTER);
        
        Button b1 = new Button("un bouton");
        Button b2 = new Button("un autre bouton");
        b1.setMaxWidth(150);
        b2.setMaxWidth(150);
        Label l = new Label("un truc");
        Label l2 = new Label("un autre truc");
        Label l3 = new Label("encore un truc");
        Label l4 = new Label("etc");

        grid.add(b1, 0, 0);
        grid.add(l, 4, 0);
        grid.add(l2, 8, 0);
        grid.add(b2, 0, 1);
        grid.add(l3, 4, 1);
        grid.add(l4, 8, 1);
        
        HBox bottom = new HBox();
        bottom.setAlignment(Pos.BASELINE_RIGHT);
        bottom.setSpacing(10);
        bottom.setPadding(new Insets(20, 20, 20, 20));
        
        Button btValider = new Button("Valider");
        Button btRetour = new Button("Retour");
        btValider.setMaxWidth(120);
        btRetour.setMaxWidth(120);
        btRetour.setOnAction(new EventHandler<ActionEvent>() {
            
            @Override
            public void handle(ActionEvent event) {
                goMenu();
            }
        });
        
        Separator sep = new Separator();
        sep.setOrientation(Orientation.VERTICAL);
        bottom.getChildren().addAll(btValider,sep, btRetour);
        
        box.getChildren().addAll(c, grid, bottom);
    }
    
    public static Canvas titreSect (String name) {
        Canvas c = new Canvas(200, 100);
        GraphicsContext gc;
        gc = c.getGraphicsContext2D();
        gc.setFill(Color.ORANGE);
        gc.setStroke(Color.WHEAT);
        double [][] coords = hex_corner(100, 50, 25);
        gc.fillPolygon(coords[0], coords[1], 6);
        gc.strokePolygon(coords[0], coords[1], 6);
        gc.setFill(Color.ORANGERED);
        gc.setFont(Font.font("Tahoma", FontWeight.NORMAL, 33));
        gc.fillText(name, coords[0][3], coords[1][2]-1);
        return c;
    }
    
    public static void goCredits () {
        VBox box = new VBox();
        box.setSpacing(30);
        box.setPadding(new Insets(20,10,20,10));
        box.setAlignment(Pos.TOP_CENTER);
        GridPane grid = new GridPane();
        grid.setHgap(10);
        grid.setVgap(20);
        grid.setAlignment(Pos.TOP_CENTER);
        Canvas c = titreSect("Credits");
        
        Label lMoteur = new Label("Moteur");
        Label lIA = new Label("IA");
        Label lIHM = new Label("IHM");
        Label lMax = new Label("Maxence Grand");
        Label lNarek = new Label("Narek Davtyan");
        Label lAme = new Label ("Amelina Douard");
        Label lLies = new Label ("Lies Hadjadj");
        Label lLu = new Label("Lucie Muller");
        Label lEnzo = new Label("Enzo Brignon");
        
        Button bRetour = new Button("Retour");
        
        bRetour.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                goMenu();
            }
        });
        
        grid.add(lMoteur, 1, 0);
        grid.add(lMax, 0, 1);
        grid.add(lNarek, 2, 1);
        grid.add(lIA, 1, 2);
        grid.add(lAme, 0, 3);
        grid.add(lLies, 2, 3);
        grid.add(lIHM, 1, 4);
        grid.add(lEnzo, 0, 5);
        grid.add(lLu, 2, 5);
        
        box.getChildren().addAll(c, grid, bRetour);
        
        root.setCenter(box);
        root.setRight(new Pane());
        root.setLeft(new Pane());
        root.setBottom(new Pane());
        
    }
    
    
    public static void infoPartie(Joueur j1, Joueur j2, int nbManche, int joueur){
        
    }
    
    public static void createBee (Canvas c, double x, double y, double taille) {
        GraphicsContext gc;
        gc = c.getGraphicsContext2D();
        double [][] coords = hex_corner(x,y,taille);
        gc.setFill(Color.BLANCHEDALMOND);
        gc.fillPolygon(coords[0], coords[1], 6);
        InputStream image = null;
        image =  ClassLoader.getSystemClassLoader().getResourceAsStream("Images/bee.png");
        Image img = new Image(image,(taille*1.75),(taille*1.75),true, true);
        gc.drawImage(img,x-(img.getWidth()/2), y-(img.getHeight()/2));

    }
    
    public static void createBeetle (Canvas c, double x, double y, double taille) {
        GraphicsContext gc;
        gc = c.getGraphicsContext2D();
        double [][] coords = hex_corner(x,y,taille);
        gc.setFill(Color.BURLYWOOD);
        gc.fillPolygon(coords[0], coords[1], 6);
        InputStream image = null;
        image =  ClassLoader.getSystemClassLoader().getResourceAsStream("Images/beetle.png");
        Image img = new Image(image,(taille*1.50),(taille*1.50),true, true);
        gc.drawImage(img,x-(img.getWidth()/2), y-(img.getHeight()/2));


    }
    
    public static void createLadybug (Canvas c, double x, double y, double taille) {
        GraphicsContext gc;
        gc = c.getGraphicsContext2D();
        double [][] coords = hex_corner(x,y,taille);
        gc.setFill(Color.RED);
        gc.fillPolygon(coords[0], coords[1], 6);
        InputStream image = null;
        image =  ClassLoader.getSystemClassLoader().getResourceAsStream("Images/ladybug.png");
        Image img = new Image(image,(taille*1.50),(taille*1.50),true, true);
        gc.drawImage(img,x-(img.getWidth()/2), y-(img.getHeight()/2));


    }
    
    public static void createMoskito (Canvas c, double x, double y, double taille) {
        GraphicsContext gc;
        gc = c.getGraphicsContext2D();
        double [][] coords = hex_corner(x,y,taille);
        gc.setFill(Color.GREENYELLOW);
        gc.fillPolygon(coords[0], coords[1], 6);
        InputStream image = null;
        image =  ClassLoader.getSystemClassLoader().getResourceAsStream("Images/moskito.png");
        Image img = new Image(image,(taille*2),(taille*2),true, true);
        gc.drawImage(img,x-(img.getWidth()/2), y-(img.getHeight()/2));


    }
    
    //  grasshopper
    
    public static void createWoodlouse (Canvas c, double x, double y, double taille) {
        GraphicsContext gc;
        gc = c.getGraphicsContext2D();
        double [][] coords = hex_corner(x,y,taille);
        gc.setFill(Color.CHOCOLATE);
        gc.fillPolygon(coords[0], coords[1], 6);
        InputStream image = null;
        image =  ClassLoader.getSystemClassLoader().getResourceAsStream("Images/woodlouse.png");
        Image img = new Image(image,(taille*1.75),(taille*1.75),true, true);
        gc.drawImage(img,x-(img.getWidth()/2), y-(img.getHeight()/2));


    }
    
    public static void createAnt (Canvas c, double x, double y, double taille) {
        GraphicsContext gc;
        gc = c.getGraphicsContext2D();
        double [][] coords = hex_corner(x,y,taille);
        gc.setFill(Color.AQUAMARINE);
        gc.fillPolygon(coords[0], coords[1], 6);
        InputStream image = null;
        image =  ClassLoader.getSystemClassLoader().getResourceAsStream("Images/ant.png");
        Image img = new Image(image,(taille*1.60),(taille*1.60),true, true);
        gc.drawImage(img,x-(img.getWidth()/2), y-(img.getHeight()/2));


    }
    
    public static void createSpider (Canvas c, double x, double y, double taille) {
        GraphicsContext gc;
        gc = c.getGraphicsContext2D();
        double [][] coords = hex_corner(x,y,taille);
        gc.setFill(Color.CORNFLOWERBLUE);
        gc.fillPolygon(coords[0], coords[1], 6);
        InputStream image = null;
        image =  ClassLoader.getSystemClassLoader().getResourceAsStream("Images/spider.png");
        Image img = new Image(image,(taille*1.60),(taille*1.60),true, true);
        gc.drawImage(img,x-(img.getWidth()/2), y-(img.getHeight()/2));


    }
    
    public static void createGrasshopper (Canvas c, double x, double y, double taille) {
        GraphicsContext gc;
        gc = c.getGraphicsContext2D();
        double [][] coords = hex_corner(x,y,taille);
        gc.setFill(Color.DARKGRAY);
        gc.fillPolygon(coords[0], coords[1], 6);
        InputStream image = null;
        image =  ClassLoader.getSystemClassLoader().getResourceAsStream("Images/grasshopper.png");
        Image img = new Image(image,(taille*1.60),(taille*1.60),true ,true);
        gc.drawImage(img,x-(img.getWidth()/2), y-(img.getHeight()/2));

    }
    
    public static void initChoix(ChoiceBox cb, int c){

    }
}
