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
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

/**
 *
 * @author maxence
 */
public class Chat {
    static ArbitreReseau arbitre;
    static String joueur;
    static ListView<String> board;
    
    public static void creer(ArbitreReseau a, String j){
        arbitre = a;
        joueur = j;
        GridPane rootPane = new GridPane();
        rootPane.setPadding(new Insets(20));
        rootPane.setAlignment(Pos.CENTER);
        rootPane.setHgap(10);
        rootPane.setVgap(10);

        board = new ListView<String>();

        TextField chatTextField = new TextField();
        chatTextField.setOnAction((ActionEvent event) -> {
            String reply = chatTextField.getText();
            board.getItems().add(joueur+": "+reply);
            arbitre.newMessage(reply);
            chatTextField.clear();
        });

        rootPane.add(board, 0, 0);
        rootPane.add(chatTextField, 0, 1);

        Stage chat = new Stage();
        chat.setScene(new Scene(rootPane, 400, 400));
        chat.show();
    }
    
    public static void writeMessage(String mess, String nom){
        board.getItems().add(nom+": "+mess);
    }
}
