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
public abstract class Visiteur {
    public boolean visite(Composant c){
        return false;
    }
    
    public boolean visite(Plateau p){
        return visite((Composant) p);
    }
    public boolean visite(Case p){
        return visite((Composant) p);
    }
    public boolean visite(Insecte p){
        return visite((Composant) p);
    }
    
    public boolean visite(Reine p){
        return visite((Insecte) p);
    }
    public boolean visite(Fourmie p){
        return visite((Insecte) p);
    }
    public boolean visite(Cloporte p){
        return visite((Insecte) p);
    }
    public boolean visite(Araignee p){
        return visite((Insecte) p);
    }
    public boolean visite(Scarabee p){
        return visite((Insecte) p);
    }
    public boolean visite(Sauterelle p){
        return visite((Insecte) p);
    }
    public boolean visite(Moustique p){
        return visite((Insecte) p);
    }
    public boolean visite(Coccinelle p){
        return visite((Insecte) p);
    }
}
