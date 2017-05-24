/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Vue;

import Modele.Arbitres.ArbitreReseau;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.paint.ImagePattern;
import javafx.stage.Popup;
import javafx.stage.Stage;

/**
 *
 * @author maxence
 */
public class Chat {
    static ArbitreReseau arbitre;
    static String joueur;
    static ListView<VBox> board;
    static boolean hide;
    static String lastName = "";
    static double width;
    static Button show;
    
    public static Button creer(ArbitreReseau a, String j, Stage s){
        hide = true;
        arbitre = a;
        joueur = j;
        VBox rootBox = new VBox();
        rootBox.setSpacing(10);
        rootBox.setPadding(new Insets (10, 10, 10, 10));
        HBox rootPane = new HBox();
        rootPane.setPadding(new Insets(0));
        rootPane.setAlignment(Pos.CENTER);
        rootPane.setSpacing(10);

        board = new ListView<VBox>();
        
        TextField chatTextField = new TextField();
        rootBox.getChildren().addAll(board, rootPane);
        width = 235;
        //rootPane.add(board, 0, 0);
        //rootPane.add(chatTextField, 0, 0);

        Stage chat = new Stage();
        chat.setScene(new Scene(rootBox, 400, 400));
        Popup disc = new Popup();
        disc.setX(200); disc.setY(200);
        disc.getContent().add(rootBox);
        chatTextField.setOnAction((ActionEvent event) -> {
            String reply = chatTextField.getText();
            Bulle b = new Bulle (joueur, reply, Color.AQUAMARINE, !lastName.equals(joueur), width);
            board.getItems().add(b.getBulle());
            lastName = joueur;
            arbitre.newMessage(reply);
            chatTextField.clear();
        });
        
        chat.setAlwaysOnTop(true);
        
        show = new Button();
        show.setBackground(new Background(new BackgroundFill(new ImagePattern(new Image(ClassLoader.getSystemClassLoader().getResourceAsStream("Images/Icone/chat.png"))), CornerRadii.EMPTY, Insets.EMPTY)));
        show.setMaxWidth(50);
        show.setMaxHeight(50);
        show.setMinWidth(50);
        show.setMinHeight(50);
        show.setOnAction((ActionEvent event) -> {
            hide = !disc.isShowing();
            if (!disc.isShowing()) {
                disc.show(s);
                show.setBackground(new Background(new BackgroundFill(new ImagePattern(new Image(ClassLoader.getSystemClassLoader().getResourceAsStream("Images/Icone/chat.png"))), CornerRadii.EMPTY, Insets.EMPTY)));
            } else
                disc.hide();
            hide = !disc.isShowing();
            disc.setX(Interface.scene.getWindow().getWidth()+Interface.scene.getWindow().getX()-disc.getWidth());
            disc.setY(Interface.scene.getWindow().getY()+Interface.scene.getWindow().getHeight()-disc.getHeight());
        });
        
        Button hide = new Button("Envoyer");
        hide.setOnAction((ActionEvent event) -> {
            String reply = chatTextField.getText();
            Bulle b = new Bulle (joueur, reply, Color.AQUAMARINE, !lastName.equals(joueur), width);
            board.getItems().add(b.getBulle());
            lastName = joueur;
            arbitre.newMessage(reply);
            chatTextField.clear();
        });
        //rootPane.add(hide, 1, 0);
        rootPane.getChildren().addAll(chatTextField, hide);
        return show;
    }
    
    public static void writeMessage(String mess, String nom){
        Bulle b = new Bulle (nom, mess, Color.CHARTREUSE, !lastName.equals(nom), 235);
        board.getItems().add(b.getBulle());
        lastName = nom;
        if(hide)
            show.setBackground(new Background(new BackgroundFill(new ImagePattern(new Image(ClassLoader.getSystemClassLoader().getResourceAsStream("Images/Icone/newMessage.png"))), CornerRadii.EMPTY, Insets.EMPTY)));
    }
}
