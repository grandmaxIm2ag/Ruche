/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Modele;

import java.util.Iterator;
import java.util.List;

/**
 *
 * @author grandmax
 */
public class Scarabee extends Insecte{

    public Scarabee(double x, double y, double larg, double haut, int j) {
        super(x, y, larg, haut,j);
    }

    @Override
    public Coup[] deplacementValide(Case[][] plateau) {
        boolean enHaut;
        List<Coup> c;
                
        Case ca = plateau[(int)pos.x()][(int)pos.y()].clone();
        ca.retirePion();
        enHaut = ca.utilise();
        
        if(enHaut){
            c = monter(plateau);
            c.addAll(descendre(plateau));
        }else{
            c = monter(plateau);
            c.addAll(glisser(plateau));
        }
        
        Coup[] coups = new Coup[c.size()];
        Iterator<Coup> it = c.iterator();
        int i=0; 
        while(it.hasNext() && i<coups.length)
            coups[i++]=it.next();
        return coups;
    }

    @Override
    public boolean equals(Object o) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public boolean accept(Visiteur v) {
        return v.visite(this);
    }

    @Override
    public Insecte clone() {
        return new Scarabee(pos.x(), pos.y(), l, h, joueur);
    }
    
    
}
