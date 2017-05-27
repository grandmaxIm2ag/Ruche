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
     * @param maxTimeInMillis
     * @param cp
     */
    public MinMax(Ordinateur me, Arbitre a, Heuristique heuristicFunction, int searchDepth, int maxTimeInMillis, Coup[] cp) {
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
            Emulateur m = em.clone();
            m.joue(cps[i]);
            if(heurs.win(m, me))
                return cps[i];
            Coup [] cpt = m.PossibleMoves();
            if(cpt != null && cpt.length != 0){                
                int hr = Min(m,1, cpt,cps[i]);
                if(hr > max_poids){
                    max_poids = hr;
                    meilleur_coup = i;      
                }
            }
        }
        return cps[meilleur_coup];
    }
    
    public int Max(Emulateur emu,int profondeur, Coup[] d, Coup c){
       // System.out.println("appel max : "+(profondeur));
        if(searchDepth - profondeur <= 0)
            return heurs.EvalPlateau(emu, d, me, c);
        int max_poids = AI.MIN;
        for(int i=0;i < d.length;i++){
            //System.out.println("max "+i+" "+d[i]);
            Emulateur m = emu.clone();
            m.joue(d[i]);
            Coup [] cpt = m.PossibleMoves();
            if(cpt != null && cpt.length != 0){
                int tmp = Min(m,profondeur+1, cpt, d[i]);
                max_poids = Math.max(max_poids, tmp);
            }
        }
        return max_poids;
    }
    
    public int Min(Emulateur emu,int profondeur, Coup[] d, Coup c){
       // System.out.println("appel min : "+ profondeur);
        if(searchDepth - profondeur <= 0)
            return heurs.EvalPlateau(emu, d, me, c);
        int min_poids = AI.MAX;
        for(int i=0;i < d.length;i++){
            //System.out.println("min "+i+" "+d[i]);
            Emulateur m = emu.clone();
            m.joue(d[i]);
            Coup [] cpt = m.PossibleMoves();
            if(cpt != null && cpt.length != 0){
                int tmp = Max(m,profondeur+1,cpt,d[i]);
                min_poids = Math.min(min_poids, tmp);
            }
        }
        return min_poids;
    }
}