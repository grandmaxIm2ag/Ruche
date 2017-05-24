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
public class ComportementA implements Runnable{
    Emulateur emu;
    Heuristique heurs;
    int me;
    int searchDepth;
    HeurPartage h;
    int idx;
    Coup[] coups;
    Coup cm;
    boolean min;
    
    public ComportementA(boolean b, int i, int pronf, HeurPartage h2, Coup[] d2, int joueur, Heuristique h3, Emulateur m, Coup cm){
        emu = m;
        idx = i;
        searchDepth = pronf;
        h = h2;
        coups = d2;
        min = b;
        me = joueur;
        heurs = h3;
        this.cm = cm;
    }
    
    
   public int Max(Emulateur emu,int profondeur, Coup[] d, Coup cp,int alpha,int beta){
        System.out.println("appel max : "+(profondeur));
        if(searchDepth - profondeur <= 0)
            return heurs.EvalPlateau(emu, d,(Ordinateur)emu.joueurs[me], cp);
        int max_poids = AI.MIN;
        for(int i=0;i < d.length;i++){
                System.out.println("max "+i+" "+d[i]);
                emu.joue(d[i]);
                Coup [] cpt = emu.PossibleMoves();
                if(cpt != null && cpt.length != 0){
                    int tmp = Min(emu.clone(),profondeur+1, cpt,d[i],Math.max(max_poids, alpha),beta);
                    if(tmp > max_poids){
                        max_poids = tmp;
                    }
                    if(max_poids >= beta) /* Coupure Beta */
                        return max_poids;
                }
                emu.precedent();
            }
        return max_poids;
    }
    
    public int Min(Emulateur emu,int profondeur, Coup[] d,Coup cp,int alpha,int beta){
        System.out.println("appel min : "+ profondeur);
        if(searchDepth - profondeur <= 0)
            return heurs.EvalPlateau(emu, d, (Ordinateur)emu.joueurs[me],cp);
        int min_poids = AI.MAX;
        for(int i=0;i < d.length ;i++){      
                System.out.println("min "+i+" "+d[i]);
                emu.joue(d[i]);
                Coup [] cpt = emu.PossibleMoves();
                if(cpt != null && cpt.length != 0){
                    int tmp = Max(emu.clone(),profondeur+1,cpt,d[i],alpha,Math.min(min_poids, beta));
                    if(tmp < min_poids){
                        min_poids = tmp;
                    }
                    if(alpha >= min_poids) /* Coupure Alpha */
                        return min_poids;
                }
                emu.precedent();
            }
        return min_poids;
    }
    

    @Override
    public void run() {
        //System.out.println(idx+" commence");
        if(min){
            h.ajout(idx, Min(emu,1, coups,cm,h.value(),AI.MAX));
        }else{
            h.ajout(idx, Max(emu,1, coups,cm,h.value(),AI.MAX));
        }
        //System.out.println(idx+" fini");
    }
}
