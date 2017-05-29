/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Vue;

import javafx.stage.Popup;
import Controleur.Bouton;
import Controleur.BoutonCommencer;
import Controleur.Choix;
import Controleur.NewGameHandler;
import Controleur.SoundSlider;
import Controleur.Souris;
import Controleur.SourisListe;
import javafx.application.*;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.layout.BorderPane;
import Modele.Arbitres.*;
import Joueurs.Joueur;
import Joueurs.Ordinateur;
import Modele.Chargeur;
import Modele.Insecte;
import Modele.Point;
import Son.SoundEngine;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.time.Duration;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.Queue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.animation.RotateTransition;
import static javafx.application.Application.launch;
import javafx.beans.binding.Bindings;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.LongProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleLongProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.*;
import javafx.geometry.Insets;
import javafx.geometry.Orientation;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonBar;
import javafx.scene.control.ButtonBar.ButtonData;
import javafx.scene.control.ButtonType;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Dialog;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.Separator;
import javafx.scene.control.Slider;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.control.TextField;
import javafx.scene.control.TextInputDialog;
import javafx.scene.effect.BlurType;
import javafx.scene.effect.DropShadow;
import javafx.scene.effect.GaussianBlur;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import static javafx.scene.input.DataFormat.URL;
import javafx.scene.input.MouseEvent;
import javafx.scene.input.SwipeEvent;
import javafx.scene.input.TouchEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
//import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
//import java.net.URL;
//import javafx.scene.input.DataFormat.URL;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.Region;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.TilePane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Modality;
import javafx.stage.StageStyle;
import javafx.util.Callback;
import javax.imageio.ImageIO;

/**
 *
 * @author brignone
 */
public class Interface extends Application {

    /**
     *
     */
    public final static int SOURIS_PRESSEE = 0;

    /**
     *
     */
    public final static int SOURIS_BOUGEE = 1;

    /**
     *
     */
    public final static int CHOIX_MODE = 0;

    /**
     *
     */
    public final static int CHOIX_DIFFICULTE = 1;

    /**
     *
     */
    public final static int CHOIX_PLATEAU = 2;

    static Pointeur pointeur;
    static Arbitre arbitre;
    static BorderPane root;
    static Scene scene;
    static boolean fullScreen = false;
    final static boolean soundEnabled = true;
    static VBox ngBox;
    static VBox loadBox;
    static VBox configBox;
    static VBox reseauBox;
    static VBox didacBox;
    static VBox crBox;
    static TabPane tabPane;
    static String[] args2;
    public static Stage stage;
    static Stage dialogConn;
    static Animation anim;
    static int colorP1;
    static int colorP2;
    /**
     *
     * @param stage
     * @throws Exception
     */
    @Override
    public void start(Stage stage) throws Exception {
        this.stage = stage;
        stage.setTitle("Ruche");
        if (fullScreen) {
            scene = new Scene(root);
            stage.setFullScreen(true);
        } else {
            scene = new Scene(root, 1000, 850);
        }
        stage.setScene(scene);
        try {
            if (soundEnabled)
                (SoundEngine.getInstance()).play();
            else 
                System.err.println("Interface.start() - Warning - Sound is disabled");
        } catch (RuntimeException e) {
            System.err.println("Interface.start() - Error while geting SoundEngine - " + e.getMessage());
        }
        //goMenu();
        goNewGame();
        FabriqueArbitre.initChargeur();
        goConfig();
        goReseau();   
        goDidacticiel();
        goCredits();
        goTest(); 
        //goPartie();
        stage.show();
    }

    /**
     *
     * @param args
     * @param a
     */
    public static void creer(String[] args) {
        root = new BorderPane();
        root.getChildren().add(new ImageView(new Image(ClassLoader.getSystemClassLoader().getResourceAsStream("Images/fond.jpg"))));
        FabriqueArbitre.initType(FabriqueArbitre.LOCAL_JVJ);
        //dialogConn = new Dialog<>();
        launch(args);

    }
    
    public static void goTest () {
        FabriqueArbitre.init();
        Rectangle rleft = new Rectangle(100,100);
        rleft.widthProperty().bind(scene.widthProperty().divide(10));
        rleft.setOpacity(0);
        Pane left = new Pane(rleft);
        root.setLeft(left);
        Rectangle rright = new Rectangle(100,100);
        rright.widthProperty().bind(scene.widthProperty().divide(10));
        rright.setOpacity(0);
        Pane right = new Pane(rright);
        root.setRight(right);
        VBox topBox = new VBox();
        topBox.setAlignment(Pos.TOP_CENTER);
        Insets i = new Insets (20,10,20,10);
        
        topBox.setPadding(new Insets(20, 10, 20, 10));
        topBox.setSpacing(10);
        topBox.getChildren().addAll(title());
        root.setTop(topBox);
        final Tab tabNG = new Tab("New Game"); 
        tabNG.setContent(ngBox);
        tabNG.setClosable(false);
        final Tab tabLD = new Tab("Load Game"); 
        tabLD.setClosable(false);
        tabLD.setContent(loadBox);
        final Tab tabCFG = new Tab("Preferences");
        tabCFG.setClosable(false);
        tabCFG.setContent(configBox);
        final Tab tabRes = new Tab("Partie en ligne");
        tabRes.setClosable(false);
        tabRes.setContent(reseauBox);
        final Tab tabDic = new Tab("Didacticiel");
        tabDic.setClosable(false);
        tabDic.setContent(didacBox);
        final Tab tabCredit = new Tab("Credits");
        tabCredit.setClosable(false);
        tabCredit.setContent(crBox);
        tabPane = new TabPane(); 
        tabPane.getTabs().setAll(tabNG,tabRes , tabLD, tabCFG, tabDic, tabCredit);
        tabPane.setPadding(new Insets(0, 20, 0, 20));
        
        //tabPane
        tabPane.getStylesheets().add("Style/Style.css");
        root.setCenter(tabPane);
        VBox bottom = new VBox();
        bottom.setAlignment(Pos.CENTER);
        bottom.setPadding(new Insets(20,20,20,20));
        Button btClose = new Button ();
        btClose.setOnAction(new EventHandler<ActionEvent> () {
            @Override
            public void handle(ActionEvent event) {
                System.exit(0);
            }
        });
        
        btClose.setBackground(new Background(new BackgroundFill(new ImagePattern(new Image(ClassLoader.getSystemClassLoader().getResourceAsStream("Images/Icone/close.png"))), CornerRadii.EMPTY, Insets.EMPTY)));
        btClose.setMinWidth(50);
        btClose.setMinHeight(50);
        
        bottom.getChildren().add(btClose);
        root.setBottom(bottom);
    }

