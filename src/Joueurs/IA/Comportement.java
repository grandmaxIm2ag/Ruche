/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package Joueurs.IA;

import Joueurs.Ordinateur;
import Modele.Coup;

/**
 *
 * @author lies
 */
public class Comportement implements Runnable{
    Emulateur emu;
    Heuristique heurs;
    int me;
    int searchDepth;
    HeurPartage h;
    int idx;
    Coup[] coups;
    boolean min;
    
    public Comportement(boolean b, int i, int pronf, HeurPartage h2, Coup[] d2, int joueur, Heuristique h3, Emulateur m){
        emu = m;
        idx = i;
        searchDepth = pronf;
        h = h2;
        coups = d2;
        min = b;
        me = joueur;
        heurs = h3;
    }
    
    
    public int Max(int profondeur, Coup[] d, Coup p){
        if(searchDepth - profondeur <= 0)
            return heurs.EvalPlateau(emu, d, (Ordinateur)emu.joueurs[me], p);
        
        int max_poids = AI.MIN;

        for(int i=0;i < d.length;i++){
            //System.out.println("max "+i+" "+d[i]);
            emu.joue(d[i]);
            Coup [] cpt = emu.PossibleMoves();
            if(cpt != null && cpt.length != 0){
                int tmp = Min(profondeur+1, cpt, d[i]);
                if(tmp > max_poids){
                    max_poids = tmp;
                }
            }
            emu.precedent();
        }
        return max_poids;
    }
    
    public int Min(int profondeur, Coup[] d, Coup p){
       // System.out.println("appel min : "+ profondeur);
        if(searchDepth - profondeur <= 0)
            return heurs.EvalPlateau(emu, d,(Ordinateur)emu.joueurs[me], p);
        int min_poids = AI.MAX;

        for(int i=0;i < d.length;i++){
            //System.out.println("min "+i+" "+d[i]);
            emu.joue(d[i]);
            Coup [] cpt = emu.PossibleMoves();
            if(cpt != null && cpt.length != 0){
                int tmp = Max(profondeur+1,cpt,d[i]);
                if(tmp < min_poids){
                    min_poids = tmp;
                } 
            }
            emu.precedent();
        }
        return min_poids;
    }
    

    @Override
    public void run() {
        //System.out.println(idx+" commence");
        if(min){
            h.ajout(idx, Min(1, coups,null));
        }else{
            h.ajout(idx, Max(1, coups,null));
        }
        //System.out.println(idx+" fini");
    }
}
