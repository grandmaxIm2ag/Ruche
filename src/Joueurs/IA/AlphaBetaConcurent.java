/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Joueurs.IA;

import Joueurs.Ordinateur;
import Modele.Arbitres.*;
import Modele.Coup;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author hadjadjl
 */
public class AlphaBetaConcurent extends AI {
    Emulateur em;
    Coup[] cps;
    int am;
    /**
     * @param me
     * @param a
     * @param heuristicFunction
     * @param searchDepth
     * @param maxTimeInMillis
     * @param cp
     */
    public AlphaBetaConcurent(Ordinateur me, Arbitre a, Heuristique heuristicFunction, int searchDepth, int maxTimeInMillis, Coup[] cp) {
        super(me, a, heuristicFunction, searchDepth, maxTimeInMillis);
        cps = cp;
        this.am = me.numJ();
        em = new Emulateur(a);
    }
    
    
    @Override
     public Coup nextmove(){
        start = System.currentTimeMillis(); 
        int max_poids = AI.MIN;
        int meilleur_coup = 0;
        Thread[] threads = new Thread[cps.length];           
        HeurPartage h = new HeurPartage(false);
        
        for(int i = 0; i < cps.length;i++){
            Emulateur m = em.clone();
            m.joue(cps[i]);
            if(heurs.win(m, me))
                return cps[i];
            Coup [] cpt = m.PossibleMoves();       
            threads[i] = new Thread(new ComportementA(false, i, searchDepth, h ,cpt,am, heurs, em, cps[i]));
            threads[i].start();
        }
        
        for(int i = 0; i < cps.length;i++){
            try {
                threads[i].join();
            } catch (InterruptedException ex) {
                Logger.getLogger(MinMaxConcurent.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
       // System.out.println(threads.length+" threads créés");
        boolean b = true;
        while(b){
            b=false;
            for(int i=0; i<threads.length; i++)
                b|=threads[i].isAlive();
        }
        return cps[h.indCoup()];       
    }
}
