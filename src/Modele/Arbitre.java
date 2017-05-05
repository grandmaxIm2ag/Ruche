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
        return plateau.deposePionValide(d);
    }
    public boolean deplacePionValide(Deplacement d){
        return plateau.deplacePionValide(d);
    }
    public void deplacePion(Deplacement d){
        plateau.deplacePion(d);
    }
    public void deposePion(Depot d){
        plateau.deposePion(d);
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

    public void accept(Visiteur dessinateur) {
        //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
        plateau.accept(dessinateur);
    }
    
}
