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
    String nom;
    int[] tabPieces;
    int nbPieces;
    boolean main;
    Properties prop;
    int numJoueur;
    
    public Joueur(boolean m, Properties p, int[] tabP, int nbP){
        main=m;
        prop=p;
        tabPieces=tabP;
        nbPieces=nbP;
    }
    
    public void setMain(){
        main = !main;
    }
    
    public void addPion (int type, int qte) {
        tabPieces[type] += qte;
    }
    
    public String nom(){
        return nom;
    }
    public int[] pions(){
        return tabPieces;
    }
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
    
}
