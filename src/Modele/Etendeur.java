/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Modele;

import Vue.Interface;
import java.util.Map;
import javafx.scene.canvas.Canvas;

/**
 *
 * @author grandmax
 */
public class Etendeur {
    double factX, factY, diffX, diffY, r;
    double[] vecteur=null;
    double l,y, x, h;
    
    
    public void fixeEchelle(Canvas c, Arbitre a){
        double A = 0, B = 0;
        for(Map.Entry<Point, Case> entry : a.plateau().matrice().entrySet()){
            A = A < (entry.getKey().x+entry.getKey().y*0.5) ? (entry.getKey().x+entry.getKey().y*0.5) : A;
            B = B < entry.getKey().x*0.5+entry.getKey().y ? entry.getKey().x*0.5+entry.getKey().y : B;
        }
        r = (c.getWidth()/(A+2)) < (c.getHeight()/(B+2)) ? (c.getWidth()/(A+2)) : (c.getHeight()/(B+2));
        diffX = (c.getWidth()/(A+2)) < (c.getHeight()/(B+2)) ? c.getWidth()/(A+2) + c.getWidth()/(A+2)*Interface.pythagorelol(0.125) : c.getHeight()/(B+2) + 0.5*(c.getWidth()-c.getHeight()) + c.getWidth()/(B+2)*Interface.pythagorelol(0.125);
        diffY = (c.getWidth()/(A+2)) < (c.getHeight()/(B+2)) ? c.getWidth()/(A+2) + 0.5*(c.getHeight()-c.getWidth())  : c.getHeight()/(B+2);
    }
    public void fixeComposant(Composant c){
       x = c.position().x()*Interface.pythagorelol(r) + c.position().y()*Interface.pythagorelol(r/2) + diffX;
       y = c.position().y()*0.75*r + diffY;
       h = r;
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
        return "factX : " + factX + " factY : " + factY + " diffX : " + diffX + " diffY : " + diffY + "\n[" + x + ";" + y + "]\nl = " + l + " h = " + h + " r : " + r;
        
    }
}
