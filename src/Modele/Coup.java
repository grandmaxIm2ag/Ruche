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
public abstract class Coup {
    public Point destination;
    int joueur;
    
    public Coup(int j){
        joueur = j;
    }
    public Point destination(){
        return destination;
    }
    
    public int joueur(){
        return joueur;
    }
}
