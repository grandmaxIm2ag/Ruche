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
import java.util.HashMap;
import java.util.Map;

/**
 *
 * @author hadjadjl
 */
public abstract class Heuristique {
    Map<Integer, Integer> configurations;
    int heurs;
    Coup Cjouer;
    
    public Heuristique(){
        configurations = new HashMap();
        heurs = 0;
    }
    
    public void SetConf(Map<Integer, Integer> conf){
        configurations = conf;
    }
    
    public int EvalPlateau(Arbitre a, Coup[] d, Plateau p, Ordinateur me,Coup j){
        return 0;
    }
    
    public int EvalPlateau(Emulateur a, Coup[] d, Ordinateur me,Coup j){
        return 0;
    }
    
    public boolean win(Emulateur a, Ordinateur me){
        return a.m.estEncerclee(me.numAdversaire());
    }
    
    public int distance(Emulateur a, Ordinateur me){
        return 0;
    }
}
