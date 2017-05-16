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
public class Depot extends Coup {
    int type;
    
    /**
     *
     * @param j
     * @param t
     * @param d
     */
    public Depot(int j, int t, Point d){
        super(j);
        type = t;
        destination = d;
    }
    
    /**
     *
     * @param j
     * @param str
     */
    public Depot(int j, String str){
        super(j);
        String[] tab = str.split(":");
        type= Integer.parseInt(tab[0]);
        destination = new Point(tab[1]);
    }
    
    /**
     *
     * @return
     */
    public int type(){
        return type;
    }

    /**
     *
     * @return
     */
    @Override
    public Point destination(){
        return destination;
    }
    
    @Override
    public String toString(){
        return type+":"+destination;
    }
}
