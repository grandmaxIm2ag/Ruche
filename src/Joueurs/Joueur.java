/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Joueurs;

/**
 *
 * @author grandmax
 */
public abstract class Joueur {
    String nom;
    int[] pions;
    boolean main;
    
    public Joueur(boolean m){
        main=m;
    }
    
    public void setMain(){
        main = !main;
    }
    public String nom(){
        return nom;
    }
    public int[] pions(){
        return pions;
    }
    
}
