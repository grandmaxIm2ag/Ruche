/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Modele.Arbitres;

import Modele.Deplacement;
import Modele.Depot;
import java.util.Properties;

/**
 *
 * @author maxence
 */
public class ReseauClient extends Arbitre{

    /**
     *
     * @param p
     */
    public ReseauClient(Properties p) {
        super(p);
    }

    /**
     *
     */
    @Override
    public void init() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
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
    
}
