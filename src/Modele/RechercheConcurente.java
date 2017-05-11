/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Modele;

import java.util.List;

/**
 *
 * @author grandmax
 */
public class RechercheConcurente implements Runnable{
    Plateau plateau;
    DeplacementPartage coup;
    Insecte insecte;
    
    public RechercheConcurente(Plateau p, DeplacementPartage c, Insecte e){
        coup = c;
        insecte = e;
        plateau = p;
    }
    
    @Override
    public void run() {
        coup.add(plateau.deplacementPossible(insecte));
    }
    
}
