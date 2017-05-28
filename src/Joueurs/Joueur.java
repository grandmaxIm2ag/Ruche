/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Joueurs;

import java.util.Properties;

/**<b>Classe représentant un joueur</b>
 * @author UGA L3 Projet Logiciel 2016-2017 groupe 7
 */
public abstract class Joueur {

    /** @see nom()  */
    public String nom;
    
    /**Tableau des pièces restantes dans la main du joueur
     * Chaque indice correspond à un type d'insecte
     * 0 -> Reine
     * 1 -> Scarabee
     * 2 -> Sauterelle
     * 3 -> Fourmi
     * 4 -> Araignee
     * 5 -> Coccinelle
     * 6 -> Moustique  
     * 7 -> Cloporte
     * 
     * @see addPion (int type, int qte)
     * @see int[] pions()
     * @see int pion(int idx)
     * @see setPieces(int[] p)
     * @see jouer(int t)
     */
    int[] tabPieces;
    
    int nbPieces;
    
    /** @see setMain() */
    boolean main;
    
    Properties prop;
    
    /** @see numJ() */
    int numJoueur;
    
    /**Constructeur
     * @param m true ssi c'est le tour du joueur
     * @param p propriétés de la partie
     * @param tabP tableau des pièces que le joueur n'a pas posé
     * @param j indice du joueur (0 ou 1)
     * @param n nom du joueur
     */
    public Joueur(boolean m, Properties p, int[] tabP, int j, String n){
        main=m;
        prop=p;
        tabPieces=tabP;
        nom = "toto";
        numJoueur=j;
        nom = n;
    }
    
    /** Passe la main à l'autre joueur */
    public void setMain(){
        main = !main;
    }
    
    /**Ajoute une quantité donné de pions d'un type dans la main du joueur
     * @param type L'indice du type de l'insecte
     * @param qte Le nombre d'insectes de ce type ajoutés
     */
    public void addPion (int type, int qte) {
        tabPieces[type] += qte;
    }
    
    /**Renvoie le nom du joueur
     * @return Le nom du joueur
     */
    public String nom(){
        return nom;
    }

    /**renvoie le tableau des pions non posés
     * @return Le tableau de pions non posés
     */
    public int[] pions(){
        return tabPieces;
    }

    /**Renvoie le nombre d'insectes de ce type dans la main du joueur
     * @param idx Indice du type d'insecte
     * @return Le tableau de pièces courant
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
    
    /**Remplace le tableau de pièces courant
     * @param p Tableau de pièce
     */
    public void setPieces(int[] p){
        this.tabPieces = p;
    }
    
    /**Met à jour le tableau de pièces courant
     * @param t Type de l'insecte joué
     */
    public void jouer(int t){
        tabPieces[t]--;
    }
    
    /**Renvoie l'indice du joueur courant
     * @return Indice du joueur
     */
    public int numJ(){
        return numJoueur;
    }
    
    public void setNom(String n){
        nom = n;
    }
    
    @Override
    public Joueur clone(){
        return null;
    }
        
    public void pred(int t){
        tabPieces[t]++;
    }
    
}
