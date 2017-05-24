/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Joueurs.IA;

import Joueurs.Ordinateur;
import Modele.Arbitres.Arbitre;
import Modele.Coup;
/**
 *
 * @author hadjadjl
 */
public class AlphaBeta extends AI {
    
    Emulateur em;
    Coup[] cps;
 //   int[] max_poids;
   // int[] min_poids;
    /**
     *
     * @param me
     * @param a
     * @param heuristicFunction
     * @param searchDepth
     * @param maxTimeInMillis
     * @param cp
     */
    public AlphaBeta(Ordinateur me, Arbitre a, Heuristique heuristicFunction, int searchDepth, int maxTimeInMillis, Coup[] cp) {
        super(me, a, heuristicFunction, searchDepth, maxTimeInMillis);
        cps = cp;
        em = new Emulateur(a);
        heurs.SetConf(configurations);
   /*     min_poids = new int[searchDepth];
        max_poids = new int[searchDepth];
        for(int i=0;i<searchDepth;i++){
            min_poids[i] = AI.MAX;
            max_poids[i] = AI.MIN;
        }*/
    }
    
    
    @Override
     public Coup nextmove(){
        start = System.currentTimeMillis(); 

        int meilleur_coup = 0;  
        int max_poids =AI.MIN;
       for(int i = 0; i < cps.length ;i++){
            em.joue(cps[i]);
            Coup [] cpt = em.PossibleMoves();
            if(cpt != null && cpt.length != 0){
                int hr = Min(em.clone(),1, cpt ,cps[i],max_poids,AI.MAX);
                if(hr > max_poids){
                    max_poids = hr;
                    meilleur_coup = i;      
                }
            }
            em.precedent();
        }
        return cps[meilleur_coup];
    }
    
    public int Max(Emulateur emu,int profondeur, Coup[] d, Coup cp,int alpha,int beta){
        System.out.println("appel max : "+(profondeur));
        if(searchDepth - profondeur <= 0)
            return heurs.EvalPlateau(emu, d, me,cp);
        int max_poids = AI.MIN;
        for(int i=0;i < d.length;i++){
                System.out.println("max "+i+" "+d[i]);
                emu.joue(d[i]);
                Coup [] cpt = emu.PossibleMoves();
                if(cpt != null && cpt.length != 0){
                    int tmp = Min(emu.clone(),profondeur+1, cpt,d[i],Math.max(max_poids, alpha),beta);
                    if(tmp > max_poids){
                        max_poids = tmp;
                    }
                    if(max_poids >= beta) /* Coupure Beta */
                        return max_poids;
                }
                emu.precedent();
            }
        return max_poids;
    }
    
    public int Min(Emulateur emu,int profondeur, Coup[] d,Coup cp,int alpha,int beta){
        System.out.println("appel min : "+ profondeur);
        if(searchDepth - profondeur <= 0)
            return heurs.EvalPlateau(emu, d, me,cp);
        int min_poids = AI.MAX;
        for(int i=0;i < d.length ;i++){      
                System.out.println("min "+i+" "+d[i]);
                emu.joue(d[i]);
                Coup [] cpt = emu.PossibleMoves();
                if(cpt != null && cpt.length != 0){
                    int tmp = Max(emu.clone(),profondeur+1,cpt,d[i],alpha,Math.min(min_poids, beta));
                    if(tmp < min_poids){
                        min_poids = tmp;
                    }
                    if(alpha >= min_poids) /* Coupure Alpha */
                        return min_poids;
                }
                emu.precedent();
            }
        return min_poids;
    }
}
