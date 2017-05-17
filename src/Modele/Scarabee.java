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

    /**
     *
     * @param x
     * @param y
     * @param larg
     * @param haut
     * @param j
     */
    public Scarabee(double x, double y, double larg, double haut, int j) {
        super(x, y, larg, haut,j);
    }

    /**
     *
     * @param plateau
     * @return
     */
    @Override
    public Coup[] deplacementValide(Plateau pl) {
        Map<Point, Case> plateau = pl.matrice();
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

    /**
     *
     * @param v
     * @return
     */
    @Override
    public boolean accept(Visiteur v) {
        return v.visite(this);
    }

    @Override
    public Insecte clone() {
        return new Scarabee(pos.x(), pos.y(), l, h, joueur);
    }

    /**
     *
     * @return
     */
    @Override
    public int type() {
        return SCAR;
        //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
    
}
