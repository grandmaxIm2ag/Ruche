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
public class Deplacement extends Coup{
    Point source;
    
    public Deplacement(Point s, Point d){
        source = s;
        destination = d;
    }
}
