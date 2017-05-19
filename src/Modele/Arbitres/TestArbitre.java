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
    
    /**
     *
     * @param p
     */
    public TestArbitre(Properties p, String n1, String n2) {
        super(p, n1,n2);
        init();
        plateau.premierPion(new Reine(0,0,1,0));
        plateau.addPion();
    }

    /**
     *
     * @param d
     */
    @Override
    public void joue(Deplacement d) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    /**
     *
     * @param d
     */
    @Override
    public void joue(Depot d) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    /**
     *
     */
    @Override
    public void prochainJoueur() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    /**
     *
     */
    @Override
    public void go() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    /**
     *
     */
    @Override
    public void init() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
}