    /**
     *
     * @return
     */
    public static Canvas title() {
        Canvas c = new Canvas(200, 100);
        GraphicsContext gc;
        gc = c.getGraphicsContext2D();
        gc.setFill(Color.ORANGE);
        gc.setStroke(Color.WHEAT);
        double[][] coords = hex_corner(100, 50, 50);
        gc.fillPolygon(coords[0], coords[1], 6);
        gc.strokePolygon(coords[0], coords[1], 6);
        gc.setFill(Color.ORANGERED);
        gc.setFont(Font.font("Tahoma", FontWeight.NORMAL, 33));
        gc.fillText("Hive", 70, 60);
        return c;
    }

    /**
     *
     * @param name
     * @return
     */
    public static Canvas titleSect(String name) {
        Canvas c = new Canvas(200, 100);
        GraphicsContext gc;
        gc = c.getGraphicsContext2D();
        gc.setFill(Color.ORANGE);
        gc.setStroke(Color.WHEAT);
        double[][] coords = hex_corner(100, 50, 25);
        gc.fillPolygon(coords[0], coords[1], 6);
        gc.strokePolygon(coords[0], coords[1], 6);
        gc.setFill(Color.ORANGERED);
        gc.setFont(Font.font("Tahoma", FontWeight.NORMAL, 33));
        gc.fillText(name, coords[0][3], coords[1][2] - 1);
        return c;
    }

    public static Pointeur pointeur(){
        return pointeur;
    }
    /**
     *
     */
    public static void goPartie() {
        //root.setBottom(new Pane());
        Canvas c = new Canvas(500, 500);
        pointeur = new Pointeur(c, arbitre);
        Pane stack = new Pane(c);
        root.setCenter(stack);

        c.widthProperty().bind(stack.widthProperty());
        c.heightProperty().bind(stack.heightProperty().subtract(50));

        HBox box = new HBox();
        box.setAlignment(Pos.TOP_CENTER);
        box.setPadding(new Insets(0, 10, 20, 10));
        box.setSpacing(20);
        root.setBottom(box);
        Image imageUndo = new Image(ClassLoader.getSystemClassLoader().getResourceAsStream("Images/Icone/left.png"));
        Button btPrec = new Button();
        btPrec.setBackground(new Background(new BackgroundFill(new ImagePattern(new Image(ClassLoader.getSystemClassLoader().getResourceAsStream("Images/Icone/left.png"))), CornerRadii.EMPTY, Insets.EMPTY)));
        btPrec.setMinWidth(50);
        btPrec.setMinHeight(50);
        Image imageDo = new Image(ClassLoader.getSystemClassLoader().getResourceAsStream("Images/Icone/droite.png"));
        Button btSuiv = new Button();
        btSuiv.setBackground(new Background(new BackgroundFill(new ImagePattern(new Image(ClassLoader.getSystemClassLoader().getResourceAsStream("Images/Icone/droite.png"))), CornerRadii.EMPTY, Insets.EMPTY)));
        btSuiv.setMinWidth(50);
        btSuiv.setMinHeight(50);
        Image imagePause = new Image(ClassLoader.getSystemClassLoader().getResourceAsStream("Images/Icone/play.png"));
        Button btPause = new Button();

        btPause.setBackground(new Background(new BackgroundFill(new ImagePattern(new Image(ClassLoader.getSystemClassLoader().getResourceAsStream("Images/Icone/menu.png"))), CornerRadii.EMPTY, Insets.EMPTY)));
        btPause.setMinWidth(50);
        btPause.setMinHeight(50);
        
        Button btSave = new Button("Sauvegarder");
        Button btMenu = new Button("Menu principal");
        btPrec.setMaxWidth(150);
        btSuiv.setMaxWidth(150);
        btSave.setMaxWidth(150);
        btMenu.setMaxWidth(150);

        btPrec.setOnAction(new Bouton(Bouton.BOUTON_UNDO, arbitre));
        btSuiv.setOnAction(new Bouton(Bouton.BOUTON_DO, arbitre));
        btSave.setOnAction(new Bouton(Bouton.BOUTON_SAUVEGARDER, arbitre));
        btMenu.setOnAction(new Bouton(Bouton.BOUTON_MENU, arbitre));
        btPause.setOnAction(new Bouton(Bouton.BOUTON_PAUSE, arbitre));
        
        Button btHelp = new Button();
        btHelp.setOnAction(new Bouton(Bouton.BOUTON_AIDE, arbitre));
        btHelp.setBackground(new Background(new BackgroundFill(new ImagePattern(new Image(ClassLoader.getSystemClassLoader().getResourceAsStream("Images/Icone/help.png"))), CornerRadii.EMPTY, Insets.EMPTY)));
        btHelp.setMinWidth(50);
        btHelp.setMinHeight(50);

        GridPane bPion = new GridPane();
        bPion.setHgap(10);
        Separator sep = new Separator();
        sep.setOrientation(Orientation.VERTICAL);
        Canvas cj1 = new Canvas(75, 375);
        Canvas cj2 = new Canvas(75, 375);
        bPion.add(cj1, 0, 0);
        bPion.add(sep, 1, 0);
        bPion.add(cj2, 2, 0);
        //root.setRight(bPion);
        PaneToken pt = PaneToken.getInstance(arbitre);
        //PaneToken pt = new PaneToken(arbitre);
        pt.initialize();
        root.setRight(pt.getLeftPane());
        root.setLeft(pt.getRightPane());

        if(arbitre instanceof ArbitreReseau)
            box.getChildren().addAll(btPause, Chat.creer((ArbitreReseau)arbitre,arbitre.joueur(Arbitre.J1).nom() , stage));
        else
            box.getChildren().addAll(btPrec, btPause, btHelp, btSuiv/*, btSave, btMenu*/);

        c.setOnMouseMoved(new Souris(arbitre, Souris.SOURIS_BOUGEE, c));
        c.setOnMouseClicked(new Souris(arbitre, Souris.SOURIS_CLIQUEE, c));

      //  if(anim==null)
            anim = new Animation(arbitre, c, cj1, cj2);
        
        anim.start();
        

    }

