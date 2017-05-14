/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Modele.Arbitres;

import Modele.Arbitres.*;
import Joueurs.Humain;
import Joueurs.Joueur;
import Modele.Deplacement;
import Modele.Depot;
import Modele.Reine;
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

    @Override
    public void joue(Deplacement d) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void joue(Depot d) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void prochainJoueur() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void go() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void init() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
}
