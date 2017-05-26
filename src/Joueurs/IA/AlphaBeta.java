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
    }
    
    
    @Override
     public Coup nextmove(){
        start = System.currentTimeMillis(); 
        int meilleur_coup = 0;  
        int max_poids =AI.MIN;
       for(int i = 0; i < cps.length ;i++){
           // System.out.println("d["+i+"] = "+cps[i]);
            Emulateur m = em.clone();
            m.joue(cps[i]);
            if(heurs.win(m, me))
                return cps[i];
            Coup [] cpt = m.PossibleMoves();
            if(cpt != null && cpt.length != 0){
                int hr = Min(m,1, cpt ,cps[i],max_poids,AI.MAX);
                if(hr > max_poids){
                    max_poids = hr;
                    meilleur_coup = i;      
                }
            }
        }
        return cps[meilleur_coup];
    }
    
    public int Max(Emulateur emu,int profondeur, Coup[] d, Coup cp,int alpha,int beta){
       // System.out.println("appel max : "+(profondeur));
        if(searchDepth - profondeur <= 0)
            return heurs.EvalPlateau(emu, d, me,cp);
        int max_poids = AI.MIN;
        for(int i=0;i < d.length;i++){
              //  System.out.println("max "+i+" "+d[i]);
                Emulateur m = emu.clone();
                m.joue(d[i]);
                Coup [] cpt = m.PossibleMoves();
                if(cpt != null && cpt.length != 0){
                    int tmp = Min(m,profondeur+1, cpt,d[i],Math.max(max_poids, alpha),beta);
                    max_poids = Math.max(max_poids, tmp);
                    if(max_poids >= beta) /* Coupure Beta */
                        return max_poids;
                }
            }
        return max_poids;
    }
    
    public int Min(Emulateur emu,int profondeur, Coup[] d,Coup cp,int alpha,int beta){
       // System.out.println("appel min : "+ profondeur);
        if(searchDepth - profondeur <= 0)
            return heurs.EvalPlateau(emu, d, me,cp);
        int min_poids = AI.MAX;
        for(int i=0;i < d.length ;i++){      
               // System.out.println("min "+i+" "+d[i]);
                Emulateur m = emu.clone();
              //  m.getPlateau().afficheGraphe(m.getPlateau().voisins());
                m.joue(d[i]);
                Coup [] cpt = m.PossibleMoves();
                if(cpt != null && cpt.length != 0){
                    int tmp = Max(m,profondeur+1,cpt,d[i],alpha,Math.min(min_poids, beta));
                    min_poids = Math.min(min_poids, tmp);
                    if(alpha >= min_poids) /* Coupure Alpha */
                        return min_poids;
                }
            }
        return min_poids;
    }
}
