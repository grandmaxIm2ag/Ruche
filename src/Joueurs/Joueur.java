/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Joueurs;

import java.util.Properties;

/**<b>Classe représentant un joueur</b>
 *
 * @author UGA L3 Projet Logiciel 2016-2017 groupe 7
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
    
    /**Constructeur
     * @param m
     *      true ssi c'est le tour du joueur
     * @param p
     *      propriétés de la partie
     * @param tabP
     *      tableau des pièces que le joueur n'a pas posé
     * @param j
     *      indice du joueur (0 ou 1)
     */
    public Joueur(boolean m, Properties p, int[] tabP, int j){
        main=m;
        prop=p;
        tabPieces=tabP;
        nom = "toto";
        numJoueur=j;
    }
    
    /**Passe la main à l'autre joueur
     * 
     */
    public void setMain(){
        main = !main;
    }
    
    /**Initialise le nombre de pions d'un type dans la main du joueur
     * @param type
     *      L'indice du type de l'insecte
     * @param qte
     *      Le nombre d'insectes de ce type
     */
    public void addPion (int type, int qte) {
        tabPieces[type] += qte;
    }
    
    /**Renvoie le nom du joueur
     * @return 
     *      Le nom du joueur
     */
    public String nom(){
        return nom;
    }

    /**renvoie le tableau des pions non posés
     * @return
     *      Le tableau de spions non posés
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
        tabPieces[t]--;
    }
    
    /**Renvoie l'indice du joueur
     * @return
     *      Indice du joueur
     */
    public int numJ(){
        return numJoueur;
    }
}
