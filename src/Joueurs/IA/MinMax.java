/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Joueurs.IA;

import Joueurs.Ordinateur;
import Modele.Arbitres.*;
import Modele.Coup;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

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
       // System.out.println("j0 "+a.joueur(0).pions()[0]);
        em = new Emulateur(a, me.numJ());
    }
    
    
     public Coup nextmove(){
        int max_poids = AI.MIN;
        int meilleur_coup = 0;
        Thread[] threads = new Thread[cps.length];   
        
        HeurPartage h = new HeurPartage(false);
        
        for(int i = 0; i < cps.length;i++){
            em.joue(cps[i]);
            Coup [] cpt = em.PossibleMoves();
            
            threads[i] = new Thread(new Emulateur(false, i, searchDepth, h ,cpt, em.nbCoup,em.joueurs,em.jCourant(),em.m,em.historique, em.me, heurs));
            threads[i].start();
            try {
                threads[i].join();
            } catch (InterruptedException ex) {
                Logger.getLogger(MinMax.class.getName()).log(Level.SEVERE, null, ex);
            }
            /*if(cpt != null && cpt.length != 0){
                /* Affichage des coups possibles.
                for(int k = 0; k < cpt.length;k++)
                 System.out.print(cpt[k]+"  ");
                System.out.println(cpt.length);*/

               /* int hr = Min(em/*.clone()*,1, cpt);
                if(hr > max_poids){
                    max_poids = hr;
                    meilleur_coup = i;      
                }
            }*/
            
            em.precedent();
        }
        System.out.println(threads.length+" threads créés");
        boolean b = true;
        while(b){
            b=false;
            for(int i=0; i<threads.length; i++)
                b|=threads[i].isAlive();
        }
        return cps[h.indCoup()];
        
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
