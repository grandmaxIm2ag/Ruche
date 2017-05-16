/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Modele;

/**
 *
 * @author grandmax
 */
public abstract class Composant {
    Point pos;
    double l,h;
    
    /**
     *
     * @param x
     * @param y
     * @param larg
     * @param haut
     */
    public Composant(double x, double y, double larg, double haut ){
        pos=new Point(x,y);
        l=larg; h = haut;
    }
    
    /**
     *
     * @return
     */
    public double l(){
        return l;
    }

    /**
     *
     * @return
     */
    public double h(){
        return h;
    }

    /**
     *
     * @return
     */
    public Point position(){
        return pos;
    }
    
    public abstract boolean equals(Object o);

    /**
     *
     * @param v
     * @return
     */
    public abstract boolean accept(Visiteur v);
    
}
