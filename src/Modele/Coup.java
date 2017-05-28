/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Modele;

/**
 *
 * @author grandmax
 */
public abstract class Coup {

    /**
     *
     */
    public Point destination;
    int joueur;
    
    /**
     *
     * @param j
     */
    public Coup(int j){
        joueur = j;
    }

    /**
     *
     * @return
     */
    public Point destination(){
        return destination;
    }
    
    /**
     *
     * @return
     */
    public int joueur(){
        return joueur;
    }
    
    public void setJoueur(int j){
        joueur = j;
    }
}
