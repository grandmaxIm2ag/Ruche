/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Modele;

import Vue.ButtonDrawer;

/**
 *
 * @author grandmax
 */
public abstract class Visiteur {

    /**
     *
     * @param c
     * @return
     */
    public boolean visite(Composant c){
        return false;
    }
    
    /**
     *
     * @param p
     * @return
     */
    public boolean visite(Plateau p){
        return visite((Composant) p);
    }

    /**
     *
     * @param p
     * @return
     */
    public boolean visite(Case p){
        return visite((Composant) p);
    }

    /**
     *
     * @param p
     * @return
     */
    public boolean visite(Insecte p){
        return visite((Composant) p);
    }
    
    /**
     *
     * @param a
     * @return
     */
    public boolean visite (Modele.Arbitres.Arbitre a) {
        return false;
    }
    
    /**
     *
     * @param p
     * @return
     */
    public boolean visite(Reine p){
        return visite((Insecte) p);
    }

    /**
     *
     * @param p
     * @return
     */
    public boolean visite(Fourmie p){
        return visite((Insecte) p);
    }

    /**
     *
     * @param p
     * @return
     */
    public boolean visite(Cloporte p){
        return visite((Insecte) p);
    }

    /**
     *
     * @param p
     * @return
     */
    public boolean visite(Araignee p){
        return visite((Insecte) p);
    }

    /**
     *
     * @param p
     * @return
     */
    public boolean visite(Scarabee p){
        return visite((Insecte) p);
    }

    /**
     *
     * @param p
     * @return
     */
    public boolean visite(Sauterelle p){
        return visite((Insecte) p);
    }

    /**
     *
     * @param p
     * @return
     */
    public boolean visite(Moustique p){
        return visite((Insecte) p);
    }

    /**
     *
     * @param p
     * @return
     */
    public boolean visite(Coccinelle p){
        return visite((Insecte) p);
    }

    /**
     *
     * @param b
     * @return
     */
    public boolean visite (ButtonDrawer b) {
        return false;
    }
}
