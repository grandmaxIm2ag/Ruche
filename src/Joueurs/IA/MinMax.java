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

    /**
     *
     * @param me
     * @param a
     * @param heuristicFunction
     * @param searchDepth
     * @param maxTimeInMillis
     */
    public MinMax(Ordinateur me, Arbitre a, Heuristique heuristicFunction, int searchDepth, int maxTimeInMillis) {
        super(me, a, heuristicFunction, searchDepth, maxTimeInMillis);
        cps = cp;
        em = new Emulateur(a);
    }
    
    
    @Override
     public Coup nextmove(){
        start = System.currentTimeMillis(); 
        int max_poids = AI.MIN;
        int meilleur_coup = 0;
           
        for(int i = 0; i < cps.length;i++){
            em.joue(cps[i]);
            Coup [] cpt = em.PossibleMoves();
            if(cpt != null && cpt.length != 0){
                /* Affichage des coups possibles.
                for(int k = 0; k < cpt.length;k++)
                 System.out.print(cpt[k]+"  ");
                System.out.println(cpt.length);*/
                 
                int hr = Min(em/*.clone()*/,1, cpt);
                if(hr > max_poids){
                    max_poids = hr;
                    meilleur_coup = i;      
                }
            }
            em.precedent();
        }
        return cps[meilleur_coup];
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
}
