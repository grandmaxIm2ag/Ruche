/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Joueurs.IA;

import Modele.Coup;

/**
 *
 * @author hadjadjl
 */
public class HeurPartage {
    Integer aJouer, value;
    boolean min;
    
    public HeurPartage(boolean b){
        min = b;
        aJouer=-1;
        if(min)
            value = Integer.MAX_VALUE;
        else
            value = Integer.MIN_VALUE;
    }
    
    public synchronized void ajout(int idx, int p){
        System.out.println(idx+" "+p+" "+aJouer+" "+value);
        if(min){
            if(p<=value){
                aJouer = idx;
                value = p;
            }
        }else{
            if(p>=value){
                aJouer = idx;
                value = p;
            }
        }
        System.out.println(idx+" passé "+aJouer);
    }
    
    public int indCoup(){
        return aJouer;
    }
}
