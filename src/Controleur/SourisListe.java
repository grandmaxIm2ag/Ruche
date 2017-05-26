/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controleur;

import Modele.Arbitres.FabriqueArbitre;
import Modele.Sauvegarde;
import javafx.event.EventHandler;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TableView;
import javafx.scene.input.MouseEvent;
import javafx.scene.text.Text;

/**
 *
 * @author grandmax
 */
public class SourisListe implements EventHandler<MouseEvent>{
    int value;
    FabriqueArbitre fabrique;
    TableView<Sauvegarde> list;
    
    public SourisListe(int v, TableView<Sauvegarde> l){
        value = v;
        list=l;
    }

    @Override
    public void handle(MouseEvent t) {
        try{
            FabriqueArbitre.initP(list.getSelectionModel().getSelectedItem().getNom());
            FabriqueArbitre.initType(list.getSelectionModel().getSelectedItem().getType());
        }catch(NullPointerException e){
        }
    }
    
    
    
}
