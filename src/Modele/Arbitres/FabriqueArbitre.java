/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Modele.Arbitres;

import Joueurs.Ordinateur;
import java.util.Properties;

/**
 *
 * @author maxence
 */
public class FabriqueArbitre {
    public final static int LOCAL_JVJ = 0;
    public final static int LOCAL_JVIA = 1;
    public final static int SIMULATION = 2;
    public final static int RESEAU_CLIENT = 3;
    public final static int RESEAU_SERVER = 4;
    
    Properties prop;
    
    int type, difficulte;
    
    String plateau;
    String[] diff;
    String[] types;
    String[] plateaux;
    
    public FabriqueArbitre(Properties p){
        prop =p;
        
        difficulte = Ordinateur.FACILE_HEURISTIQUE;
        diff = new String[4];
        diff[Ordinateur.FACILE_ALEATOIRE] = "Très Facile";
        diff[Ordinateur.FACILE_HEURISTIQUE] = "Facile";
        diff[Ordinateur.MOYEN] = "Normal";
        diff[Ordinateur.DIFFICILE] = "Difficile";
        
        type = SIMULATION;
        types = new String[4];
        types[LOCAL_JVJ] = "Joueur vs Joueur";
        types[LOCAL_JVIA] = "Joueur vs IA";
    }
    
    public Arbitre nouveau(){
        switch(type){
            case LOCAL_JVJ:
                return new Local(prop, type, difficulte);
            case LOCAL_JVIA:
                return new Local(prop, type, difficulte);
            case SIMULATION:
                return new SimulationIA(prop, difficulte);
            case RESEAU_CLIENT:
                return new ReseauClient(prop);
            case RESEAU_SERVER:
                return new ReseauServer(prop);
            default:
                return null;
        }
    }
    
    public void initType(int t){
        type = t;
    }
    public void initDiff(int t){
        difficulte = t;
    }
    public void initP(String p){
        plateau=p;
    }
    
    public String[] types(){
        return types;
    }
    public String[] difficultes(){
        return diff;
    }
    public String[] plateaux(){
        return plateaux;
    }
    
}
