/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Vue;


import Controleur.Bouton;
import Controleur.Souris;
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
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.event.*;
import javafx.geometry.Insets;
import javafx.geometry.Orientation;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.Separator;
import javafx.scene.control.Slider;
import javafx.scene.control.TextField;
import javafx.scene.effect.BlurType;
import javafx.scene.effect.DropShadow;
import javafx.scene.effect.GaussianBlur;
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
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javax.imageio.ImageIO;

/**
 *
 * @author grandmax
 */
public class Interface extends Application{
    
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
    public  void start(Stage stage) throws Exception {


        stage.setTitle("Ruche");
        if (fullScreen) {
            s = new Scene(root);
            stage.setFullScreen(true);
        } else {
            s = new Scene(root, 800, 600);
        }
        stage.setScene(s);
        goMenu();
        stage.show();
    }

    public static void creer(String[] args, Arbitre a) {
        root = new BorderPane();
        arbitre = a;
        launch(args);
        
    }
    
    public static Canvas title() {
        Canvas c = new Canvas(200, 100);
        GraphicsContext gc;
        gc = c.getGraphicsContext2D();
        gc.setFill(Color.ORANGE);
        gc.setStroke(Color.WHEAT);
        double [][] coords = hex_corner(100, 50, 50);
        gc.fillPolygon(coords[0], coords[1], 6);
        gc.strokePolygon(coords[0], coords[1], 6);
        gc.setFill(Color.ORANGERED);
        gc.setFont(Font.font("Tahoma", FontWeight.NORMAL, 33));
        gc.fillText("Hive", 70, 60);
        return c;
    }
    
