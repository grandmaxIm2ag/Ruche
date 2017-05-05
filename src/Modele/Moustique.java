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
public class Moustique extends Insecte{

    public Moustique(double x, double y, double larg, double haut, int j) {
        super(x, y, larg, haut,j);
    }

    @Override
    public Coup[] deplacementValide(Map<Point, Case> plateau) {
        boolean enHaut;
        Case ca = plateau.get(pos).clone();
        ca.retirePion();
        enHaut = ca.utilise();
        List<Coup> c = new ArrayList();
        
        if(enHaut){
            Scarabee scar = new Scarabee(pos.x(), pos.y(), l, h, joueur);
            return scar.deplacementValide(plateau);
        }else{
            List<Case> voisins = new ArrayList();
            for(int i=(int)pos.x()-1; i<=(int)pos.x()+1;i++)
                for(int j=(int)pos.y()-1; j<=(int)pos.y()+1;i++)
                    if(!((i==(int)pos.x()-1 && j==(int)pos.y()-1) || (i==(int)pos.x()+1 && j==(int)pos.y()+1) ))
                        if(!pos.equals(new Point(i,j)) && plateau.get(new Point(i,j))!=null ){
                            voisins.add(plateau.get(new Point(i,j)));
                        }
        
            Iterator<Case> v = voisins.iterator();
            while(v.hasNext()){
                Case tmp = v.next().clone();
                tmp.tete().position().fixe(pos.x(), pos.y());
                Coup[] co = tmp.tete().deplacementValide(plateau);
                for(int i=0; i<co.length; i++)
                    c.add(co[i]);
            }
        
        
            Coup [] coups = new Coup[c.size()];
            Iterator<Coup> it = c.iterator();
            for(int i=0; i<coups.length && it.hasNext(); i++){
                coups[i]=it.next();
            }
            return coups;
        }
    }

    @Override
    public boolean equals(Object o) {
        if(o instanceof Moustique){
            Moustique a = (Moustique)o;
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
       return new Moustique(pos.x(), pos.y(), l, h, joueur);
    }
    
}
