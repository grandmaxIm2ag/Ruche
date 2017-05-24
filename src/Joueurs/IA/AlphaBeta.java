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
    int[] max_poids;
    int[] min_poids;
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
        min_poids = new int[searchDepth];
        max_poids = new int[searchDepth];
        for(int i=0;i<searchDepth;i++){
            min_poids[i] = AI.MAX;
            max_poids[i] = AI.MIN;
        }
    }
    
    
    @Override
     public Coup nextmove(){
        start = System.currentTimeMillis(); 

        int meilleur_coup = 0;   
        
        for(int i = 0; i < cps.length;i++){
            em.joue(cps[i]);
            Coup [] cpt = em.PossibleMoves();
            if(cpt != null && cpt.length != 0){
                int hr = Min(em,1, cpt ,cps[i]);
                if(hr > max_poids[0]){
                    max_poids[0] = hr;
                    meilleur_coup = i;      
                }
            }
            em.precedent();
        }
        return cps[meilleur_coup];
    }
    
    public int Max(Emulateur emu,int profondeur, Coup[] d, Coup cp){
        System.out.println("appel max : "+(profondeur));
        if(searchDepth - profondeur <= 0)
            return heurs.EvalPlateau(emu, d, me,cp);
        profondeur++;
        for(int i=0;i < d.length;i++){
            if(min_poids[profondeur-2] >= max_poids[profondeur-1]){
                System.out.println("max "+i+" "+d[i]);
                emu.joue(d[i]);
                Coup [] cpt = emu.PossibleMoves();
                if(cpt != null && cpt.length != 0){
                    int tmp = Min(emu,profondeur, cpt,d[i]);
                    if(tmp > max_poids[profondeur-1]){
                        max_poids[profondeur-1] = tmp;
                    }
                }
                emu.precedent();
            }
        }
        return max_poids[profondeur-1];
    }
    
    public int Min(Emulateur emu,int profondeur, Coup[] d,Coup cp){
        System.out.println("appel min : "+ profondeur);
        if(searchDepth - profondeur <= 0)
            return heurs.EvalPlateau(emu, d, me,cp);
        profondeur++;
        for(int i=0;i < d.length;i++){      
            if(max_poids[profondeur-2] <= min_poids[profondeur-1]){ 
                System.out.println("min "+i+" "+d[i]);
                emu.joue(d[i]);
                Coup [] cpt = emu.PossibleMoves();
                if(cpt != null && cpt.length != 0){
                    int tmp = Max(emu,profondeur,cpt,d[i]);
                    if(tmp < min_poids[profondeur-1]){
                        min_poids[profondeur-1] = tmp;
                    }
                }
                emu.precedent();
            }
        }
        return min_poids[profondeur-1];
    }
}
