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
    
    public NewGameHandler (ChoiceBox cbJ1, ChoiceBox cbJ2, TextField tfJ1, TextField tfJ2, FabriqueArbitre fabrique) {
        this.cbJ1 = cbJ1;
        this.cbJ2 = cbJ2;
        this.tfJ1 = tfJ1;
        this.tfJ2 = tfJ2;
        this.fabrique = fabrique;
    }
    
    public NewGameHandler (TextField tfJ1, TextField tfHost, FabriqueArbitre fabrique) {
        this.tfJ1 = tfJ1;
        this.tfJ2 = tfHost;
        this.fabrique = fabrique;
    }
    
    @Override
    public void handle(ActionEvent event) {
        //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
        if (cbJ1 != null && cbJ2 != null) {
            if (cbJ1.getSelectionModel().getSelectedIndex() == 0 && cbJ2.getSelectionModel().getSelectedIndex() == 0) {
                fabrique.initType(FabriqueArbitre.LOCAL_JVJ);
                fabrique.initN1(tfJ1.getText());
                fabrique.initN2(tfJ2.getText());
                Interface.nouvelArbitre();
            }
            if (cbJ1.getSelectionModel().getSelectedIndex() != 0) {
                fabrique.initDiff(cbJ1.getSelectionModel().getSelectedIndex()-1);
            } else {
                fabrique.initDiff(0);
            }
            if (cbJ2.getSelectionModel().getSelectedIndex() != 0) {
                fabrique.initDiff2(cbJ2.getSelectionModel().getSelectedIndex()-1);
            } else {
                fabrique.initDiff2(0);
            }
            if (cbJ1.getSelectionModel().getSelectedIndex() != 0 && cbJ2.getSelectionModel().getSelectedIndex() != 0) {
                fabrique.initType(FabriqueArbitre.SIMULATION);
                Interface.nouvelArbitre();
            } else {
                fabrique.initType(FabriqueArbitre.LOCAL_JVIA);
                if (cbJ1.getSelectionModel().getSelectedIndex() == 0)
                    fabrique.initN1(tfJ1.getText());
                else
                    fabrique.initN1(tfJ2.getText());
                Interface.nouvelArbitre();
            }
        }
    }
}
