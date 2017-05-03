/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Joueurs;

import Modele.Arbitre;
import Modele.Coup;

/**
 *
 * @author grandmax
 */
public class Ordinateur extends Joueur{
    public final static int FACILE=0;
    public final static int MOYEN=1;
    public final static int DIFFICILE=2;
    
    int difficulte;
    public Ordinateur(boolean m, int d) {
        super(m);
        difficulte = d;
    }
    
    public Coup coup(Arbitre a){
       
        return null;
    }
    
}
