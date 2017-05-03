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
import javafx.scene.input.*;
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
    
    public  void goPartie(){
        root.setLeft(new Pane());
        root.setBottom(new Pane());
        Canvas c = new Canvas (500, 500);
        StackPane stack = new StackPane(c);
        stack.setAlignment(Pos.TOP_CENTER);
        GraphicsContext gc;
        gc = c.getGraphicsContext2D();
        gc.setFill(Color.WHITESMOKE);
        gc.fillRect(0, 0, 500, 500);
        gc.strokeRect(0, 0, 500, 500);
        gc.setFill(Color.BLUE);
        gc.setFont(Font.font("Tahoma", FontWeight.NORMAL, 33));
        gc.fillText("Ceci est le terrain de jeu", 50, 250);
        root.setCenter(stack);
        VBox box = new VBox();
        box.setAlignment(Pos.TOP_CENTER);
        box.setPadding(new Insets(20,10,20,10));
        box.setSpacing(20);
        root.setRight(box);
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
        
        box.getChildren().addAll(btPrec, btSuiv, btSave, btMenu);

    }
    
    public void goMenu(){
        root.setRight(new Pane());
        root.setLeft(new Pane());
        root.setBottom(new Pane());
        
        GridPane grid = new GridPane();
        grid.setHgap(10);
        grid.setVgap(20);
        StackPane stack = new StackPane();
        VBox box = new VBox();
        root.setCenter(box);
        stack.setAlignment(Pos.CENTER);
        grid.setAlignment(Pos.CENTER);
        
        Button btNouveau = new Button("Nouvelle partie");
        Button btCharger = new Button("Charger partie");
        Button btConfig = new Button("Configuration");
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
        
        
        btNouveau.setMaxWidth(150);
        btCharger.setMaxWidth(150);
        btConfig.setMaxWidth(150);
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
        gc.fillText("Menu", coords[0][3], coords[1][2]-1);
        
        box.getChildren().addAll(c, grid, btQuit);
        
    }
    
    /*
    function hex_corner(center, size, i):
    var angle_deg = 60 * i   + 30
    var angle_rad = PI / 180 * angle_deg
    return Point(center.x + size * cos(angle_rad),
                 center.y + size * sin(angle_rad))
    */
    
    public double[][] hex_corner (double x, double y, double rayon) {
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
    
    public void goConfig () {
        VBox box = new VBox();
        box.setAlignment(Pos.TOP_CENTER);
        box.setSpacing(30);
        root.setCenter(box);
        
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
        gc.fillText("Config", coords[0][3], coords[1][2]-1);
        
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
        
        
        bottom.getChildren().addAll(btValider, btRetour);
        
        box.getChildren().addAll(c, grid, bottom);
    }
    
    public static void infoPartie(Joueur j1, Joueur j2, int nbManche, int joueur){
        
    }
    
    public static void initChoix(ChoiceBox cb, int c){

    }
}
