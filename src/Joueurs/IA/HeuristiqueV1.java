/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Joueurs.IA;

import Joueurs.Ordinateur;
import Modele.Arbitres.*;
import Modele.Case;
import Modele.Plateau;
import Modele.Point;
import java.util.Map;

/**
 *
 * @author hadjadjl
 */
public class HeuristiqueV1 extends Heuristique {
    
    private int free(Plateau m,int numJoueur) {
        int fr=0;
        for(Map.Entry<Point,Case> entry : m.matrice.entrySet()) {
            if(entry.getValue().utilise()) {
                if(entry.getValue().tete().joueur() == numJoueur)
                   fr++; 
            }
        }
        return fr;   
    }
    
    /**
     *
     * @param etat
     * @param me
     * @return
     */
    public int EvalPlateau(Arbitre etat, Ordinateur me) {
        
        // Un null est considérer comme une défaite
        Boolean meWon = etat.plateau().estEncerclee(me.numJ());
        Boolean otherWon = etat.plateau().estEncerclee((me.numJ()+1)%2);
        
        
        if ( meWon && otherWon) {
            if (me.numJ() == etat.jCourant()) {
                return AI.MIN + 5;
            }  else {
                return AI.MAX - 5;
            }
        } else if (meWon) {
            return AI.MAX;
        } else if(otherWon) {
            return AI.MIN;
        }

        int mePossibleDepl = me.d.length;
        int meTokensOnBoard = free(etat.plateau(),me.numJ());
        int meHexesFilledAroundOpposingQueen = me.nbLiberteesReine(etat.plateau(), me.numAdversaire());


        int otherPossibleDepl = etat.deplacementPossible(me.numAdversaire()).length;
        int otherTokensOnBoard = free(etat.plateau(),me.numAdversaire());
        int otherHexesFilledAroundOpposingQueen = me.nbLiberteesReine(etat.plateau(), me.numJ());

        return 10*(meHexesFilledAroundOpposingQueen - otherHexesFilledAroundOpposingQueen)
                + 2*(mePossibleDepl - otherPossibleDepl)
                + 1*(meTokensOnBoard - otherTokensOnBoard);
    }
}
