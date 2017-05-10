/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Modele;

import Vue.Interface;
import static Vue.Interface.py;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/**
 *
 * @author grandmax
 */
public class Reine extends Insecte{

    public Reine(double x, double y, /*double larg,*/ double haut, int j) {
        //super(x, y, larg, haut,j);
        super(x, y, Interface.py(haut), haut, j);

    }

    @Override
    public boolean equals(Object o) {
        if(o instanceof Reine){
            Reine a = (Reine)o;
            return (a.position().equals(pos) && a.l()==l && a.h()==h);
        }
        return false;
    }

    @Override
    public boolean accept(Visiteur v) {
        return v.visite(this);
    }

    @Override
    public Coup[] deplacementValide(Map<Point, Case> plateau) {
        List<Coup> c = glisser(plateau);
        Coup[] coups = new Coup[c.size()];
        Iterator<Coup> it = c.iterator();
        int i=0;
        while(it.hasNext() && i<coups.length)
            coups[i++]=it.next();
        return coups;
    }

    @Override
    public Insecte clone() {
        return new Reine(pos.x(), pos.y(), /*l,*/ h, joueur);
    }

    @Override
    public int type() {
        return REINE;
        //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
}
