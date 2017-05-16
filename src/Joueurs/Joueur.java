/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Joueurs;

import Modele.Insecte;
import java.util.Properties;

/**
 *
 * @author grandmax
 */
public abstract class Joueur {

    /**
     *
     */
    public String nom;
    int[] tabPieces;
    int nbPieces;
    boolean main;
    Properties prop;
    int numJoueur;
    
    /**
     *
     * @param m
     * @param p
     * @param tabP
     * @param j
     */
    public Joueur(boolean m, Properties p, int[] tabP, int j){
        main=m;
        prop=p;
        tabPieces=tabP;
        nom = "toto";
        numJoueur=j;
    }
    
    /**
     *
     */
    public void setMain(){
        main = !main;
    }
    
    /**
     *
     * @param type
     * @param qte
     */
    public void addPion (int type, int qte) {
        tabPieces[type] += qte;
    }
    
    /**
     *
     * @return
     */
    public String nom(){
        return nom;
    }

    /**
     *
     * @return
     */
    public int[] pions(){
        return tabPieces;
    }

    /**
     *
     * @param idx
     * @return
     */
    public int pion(int idx){
        return tabPieces[idx];
    }
    
    @Override
    public String toString(){
        String res = nom+"=";
        
        res+=tabPieces[0];
        for(int i=1; i<tabPieces.length; i++)
            res += ":"+tabPieces[i];
        
        return res;
    }
    
    /**
     *
     * @param p
     */
    public void setPieces(int[] p){
        this.tabPieces = p;
    }
    
    /**
     *
     * @param t
     */
    public void jouer(int t){
        System.err.println("coucou"+" "+numJoueur);
        tabPieces[t]--;
    }
    
    /**
     *
     * @return
     */
    public int numJ(){
        return numJoueur;
    }
}
