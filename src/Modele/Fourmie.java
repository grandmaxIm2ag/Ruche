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
public class Fourmie extends Insecte{

    /**
     *
     * @param x
     * @param y
     * @param larg
     * @param haut
     * @param j
     */
    public Fourmie(double x, double y, double larg, double haut, int j) {
        super(x, y, larg, haut,j);
    }

    @Override
    public boolean equals(Object o) {
        if(o instanceof Fourmie){
            Fourmie a = (Fourmie)o;
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
        List<Point> marquer = new ArrayList();
        List<Deplacement> visite = new ArrayList();
        marquer.add(p);
        
        List<Coup> co = glisser(plateau);
        Iterator<Coup> it = co.iterator();
        while(it.hasNext()){
            
            Coup po = it.next();
            marquer.add(po.destination());
            visite.add((Deplacement) po);
            aVisiter.push((Deplacement) po);
           
        }
        
        boolean insert = !aVisiter.isEmpty();
        while(insert){
            insert = false;
            Stack<Deplacement> tmp = new Stack();
            while(!aVisiter.isEmpty()){
                Deplacement tmp1 = aVisiter.pop();
                Point tmp2 = tmp1.destination();
                pos.fixe(tmp2.x(), tmp2.y());
                co = glisser(plateau);
                it = co.iterator();
                while(it.hasNext()){
                    Point po = it.next().destination();
                    if(!marquer.contains(po)){
                        insert = true;
                        Deplacement clone = tmp1.clone();
                        marquer.add(po);
                        clone.add(po);
                        visite.add(clone);
                        tmp.push(clone);
                    }
                }
            }
            while(!tmp.isEmpty())
                aVisiter.push(tmp.pop());
            
        }
        
        
        pos.fixe(p.x(), p.y());
        
        marquer.remove(p);//
        Coup[] coups = new Coup[visite.size()];
        for(int i=0; i<coups.length; i++){
             coups[i] = visite.get(i);
        }
        
        c.deposePion(this);
        plateau.put(p, c);
            
        return coups;
    }

    @Override
    public Insecte clone() {
        return new Fourmie(pos.x(), pos.y(), l, h, joueur);
    }
    
    /**
     *
     * @return
     */
    @Override
    public int type() {
       return FOUR;
//throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
}
