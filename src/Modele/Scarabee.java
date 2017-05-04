/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Modele;

import java.util.Iterator;
import java.util.List;
import java.util.Map;

/**
 *
 * @author grandmax
 */
public class Scarabee extends Insecte{

    public Scarabee(double x, double y, double larg, double haut, int j) {
        super(x, y, larg, haut,j);
    }

    @Override
    public Coup[] deplacementValide(Map<Point, Case> plateau) {
        boolean enHaut;
        List<Coup> c;
                
        Case ca = plateau.get(pos).clone();
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
        if(o instanceof Scarabee){
            Scarabee a = (Scarabee)o;
            return (a.position().equals(pos) && a.l()==l && a.h()==h);
        }
        return false;
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
