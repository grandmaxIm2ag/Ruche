/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Joueurs.IA;

import Joueurs.Ordinateur;
import Modele.Arbitres.*;

/**
 *
 * @author hadjadjl
 */
public abstract class AI {
    public final static int MAX = Integer.MAX_VALUE;        // valeur Maximale pour l'heursitique.
    public final static int MIN = Integer.MIN_VALUE + 1;    // valeur Minimale pour l'heuristique.
    
    protected Ordinateur me;
    protected Arbitre arb;
    //protected AIStatistics aiStats = new AIStatistics(this);
    protected Heuristique heurs;
    protected int searchDepth;      // Search limit in depth
    protected int maxTimeInMillis;  // Search limit in milliseconds
    protected long start; // Start time in millis when nextMove was called
    
    public AI(Ordinateur me, Arbitre a, Heuristique heuristicFunction, int searchDepth, int maxTimeInMillis) {
        this.me = me;
        this.arb = a;
        this.heurs = heuristicFunction;
        this.searchDepth = searchDepth;
        this.maxTimeInMillis = maxTimeInMillis;
        
    }
    
}
