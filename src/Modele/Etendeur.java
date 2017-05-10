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
        //r = (c.getWidth()/(A+2 + Math.abs(a))) < (c.getHeight()/(B+2)) ? (c.getWidth()/(A+2+Math.abs(a))) : (c.getHeight()/(B+2));
        r = (c.getWidth()/(A+3-a)) < (c.getHeight()/(B+3-b)) ? (c.getWidth()/(A+3-a)) : (c.getHeight()/(B+3-b));
        diffX = (c.getWidth()/(A+2)) < (c.getHeight()/(B+2)) ? c.getWidth()/(A+2) + c.getWidth()/(A+2)*Interface.pythagorelol(0.125) : c.getHeight()/(B+2) + 0.5*(c.getWidth()-c.getHeight()) + c.getWidth()/(B+2)*Interface.pythagorelol(0.125);
        //diffX = c.getWidth()/(A+3-a);// + c.getWidth()/(A+1+a)*Interface.pythagorelol(0.125);
        //diffX += Interface.pythagorelol(1)*r*(-a) ;
        //diffX = (c.getWidth() - Interface.pythagorelol(1)*r*(A+1-a))/2 + (2-a)*r*Interface.pythagorelol(1);
        //diffX = (c.getWidth() - Interface.pythagorelol(0.5*r)*2*(A+1-a))/2 + (3-2*a)*Interface.pythagorelol(0.5*r);
        //diffX = (c.getWidth() - r*(A+3-a))/2;
        diffX = (c.getWidth()/(A+3-a)) < (c.getHeight()/(B+3-b)) ? Interface.pythagorelol(r)*(2-a) : Interface.pythagorelol(r)*(2-a) + (c.getWidth() - r*(B-3+b))/2;
        //System.out.println ("a : " + c.getWidth() + "\nb : " + Interface.pythagorelol(1)*r*(A+1-a));
        System.out.println(A+1-a);
        diffY = (c.getWidth()/(A+2)) < (c.getHeight()/(B+2)) ? c.getWidth()/(A+2) + 0.5*(c.getHeight()-c.getWidth())  : c.getHeight()/(B+2);
        diffY = c.getHeight()/(B+2);
        diffY = (c.getWidth()/(A+3-a)) < (c.getHeight()/(B+3-b)) ? 0 : Interface.pythagorelol(r)*(2-b);

        //System.out.println("A : " + A + "\nB : " + B + "\na : " + a + "\nb : " + b + "\nA? " + ((c.getWidth()/(A+2)) < (c.getHeight()/(B+2))) + "\nB? " + ((c.getWidth()/(A+2)) < (c.getHeight()/(B+2))) + "\ndiffX : " + diffX + "\ndiffY : " + diffY + "\n0.5*(c.getWidth()-c.getHeight()) = " + (0.5*(c.getWidth()-c.getHeight())));
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