    public static Canvas titleSect (String name) {
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
    
    public static void goPartie() {
        arbitre.charger("test");
        root.setBottom(new Pane());
        Canvas c = new Canvas (500, 500);
        Pane stack = new Pane(c);
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
        
        
        btPrec.setOnAction(new Bouton(Bouton.BOUTON_UNDO, arbitre));
        btSuiv.setOnAction(new Bouton(Bouton.BOUTON_DO, arbitre));
        btSave.setOnAction(new Bouton (Bouton.BOUTON_SAUVEGARDER, arbitre));
        btMenu.setOnAction(new Bouton(Bouton.BOUTON_MENU, arbitre));
        
        
        GridPane bPion = new GridPane();
        bPion.setHgap(10);
        Separator sep = new Separator();
        sep.setOrientation(Orientation.VERTICAL);
        Canvas cj1 = new Canvas(75, 375);
        Canvas cj2 = new Canvas(75, 375);
        bPion.add(cj1, 0, 0);
        bPion.add(sep, 1, 0);
        bPion.add(cj2, 2, 0);
        root.setRight(bPion);
        
        box.getChildren().addAll(btPrec, btSuiv, btSave, btMenu);
        
        c.setOnMouseMoved(new Souris(arbitre, Souris.SOURIS_BOUGEE,c));
        
        Animation anim = new Animation(arbitre, c, cj1, cj2);
        anim.start();

    }
    
    public static double py (double a) {
        return Math.sqrt(Math.pow(a, 2) - Math.pow(a/2, 2));
    }
    
    public static void goMenu () {
        root.setRight(new Pane());
        VBox topBox = new VBox();
        topBox.setAlignment(Pos.TOP_CENTER);
        topBox.setPadding(new Insets(20,10,20,10));
        topBox.setSpacing(10);
        topBox.getChildren().addAll(title(), titleSect("Accueil"));
        root.setTop(topBox);
        
        StackPane leftStack = new StackPane();
        Rectangle leftRect = new Rectangle();
        leftRect.widthProperty().bind(leftStack.widthProperty());
        leftRect.heightProperty().bind(leftStack.heightProperty());
        leftRect.setFill(Color.WHITESMOKE);
        leftRect.setArcWidth(20);
        leftRect.setArcHeight(20);
        DropShadow shadow = new DropShadow();
        leftRect.setEffect(shadow);
        VBox leftBox = new VBox();
        leftBox.setPadding(new Insets(70,30,70,30));
        leftBox.setSpacing(30);
        VBox box = new VBox();
        box.setPadding(new Insets(20,20,20,20));
        box.getChildren().add(leftStack);
        leftStack.getChildren().addAll(leftRect, leftBox);
        
        Button btNG = new Button("Nouvelle partie");
        Button btLD = new Button("Charger partie");
        Button btCFG = new Button("Configuration");
        Button btQUIT = new Button("Quitter");
        
        btNG.setMinWidth(150);
        btLD.setMinWidth(150);
        btCFG.setMinWidth(150);
        btQUIT.setMinWidth(150);
        
        btNG.setOnAction(new Bouton(Bouton.BOUTON_NOUVELLE_PARTIE, arbitre));
        btLD.setOnAction(new Bouton(Bouton.BOUTON_CHARGER, arbitre));
        btCFG.setOnAction(new Bouton(Bouton.BOUTON_CONFIG, arbitre));
        btQUIT.setOnAction(new Bouton(Bouton.BOUTON_QUITTER, arbitre));
        
        leftBox.getChildren().addAll(btNG, btLD, btCFG, btQUIT);
        
        
        root.setLeft(box);
        goNewGame();
    }
    
    public static void goNewGame() {
        VBox centerBox = new VBox();
        StackPane centerStack = new StackPane();
        GridPane centerGrid = new GridPane();
        VBox insideBox = new VBox();
        Rectangle centerRect = new Rectangle();
        centerBox.setPadding(new Insets(20,20,20,0));
        centerBox.setAlignment(Pos.TOP_CENTER);
        centerGrid.setHgap(10);
        centerGrid.setVgap(10);
        centerRect.widthProperty().bind(centerStack.widthProperty());
        centerRect.heightProperty().bind(centerStack.heightProperty());
        centerRect.setArcWidth(20);
        centerRect.setArcHeight(20);
        centerRect.setFill(Color.WHITESMOKE);
        insideBox.setPadding(new Insets(70,30,70,30));
        insideBox.setSpacing(30);
        insideBox.setAlignment(Pos.CENTER);
        DropShadow shadow = new DropShadow();
        centerRect.setEffect(shadow);
        
        ChoiceBox cbMOD = new ChoiceBox(FXCollections.observableArrayList("Contre IA", "Contre Joueur", "En Ligne"));
        ChoiceBox cbDIFF = new ChoiceBox(FXCollections.observableArrayList("Facile", "Moyen", "Difficile"));
        
        cbMOD.getSelectionModel().selectFirst();
        cbDIFF.getSelectionModel().selectFirst();
        
        cbMOD.setMinWidth(200);
        cbDIFF.setMinWidth(200);
        
        TextField tfJ1 = new TextField();
        tfJ1.setPromptText("Nom joueur 1");
        
        TextField tfJ2 = new TextField();
        tfJ2.setPromptText("Nom joueur 2");
        
        tfJ2.setDisable(true);
        
        cbMOD.getSelectionModel().selectedIndexProperty().addListener(new ChangeListener<Number> () {
            
            @Override
            public void changed (ObservableValue ov, Number value, Number newValue) {
                if (newValue.intValue() >= 1) {
                    cbDIFF.setDisable(true);
                    tfJ2.setDisable(false);
                } else {
                    cbDIFF.setDisable(false);
                    tfJ2.setDisable(true);
                }
            }
        });
        

        
        centerGrid.add(cbMOD, 0, 0);
        centerGrid.add(cbDIFF, 2, 0);
        centerGrid.add(tfJ1, 0, 2);
        centerGrid.add(tfJ2, 2, 2);
        centerGrid.setAlignment(Pos.CENTER);
        
        Button btBEG = new Button("Commencer");
        
        btBEG.setMinWidth(150);
        
        btBEG.setOnAction(new Bouton(Bouton.BOUTON_NOUVELLE_PARTIE_COMMENCER, arbitre));

        
        centerBox.getChildren().add(centerStack);
        centerStack.getChildren().addAll(centerRect, insideBox);//centerGrid);
        Label lNG = new Label("Nouvelle Partie");
        lNG.setFont(new Font(22));
        
        insideBox.getChildren().addAll(lNG, centerGrid, btBEG);
        
        
        
        
        root.setCenter(centerBox);
    }
    
    public static void goLoadGame () {
        VBox centerBox = new VBox();
        StackPane centerStack = new StackPane();
        GridPane centerGrid = new GridPane();
        VBox insideBox = new VBox();
        Rectangle centerRect = new Rectangle();
        centerBox.setPadding(new Insets(20,20,20,0));
        centerBox.setAlignment(Pos.TOP_CENTER);
        centerGrid.setHgap(10);
        centerGrid.setVgap(10);
        centerRect.widthProperty().bind(centerStack.widthProperty());
        centerRect.heightProperty().bind(centerStack.heightProperty());
        centerRect.setArcWidth(20);
        centerRect.setArcHeight(20);
        centerRect.setFill(Color.WHITESMOKE);
        insideBox.setPadding(new Insets(70,30,70,30));
        insideBox.setSpacing(30);
        insideBox.setAlignment(Pos.CENTER);
        DropShadow shadow = new DropShadow();
        centerRect.setEffect(shadow);
        
        centerGrid.setAlignment(Pos.CENTER);
        
        Button btBEG = new Button("Commencer");
        
        btBEG.setMinWidth(150);
        
        btBEG.setOnAction(new Bouton(Bouton.BOUTON_NOUVELLE_PARTIE_COMMENCER, arbitre));

        
        centerBox.getChildren().add(centerStack);
        centerStack.getChildren().addAll(centerRect, insideBox);//centerGrid);
        Label lNG = new Label("Charger Partie");
        lNG.setFont(new Font(22));
        
        insideBox.getChildren().addAll(lNG, centerGrid, btBEG);
        
        root.setCenter(centerBox);
    }
   
    
    public static void goM1nu(){
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
        
        Button btTest = new Button("Test");
        
        GaussianBlur gaussianBlur = new GaussianBlur() ;       
        gaussianBlur.setRadius(0) ; 
        
        root.setEffect(gaussianBlur);

        
        btTest.setOnAction(new EventHandler<ActionEvent> () {
            @Override
            public void handle (ActionEvent e) {
                gaussianBlur.setRadius(10);
            }
        });
        
        btNouveau.setMaxWidth(150);
        btCharger.setMaxWidth(150);
        btConfig.setMaxWidth(150);
        btCredits.setMaxWidth(150);
        
        btNouveau.setOnAction(new Bouton(Bouton.BOUTON_NOUVELLE_PARTIE, arbitre));
        btCharger.setOnAction(new Bouton(Bouton.BOUTON_CHARGER, arbitre));
        btConfig.setOnAction(new Bouton(Bouton.BOUTON_CONFIG, arbitre));
        btCredits.setOnAction(new Bouton(Bouton.BOUTON_CREDITS, arbitre));
        btQuit.setOnAction(new Bouton(Bouton.BOUTON_QUITTER, arbitre));

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
        
        Canvas c = titleSect("Menu");
        
        box.getChildren().addAll(c, grid, btQuit, btTest);
        
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
        VBox centerBox = new VBox();
        StackPane centerStack = new StackPane();
        GridPane centerGrid = new GridPane();
        VBox insideBox = new VBox();
        Rectangle centerRect = new Rectangle();
        centerBox.setPadding(new Insets(20,20,20,0));
        centerBox.setAlignment(Pos.TOP_CENTER);
        centerGrid.setHgap(10);
        centerGrid.setVgap(10);
        centerRect.widthProperty().bind(centerStack.widthProperty());
        centerRect.heightProperty().bind(centerStack.heightProperty());
        centerRect.setArcWidth(20);
        centerRect.setArcHeight(20);
        centerRect.setFill(Color.WHITESMOKE);
        insideBox.setPadding(new Insets(70,30,70,30));
        insideBox.setSpacing(30);
        insideBox.setAlignment(Pos.CENTER);
        DropShadow shadow = new DropShadow();
        centerRect.setEffect(shadow);
        
        centerGrid.setAlignment(Pos.CENTER);
        
        Slider sSon = new Slider();
        sSon.setValue(50); 
        sSon.setShowTickMarks(true); 
        sSon.setMajorTickUnit(20); 
        
        Slider sMusique = new Slider();
        sMusique.setValue(50); 
        sMusique.setShowTickMarks(true); 
        sMusique.setMajorTickUnit(20);
        
        CheckBox cFC = new CheckBox();
        
        Label lSon = new Label("Son");
        Label lMusique = new Label("Musique");
        Label lFullScreen = new Label("Plein Ecran");
        
        centerBox.getChildren().add(centerStack);
        centerStack.getChildren().addAll(centerRect, insideBox);//centerGrid);
        Label lNG = new Label("Parametres");
        lNG.setFont(new Font(22));
        
        centerGrid.add(lSon, 0, 0);
        centerGrid.add(lMusique, 0,1);
        centerGrid.add(lFullScreen, 0,2);
        centerGrid.add(sSon, 1,0);
        centerGrid.add(sMusique,1,1);
        centerGrid.add(cFC,1,2);
        
        insideBox.getChildren().addAll(lNG, centerGrid);
        
        root.setCenter(centerBox);
    }
    
    public static void goConf1g () {
        
        BorderPane border = new BorderPane();
        VBox centerBox = new VBox();
        centerBox.setPadding(new Insets(20,10,20,10));
        centerBox.setSpacing(20);
        //root.setCenter(border);
        root.setCenter(centerBox);
        
        //border.setTop(titreSect("Config"));
        Canvas title = titleSect("Config");
        
        VBox leftBox = new VBox();
        leftBox.setPadding(new Insets(70,20,70,20));
        leftBox.setSpacing(40);
        
        Button btAff = new Button("Affichage");
        Button btSon = new Button ("Son");
        Button btJeu = new Button ("Jeu");
        Button btMenu = new Button ("Menu");
        
        btMenu.setOnAction(new Bouton(Bouton.BOUTON_MENU, arbitre));
        
        btAff.setMinWidth(100);
        btSon.setMinWidth(100);
        btJeu.setMinWidth(100);
        btMenu.setMinWidth(100);
        
        leftBox.getChildren().addAll(btAff, btSon, btJeu, btMenu);
        
        StackPane leftStack = new StackPane();
        Rectangle leftRect = new Rectangle();
        leftRect.setFill(Color.WHITESMOKE);
        leftRect.widthProperty().bind(leftStack.widthProperty());
        leftRect.heightProperty().bind(leftStack.heightProperty());
        leftRect.setArcWidth(20);
        leftRect.setArcHeight(20);
        leftRect.setEffect(new DropShadow());
        leftStack.getChildren().addAll(leftRect, leftBox);
        
        border.setLeft(leftStack);
        centerBox.getChildren().addAll(title, border);
        centerBox.setAlignment(Pos.TOP_CENTER);
        
        //leftRect.onmo
        
        /*
        VBox box = new VBox();
        box.setAlignment(Pos.TOP_CENTER);
        box.setSpacing(30);
        root.setCenter(box);
        
        Canvas c = titreSect("Config");
        
        StackPane stack = new StackPane();
        final Color shadowColor = Color.BLACK.deriveColor(0, 0, 0, 0.5); 
        final DropShadow dropShadow = new DropShadow(BlurType.THREE_PASS_BOX, shadowColor, 20, 0, 10, 10); 
        final Rectangle rectangle = new Rectangle(100,100); 
        rectangle.widthProperty().bind(stack.widthProperty().subtract(0));
        rectangle.heightProperty().bind(stack.heightProperty());
        rectangle.setFill(Color.RED); 
        rectangle.setEffect(dropShadow); 
        Button b1 = new Button("un bouton");
        Button b2 = new Button("un autre bouton");
        VBox v = new VBox();
        v.getChildren().addAll(b1, b2);
        stack.getChildren().addAll(rectangle, v);
        box.getChildren().addAll(c);
        root.setLeft(stack);
        */
        /*
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
        
        btRetour.setOnAction(new Bouton(Bouton.BOUTON_MENU, arbitre));
        
        Separator sep = new Separator();
        sep.setOrientation(Orientation.VERTICAL);
        bottom.getChildren().addAll(btValider,sep, btRetour);
        
        box.getChildren().addAll(c, grid, bottom);
        */
        /*
        final Color shadowColor = Color.BLACK.deriveColor(0, 0, 0, 0.5); 
        final DropShadow dropShadow = new DropShadow(BlurType.THREE_PASS_BOX, shadowColor, 20, 0, 10, 10); 
        final Rectangle rectangle = new Rectangle(100, 100, 150, 100); 
        rectangle.setFill(Color.RED); 
        rectangle.setEffect(dropShadow); 
        */
        
    }
    
    public static void goAffichage() {
        
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
        Canvas c = titleSect("Credits");
        
        Label lMoteur = new Label("Moteur");
        Label lIA = new Label("IA");
        Label lIHM = new Label("IHM");
        Label lMax = new Label("Maxence Grand");
        Label lNarek = new Label("Narek Davtyan");
        Label lAme = new Label ("Amelina Douard");
        Label lLies = new Label ("Lies Hadjadj");
        Label lLu = new Label("Lucie Muller");
        Label lEnzo = new Label("Enzo Brignon");
        
        Button btRetour = new Button("Retour");
        
        btRetour.setOnAction(new Bouton(Bouton.BOUTON_MENU, arbitre));

        grid.add(lMoteur, 1, 0);
        grid.add(lMax, 0, 1);
        grid.add(lNarek, 2, 1);
        grid.add(lIA, 1, 2);
        grid.add(lAme, 0, 3);
        grid.add(lLies, 2, 3);
        grid.add(lIHM, 1, 4);
        grid.add(lEnzo, 0, 5);
        grid.add(lLu, 2, 5);
        
        box.getChildren().addAll(c, grid, btRetour);
        
        root.setCenter(box);
        root.setRight(new Pane());
        root.setLeft(new Pane());
        root.setBottom(new Pane());
        
    }
    
    
    public static void infoPartie(Joueur j1, Joueur j2, int nbManche, int joueur){
        
    }
    
    
    public static void initChoix(ChoiceBox cb, int c){

    }
}
