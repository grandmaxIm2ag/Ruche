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
    int[] pions;
    boolean main;
    Properties prop;
    
    public Joueur(boolean m, Properties p){
        main=m;
        //prop=p;
        pions = new int[8];
        for (int i = Insecte.REINE; i <= Insecte.CLOP; i++)
            pions[i] = 0;
    }
    
    public void setMain(){
        main = !main;
    }
    
    public void addPion (int type, int qte) {
        pions[type] += qte;
    }
    
    public String nom(){
        return nom;
    }
    public int[] pions(){
        return pions;
    }
    
}
