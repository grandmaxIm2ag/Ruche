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
public class Fourmie extends Insecte{

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

    @Override
    public boolean accept(Visiteur v) {
        return v.visite(this);
    }

    @Override
    public Coup[] deplacementValide(Map<Point, Case> plateau) {
        Point p = pos.clone();
        Stack<Point> aVisiter = new Stack();
        List<Point> marquer = new ArrayList();
        marquer.add(p);
        
        List<Coup> co = glisser(plateau);
        Iterator<Coup> it = co.iterator();
        while(it.hasNext()){
            Point po = it.next().destination();
            //System.out.println(po);
            if(!marquer.contains(po)){
                System.out.println(po);
                marquer.add(po);
                aVisiter.push(po);
           }
        }
        boolean insert = !aVisiter.isEmpty();
        System.out.println("Valeur insert : "+insert);
        while(insert){
            insert = false;
            Stack<Point> tmp = new Stack();
            while(!aVisiter.isEmpty()){
                Point tmp2 = aVisiter.pop();
                pos.fixe(tmp2.x(), tmp2.y());
                co = glisser(plateau);
                it = co.iterator();
                while(it.hasNext()){
                    Point po = it.next().destination();
                    ///System.out.println(po);
                    if(!marquer.contains(po)){
                        System.out.println(po);
                        insert = true;
                        marquer.add(po);
                        tmp.push(po);
                    }
                }
            }
            while(!tmp.isEmpty())
                aVisiter.push(tmp.pop());
            
        }
        
        
        pos.fixe(p.x(), p.y());
        
        System.out.println(aVisiter.size());
        Coup[] coups = new Coup[marquer.size()];
        for(int i=0; i<coups.length; i++)
            coups[i] = new Deplacement(joueur, pos, marquer.get(i));
        return coups;
    }

    @Override
    public Insecte clone() {
        return new Fourmie(pos.x(), pos.y(), l, h, joueur);
    }
    
}
