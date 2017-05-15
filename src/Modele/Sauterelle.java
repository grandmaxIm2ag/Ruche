/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Modele;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/**
 *
 * @author grandmax
 */
public class Sauterelle extends Insecte{

    /**
     *
     * @param x
     * @param y
     * @param larg
     * @param haut
     * @param j
     */
    public Sauterelle(double x, double y, double larg, double haut, int j) {
        super(x, y, larg, haut,j);
    }

    /**
     *
     * @param plateau
     * @return
     */
    @Override
    public Coup[] deplacementValide(Map<Point, Case> plateau) {
        List<Coup> c = new ArrayList();
        
        for(int i=(int)pos.x()-1; i<=(int)pos.x()+1;i++)
            for(int j=(int)pos.y()-1; j<=(int)pos.y()+1;j++)
                if(!((i==(int)pos.x()-1 && j==(int)pos.y()-1) || (i==(int)pos.x()+1 && j==(int)pos.y()+1) ))
                    if(!pos.equals(new Point(i,j))){
                        Case ca = plateau.get(new Point(i,j)) ;
                        if(ca!=null){
                            int diffx = i - ((int)pos.x());
                            int diffy = j - ((int)pos.y());
                            Point p = new Point(ca.position().x()+diffx, ca.position().y()+diffy);
                            while(ca!=null){
                                p = new Point(ca.position().x()+diffx, ca.position().y()+diffy);
                                ca=plateau.get(p);
                            }
                            c.add(new Deplacement(joueur, pos, p));
                        }
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
        if(o instanceof Sauterelle){
            Sauterelle a = (Sauterelle)o;
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
        return new Sauterelle(pos.x(), pos.y(), l, h, joueur);
    }

    /**
     *
     * @return
     */
    @Override
    public int type() {
        return SAUT;
        //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
}
