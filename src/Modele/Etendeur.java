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
    
    public void fixeEchelle(Canvas c, Plateau p){
        double A = 0, B = 0, a = 0, b = 0;
        for(Map.Entry<Point, Case> entry : p.matrice().entrySet()){
            A = A < (entry.getKey().x+entry.getKey().y*0.5) ? (entry.getKey().x+entry.getKey().y*0.5) : A;
            a = a < (entry.getKey().x+entry.getKey().y*0.5) ? a : (entry.getKey().x+entry.getKey().y*0.5);
            B = B < entry.getKey().x*0.5+entry.getKey().y ? entry.getKey().x*0.5+entry.getKey().y : B;
            b = b < entry.getKey().x*0.5+entry.getKey().y ? b : entry.getKey().x*0.5+entry.getKey().y;
        }
        r = (c.getWidth()/(A+3-a)) < (c.getHeight()/(B+3-b)) ? (c.getWidth()/(A+3-a)) : (c.getHeight()/(B+3-b));
        System.out.println ((A-a) + "\n" + (B-b));
        if ((A+1-a)%2 == 1)
            diffX = (c.getWidth()/(A+3-a)) < (c.getHeight()/(B+3-b)) ? Interface.pythagorelol(r)*(1.75-a) : (c.getWidth() - Interface.pythagorelol(r)*(A+3-a))/2 + Interface.pythagorelol(r)*(1.5-a);
        else
            diffX = (c.getWidth()/(A+3-a)) < (c.getHeight()/(B+3-b)) ? Interface.pythagorelol(r)*(2.25-a) : (c.getWidth() - Interface.pythagorelol(r)*(A+3-a))/2 + Interface.pythagorelol(r)*(1.5-a);
        if ((B+1-b)%2 == 0)
            diffY = (c.getWidth()/(A+3-a)) < (c.getHeight()/(B+3-b)) ? (c.getHeight() - r*(B+3-b))/2 + Interface.pythagorelol(r)*(2-b) : (r)*(1.75-b);
        else
            diffY = (c.getWidth()/(A+3-a)) < (c.getHeight()/(B+3-b)) ? (c.getHeight() - r*(B+3-b))/2 + Interface.pythagorelol(r)*(1.75-b) : (r)*(1.5-b);

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
