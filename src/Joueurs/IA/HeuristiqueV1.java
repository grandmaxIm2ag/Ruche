/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Joueurs.IA;

import Joueurs.Ordinateur;
import Modele.Arbitre;
import Modele.Case;
import Modele.Coup;
import Modele.Plateau;
import Modele.Point;
import java.util.Map;

/**
 *
 * @author hadjadjl
 */
public class HeuristiqueV1 extends Heuristique {
    
    private int freeBugs(Plateau m,int numJoueur) {
        int fr=0;
        for(Map.Entry<Point,Case> entry : m.matrice.entrySet()) {
            if(entry.getValue().utilise()) {
                if(entry.getValue().tete().joueur() == numJoueur)
                   //fr+= m.deplacementPossible(entry.getValue().tete()).size();
                    fr++;
            }
        }
        return fr;   
    }
    
    private int depl(Arbitre a, Plateau m, int numJoueur) {
        int dpl = m.deplacementPossible(numJoueur).length;
        
        for(int i=0; i<a.joueur(numJoueur).pions().length; i++){
                if(a.joueur(numJoueur).pions()[i]!=0)
                    dpl+=m.depotPossible(numJoueur, i).length;
            }
        
        return dpl;
    }
    
    public int EvalPlateau(Arbitre a, Coup[] d, Plateau p, Ordinateur me) {
        
        // Un null est considérer comme une défaite
        Boolean meWon = p.estEncerclee(me.numJ());
        Boolean otherWon = p.estEncerclee((me.numJ()+1)%2);
        
        
        if ( meWon && otherWon) {
           // if (me.numJ() == etat.jCourant()) {
                return AI.MIN + 5;
           /* }  else {
                return AI.MAX - 5;
            }*/
        } else if (meWon) {
            return AI.MAX;
        } else if(otherWon) {
            return AI.MIN;
        }

        int mePossibleDepl = d.length;
        int meTokensOnBoard = freeBugs(p,me.numJ());
        int meHexesFilledAroundOpposingQueen = me.nbLiberteesReine(p, me.numAdversaire());
        
        int otherPossibleDepl = depl(a,p,me.numAdversaire());
        int otherTokensOnBoard = freeBugs(p,me.numAdversaire());
        int otherHexesFilledAroundOpposingQueen = me.nbLiberteesReine(p, me.numJ());

        return 10*(meHexesFilledAroundOpposingQueen - otherHexesFilledAroundOpposingQueen)
                + 2*(mePossibleDepl - otherPossibleDepl)
                + 1*(meTokensOnBoard - otherTokensOnBoard);
    }
}
