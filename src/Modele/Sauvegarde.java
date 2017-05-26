/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Modele;

import Joueurs.Ordinateur;
import Modele.Arbitres.FabriqueArbitre;

/**
 *
 * @author maxence
 */
public class Sauvegarde {
    String nom;
    String joueur1;
    String joueur2;
    
    String donnee;
    boolean sauver;
    
    int type;
    int diff1;
    int diff2;
    
    public Sauvegarde(String n, String j1, String j2, int c){
        this(n,j1,j2);
        type=c;
    }
    public Sauvegarde(String n, String j1, String j2){
        nom=n; joueur1=j1; joueur2=j2;sauver = true;
    }
    public Sauvegarde(String n){
        nom=n; sauver = true;
    }
    
    public String getNom(){
        return nom;
        
    }
    public String getJoueur1(){
        return joueur1;
    }
    public String getJoueur2(){
        return joueur2;
    }
    
    public String getDonnee(){
        return donnee;
    }
    
    public void setPropriete(int t){
        type = t;
    }
    public void setPropriete(int t, int d1){
        setPropriete(t);
        diff1=d1;
        if(t!=FabriqueArbitre.LOCAL_JVJ)
            if(t==FabriqueArbitre.LOCAL_JVIA){
                switch(diff1){
                    case Ordinateur.FACILE_HEURISTIQUE:
                        joueur2 = "Facile";
                        break;
                    case Ordinateur.FACILE_ALEATOIRE:
                        joueur2 = "Très Facile";
                        break;
                    case Ordinateur.MOYEN:
                        joueur2 = "Moyen";
                        break;
                    case Ordinateur.DIFFICILE:
                        joueur2 = "Difficile";
                        break;
                }
            }else{
                switch(diff1){
                    case Ordinateur.FACILE_HEURISTIQUE:
                        joueur1 = "Facile";
                        break;
                    case Ordinateur.FACILE_ALEATOIRE:
                        joueur1 = "Très Facile";
                        break;
                    case Ordinateur.MOYEN:
                        joueur1 = "Moyen";
                        break;
                    case Ordinateur.DIFFICILE:
                        joueur1 = "Difficile";
                        break;
                }
        }
    }
    public void setPropriete(int t, int d1, int d2){
        setPropriete(t, d1);
        diff2=d2;
        if(t!=FabriqueArbitre.LOCAL_JVJ)
            switch(diff2){
                case Ordinateur.FACILE_HEURISTIQUE:
                    joueur2 = "Facile";
                    break;
                case Ordinateur.FACILE_ALEATOIRE:
                    joueur2 = "Très Facile";
                    break;
                case Ordinateur.MOYEN:
                    joueur2 = "Moyen";
                    break;
                case Ordinateur.DIFFICILE:
                    joueur2 = "Difficile";
                    break;
            }
    }
    
    public String propriete(){
        switch(type){
            case FabriqueArbitre.LOCAL_JVJ:
                return type+"::"+joueur1+"::"+joueur2;
            case FabriqueArbitre.LOCAL_JVIA:
                return type+"::"+joueur1+"::"+diff1;
            case FabriqueArbitre.LOCAL_IAVJ:
                return type+"::"+diff1+"::"+joueur2;
            case FabriqueArbitre.SIMULATION:
                return type+"::"+diff1+"::"+diff2;
            default:
                return null;
            
        }
    }
    
    public void setDonne(String c){
        donnee=c;
    }
    
    public int getType(){
        return type;
    }
}
