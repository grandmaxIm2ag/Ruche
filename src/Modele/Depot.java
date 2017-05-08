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
    
    public Depot(int j, int t, Point d){
        super(j);
        type = t;
        destination = d;
    }
    
    public int type(){
        return type;
    }
    @Override
    public Point destination(){
        return destination;
    }
    
    @Override
    public String toString(){
        return type+":"+destination;
    }
}
