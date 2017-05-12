/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Modele;

import Vue.Interface;
import static Vue.Interface.py;
import java.util.Iterator;
import java.util.LinkedList;
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
    double py;
    
    public Etendeur () {
        py = py(1);
    }
    
    public void fixeEchelle(Canvas c, Plateau p){
        double A = 0, B = 0, a = 0, b = 0;
        for(Map.Entry<Point, Case> entry : p.matrice().entrySet()){
            A = A < (entry.getKey().x+entry.getKey().y*0.5) ? (entry.getKey().x+entry.getKey().y*0.5) : A;
            a = a < (entry.getKey().x+entry.getKey().y*0.5) ? a : (entry.getKey().x+entry.getKey().y*0.5);
            B = B < entry.getKey().x*0.5+entry.getKey().y ? entry.getKey().x*0.5+entry.getKey().y : B;
            b = b < entry.getKey().x*0.5+entry.getKey().y ? b : entry.getKey().x*0.5+entry.getKey().y;
        }
        r = (c.getWidth()/(A+3-a)) < (c.getHeight()/(B+3-b)) ? (c.getWidth()/(A+3-a)) : (c.getHeight()/(B+3-b));
        if ((A+1-a)%2 != 0)
            diffX = (c.getWidth()/(A+3-a)) < (c.getHeight()/(B+3-b)) ? py*(r)*(1.75-a) : (c.getWidth() - py*(r)*(A+3-a))/2 + py*(r)*(1.5-a);
        else
            diffX = (c.getWidth()/(A+3-a)) < (c.getHeight()/(B+3-b)) ? py*(r)*(2.25-a) : (c.getWidth() - py*(r)*(A+3-a))/2 + py*(r)*(1.5-a);
        if ((B+1-b)%2 != 1)
            diffY = (c.getWidth()/(A+3-a)) < (c.getHeight()/(B+3-b)) ? (c.getHeight() - r*(B+3-b))/2 + py*(r)*(2-b) : (r)*(1.75-b);
        else
            diffY = (c.getWidth()/(A+3-a)) < (c.getHeight()/(B+3-b)) ? (c.getHeight() - r*(B+3-b))/2 + py*(r)*(1.75-b) : (r)*(1.5-b);

    }
    
    public void fixeEchelle(Canvas c, LinkedList<Insecte> p){
        double A = 0, B = 0, a = 0, b = 0;
        Iterator it = p.iterator();
        while (it.hasNext()){
            Insecte i = (Insecte) it.next();
            A = A < (i.pos.x+i.pos.y*0.5) ? (i.pos.x+i.pos.y*0.5) : A;
            a = a < (i.pos.x+i.pos.y*0.5) ? a : (i.pos.x+i.pos.y*0.5);
            B = B < i.pos.x*0.5+i.pos.y ? i.pos.x*0.5+i.pos.y : B;
            b = b < i.pos.x*0.5+i.pos.y ? b : i.pos.x*0.5+i.pos.y;
        }
        r = (c.getWidth()/(A+1-a)) < (c.getHeight()/(B+3-b)) ? (c.getWidth()/(A+1-a)) : (c.getHeight()/(B+3-b));
            diffX = (c.getWidth()/(A+1-a)) < (c.getHeight()/(B+1-b)) ? py*(r)*(0.75-a) : (c.getWidth() - py*(r)*(A+1-a))/2 + py*(r)*(1-a);
            diffY = (c.getWidth()/(A+3-a)) < (c.getHeight()/(B+3-b)) ? (c.getHeight() - r*(B+3-b))/2 + py*(r)*(2-b) : (r)*(1.75-b);

    }
    
    public void fixeComposant(Composant c){
       x = c.position().x()*py*(r) + c.position().y()*py*(r/2) + diffX;
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
