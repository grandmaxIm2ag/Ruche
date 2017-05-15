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
import java.time.Duration;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.animation.RotateTransition;
import javafx.beans.binding.Bindings;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.LongProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleLongProperty;
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
import javax.imageio.ImageIO;

/**
 *
 * @author brignone
 */
public class Interface extends Application {

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

        stage.setTitle("Ruche");
        if (fullScreen) {
            s = new Scene(root);
            stage.setFullScreen(true);
        } else {
            s = new Scene(root, 800, 600);
        }
        stage.setScene(s);
        goMenu();
        //goPartie();
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
        double[][] coords = hex_corner(100, 50, 50);
        gc.fillPolygon(coords[0], coords[1], 6);
        gc.strokePolygon(coords[0], coords[1], 6);
        gc.setFill(Color.ORANGERED);
        gc.setFont(Font.font("Tahoma", FontWeight.NORMAL, 33));
        gc.fillText("Hive", 70, 60);
        return c;
    }

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

    public static void goPartie() {
        arbitre.init();
        //root.setBottom(new Pane());
        Canvas c = new Canvas(500, 500);
        Pane stack = new Pane(c);
        root.setCenter(stack);

        c.widthProperty().bind(stack.widthProperty());
        c.heightProperty().bind(stack.heightProperty().subtract(50));

        VBox box = new VBox();
        box.setAlignment(Pos.TOP_CENTER);
        box.setPadding(new Insets(20, 10, 20, 10));
        box.setSpacing(20);
        root.setBottom(box);
        Button btPrec = new Button("Précédent");
        Button btSuiv = new Button("Suivant");
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
        root.setRight(pt.getRightPane());
        root.setLeft(pt.getLeftPane());

        box.getChildren().addAll(btPrec, btSuiv, btSave, btMenu);

        c.setOnMouseClicked(new Souris(arbitre, Souris.SOURIS_BOUGEE, c));

        Animation anim = new Animation(arbitre, c, cj1, cj2);
        anim.start();

    }

    public static double py(double a) {
        return Math.sqrt(Math.pow(a, 2) - Math.pow(a / 2, 2));
    }
    //static int i = 10;
    static IntegerProperty i = new SimpleIntegerProperty(10);


    public static void goMenu() {
        root.setRight(new Pane());
        VBox topBox = new VBox();
        topBox.setAlignment(Pos.TOP_CENTER);
        topBox.setPadding(new Insets(20, 10, 20, 10));
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

        Button b = new Button();
        b.setBackground(new Background(new BackgroundFill(new ImagePattern(new Image(ClassLoader.getSystemClassLoader().getResourceAsStream("Images/woodlouse.png"))), CornerRadii.EMPTY, Insets.EMPTY)));
        
        b.setMinWidth(100);
        b.setMinHeight(200);
        
        b.setOnAction(new Bouton(Bouton.BOUTON_QUITTER, arbitre));
        
        Button bTest = new Button("test");
        Label tTest = new Label();
        //tTest.textProperty().bind(i.asString());
        tTest.textProperty().bind(Bindings.createStringBinding(() -> "" + i.get(), i));
        
        bTest.setOnAction(new EventHandler<ActionEvent> () {
            @Override
            public void handle (ActionEvent e) {
                i.set(i.get() +1);
                System.out.println(i);
            }
        });
        
        leftBox.getChildren().addAll(btNG, btLD, btCFG, btQUIT, b, bTest, tTest);

        root.setLeft(box);
        goNewGame();
    }

    public static void goNewGame() {
        VBox centerBox = new VBox();
        StackPane centerStack = new StackPane();
        GridPane centerGrid = new GridPane();
        VBox insideBox = new VBox();
        Rectangle centerRect = new Rectangle();
        centerBox.setPadding(new Insets(20, 20, 20, 0));
        centerBox.setAlignment(Pos.TOP_CENTER);
        centerGrid.setHgap(10);
        centerGrid.setVgap(10);
        centerRect.widthProperty().bind(centerStack.widthProperty());
        centerRect.heightProperty().bind(centerStack.heightProperty());
        centerRect.setArcWidth(20);
        centerRect.setArcHeight(20);
        centerRect.setFill(Color.WHITESMOKE);
        insideBox.setPadding(new Insets(70, 30, 70, 30));
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

        cbMOD.getSelectionModel().selectedIndexProperty().addListener(new ChangeListener<Number>() {

            @Override
            public void changed(ObservableValue ov, Number value, Number newValue) {
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

    public static void goLoadGame() {
        VBox centerBox = new VBox();
        StackPane centerStack = new StackPane();
        GridPane centerGrid = new GridPane();
        VBox insideBox = new VBox();
        Rectangle centerRect = new Rectangle();
        centerBox.setPadding(new Insets(20, 20, 20, 0));
        centerBox.setAlignment(Pos.TOP_CENTER);
        centerGrid.setHgap(10);
        centerGrid.setVgap(10);
        centerRect.widthProperty().bind(centerStack.widthProperty());
        centerRect.heightProperty().bind(centerStack.heightProperty());
        centerRect.setArcWidth(20);
        centerRect.setArcHeight(20);
        centerRect.setFill(Color.WHITESMOKE);
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
        centerStack.getChildren().addAll(centerRect, insideBox);//centerGrid);
        Label lNG = new Label("Charger Partie");
        lNG.setFont(new Font(22));

        insideBox.getChildren().addAll(lNG, centerGrid, btBEG);

        root.setCenter(centerBox);
    }

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

    public void goFin(String gagnant) {

    }

    public static void goConfig() {
        VBox centerBox = new VBox();
        StackPane centerStack = new StackPane();
        GridPane centerGrid = new GridPane();
        VBox insideBox = new VBox();
        Rectangle centerRect = new Rectangle();
        centerBox.setPadding(new Insets(20, 20, 20, 0));
        centerBox.setAlignment(Pos.TOP_CENTER);
        centerGrid.setHgap(10);
        centerGrid.setVgap(10);
        centerRect.widthProperty().bind(centerStack.widthProperty());
        centerRect.heightProperty().bind(centerStack.heightProperty());
        centerRect.setArcWidth(20);
        centerRect.setArcHeight(20);
        centerRect.setFill(Color.WHITESMOKE);
        insideBox.setPadding(new Insets(70, 30, 70, 30));
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
        centerGrid.add(lMusique, 0, 1);
        centerGrid.add(lFullScreen, 0, 2);
        centerGrid.add(sSon, 1, 0);
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

        bMoskito.getChildren().addAll(cMoskito, cbMoskito);
        bClop.getChildren().addAll(cClop, cbClop);
        bCocc.getChildren().addAll(cCocc, cbCocc);
        bInsecte.getChildren().addAll(bMoskito, bClop, bCocc);
        insideBox.getChildren().addAll(lNG, centerGrid, bInsecte);
        root.setCenter(centerBox);
    }

    public static void goAffichage() {

    }

    public static void goCredits() {
        VBox box = new VBox();
        box.setSpacing(30);
        box.setPadding(new Insets(20, 10, 20, 10));
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
        Label lAme = new Label("Amelina Douard");
        Label lLies = new Label("Lies Hadjadj");
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

    public static void infoPartie(Joueur j1, Joueur j2, int nbManche, int joueur) {

    }

    public static void initChoix(ChoiceBox cb, int c) {

    }
}
