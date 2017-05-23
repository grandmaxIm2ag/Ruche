/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Joueurs.IA;

import Joueurs.Ordinateur;
import Modele.Arbitres.*;
import Modele.Coup;
import Modele.Plateau;
import java.util.Map;

/**
 *
 * @author hadjadjl
 */
public abstract class AI {
    
    public Map<Plateau, Integer> configurations;
    /**
     *
     */
    public final static int MAX = Integer.MAX_VALUE;        // valeur Maximale pour l'heursitique.

    /**
     *
     */
    public final static int MIN = Integer.MIN_VALUE + 1;    // valeur Minimale pour l'heuristique.
    
    /**
     *
     */
    protected Ordinateur me;

    /**
     *
     */
    protected Arbitre arb;
    //protected AIStatistics aiStats = new AIStatistics(this);

    /**
     *
     */
    protected Heuristique heurs;

    /**
     *
     */
    protected int searchDepth;      // Search limit in depth

    /**
     *
     */
    protected int maxTimeInMillis;  // Search limit in milliseconds

    /**
     *
     */
    protected long start; // Start time in millis when nextMove was called
    
    /**
     *
     * @param me
     * @param a
     * @param heuristicFunction
     * @param searchDepth
     * @param maxTimeInMillis
     */
    public AI(Ordinateur me, Arbitre a, Heuristique heuristicFunction, int searchDepth, int maxTimeInMillis) {
        this.me = me;
        this.arb = a;
        this.heurs = heuristicFunction;
        this.searchDepth = searchDepth;
        this.maxTimeInMillis = maxTimeInMillis;
        
    }   
        
    public boolean existConfig(Plateau p){
        return heurs.configurations.get(p)!=null;
    }
    
    public Coup nextmove(){
        start = System.currentTimeMillis();
        return null;
    }
}
