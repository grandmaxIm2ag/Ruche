/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controleur;

import Modele.Arbitres.FabriqueArbitre;
import Vue.Interface;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextField;

/**
 *
 * @author brignone
 */
public class NewGameHandler implements EventHandler<ActionEvent> {
    public static final int JOUEUR = 0;
    public static final int TRES_FACILE = 1;
    public static final int FACILE = 2;
    public static final int MOYEN = 3;
    public static final int DIFFICILE = 4;
    
    public static final String[] DIFFICULTEES = {"Joueur", "Très facile", "Facile", "Moyen", "Difficile"};
    
    private ChoiceBox cbJ1;
    private ChoiceBox cbJ2;
    
    private TextField tfJ1;
    private TextField tfJ2;
    
    private FabriqueArbitre fabrique;
    
    public NewGameHandler (ChoiceBox cbJ1, ChoiceBox cbJ2, TextField tfJ1, TextField tfJ2) {
        this.cbJ1 = cbJ1;
        this.cbJ2 = cbJ2;
        this.tfJ1 = tfJ1;
        this.tfJ2 = tfJ2;
    }
    
    public NewGameHandler (TextField tfJ1, TextField tfHost) {
        this.tfJ1 = tfJ1;
        this.tfJ2 = tfHost;
    }
    
    @Override
    public void handle(ActionEvent event) {
        //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
        
        FabriqueArbitre.initP("(none)");
        boolean pass = true;
        if (cbJ1 != null && cbJ2 != null) {
            if (cbJ1.getSelectionModel().getSelectedIndex() == 0 && cbJ2.getSelectionModel().getSelectedIndex() == 0) {
                pass = false;
                FabriqueArbitre.initType(FabriqueArbitre.LOCAL_JVJ);
                FabriqueArbitre.initN1((tfJ1.getText()).isEmpty() ? "Joueur 1" : tfJ1.getText());
                FabriqueArbitre.initN2((tfJ2.getText()).isEmpty() ? "Joueur 2" : tfJ2.getText());
                Interface.nouvelArbitre();
            }
            if (cbJ1.getSelectionModel().getSelectedIndex() != 0) {
                FabriqueArbitre.initDiff(cbJ1.getSelectionModel().getSelectedIndex()-1);
            } else {
                FabriqueArbitre.initDiff(0);
            }
            if (cbJ2.getSelectionModel().getSelectedIndex() != 0) {
                FabriqueArbitre.initDiff2(cbJ2.getSelectionModel().getSelectedIndex()-1);
            } else {
                FabriqueArbitre.initDiff2(0);
            }
            if (cbJ1.getSelectionModel().getSelectedIndex() != 0 && cbJ2.getSelectionModel().getSelectedIndex() != 0) {
                FabriqueArbitre.initType(FabriqueArbitre.SIMULATION);
                Interface.nouvelArbitre();
            }  else if(pass) {
                if (cbJ2.getSelectionModel().getSelectedIndex() != 0)
                    FabriqueArbitre.initType(FabriqueArbitre.LOCAL_JVIA);
                else
                    FabriqueArbitre.initType(FabriqueArbitre.LOCAL_IAVJ);
                if (cbJ1.getSelectionModel().getSelectedIndex() == 0)
                    FabriqueArbitre.initN1(tfJ1.getText());
                else
                    FabriqueArbitre.initN2(tfJ2.getText());
                Interface.nouvelArbitre();
            }
        }else if(cbJ2 == null && cbJ1 != null ){
            if (cbJ1.getSelectionModel().getSelectedIndex() == 0) {
                pass = false;
                FabriqueArbitre.initType(FabriqueArbitre.RESEAU_SERVER);
                FabriqueArbitre.initN1(tfJ1.getText().isEmpty() ? "Client" : tfJ1.getText());
                Interface.nouvelArbitre();
            }else{
                pass = false;
                FabriqueArbitre.initType(FabriqueArbitre.RESEAU_CLIENT);
                FabriqueArbitre.initN1(tfJ1.getText().isEmpty() ? "Client" : tfJ1.getText());
                FabriqueArbitre.initIP(tfJ2.getText().isEmpty() ? "127.0.0.1" : tfJ2.getText());
                Interface.nouvelArbitre();
            }
        }
    }

}
