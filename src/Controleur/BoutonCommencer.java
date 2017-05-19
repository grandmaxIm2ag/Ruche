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
import javafx.scene.control.TextField;

/**
 *
 * @author grandmax
 */
public class BoutonCommencer implements EventHandler<ActionEvent> {
    TextField text1, text2, text3;
    FabriqueArbitre fab;
    
    public BoutonCommencer(TextField t1, TextField t2, TextField t3, FabriqueArbitre f){
        fab = f;
        text1 = t1;
        text2 = t2;
        text3 = t3;
    }

    @Override
    public void handle(ActionEvent event) {
        switch(fab.type()){
            case FabriqueArbitre.LOCAL_JVJ:
                System.out.println("Handle BoutComm test1");
                fab.initN1(text1.getText());
                fab.initN2(text2.getText());
                Interface.nouvelArbitre();
                break;
            case FabriqueArbitre.LOCAL_JVIA:
                System.out.println("Handle BoutComm test2");
                fab.initN1(text1.getText());
                Interface.nouvelArbitre();
                break;
            case FabriqueArbitre.SIMULATION:
                Interface.nouvelArbitre();
                break;
            case FabriqueArbitre.RESEAU_SERVER:
                fab.initN1(text1.getText());
                Interface.nouvelArbitre();
                break;
            case FabriqueArbitre.RESEAU_CLIENT:
                fab.initN1(text1.getText());
                fab.initIP(text3.getText());
                Interface.nouvelArbitre();
                break;
            default:
                break;
        }
    }
}
