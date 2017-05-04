/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Modele;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
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
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public boolean accept(Visiteur v) {
        return v.visite(this);
    }

    @Override
    public Coup[] deplacementValide(Case[][] plateau) {
        Point p = pos.clone();
        Stack<Point> aVisiter = new Stack();
        List<Point> marquer = new ArrayList();
        marquer.add(p);
        
        List<Coup> co = glisser(plateau);
        Iterator<Coup> it = co.iterator();
        while(it.hasNext()){
            Point po = it.next().destination();
            if(!marquer.contains(po)){
                marquer.add(po);
                aVisiter.push(po);
           }
        }
        boolean insert = true;
        while(insert){
            insert = false;
            Stack<Point> tmp = new Stack();
            while(!aVisiter.isEmpty()){
                Point tmp2 = aVisiter.pop();
                pos.fixe(p.x(), p.y());
                co = glisser(plateau);
                it = co.iterator();
                while(it.hasNext()){
                    Point po = it.next().destination();
                    if(!marquer.contains(po)){
                        insert = true;
                        marquer.add(po);
                        tmp.push(po);
                    }
                }
            }
            aVisiter = tmp;
            
        }
        
        
        pos.fixe(p.x(), p.y());
        
        Coup[] coups = new Coup[aVisiter.size()];
        for(int i=0; i<coups.length; i++)
            coups[i] = new Deplacement(joueur, pos, aVisiter.pop());
        return coups;
    }

    @Override
    public Insecte clone() {
        return new Fourmie(pos.x(), pos.y(), l, h, joueur);
    }
    
}
