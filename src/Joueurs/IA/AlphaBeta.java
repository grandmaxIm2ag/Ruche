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
       // System.out.println("j0 "+a.joueur(0).pions()[0]);
        em = new Emulateur(a);
        heurs.SetConf(configurations);
        em.SetConf(configurations);
        min_poids = new int[searchDepth];
        min_poids = new int[searchDepth];
    }
    
    
     public Coup nextmove(){
        max_poids[0] = AI.MIN;
        int meilleur_coup = 0;
        //List<Thread> threads = new ArrayList();   
        
        for(int i = 0; i < cps.length;i++){
            em.joue(cps[i]);
            Coup [] cpt = em.PossibleMoves();
            if(cpt != null && cpt.length != 0){
                /* Affichage des coups possibles.
                for(int k = 0; k < cpt.length;k++)
                 System.out.print(cpt[k]+"  ");
                System.out.println(cpt.length);*/

                int hr = Min(em/*.clone()*/,1, cpt);
                if(hr > max_poids[0]){
                    max_poids[0] = hr;
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
        max_poids[profondeur] = AI.MIN;
        profondeur++;
        for(int i=0;i < d.length;i++){
            //System.out.println("max "+i+" "+d[i]);
            emu.joue(d[i]);
            Coup [] cpt = emu.PossibleMoves();
            if(cpt != null && cpt.length != 0){
                int tmp = Min(emu/*.clone()*/,profondeur, cpt);
                if(tmp > max_poids[profondeur-1]){
                    max_poids[profondeur-1] = tmp;
                }
            }
            emu.precedent();
        }
        return max_poids[profondeur-1];
    }
    
    public int Min(Emulateur emu,int profondeur, Coup[] d){
       // System.out.println("appel min : "+ profondeur);
        if(searchDepth - profondeur <= 0)
            return heurs.EvalPlateau(emu, d, me);
        min_poids[profondeur] = AI.MAX;
        profondeur++;
        for(int i=0;i < d.length;i++){
            //System.out.println("min "+i+" "+d[i]);
            emu.joue(d[i]);
            Coup [] cpt = emu.PossibleMoves();
            if(cpt != null && cpt.length != 0){
                int tmp = Max(emu/*.clone()*/,profondeur,cpt);
                if(tmp < min_poids[profondeur-1]){
                    min_poids[profondeur-1] = tmp;
                }
            }
            emu.precedent();
        }
        return min_poids[profondeur-1];
    }
}
