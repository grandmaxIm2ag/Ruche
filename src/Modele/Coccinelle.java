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
import java.util.Stack;

/**
 *
 * @author grandmax
 */
public class Coccinelle extends Insecte{

    public Coccinelle(double x, double y, double larg, double haut, int j) {
        super(x, y, larg, haut,j);
    }

    @Override
    public Coup[] deplacementValide(Map<Point, Case> plateau) {
        Point p = pos.clone();
        
        Case c = plateau.get(p);
        c.retirePion();
        if(c.utilise())
            plateau.put(p, c);
        else
            plateau.remove(p);
        
        Stack<Point> aVisiter = new Stack();
        List<Point> marquer = new ArrayList();
        marquer.add(p);
        List<Coup> co = monter(plateau);
        Iterator<Coup> it = co.iterator();
        while(it.hasNext()){
            Point po = it.next().destination();
            if(!marquer.contains(po)){
                
                marquer.add(po);
                aVisiter.push(po);
           }
        }
        
        Stack<Point> tmp = new Stack();
        while(!aVisiter.isEmpty()){
            Point tmp2 = aVisiter.pop();
                pos.fixe(tmp2.x(), tmp2.y());
                co = monter(plateau);
                it = co.iterator();
                while(it.hasNext()){
                    Point po = it.next().destination();
                    marquer.add(po);
                    tmp.push(po);
                }
        }
        
        while(!tmp.isEmpty())
            aVisiter.push(tmp.pop());
        while(!aVisiter.isEmpty()){
            Point tmp2 = aVisiter.pop();
                pos.fixe(tmp2.x(), tmp2.y());
                co = descendre(plateau);
                it = co.iterator();
                System.out.println(co.size());
                while(it.hasNext()){
                    Point po = it.next().destination();
                    
                    if(!marquer.contains(po)){
                        System.out.println(po);
                        marquer.add(po);
                        tmp.push(po);
                    }
                }
        }
        
        pos.fixe(p.x(), p.y());
        
        Coup[] coups = new Coup[tmp.size()];
        for(int i=0; i<coups.length; i++)
            coups[i] = new Deplacement(joueur, pos, tmp.pop());
        return coups;
    }

    @Override
    public boolean equals(Object o) {
        if(o instanceof Coccinelle){
            Coccinelle a = (Coccinelle)o;
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
        return new Coccinelle(pos.x(), pos.y(), l, h, joueur);
    }
    
}
