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
public class Sauterelle extends Insecte{

    public Sauterelle(double x, double y, double larg, double haut, int j) {
        super(x, y, larg, haut,j);
    }

    @Override
    public Coup[] deplacementValide(Case[][] plateau) {
        List<Coup> c = new ArrayList();
        
        for(int i=(int)pos.x()-1; i<=(int)pos.x()+1;i++)
            for(int j=(int)pos.y()-1; j<=(int)pos.y()+1;i++)
                if(!((i==(int)pos.x()-1 && j==(int)pos.y()-1) || (i==(int)pos.x()+1 && j==(int)pos.y()+1) ))
                    if(!pos.equals(plateau[i][j].position())){
                        Case ca = plateau[i][j];
                        if(ca.utilise()){
                            int diffx = i - ((int)pos.x());
                            int diffy = j - ((int)pos.y());
                            while(ca.utilise())
                                ca=plateau[(int)ca.position().x()+diffx][(int)ca.position().y()+diffy];
                            c.add(new Deplacement(joueur, pos, ca.position()));
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
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public boolean accept(Visiteur v) {
        return v.visite(this);
    }

    @Override
    public Insecte clone() {
        return new Sauterelle(pos.x(), pos.y(), l, h, joueur);
    }
    
}