    /**
     *
     * @param a
     * @return
     */
    public static double py(double a) {
        return Math.sqrt(Math.pow(a, 2) - Math.pow(a / 2, 2));
    }
    //static int i = 10;
    static IntegerProperty i = new SimpleIntegerProperty(10);

    /**
     *
     */
    public static void goMenu() {
        root.setRight(new Pane());
        root.setBottom(new Pane());
        VBox topBox = new VBox();
        topBox.setAlignment(Pos.TOP_CENTER);
        topBox.setPadding(new Insets(20, 10, 20, 10));
        topBox.setSpacing(10);
        topBox.getChildren().addAll(title());
        root.setTop(topBox);

        StackPane leftStack = new StackPane();
        Rectangle leftRect = new Rectangle();
        leftRect.setOpacity(0.25);
        leftRect.widthProperty().bind(leftStack.widthProperty());
        leftRect.heightProperty().bind(leftStack.heightProperty());
        leftRect.setFill(Color.BLACK);
        leftRect.setArcWidth(20);
        leftRect.setArcHeight(20);
        DropShadow shadow = new DropShadow();
        leftRect.setEffect(shadow);
        VBox leftBox = new VBox();
        leftBox.setPadding(new Insets(70, 30, 70, 30));
        leftBox.setSpacing(30);
        VBox box = new VBox();
        box.setPadding(new Insets(20, 20, 20, 20));
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

    /**
     *
     */
    public static void goNewGame() {
        VBox centerBox = new VBox();
        StackPane centerStack = new StackPane();
        GridPane centerGrid = new GridPane();
        VBox insideBox = new VBox();
        Rectangle centerRect = new Rectangle();
        VBox rectBox = new VBox();
        rectBox.setAlignment(Pos.CENTER);
        centerBox.setPadding(new Insets(0, 0, 20, 0));
        rectBox.getChildren().add(centerRect);
        centerRect.setOpacity(0.25);
        centerBox.setPadding(new Insets(0, 0, 20, 0));
        centerBox.setAlignment(Pos.TOP_CENTER);
        centerGrid.setHgap(10);
        centerGrid.setVgap(10);
        centerRect.widthProperty().bind(insideBox.widthProperty());
        centerRect.heightProperty().bind(insideBox.heightProperty());
        centerRect.setArcWidth(20);
        centerRect.setArcHeight(20);
        centerRect.setFill(Color.BLACK);
        insideBox.setPadding(new Insets(70, 30, 70, 30));
        insideBox.setSpacing(30);
        insideBox.setAlignment(Pos.CENTER);
        DropShadow shadow = new DropShadow();
        centerRect.setEffect(shadow);

        ChoiceBox cbJ1 = new ChoiceBox();
        ChoiceBox cbJ2 = new ChoiceBox();
        
        cbJ1.getItems().addAll((Object[]) NewGameHandler.DIFFICULTEES);
        cbJ2.getItems().addAll((Object[]) NewGameHandler.DIFFICULTEES);
        
        cbJ1.getSelectionModel().select(0);
        cbJ2.getSelectionModel().select(0);
        
        cbJ1.setMaxWidth(500);
        cbJ2.setMaxWidth(500);
        
        TextField tfJ1 = new TextField();
        TextField tfJ2 = new TextField();
        
        tfJ1.setPromptText("Joueur 1");
        tfJ2.setPromptText("Joueur 2");
        
        cbJ1.setOnAction(new EventHandler<ActionEvent> () {
            @Override
            public void handle(ActionEvent event) {
                if (cbJ1.getSelectionModel().getSelectedIndex() > 0)
                    tfJ1.setDisable(true);
                else
                    tfJ1.setDisable(false);
            }
            
        });
        
        cbJ2.setOnAction(new EventHandler<ActionEvent> () {
            @Override
            public void handle(ActionEvent event) {
                if (cbJ2.getSelectionModel().getSelectedIndex() > 0)
                    tfJ2.setDisable(true);
                else
                    tfJ2.setDisable(false);
            }
            
        });
        
        
        // Pour ajouter les boutons de couleur
        ColorChoice cc = ColorChoice.getInstance();

        centerGrid.add(cbJ1, 0, 0);
        centerGrid.add(cbJ2, 2, 0);
        centerGrid.add(tfJ1, 0, 2);
        centerGrid.add(tfJ2, 2, 2);
        // pour mettre les gridpane dans le menu
        centerGrid.add(cc.getPlayer2(), 0, 4);
        centerGrid.add(cc.getPlayer1(), 2, 4);
        centerGrid.setAlignment(Pos.CENTER);

        Button btBEG = new Button("Commencer");

        btBEG.setMinWidth(150);

        //btBEG.setOnAction(new BoutonCommencer(tfJ1, tfJ2, cbJ1, cbJ2 fabrique));

        btBEG.setOnAction(new NewGameHandler (cbJ1, cbJ2, tfJ1, tfJ2));
        
        centerBox.getChildren().add(centerStack);
        centerStack.getChildren().addAll(rectBox, insideBox);//centerGrid);
        Label lNG = new Label("Nouvelle Partie");
        lNG.setTextFill(Color.WHITE);
        lNG.setFont(new Font(22));
        
        Label lTest = new Label ();
        lTest.setMaxWidth(300);
        lTest.setWrapText(true);
        
        insideBox.getChildren().addAll(lNG, centerGrid, btBEG, lTest);

        //root.setCenter(centerBox);
        ngBox = centerBox;
    }
    
    public static void goReseau () {
        VBox centerBox = new VBox();
        StackPane centerStack = new StackPane();
        GridPane centerGrid = new GridPane();
        VBox insideBox = new VBox();
        Rectangle centerRect = new Rectangle();
        VBox rectBox = new VBox();
        rectBox.setAlignment(Pos.CENTER);
        centerBox.setPadding(new Insets(0, 0, 20, 0));
        rectBox.getChildren().add(centerRect);
        centerRect.setOpacity(0.25);
        centerBox.setPadding(new Insets(0, 0, 20, 0));
        centerBox.setAlignment(Pos.TOP_CENTER);
        centerGrid.setHgap(10);
        centerGrid.setVgap(10);
        centerRect.widthProperty().bind(insideBox.widthProperty());
        centerRect.heightProperty().bind(insideBox.heightProperty());
        centerRect.setArcWidth(20);
        centerRect.setArcHeight(20);
        centerRect.setFill(Color.BLACK);
        insideBox.setPadding(new Insets(70, 30, 70, 30));
        insideBox.setSpacing(30);
        insideBox.setAlignment(Pos.CENTER);
        DropShadow shadow = new DropShadow();
        centerRect.setEffect(shadow);

        

        TextField tfJ1 = new TextField();
        tfJ1.setPromptText("Nom joueur 1");
        TextField tfJ2 = new TextField();
        tfJ2.setPromptText("Nom joueur 2");
        TextField host = new TextField();
        host.setPromptText("IP du joueur à rejoindre");

        host.setDisable(true);
        ChoiceBox cbJ1 = new ChoiceBox();
        
        String[] t = {"Héberger une partie en ligne", "Rejoindre un hôte"};
        cbJ1.getItems().addAll((Object[])t);
        cbJ1.setMaxWidth(500);
         cbJ1.getSelectionModel().select(0);
        
        cbJ1.setOnAction(new EventHandler<ActionEvent> () {
            @Override
            public void handle(ActionEvent event) {
                if (cbJ1.getSelectionModel().getSelectedIndex() > 0){
                    FabriqueArbitre.setInit(Choix.CHOIX_MODE,FabriqueArbitre.RESEAU_CLIENT);
                    host.setDisable(false);
                }
                else{
                    host.setDisable(true);
                    FabriqueArbitre.setInit(Choix.CHOIX_MODE,FabriqueArbitre.RESEAU_SERVER);
                }
            }
            
        });
        
        
        // Pour ajouter les boutons de couleur
        ColorChoice cc = ColorChoice.getInstance();
        
        centerGrid.add(cbJ1, 0, 2);
        centerGrid.add(tfJ1, 0, 4);
        centerGrid.add(host, 2, 4);
        // pour mettre les gridpane dans le menu
        /*centerGrid.add(cc.getPlayer2(), 0, 4);
        centerGrid.add(cc.getPlayer1(), 2, 4);
        */centerGrid.setAlignment(Pos.CENTER);

        Button btBEG = new Button("Commencer");

        btBEG.setMinWidth(150);

        btBEG.setOnAction(new NewGameHandler(cbJ1, null, tfJ1, host));

        centerBox.getChildren().add(centerStack);
        centerStack.getChildren().addAll(rectBox, insideBox);//centerGrid);
        Label lNG = new Label("Nouvelle Partie");
        lNG.setTextFill(Color.WHITE);
        lNG.setFont(new Font(22));
        
        insideBox.getChildren().addAll(lNG, centerGrid, btBEG);//, lTest);

        //root.setCenter(centerBox);
        reseauBox = centerBox;
    }

    /**
     *
     */
    public static void goLoadGame(String[] plateaux) {
        VBox centerBox = new VBox();
        StackPane centerStack = new StackPane();
        GridPane centerGrid = new GridPane();
        VBox insideBox = new VBox();
        Rectangle centerRect = new Rectangle();
        VBox rectBox = new VBox();
        rectBox.getChildren().add(centerRect);
        rectBox.setAlignment(Pos.CENTER);
        centerRect.setOpacity(0.25);
        centerBox.setPadding(new Insets(0, 0, 20, 0));
        centerBox.setAlignment(Pos.TOP_CENTER);
        centerGrid.setHgap(10);
        centerGrid.setVgap(10);
        centerRect.widthProperty().bind(insideBox.widthProperty());
        centerRect.heightProperty().bind(insideBox.heightProperty());
        centerRect.setArcWidth(20);
        centerRect.setArcHeight(20);
        centerRect.setFill(Color.BLACK);
        insideBox.setPadding(new Insets(70, 30, 70, 30));
        insideBox.setSpacing(30);
        insideBox.setAlignment(Pos.CENTER);
        DropShadow shadow = new DropShadow();
        centerRect.setEffect(shadow);

        centerGrid.setAlignment(Pos.CENTER);

        Button btBEG = new Button("Commencer");

        btBEG.setMinWidth(150);

        btBEG.setOnAction(new Bouton(Bouton.BOUTON_NOUVELLE_PARTIE_COMMENCER, arbitre));

        centerBox.getChildren().add(centerStack);
        centerStack.getChildren().addAll(rectBox, insideBox);//centerGrid);
        Label lNG = new Label("Charger Partie");
        lNG.setTextFill(Color.WHITE);
        lNG.setFont(new Font(22));
        ChoiceBox cbMOD = new ChoiceBox();
        ListView<String> list = new ListView<>();
        list.getItems().addAll(Arrays.asList(plateaux));
        list.setOnMouseClicked(new SourisListe( CHOIX_PLATEAU, list));
        
        list.setMaxWidth(500);
        list.setMinWidth(500);
        cbMOD.getSelectionModel().selectedIndexProperty().addListener(new Choix( Choix.CHOIX_PLATEAU));
        
        insideBox.getChildren().addAll(lNG, centerGrid, list, btBEG);

        //root.setCenter(centerBox);
        loadBox = centerBox;
    }

    public static void goDidacticiel(){
        VBox centerBox = new VBox();
        StackPane centerStack = new StackPane();
        GridPane centerGrid = new GridPane();
        VBox insideBox = new VBox();
        Rectangle centerRect = new Rectangle();
        VBox rectBox = new VBox();
        rectBox.getChildren().add(centerRect);
        rectBox.setAlignment(Pos.CENTER);
        centerRect.setOpacity(0.25);
        centerBox.setPadding(new Insets(0, 0, 20, 0));
        centerBox.setAlignment(Pos.TOP_CENTER);
        centerGrid.setHgap(10);
        centerGrid.setVgap(10);
        centerRect.widthProperty().bind(insideBox.widthProperty());
        centerRect.heightProperty().bind(insideBox.heightProperty());
        centerRect.setArcWidth(20);
        centerRect.setArcHeight(20);
        centerRect.setFill(Color.BLACK);
        insideBox.setPadding(new Insets(70, 30, 70, 30));
        insideBox.setSpacing(30);
        insideBox.setAlignment(Pos.CENTER);
        DropShadow shadow = new DropShadow();
        centerRect.setEffect(shadow);

        centerGrid.setAlignment(Pos.CENTER);
        
        Button prev = new Button();//("précédent");
        prev.setBackground(new Background(new BackgroundFill(new ImagePattern(new Image(ClassLoader.getSystemClassLoader().getResourceAsStream("Images/Icone/prec.png"))), CornerRadii.EMPTY, Insets.EMPTY)));
        
        Button btBEG = new Button();
        btBEG.setBackground(new Background(new BackgroundFill(new ImagePattern(new Image(ClassLoader.getSystemClassLoader().getResourceAsStream("Images/Icone/next.png"))), CornerRadii.EMPTY, Insets.EMPTY)));
        Slide s = new Slide();
        btBEG.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                if(!s.next()){
                    btBEG.setDisable(true);
                }
                prev.setDisable(false);
            }
        } );
        
        
        prev.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                if(!s.previous()){
                    prev.setDisable(true);
                }
                btBEG.setDisable(false);
            }
        } );
        
        prev.setDisable(true);
        final Region region = new Region(); 
        region.setStyle("-fx-background-color: gold; -fx-border-color: goldenrod;"); 
        region.setPrefSize(100, 100); 
        AnchorPane.setLeftAnchor(region, 10.0); 
        AnchorPane.setBottomAnchor(region, 10.0);
        
        btBEG.setMinWidth(90);
        btBEG.setMaxWidth(90);
        prev.setMinWidth(90);
        prev.setMaxWidth(90);

        centerBox.getChildren().add(centerStack);
        centerStack.getChildren().addAll(rectBox, insideBox);//centerGrid);
        Label lNG = new Label("Didacticiel");
        lNG.setTextFill(Color.WHITE);
        final AnchorPane root2 = new AnchorPane(); 
        root2.getChildren().setAll(btBEG, region);
        
        HBox buttons = new HBox();
        buttons.setAlignment(Pos.CENTER);
        buttons.getChildren().addAll(prev, btBEG);
        insideBox.getChildren().addAll(lNG,s.pane(), buttons);
        didacBox = centerBox;
    }
    /**
     *
     * @param x
     * @param y
     * @param rayon
     * @return
     */
    public static double[][] hex_corner(double x, double y, double rayon) {
        double[][] coords = new double[2][6];
        double angle_deg;
        double angle_rad;
        for (int i = 0; i < 6; i++) {
            angle_deg = 60 * i + 30;
            angle_rad = Math.PI / 180 * angle_deg;
            coords[0][i] = x + rayon * Math.cos(angle_rad);
            coords[1][i] = y + rayon * Math.sin(angle_rad);
        }
        return coords;
    }

    /**
     *
     * @param gagnant
     */
    public void goFin(String gagnant) {

    }

    /**
     *
     */
    public static void goConfig() {
        VBox centerBox = new VBox();
        StackPane centerStack = new StackPane();
        GridPane centerGrid = new GridPane();
        VBox insideBox = new VBox();
        Rectangle centerRect = new Rectangle();
        VBox rectBox = new VBox();
        rectBox.getChildren().add(centerRect);
        rectBox.setAlignment(Pos.CENTER);
        centerRect.setOpacity(0.25);
        centerBox.setPadding(new Insets(0, 20, 0, 0));
        centerBox.setAlignment(Pos.TOP_CENTER);
        centerGrid.setHgap(10);
        centerGrid.setVgap(10);
        centerRect.widthProperty().bind(insideBox.widthProperty());
        centerRect.heightProperty().bind(insideBox.heightProperty());
        centerRect.setArcWidth(20);
        centerRect.setArcHeight(20);
        centerRect.setFill(Color.BLACK);
        insideBox.setPadding(new Insets(70, 30, 70, 30));
        insideBox.setSpacing(30);
        insideBox.setAlignment(Pos.CENTER);
        DropShadow shadow = new DropShadow();
        centerRect.setEffect(shadow);

        centerGrid.setAlignment(Pos.CENTER);


        Slider sMusique = new Slider();
        /*
        sMusique.valueProperty().addListener(new ChangeListener<Number>() {
            public void changed(ObservableValue<? extends Number> ov,
                Number old_val, Number new_val) {
                    System.out.println(new_val.doubleValue());
                    //SoundEngine.getInstance().volume(new_val);
            }
        });
        */
        
        sMusique.valueProperty().addListener(new SoundSlider(SoundSlider.MUSIC_SLIDER));
        sMusique.setValue(100);
        sMusique.setShowTickMarks(true);
        sMusique.setMajorTickUnit(20);

        CheckBox cFC = new CheckBox();
        
        if(fullScreen){
            cFC.setSelected(true);
        }
        cFC.setOnAction((event) -> {
            stage.setFullScreen(cFC.isSelected());
            
            fullScreen = cFC.isSelected();
            if(!fullScreen){
                goConfig();
                goTest();
                tabPane.getSelectionModel().select(3);
            }
        });
        //
        
        Label lSon = new Label("Son");
        Label lMusique = new Label("Musique");
        Label lFullScreen = new Label("Plein Ecran");
        lSon.setTextFill(Color.WHITE);
        lMusique.setTextFill(Color.WHITE);
        lFullScreen.setTextFill(Color.WHITE);
        centerBox.getChildren().add(centerStack);
        centerStack.getChildren().addAll(rectBox, insideBox);//centerGrid);
        Label lNG = new Label("Configuration");
        lNG.setTextFill(Color.WHITE);
        lNG.setFont(new Font(22));

        //centerGrid.add(lSon, 0, 0);
        centerGrid.add(lMusique, 0, 1);
        centerGrid.add(lFullScreen, 0, 2);
        //centerGrid.add(sSon, 1, 0);
        centerGrid.add(sMusique, 1, 1);
        centerGrid.add(cFC, 1, 2);

        //insideBox.getChildren().addAll(lNG, centerGrid);
        double[][] coords;
        GraphicsContext gc;

        HBox bInsecte = new HBox();
        bInsecte.setSpacing(20);
        bInsecte.setAlignment(Pos.CENTER);

        VBox bMoskito = new VBox();
        bMoskito.setAlignment(Pos.CENTER);
        bMoskito.setSpacing(10);
        Canvas cMoskito = new Canvas(80, 80);
        gc = cMoskito.getGraphicsContext2D();
        coords = hex_corner(40, 40, 40);
        gc.setFill(Color.CADETBLUE);
        gc.fillPolygon(coords[0], coords[1], 6);
        InputStream imageM = null;
        imageM = ClassLoader.getSystemClassLoader().getResourceAsStream("Images/moskito.png");
        Image imgM = new Image(imageM, ((80) * 1.20), ((80) * 1.20), true, true);
        gc.drawImage(imgM, 40 - (imgM.getWidth() / 2), 40 - (imgM.getHeight() / 2));
        CheckBox cbMoskito = new CheckBox();

        VBox bClop = new VBox();
        bClop.setAlignment(Pos.CENTER);
        bClop.setSpacing(10);
        Canvas cClop = new Canvas(80, 80);
        gc = cClop.getGraphicsContext2D();
        coords = hex_corner(40, 40, 40);
        gc.setFill(Color.BURLYWOOD);
        gc.fillPolygon(coords[0], coords[1], 6);
        InputStream imageC = null;
        imageC = ClassLoader.getSystemClassLoader().getResourceAsStream("Images/woodlouse.png");
        Image imgC = new Image(imageC, ((80) * 1.0), ((80) * 1.0), true, true);
        gc.drawImage(imgC, 40 - (imgC.getWidth() / 2), 40 - (imgC.getHeight() / 2));
        CheckBox cbClop = new CheckBox();

        VBox bCocc = new VBox();
        bCocc.setAlignment(Pos.CENTER);
        bCocc.setSpacing(10);
        Canvas cCocc = new Canvas(80, 80);
        gc = cCocc.getGraphicsContext2D();
        coords = hex_corner(40, 40, 40);
        gc.setFill(Color.INDIANRED);
        gc.fillPolygon(coords[0], coords[1], 6);
        InputStream imageCo = null;
        imageCo = ClassLoader.getSystemClassLoader().getResourceAsStream("Images/ladybug.png");
        Image imgCo = new Image(imageCo, ((80) * 0.80), ((80) * 0.80), true, true);
        gc.drawImage(imgCo, 40 - (imgCo.getWidth() / 2), 40 - (imgCo.getHeight() / 2));
        CheckBox cbCocc = new CheckBox();
        cbCocc.setOnAction((event) -> {
            FabriqueArbitre.setBonus(Insecte.COCC, cbCocc.isSelected());
        });
        cbClop.setOnAction((event) -> {
            FabriqueArbitre.setBonus(Insecte.CLOP, cbClop.isSelected());
        });
        cbMoskito.setOnAction((event) -> {
            FabriqueArbitre.setBonus(Insecte.MOUS, cbMoskito.isSelected());
        });
        bMoskito.getChildren().addAll(cMoskito, cbMoskito);
        bClop.getChildren().addAll(cClop, cbClop);
        bCocc.getChildren().addAll(cCocc, cbCocc);
        bInsecte.getChildren().addAll(bMoskito, bClop, bCocc);
        insideBox.getChildren().addAll(lNG, centerGrid, bInsecte);
        //root.setCenter(centerBox);
        configBox = centerBox;
    }

    /**
     *
     */
    public static void goAffichage() {

    }

    /**
     *
     */
    public static void goCredits() {
        VBox centerBox = new VBox();
        StackPane centerStack = new StackPane();
        VBox centerGrid = new VBox();
        VBox insideBox = new VBox();
        Rectangle centerRect = new Rectangle();
        VBox rectBox = new VBox();
        rectBox.setAlignment(Pos.CENTER);
        centerBox.setPadding(new Insets(0, 0, 20, 0));
        rectBox.getChildren().add(centerRect);
        centerRect.setOpacity(0.25);
        centerBox.setPadding(new Insets(0, 0, 20, 0));
        centerBox.setAlignment(Pos.TOP_CENTER);
        centerGrid.setSpacing(10);
        
        centerRect.widthProperty().bind(insideBox.widthProperty());
        centerRect.heightProperty().bind(insideBox.heightProperty());
        centerRect.setArcWidth(20);
        centerRect.setArcHeight(20);
        centerRect.setFill(Color.BLACK);
        insideBox.setPadding(new Insets(70, 30, 70, 30));
        insideBox.setSpacing(30);
        insideBox.setAlignment(Pos.CENTER);
        DropShadow shadow = new DropShadow();
        centerRect.setEffect(shadow);

        Label lDev = new Label ("Dévelopers");
        lDev.setFont(new Font(20));
        lDev.setTextFill(Color.WHITE);
        Label l1 = new Label("Maxence Grand\tEnzo Brignon");
        l1.setTextFill(Color.WHITE);
        Label l2 = new Label ("Liel Hadjadj\tNarek Davtyan");
        l2.setTextFill(Color.WHITE);
        Label l3 = new Label ("Amelina Douard\tLucie Muller");
        l3.setTextFill(Color.WHITE);
        
        
        Label lIcones = new Label ("Credit Icones");
        lIcones.setFont(new Font(20));
        lIcones.setTextFill(Color.WHITE);
        
        Label lMonsieur = new Label("Bryn Taylor on Flat Icon");
        lMonsieur.setTextFill(Color.WHITE);
        
        
        centerGrid.setAlignment(Pos.CENTER);
        centerGrid.getChildren().addAll(lDev, l1, l2, l3, lIcones, lMonsieur);
        


        centerBox.getChildren().add(centerStack);
        centerStack.getChildren().addAll(rectBox, insideBox);//centerGrid);
        Label lNG = new Label("Credits");
        lNG.setTextFill(Color.WHITE);
        lNG.setFont(new Font(22));
        
        insideBox.getChildren().addAll(lNG, centerGrid);

        //root.setCenter(centerBox);
        crBox = centerBox;
    }

    /**
     *
     * @param j1
     * @param j2
     * @param nbManche
     * @param joueur
     */
    public static void infoPartie(Joueur j1, Joueur j2, int nbManche, int joueur) {

    }

    /**
     *
     * @param cb
     * @param c
     */
    public static void initChoix(ChoiceBox cb, int c) {

    }


    public static void pause(){
        // Custom dialog
        Dialog<Boolean> dialog = new Dialog<>();
        
        dialog.setTitle("Pause");
        dialog.setResizable(false);
        dialog.initStyle(StageStyle.UNDECORATED);


        // Create layout and add to dialog
        VBox box = new VBox();
        box.setSpacing(30);
        box.setAlignment(Pos.CENTER);
        dialog.getDialogPane().setContent(box);
        
        Label l = new Label("Pause");
        l.setGraphic(new ImageView(new Image(ClassLoader.getSystemClassLoader().getResourceAsStream("Images/Icone/play.png"))));
        l.setFont(new Font("Arial", 30));
        l.setTextFill(Color.RED);
        box.getChildren().add(l);
        Button play = new Button("Continuer");
        play.setMaxWidth(200);
        play.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                dialog.setResult(Boolean.TRUE);
                dialog.close();
            }
        });

        box.getChildren().addAll(play);
        Button restart = new Button("Recommencer");
        restart.setMaxWidth(200);
        restart.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                arbitre.nouvellePartie();
                dialog.setResult(Boolean.TRUE);
                dialog.close();
                
            }
        });
        box.getChildren().addAll(restart);
        Button save = new Button("Sauvegarder");
        save.setMaxWidth(200);
        save.setOnAction(new Bouton(Bouton.BOUTON_SAUVEGARDER,arbitre) );
        box.getChildren().addAll(save);
        Button saveQuit = new Button("Sauvegarder Quitter");
        saveQuit.setMaxWidth(200);
        saveQuit.setOnAction(new Bouton(Bouton.BOUTON_SAUVEGARDER_QUITTER,arbitre) );
        box.getChildren().addAll(saveQuit);
        Button menu = new Button("Menu pincipal");
        menu.setMaxWidth(200);
        menu.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                dialog.setResult(Boolean.TRUE);
                dialog.close();
                if(arbitre!=null)
                    arbitre.abandon();
                goTest();
            }
        });
        Button quit = new Button("Quitter");
        quit.setMaxWidth(200);
        quit.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                quitter();
            }
        });
        
        
        //
        box.getChildren().addAll(menu);
        box.getChildren().addAll(quit);
        dialog.show();
                
		
    }
    
    public static void error(String s1, String s2){
        Alert alert = new Alert(AlertType.WARNING);
        alert.setTitle("Erreur");
        alert.setHeaderText(s1);
        alert.setContentText(s2);
        alert.showAndWait();
    }
    
    public static void nouvelArbitre(){
        arbitre = FabriqueArbitre.nouveau();
        System.out.println((arbitre instanceof SimulationIA));
        arbitre.init();
        System.out.println("Arbitre créé");
    }
    
    public static void goFin(String joueur, int etat){
        Label l = new Label();
        l.setFont(Font.font("Cambria", 32));
        l.setTextFill(Color.WHITE);
        switch(etat){
            case Arbitre.GAGNE:
                l.setText("Vous avez battu "+joueur);
                break;
            case Arbitre.PERDU:
                l.setText(joueur+" vous a battu");
                break;
           case Arbitre.NUL:
                l.setText("Match null");
                break;
        }
        StackPane centerRoot = new StackPane();
        centerRoot.setAlignment(Pos.CENTER);
        Rectangle rleft = new Rectangle();
        rleft.widthProperty().bind(centerRoot.widthProperty());
        rleft.heightProperty().bind(centerRoot.heightProperty());
        rleft.setOpacity(0.25);
        rleft.setFill(Color.BLACK);
        rleft.setEffect(new DropShadow());
        VBox center = new VBox();
        center.getChildren().add(l);
        HBox menu = new HBox();
        center.setSpacing(80);
        center.setAlignment(Pos.CENTER);
        menu.setSpacing(30);
        menu.setAlignment(Pos.CENTER);
        Button recommencer = new Button("Recommencer");
        recommencer.setOnAction(new Bouton(Bouton.BOUTON_RECOMMENCER,arbitre) );
        Button quit = new Button("Quitter");
        quit.setOnAction(new Bouton(Bouton.BOUTON_QUITTER,arbitre) );
        Button retMenu = new Button("Menu");
        retMenu.setOnAction(new Bouton(Bouton.BOUTON_MENU ,arbitre) );
        menu.getChildren().addAll(recommencer, quit, retMenu);
        center.getChildren().add(menu);
        centerRoot.getChildren().addAll(rleft,center);

        root.setCenter(centerRoot);
    }
    
    
    public static void sauvegarder(){
        TextInputDialog Sauv = new TextInputDialog();
        Sauv.setTitle("Sauvegarder");
        Sauv.setHeaderText("Entrez le nom de la sauvegarde, et confirmer");
        Sauv.setGraphic(new ImageView(new Image(ClassLoader.getSystemClassLoader().getResourceAsStream("Images/Icone/save.png"))));
  
        
        Optional<String> result = Sauv.showAndWait();
        if (result.isPresent()){
            arbitre.sauvegarder(result.get());
        }
    }
    
    public static void sauvegarderQuitter(){
        TextInputDialog Sauv = new TextInputDialog();
        Sauv.setTitle("Sauvegarder & Quitter");
        Sauv.setHeaderText("Entrez le nom de la sauvegarde, et confirmer");
        Sauv.setGraphic(new ImageView(new Image(ClassLoader.getSystemClassLoader().getResourceAsStream("Images/Icone/save.png"))));
  
        
        Optional<String> result = Sauv.showAndWait();
        if (result.isPresent()){
            arbitre.sauvegarder(result.get());
            System.exit(0);
        }
    }
    
    public static void quitter(){
        Alert alert = new Alert(AlertType.CONFIRMATION);
        alert.setTitle("Quitter le jeu");
        alert.setHeaderText("Vous allez quittez le jeu");
        alert.setContentText("Êtes vous sûr ?");

        Optional<ButtonType> result = alert.showAndWait();
        if (result.get() == ButtonType.OK){
            System.exit(0);
        }
    }
    public static void closeConnexion(){
        if(dialogConn != null)
            dialogConn.close();
    }
    public static void connexion(){
        /*
        dialogConn = new Dialog<>();
        dialogConn.initModality(Modality.NONE);
        dialogConn.setTitle("En attente de Connexion");
        dialogConn.setHeaderText("En attente d'un nouveau joueur");
        dialogConn.setResizable(true);
        dialogConn.setGraphic(new ImageView(new Image(ClassLoader.getSystemClassLoader().getResourceAsStream("Images/Icone/wait.gif"))));
        ButtonType buttonTypeOk = new ButtonType("Annuler", ButtonData.OK_DONE);

        dialogConn.getDialogPane().getButtonTypes().add(buttonTypeOk);
        dialogConn.setResultConverter(new Callback<ButtonType, Boolean>() {
            @Override
            public Boolean call(ButtonType param) {
                arbitre.setEtat(Arbitre.FIN);
                return true;
                //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
            }

        });
        
        dialogConn.showAndWait();
*/
        GridPane grid = new GridPane();
	grid.setAlignment(Pos.CENTER);
	grid.setHgap(10);
	grid.setVgap(10);
	grid.setPadding(new Insets(10));
	
	Text text = new Text("En attente du nouveau joueur");
	grid.add(text, 0, 0);
        grid.add(new ImageView(new Image(ClassLoader.getSystemClassLoader().getResourceAsStream("Images/Icone/wait.gif"))), 1,1);
	Button bnOK = new Button("Annuler");
	grid.add(bnOK, 0, 2);
	
	Scene dialog = new Scene(grid);
	
	dialogConn = new Stage();
	dialogConn.setScene(dialog);
	
	bnOK.setOnAction((e)-> {
                arbitre.setEtat(ReseauServer.ANNUL);
		dialogConn.close();
                
	});

	dialogConn.show();
	dialogConn.setAlwaysOnTop(true);
	dialogConn.toFront();
    }
    
    public static void fin(){
        System.gc();
        anim.stop();
        PaneToken.reset();
    }
    
    public static void setColorP2 (int color) {
        colorP1 = color;
    }    
    
    public static void setColorP1 (int color) {
        colorP2 = color;
    }    
    
    public static int getColorP1 () {
        return colorP1;
    }
    
    public static int getColorP2 () {
        return colorP2;
    }
    
}
