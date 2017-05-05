/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Modele;

import Vue.Interface;
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
        //diffX = c.getWidth()/2;
        //diffY = c.getHeight()/2;
        int Xmoy = ((a.plateau().xMax + a.plateau().xMin)/2);
        int Ymoy = ((a.plateau().yMax + a.plateau().yMin)/2);
        int X = Math.abs(a.plateau().xMin) + Math.abs(a.plateau().xMax)+1;
        int Y = Math.abs(a.plateau().yMax) + Math.abs(a.plateau().yMin)+1;
        r = (c.getWidth()/(X+2)) < (c.getHeight()/(Y+2)) ? (c.getWidth()/(X+2)) : (c.getHeight()/(Y+2));
        diffX = c.getWidth()/2 - (Xmoy+Ymoy-(Xmoy+Ymoy==0 ? 0 : 1))*Interface.pythagorelol(r);
        diffY = c.getHeight()/2-(1.5*r)*Ymoy;
    }
    public void fixeComposant(Composant c){
       //x = (c.position().x()*factX+diffX);
       //y = (c.position().y()*factY+diffY);//*factY;
       x = c.position().x()*2*Interface.pythagorelol(r) + c.position().y()*Interface.pythagorelol(r) + diffX;
       y = c.position().y()*1.5*r + diffY;
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
        return "factX : " + factX + " factY : " + factY + " diffX : " + diffX + " diffY : " + diffY + "\n[" + x + ";" + y + "]\nl = " + l + " h = " + h;
        
    }
}
