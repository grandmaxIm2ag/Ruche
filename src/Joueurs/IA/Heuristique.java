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
    Map<Plateau, Integer> configurations;
    int heurs;
    
    
    public Heuristique(){
        configurations = new HashMap();
        heurs = 0;
    }
    
    public void SetConf(Map<Plateau, Integer> conf){
        configurations = conf;
    }
    
    public int EvalPlateau(Arbitre a, Coup[] d, Plateau p, Ordinateur me){
        return 0;
    }
    
    public int EvalPlateau(Emulateur a, Coup[] d, Ordinateur me){
        return 0;
    }
}
