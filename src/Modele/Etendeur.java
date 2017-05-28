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
    
    /**
     *
     */
    public Etendeur () {
        py = py(1);
        System.out.println(py);
        r = 0;
        diffX = 0;
        diffY = 0;
    }
    
    /**
     *
     * @param c
     * @param p
     */
    public void fixeEchelle(Canvas c, Plateau p){
        double xMax = 0, yMax = 0, xMin = c.getWidth(), yMin = c.getHeight();
        double coords[][];
        double A = 0, B = 0, a = 0, b = 0;
        for(Map.Entry<Point, Case> entry : p.matrice().entrySet()){
            if (r != 0) {
                coords = Interface.hex_corner(entry.getKey().x()*py*r + entry.getKey().y()*py*r/2 +diffX, entry.getKey().y()*0.75*r +diffY, r);
                xMin = xMin < coords[0][2] ? xMin : coords[0][2];
                yMin = yMin < coords[1][4] ? yMin : coords[1][4];
                xMax = xMax > coords[0][0] ? xMax : coords[0][0];
                yMax = yMax > coords[1][1] ? yMax : coords[1][4];
            }
            A = A < (entry.getKey().x+entry.getKey().y*0.5) ? (entry.getKey().x+entry.getKey().y*0.5) : A;
            a = a < (entry.getKey().x+entry.getKey().y*0.5) ? a : (entry.getKey().x+entry.getKey().y*0.5);
            B = B < entry.getKey().x*0.5+entry.getKey().y ? entry.getKey().x*0.5+entry.getKey().y : B;
            b = b < entry.getKey().x*0.5+entry.getKey().y ? b : entry.getKey().x*0.5+entry.getKey().y;
        }
        
        //r = (c.getWidth()/(A+3-a)) < (c.getHeight()/(B+3-b)) ? (c.getWidth()/(A+3-a)) : (c.getHeight()/(B+3-b));
        r = (c.getWidth()/p.l()*py) < (c.getHeight()/p.h()) ? c.getWidth()/p.l()*py : c.getHeight()/p.h();
        
        if (diffX == 0) {
        diffX = c.getWidth()/2;
        diffY = c.getHeight()/2;
        }
        
        /*if (recalc(c, xMin, xMax, yMin, yMax)) {
            diffX = c.getWidth()/2 + (-a-A)/2*r*py;
            diffY = c.getHeight()/2 + (-b-B)/2*r;
        }*/
        
        /*
        if ((A+1-a)%2 != 0)
            diffX = (c.getWidth()/(A+3-a)) < (c.getHeight()/(B+3-b)) ? py*(r)*(1.75-a) : (c.getWidth() - py*(r)*(A+3-a))/2 + py*(r)*(1.5-a);
        else
            diffX = (c.getWidth()/(A+3-a)) < (c.getHeight()/(B+3-b)) ? py*(r)*(2.25-a) : (c.getWidth() - py*(r)*(A+3-a))/2 + py*(r)*(1.5-a);
        if ((B+1-b)%2 != 1)
            diffY = (c.getWidth()/(A+3-a)) < (c.getHeight()/(B+3-b)) ? (c.getHeight() - r*(B+3-b))/2 + py*(r)*(2-b) : (r)*(1.75-b);
        else
            diffY = (c.getWidth()/(A+3-a)) < (c.getHeight()/(B+3-b)) ? (c.getHeight() - r*(B+3-b))/2 + py*(r)*(1.75-b) : (r)*(1.5-b);
        */
    }
    
    private boolean recalc (Canvas c, double xMin, double xMax, double yMin, double yMax) {
        return xMin < r*py || xMax > c.getWidth()-r*py || yMin < r || yMax > c.getHeight()-r;
    }
    
    /**
     *
     * @param c
     * @param p
     */
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
    
    /**
     *
     * @param c
     */
    public void fixeComposant(Composant c){
       x = c.position().x()*py*(r) + c.position().y()*py*(r/2) + diffX;
       y = c.position().y()*0.75*r + diffY;
       h = r;
    }

    /**
     *
     * @param v
     */
    public void fixeVecteur(double[] v){
        
    }
    
    /**
     *
     * @return
     */
    public double x(){
        return x;
    }

    /**
     *
     * @return
     */
    public double y(){
        return y;
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
    public double[] vecteur(){
        return null;
    }
    
    public String toString () {
        return "factX : " + factX + " factY : " + factY + " diffX : " + diffX + " diffY : " + diffY + "\n[" + x + ";" + y + "]\nl = " + l + " h = " + h + " r : " + r;
        
    }
}
