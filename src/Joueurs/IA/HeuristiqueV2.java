/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Joueurs.IA;

import Joueurs.Ordinateur;
import Modele.Arbitres.*;
import Modele.Case;
import Modele.Coup;
import Modele.Depot;
import Modele.Insecte;
import Modele.Plateau;
import Modele.Point;
import java.util.List;
import java.util.Map;

/**
 *
 * @author hadjadjl
 */
public class HeuristiqueV2 extends Heuristique {
    
    private int freeBugs(Plateau m,int numJoueur) {
        int fr=0;
        List<Coup> dep = null;
        for(Map.Entry<Point,Case> entry : m.matrice.entrySet()) {
            if(entry.getValue().utilise() && entry.getValue().tete().joueur() == numJoueur) {
                   dep = m.deplacementPossible(entry.getValue().tete());
                   if(dep !=null && !dep.isEmpty())
                        fr++;
            }
        }
        return fr;   
    }
    
    private int depl(Arbitre a, Plateau m, int numJoueur) {
        Coup [] depl = m.deplacementPossible(numJoueur);
        int dpl = 0;
        if(depl != null)
            dpl = depl.length;
        
        for(int i=0; i<a.joueur(numJoueur).pions().length; i++){
                if(a.joueur(numJoueur).pions()[i]!=0){
                   depl = m.depotPossible(numJoueur, i);
                   if(depl != null)
                       dpl += depl.length;
                }
                
            }
        
        return dpl;
    }
    
    private int depl(Emulateur a, Plateau m, int numJoueur) {
        Coup [] depl = m.deplacementPossible(numJoueur);
        int dpl = 0;
        if(depl != null)
            dpl = depl.length;
        
        for(int i=0; i<a.joueur(numJoueur).pions().length; i++){
                if(a.joueur(numJoueur).pions()[i]!=0){
                   depl = m.depotPossible(numJoueur, i);
                   if(depl != null)
                       dpl += depl.length;
                }
                
            }
        
        return dpl;
    }
    
    @Override
    public int EvalPlateau(Arbitre a, Coup[] d, Plateau p, Ordinateur me,Coup j) {
        
        // Un null est considérer comme une défaite
        Boolean meWon = p.estEncerclee(me.numAdversaire());
        Boolean otherWon = p.estEncerclee(me.numJ());
             
        if ( meWon && otherWon) {
            if (me.numJ() == a.jCourant()) {
                return AI.MIN + 5;
            }  else {
                return AI.MAX - 5;
            }
        } else if (meWon) {
            return AI.MAX;
        } else if(otherWon) {
            return AI.MIN;
        }
        if(configurations.get(p)!=null){
            return configurations.get(p);
        } 
        heurs = 0;
        int mePossibleDepl = d.length;
        int meTokensOnBoard = freeBugs(p,me.numJ());
        int HexesFilledAroundMyQueen = me.nbLiberteesReine(p, me.numJ());
        
        int otherPossibleDepl = depl(a,p,me.numAdversaire());
        int otherTokensOnBoard = freeBugs(p,me.numAdversaire());
        int HexesFilledAroundOpposingQueen = me.nbLiberteesReine(p, me.numAdversaire());

        heurs = 10*( HexesFilledAroundMyQueen - HexesFilledAroundOpposingQueen)
                +  (mePossibleDepl - otherPossibleDepl)
                + 1*(meTokensOnBoard - otherTokensOnBoard);
        configurations.put(p, heurs);
        return heurs;
    }
    
    @Override
    public int EvalPlateau(Emulateur a, Coup[] d, Ordinateur me,Coup j) {
        
        // Un null est considérer comme une défaite
        Boolean meWon = a.m.estEncerclee(me.numAdversaire());
        Boolean otherWon = a.m.estEncerclee(me.numJ());
               
        if ( meWon && otherWon) {
            if (me.numJ() == a.jCourant()) {
                return AI.MIN + 5;
            } else {
                return AI.MAX - 5;
            }
        } else if (meWon) {
            return AI.MAX;
        } else if(otherWon) {
            return AI.MIN;
        }
        if(configurations.get(a.m)!=null){
            return configurations.get(a.m);
        } 
        heurs = 0;
        int mePossibleDepl = d.length;
        int meTokensOnBoard = freeBugs(a.m,me.numJ());
        int HexesFilledAroundMyQueen = me.nbLiberteesReine(a.m, me.numJ());
        
        int otherPossibleDepl = depl(a,a.m,me.numAdversaire());
        int otherTokensOnBoard = freeBugs(a.m,me.numAdversaire());
        int HexesFilledAroundOpposingQueen = me.nbLiberteesReine(a.m, me.numAdversaire());

        heurs = ( 50*HexesFilledAroundMyQueen - 100*HexesFilledAroundOpposingQueen)
               + (mePossibleDepl - otherPossibleDepl)
                + 2*(meTokensOnBoard - otherTokensOnBoard);
        
        if(a.m.reine(me.numJ())!=null && (a.m.matrice().get(a.m.reine(me.numJ())).tete().joueur()!=me.numJ() || a.m.matrice().get(a.m.reine(me.numJ())).tete().type()!=Insecte.REINE ))
            heurs-=1000;
        if(a.m.reine(me.numAdversaire())!=null && (a.m.matrice().get(a.m.reine(me.numAdversaire())).tete().joueur()!=me.numAdversaire() || a.m.matrice().get(a.m.reine(me.numAdversaire())).tete().type()!=Insecte.REINE ))
            heurs+=1000;
        if(j instanceof Depot)
            heurs+= a.GetValue(((Depot) j).type());
         
        configurations.put(a.m, heurs);
        return heurs;
    }
}
