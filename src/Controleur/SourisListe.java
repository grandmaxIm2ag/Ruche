/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controleur;

import Modele.Arbitres.FabriqueArbitre;
import javafx.event.EventHandler;
import javafx.scene.control.ListView;
import javafx.scene.input.MouseEvent;

/**
 *
 * @author grandmax
 */
public class SourisListe implements EventHandler<MouseEvent>{
    int value;
    FabriqueArbitre fabrique;
    ListView<String> list;
    
    public SourisListe(int v, ListView<String> l){
        value = v;
        list=l;
    }

    @Override
    public void handle(MouseEvent t) {
        FabriqueArbitre.initP(list.getSelectionModel().getSelectedItem().split(" ")[0]);
    }
    
    
    
}
