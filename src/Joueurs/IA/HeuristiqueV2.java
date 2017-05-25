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
    int mePossibleDepl;
    int meTokensOnBoard;
    int HexesFilledAroundMyQueen;
    
    int otherPossibleDepl;
    int otherTokensOnBoard;
    int HexesFilledAroundOpposingQueen;   
    
    private void freeBugs(Plateau m, Ordinateur me) {
        List<Coup> dep = null;
        List<Coup> depa = null;
        for(Map.Entry<Point,Case> entry : m.matrice.entrySet()) {
            
            if(entry.getValue().utilise() && entry.getValue().tete().joueur() == me.numJ()) {
                   dep = m.deplacementPossible(entry.getValue().tete());
                   if(dep !=null && !dep.isEmpty())
                        meTokensOnBoard++;
            }
            
            if(entry.getValue().utilise() && entry.getValue().tete().joueur() == me.numAdversaire()) {
                   depa = m.deplacementPossible(entry.getValue().tete());
                   if(depa !=null && !depa.isEmpty())
                        otherTokensOnBoard++;
            }
        }   
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
    
    
    public void nbLiberteesReine(Plateau p, Ordinateur me){
        if(p.reine(me.numJ())==null || p.voisins().get(p.reine(me.numJ()))==null){
            HexesFilledAroundMyQueen = 0;
        }else{    
            HexesFilledAroundMyQueen = 6-p.voisins().get(p.reine(me.numJ())).size();
        }
        
        if(p.reine(me.numAdversaire())==null || p.voisins().get(p.reine(me.numAdversaire()))==null){
            HexesFilledAroundOpposingQueen = 0;
        }else{    
            HexesFilledAroundOpposingQueen = 6-p.voisins().get(p.reine(me.numAdversaire())).size();
        }
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
        
        mePossibleDepl = d.length;
        meTokensOnBoard = 0;
        HexesFilledAroundMyQueen = 0;
    
        otherPossibleDepl = depl(a,p,me.numAdversaire());
        otherTokensOnBoard = 0;
        HexesFilledAroundOpposingQueen = 0; 
        heurs = 0;
        
        nbLiberteesReine(p, me);
        freeBugs(p, me);

        heurs = 10*( HexesFilledAroundMyQueen - HexesFilledAroundOpposingQueen)
                +  (mePossibleDepl - otherPossibleDepl)
                + 1*(meTokensOnBoard - otherTokensOnBoard);
        configurations.put(p.hashCode(), heurs);
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
        
        mePossibleDepl = d.length;
        meTokensOnBoard = 0;
        HexesFilledAroundMyQueen = 0;
    
        otherPossibleDepl = depl(a,a.m,me.numAdversaire());
        otherTokensOnBoard = 0;
        HexesFilledAroundOpposingQueen = 0; 
        heurs = 0;
        
        nbLiberteesReine(a.m, me);
        freeBugs(a.m, me);
        
        heurs = ( 50*HexesFilledAroundMyQueen - 100*HexesFilledAroundOpposingQueen)
               + (mePossibleDepl - otherPossibleDepl)
                + 2*(meTokensOnBoard - otherTokensOnBoard);
        
        if(a.m.reine(me.numJ())!=null && (a.m.matrice().get(a.m.reine(me.numJ())).tete().joueur()!=me.numJ() || a.m.matrice().get(a.m.reine(me.numJ())).tete().type()!=Insecte.REINE ))
            heurs-=1000;
        if(a.m.reine(me.numAdversaire())!=null && (a.m.matrice().get(a.m.reine(me.numAdversaire())).tete().joueur()!=me.numAdversaire() || a.m.matrice().get(a.m.reine(me.numAdversaire())).tete().type()!=Insecte.REINE ))
            heurs+=1000;
        if(j instanceof Depot)
            heurs+= a.GetValue(((Depot) j).type());
         
        configurations.put(a.m.hashCode(), heurs);
        return heurs;
    }
}
