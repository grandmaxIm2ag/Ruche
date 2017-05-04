/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Modele;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 *
 * @author grandmax
 */
public class Cloporte extends Insecte{

   public Cloporte(double x, double y, double larg, double haut, int j) {
        super(x, y, larg, haut,j);
    }

    @Override
    public Coup[] deplacementValide(Case[][] plateau) {
        List<Coup> c = glisser(plateau);
        
        List<Case> voisins = new ArrayList();
        List<Point> depot = new ArrayList();
        for(int i=(int)pos.x()-1; i<=(int)pos.x()+1;i++)
            for(int j=(int)pos.y()-1; j<=(int)pos.y()+1;i++)
                if(!((i==(int)pos.x()-1 && j==(int)pos.y()-1) || (i==(int)pos.x()+1 && j==(int)pos.y()+1) ))
                    if(!pos.equals(plateau[i][j].position())){
                        if(plateau[i][j].utilise())
                            voisins.add(plateau[i][j]);
                        else
                            depot.add(plateau[i][j].position());
                    }
        Iterator<Case> v = voisins.iterator();
        while(v.hasNext()){
            Case tmp = v.next();
            Iterator<Point> d = depot.iterator();
            while(d.hasNext()){
                c.add(new Deplacement(tmp.tete().joueur(), tmp.position(), d.next()));
            }
        }
                
        Coup [] coups = new Coup[c.size()];
        Iterator<Coup> it = c.iterator();
        for(int i=0; i<coups.length && it.hasNext(); i++){
            coups[i]=it.next();
        }
        return coups;
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
    public Insecte clone() {
        return new Cloporte(pos.x(), pos.y(), l, h, joueur);
    }
    
}
