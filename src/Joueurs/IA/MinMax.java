/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Joueurs.IA;

import Joueurs.Ordinateur;
import Modele.Arbitres.*;
import Modele.Coup;

/**
 *
 * @author hadjadjl
 */
public class MinMax extends AI {

    public MinMax(Ordinateur me, Arbitre a, Heuristique heuristicFunction, int searchDepth, int maxTimeInMillis) {
        super(me, a, heuristicFunction, searchDepth, maxTimeInMillis);
    }
    
    /*
     public Coup nextmove(int profondeur){
        int max_poids = -10000;
        int meilleur_coup = 1;
        int hauteur = profondeur;
     
        for(int i = 1; i < casesJouables.size();i++){
            LinkedList<Integer> casestmp = (LinkedList<Integer>) casesJouables.clone();
            coupIA(casesJouables.get(i));
            int tmp = Min(profondeur -1, hauteur);
            if(tmp > max_poids){
                max_poids = tmp;
                meilleur_coup = i;
                
            }
            casesJouables =  casestmp;
            meilleur_coup = casesJouables.get(i);
        }
        Point res = convertToPoint(meilleur_coup);
        res.fixe((int)res.y(),(int) res.x());
        System.out.println("coup choisi : "+meilleur_coup+" , "+res);
        return res;
    }
    
    public int Max(int profondeur,int hauteur){
        if(profondeur == 0)
            return testconfig(hauteur - profondeur);
        int max_poids = -100000;
        
        for(int i=0;i<casesJouables.size();i++){
            LinkedList<Integer> casestmp = (LinkedList<Integer>) casesJouables.clone();
            coupIA(casesJouables.get(i));
            int tmp = Min(profondeur-1, hauteur);
            if(tmp > max_poids){
                max_poids = tmp;
            }
            casesJouables = casestmp;
        }
        return max_poids;
    }
    
    public int Min(int profondeur,int hauteur){
        if(profondeur == 0)
            return testconfig(hauteur - profondeur);
        int min_poids = 100000;
        
        for(int i=0;i<casesJouables.size();i++){
            LinkedList<Integer> casestmp = (LinkedList<Integer>) casesJouables.clone();
            coupIA(casesJouables.get(i));
            int tmp = Max(profondeur-1, hauteur);
            if(tmp < min_poids){
                min_poids = tmp;
            }
            casesJouables = casestmp;
        }
        return min_poids;
    }
    */
}
