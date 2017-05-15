/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Modele;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Stack;

/**
 *
 * @author grandmax
 */
public class Araignee extends Insecte{

    /**
     *
     * @param x
     * @param y
     * @param larg
     * @param haut
     * @param j
     */
    public Araignee(double x, double y, double larg, double haut, int j) {
        super(x, y, larg, haut,j);
    }

    /**
     *
     * @param pl
     * @return
     */
    @Override
    public Coup[] deplacementValide(Map<Point, Case> pl) {
        
        Map<Point, Case> plateau = new HashMap();
        
        for(Map.Entry<Point, Case> entry : pl.entrySet())
            plateau.put(entry.getKey(), entry.getValue().clone());
        
        Point p = pos.clone();
        
        Case c = plateau.get(p);
        c.retirePion();
        if(c.utilise())
            plateau.put(p, c);
        else
            plateau.remove(p);
        
        
        Stack<Deplacement> aVisiter = new Stack();
        
        List<Coup> co = glisser(plateau);
        Iterator<Coup> it = co.iterator();
        while(it.hasNext()){
            aVisiter.push((Deplacement) it.next());
        }
        
        Stack<Deplacement> tmp = new Stack();
        for(int i=0; i<2; i++){
            while(!aVisiter.isEmpty()){
                Deplacement tmp1 = aVisiter.pop();
                Point tmp2 = tmp1.destination().clone();
                pos.fixe(tmp2.x(), tmp2.y());
                co = glisser(plateau);
                it = co.iterator();
                while(it.hasNext()){
                    Point po = it.next().destination().clone();
                    if(!tmp1.aDejaVisite(po)){
                        Deplacement clone = tmp1.clone();
                        
                        clone.add(po);
                        
                        tmp.push(clone);
                    }
                }
            }
            while(!tmp.isEmpty())
                aVisiter.push(tmp.pop());
        }
        
        pos.fixe(p.x(), p.y());
        
        Coup[] coups = new Coup[aVisiter.size()];
        for(int i=0; i<coups.length; i++)
            coups[i] = aVisiter.pop();
        
        c.deposePion(this);
        plateau.put(p,c);
        
        return coups;
    }

    @Override
    public boolean equals(Object o) {
        if(o instanceof Araignee){
            Araignee a = (Araignee)o;
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
        return new Araignee(pos.x(), pos.y(), l, h, joueur);
    }

    /**
     *
     * @return
     */
    @Override
    public int type() {
        return ARAI;
        //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
}
