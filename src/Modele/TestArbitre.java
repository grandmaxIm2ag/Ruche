/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Modele;

import Joueurs.Humain;
import Joueurs.Joueur;
import java.util.Properties;

/**
 *
 * @author brignone
 */
public class TestArbitre extends Arbitre {
    
    public TestArbitre(Properties p) {
        super(p);
        init();
        plateau.premierPion(new Reine(0,0,1,0));
        plateau.addPion();
    }
    
}
