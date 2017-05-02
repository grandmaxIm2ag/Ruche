/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Vue;

import Joueurs.Joueur;
import Modele.Arbitre;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

/**
 *
 * @author grandmax
 */
public class Interface extends Application{

    public final static int BOUTON_NOUVEL = 0;
    public final static int BOUTON_REFAIRE = 1;
    public final static int BOUTON_ANNULER = 2;
    public final static int BOUTON_SAUVEGARDER = 3;
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
    static BorderPane b;
    static Scene s;
    final static boolean fullScreen = false;
        
    @Override
    public void start(Stage stage) throws Exception {

    }

    public static void creer(String[] args, Arbitre a) {
        
    }
    
    public static void goPartie(){
        
    }
    
    public static void goMenu(){
        
    }
    
    public static void goFin(String gagnant){
        
        
    }
    
    public static void infoPartie(Joueur j1, Joueur j2, int joueur){
        
    }
 
    
}
