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
    Emulateur em;
    Coup[] cps;
    /**
     *
     * @param me
     * @param a
     * @param heuristicFunction
     * @param searchDepth
     * @param start
     * @param cp
     */
    public MinMax(Ordinateur me, Arbitre a, Heuristique heuristicFunction, int searchDepth, long start, Coup[] cp) {
        super(me, a, heuristicFunction, searchDepth, start);
        cps = cp;
        em = new Emulateur(a);
    }
    
    
     public Coup nextmove(){
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
    
    public int Max(Emulateur emu,int profondeur, Coup[] d){
       // System.out.println("appel max : "+(profondeur));
        if(searchDepth - profondeur <= 0)
            return heurs.EvalPlateau(emu, d, me);
        int max_poids = AI.MIN;
        profondeur++;
        for(int i=0;i < d.length;i++){
            //System.out.println("max "+i+" "+d[i]);
            emu.joue(d[i]);
            Coup [] cpt = emu.PossibleMoves();
            if(cpt != null && cpt.length != 0){
                int tmp = Min(emu/*.clone()*/,profondeur, cpt);
                if(tmp > max_poids){
                    max_poids = tmp;
                }
            }
            emu.precedent();
        }
        return max_poids;
    }
    
    public int Min(Emulateur emu,int profondeur, Coup[] d){
       // System.out.println("appel min : "+ profondeur);
        if(searchDepth - profondeur <= 0)
            return heurs.EvalPlateau(emu, d, me);
        int min_poids = AI.MAX;
        profondeur++;
        for(int i=0;i < d.length;i++){
            //System.out.println("min "+i+" "+d[i]);
            emu.joue(d[i]);
            Coup [] cpt = emu.PossibleMoves();
            if(cpt != null && cpt.length != 0){
                int tmp = Max(emu/*.clone()*/,profondeur,cpt);
                if(tmp < min_poids){
                    min_poids = tmp;
                }
            }
            emu.precedent();
        }
        return min_poids;
    }
}