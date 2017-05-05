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
//            for(int j=(int)pos.y(); j<=(int)pos.y()+1;j++)
//                if(!pos.equals(new Point((int)pos.x()-1,j)) && plateau.get(new Point((int)pos.x()-1,j))!=null)
//                    voisins.add(plateau.get(new Point((int)pos.x()-1,j)));
//            System.out.println("MOU*******");
//
//                for(int j=(int)pos.y()-1; j<=(int)pos.y()+1;j++){
//                    System.out.println("MOU*******"+pos + "**" + pos.x() + "**" + j);
//                    
//                        if(!pos.equals(new Point(pos.x(),j)) && plateau.get(new Point(pos.x(),j))!=null){
//
//                                voisins.add(plateau.get(new Point(pos.x(),j)));
//                                //System.out.println("MOU*******"+pos);
//
//
//                            //System.out.println("MOU*******"+pos);
//                            //2 fois
//                        }
//                }
//            System.out.println("MOU*******");
//            for(int j=(int)pos.y()-1; j<=(int)pos.y();j++)
//                if(!pos.equals(new Point((int)pos.x()+1,j)) && plateau.get(new Point((int)pos.x()+1,j))!=null)
//                    voisins.add(plateau.get(new Point((int)pos.x()+1,j)));
            for(int i=(int)pos.x()-1; i<=(int)pos.x()+1;i++)
                for(int j=(int)pos.y()-1; j<=(int)pos.y()+1;j++){
                    System.out.println("MOU*******"+pos + "**" + i + "**" + j);
                    if(!((i==(int)pos.x()-1 && j==(int)pos.y()-1) || (i==(int)pos.x()+1 && j==(int)pos.y()+1) ))
                        if(!pos.equals(new Point(i,j)) && plateau.get(new Point(i,j))!=null){
                            //if(!voisins.contains(plateau.get(new Point(i,j)))){
                                voisins.add(plateau.get(new Point(i,j)));
                                System.out.println("MOU***COUNT*"+ "**" + i + "**" + j);
                            //}

                            //System.out.println("MOU*******"+pos);
                            //2 fois
                        }
                }
            Iterator<Case> w = voisins.iterator();
            while(w.hasNext()){
                Case tmp = w.next().clone();
                //System.out.println((tmp==null));
                //tmp.tete().position().fixe(pos.x(), pos.y());
                System.out.println("MOUSTI"+tmp);
                //System.out.println((plateau.get(pos)==null));
                //Coup[] co = tmp.tete().deplacementValide(plateau);
                //for(int i=0; i<co.length; i++)
                //    c.add(co[i]);
            }
            
            
            
            Iterator<Case> v = voisins.iterator();
            while(v.hasNext()){
                Case tmp = v.next().clone();
                //System.out.println((tmp==null));
                tmp.tete().position().fixe(pos.x(), pos.y());
                System.out.println("MOUSTIQUE"+pos);
                //System.out.println((plateau.get(pos)==null));
                Coup[] co = tmp.tete().deplacementValide(plateau);
                for(int i=0; i<co.length; i++)
                    c.add(co[i]);
            }
        
        
            Coup [] coups = new Coup[c.size()];
            Iterator<Coup> it = c.iterator();
            for(int i=0; i<coups.length && it.hasNext(); i++){
                coups[i]=it.next();
            }
//        System.out.println("****************************");
//        for(int i=0; i<coups.length; i++)
//            System.out.println(coups[i].destination);
//        System.out.println("****************************");
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
