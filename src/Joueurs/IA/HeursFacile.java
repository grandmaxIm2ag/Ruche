/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Joueurs.IA;

import Joueurs.Ordinateur;
import Modele.Arbitres.Arbitre;
import Modele.Coup;
import Modele.Plateau;

/**
 *
 * @author lies
 */
public class HeursFacile extends Heuristique{
    
    @Override
    public int EvalPlateau(Arbitre a, Coup[] d, Plateau p, Ordinateur me, Coup j) {
        
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
        if(!me.reineLibre(p,me.numJ(), d)){
            heurs=heurs-2;
        }else{
            heurs=heurs+2;
        }
        if(!me.reineLibre(p,me.numAdversaire(), d)){
            heurs=heurs+2;
        }else{
            heurs=heurs-2;
        }
        heurs = heurs + me.nbLiberteesReine(p, me.numJ()) - me.nbLiberteesReine(p, me.numAdversaire());
        configurations.put(p, heurs);
        return heurs;
    }
    
    @Override
    public int EvalPlateau(Emulateur a, Coup[] d, Ordinateur me, Coup j) {
        
        // Un null est considérer comme une défaite
        Boolean meWon = a.m.estEncerclee(me.numAdversaire());
        Boolean otherWon = a.m.estEncerclee(me.numJ());
        
        
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
        if(configurations.get(a.m)!=null){
            return configurations.get(a.m);
        } 
        heurs = 0;
        if(!me.reineLibre(a.m,me.numJ(), d)){
            heurs=heurs-2;
        }else{
            heurs=heurs+2;
        }
        if(!me.reineLibre(a.m,me.numAdversaire(), d)){
            heurs=heurs+2;
        }else{
            heurs=heurs-2;
        }
        heurs = heurs + me.nbLiberteesReine(a.m, me.numJ()) - me.nbLiberteesReine(a.m, me.numAdversaire());
        configurations.put(a.m, heurs);
        return heurs;
    }
}
