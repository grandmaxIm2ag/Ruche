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
import javafx.scene.input.*;
import javafx.scene.layout.GridPane;
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
        stage.setTitle("Gauffre empoisonnee");
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
        double [] xPoints = {100, 150, 150, 100, 50, 50};
        double [] yPoints = {0, 33, 66, 100, 66, 33};
        gc.fillPolygon(xPoints, yPoints, 6);
        gc.strokePolygon(xPoints, yPoints, 6);
        gc.setFill(Color.ORANGERED);
        gc.setFont(Font.font("Tahoma", FontWeight.NORMAL, 33));
        gc.fillText("Hive", 75, 60);
        paneTop.setAlignment(Pos.CENTER);
        root.setTop(paneTop);
        
        //goMenu();
        stage.setScene(s);
        goMenu();
        stage.show();
    }

    public static void creer(String[] args, Arbitre a) {
        root = new BorderPane();
        arbitre = a;
        launch(args);
        
    }
    
    public static void goPartie(){
        
    }
    
    public static void goMenu(){
        root.setRight(new Pane());
        root.setLeft(new Pane());
        root.setBottom(new Pane());
        
        GridPane grid = new GridPane();
        grid.setHgap(55);
        grid.setVgap(20);
        StackPane stack = new StackPane();
        VBox box = new VBox();
        root.setCenter(box);
        stack.setAlignment(Pos.CENTER);
        grid.setAlignment(Pos.CENTER);
        
        //Text tMenu = new Text("Menu");
        //tMenu.setFont(Font.font("Tahoma", FontWeight.NORMAL, 20));
        Button nouv = new Button("Nouvelle partie");
        Button charg = new Button("Charger partie");
        nouv.setMaxWidth(150);
        charg.setMaxWidth(150);
        box.setAlignment(Pos.TOP_CENTER);
        box.setSpacing(30);
        grid.add(nouv, 0, 0);
        grid.add(charg, 0, 1);
        
        
        Canvas c = new Canvas(200, 100);
        GraphicsContext gc;
        gc = c.getGraphicsContext2D();
        gc.setFill(Color.ORANGE);
        gc.setStroke(Color.WHEAT);
        double [] xPoints = {100, 125, 125, 100, 75, 75};
        double [] yPoints = {25, 38, 62, 75, 62, 38};
        gc.fillPolygon(xPoints, yPoints, 6);
        gc.strokePolygon(xPoints, yPoints, 6);
        gc.setFill(Color.ORANGERED);
        gc.setFont(Font.font("Tahoma", FontWeight.NORMAL, 33));
        gc.fillText("Menu", 75, 60);
        
        
        box.getChildren().addAll(c, grid);
        //grid.add ();
        //g.add(new Label("Menu"), 1,1);
        //g.add(nouv, 1,2);
        //g.add(charg, 1,3);
        
    }
    
    public static void goFin(String gagnant){
        
        
    }
    
    public static void infoPartie(Joueur j1, Joueur j2, int nbManche, int joueur){
        
    }
    
    public static void initChoix(ChoiceBox cb, int c){

    }
}
