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
public abstract class Insecte extends Composant{
    public final static int SCAR = 0;
    public final static int REINE = 1;
    public final static int SAUT = 2;
    public final static int FOUR = 3;
    public final static int ARAI = 4;
    
    int type;
    
    public Insecte(double x, double y, double larg, double haut) {
        super(x, y, larg, haut);
    }

    public int type(){
        return type;
    }
    public double[] vecteur(){
        return null;
    }
    
    public abstract Point[] deplacementValide(Case[] plateau);
    
    
}
