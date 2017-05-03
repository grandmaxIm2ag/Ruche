/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Modele;

import Joueurs.Joueur;
import java.util.Properties;
import java.util.Stack;

/**
 *
 * @author grandmax
 */
public class Arbitre {
    public final static int JvJ=0;
    public final static int JvIA=1;
            
    public final static int J1 = 0;
    public final static int J2 = 1;
    
    
    Properties prop;
    Joueur[] joueurs;
    int jCourant, type, difficulte;
    Plateau plateau;
    Chargeur chargeur;
    Stack<String> historique;
    Stack<String> refaire;
    
    String[] diff;
    String[] types;
    String[] plateaux;
    
    int[] nbCoup;
    
    public Arbitre(Properties p){
        prop = p;
    }
    
    public void init(){
        
    }
    
    public Plateau plateau(){
        return plateau;
    }
    public Joueur joueur(int i){
        return joueurs[i];
    }
    
    public boolean deposePionValide(Depot d){
        
        return false;
    }
    public boolean deplacePionValide(Deplacement d){
        
        return false;
    }
    public boolean deplacePion(Deplacement d){
        
        return false;
    }
    public boolean deposePion(Depot d){
        
        return false;
    }
    
    public void nouvellePartie(){
        
    }
    
    public void precedent(){
        
    }
    public void refaire(){
        
    }
    public void abandon(){
        
    }
    
    public void charger(String plateau){
        
    }
    public void sauvegarder(){
        
    }
    public void aide(){
        
    }
    
}
