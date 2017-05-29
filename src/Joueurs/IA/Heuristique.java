/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Joueurs.IA;

import Joueurs.Ordinateur;
import Modele.Arbitres.Arbitre;
import Modele.Coup;
import Modele.Deplacement;
import Modele.Plateau;
import Modele.Point;
import java.util.HashMap;
import java.util.Map;

/**
 *
 * @author hadjadjl
 */
public abstract class Heuristique {
    Map<Integer, Integer> configurations;
    int heurs;
    Coup Cjouer;
    
    public Heuristique(){
        configurations = new HashMap();
        heurs = 0;
    }
    
    public void SetConf(Map<Integer, Integer> conf){
        configurations = conf;
    }
    
    public int EvalPlateau(Arbitre a, Coup[] d, Plateau p, Ordinateur me,Coup j){
        return 0;
    }
    
    public int EvalPlateau(Emulateur a, Coup[] d, Ordinateur me,Coup j){
        return 0;
    }
    
    public boolean win(Emulateur a, Ordinateur me){
        return a.m.estEncerclee(me.numAdversaire());
    }
    
    private int hex_distance(Point a, Point b){
    return (int) (Math.abs(a.x() - b.x()) 
           + Math.abs(a.x() + a.y() - b.x() - b.y())
           + Math.abs(a.y() - b.y()) )/2;
    }
    
    public int EvalCoup(Emulateur a, Ordinateur me, Coup cp){
        if(cp instanceof Deplacement){
           Deplacement cm = (Deplacement) cp;
           Point rVs = a.m.reine(me.numAdversaire());
           if(rVs != null)
           return hex_distance(cm.destination(), rVs);
           else
               return 0;
        }       
        return 0;
    }
}
