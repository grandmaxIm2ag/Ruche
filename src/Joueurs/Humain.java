/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Joueurs;

import java.util.Properties;

/**
 *
 * @author grandmax
 */
public class Humain extends Joueur{

    /**
     *
     * @param m
     * @param p
     * @param tabP
     * @param j
     */
    public Humain(boolean m, Properties p, int[] tabP, int j) {
        super(m, p, tabP, j);
    }
    
    @Override
    public Humain clone(){
        Humain jH = new Humain(main, prop, tabPieces.clone(), numJoueur);    
        return jH;
    }
    
}
