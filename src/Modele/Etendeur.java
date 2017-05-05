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
public class Etendeur {
    double factX, factY, diffX, diffY;
    double[] vecteur=null;
    double l,y, x, h;
    public void fixeEchelle(double fx, double fy, double dx, double dy){
        factX = fx; factY = fy; diffX=dx; diffY=dy;
    }
    public void fixeComposant(Composant c){
       x = (c.position().x()+diffX);//*factX;
       y = (c.position().y()+diffY);//*factY;
       h = c.h()*50;
    }
    public void fixeVecteur(double[] v){
        
    }
    
    public double x(){
        return x;
    }
    public double y(){
        return y;
    }
    public double l(){
        return l;
    }
    public double h(){
        return h;
    }
    public double[] vecteur(){
        return null;
    }
    
    public String toString () {
        return "factX : " + factX + " factY : " + factY + " diffX : " + diffX + " diffY : " + diffY + "\n[" + x + ";" + y + "]\nl = " + l + " h = " + h;
        
    }
}
