/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Modele;

import java.util.Properties;

/**
 *
 * @author grandmax
 */
public class Plateau extends Composant {
    Case[][] matrice;
    int xMin, yMin;
    Properties prop;

    public Plateau(double x, double y, double larg, double haut, Properties p) {
        super(x, y, larg, haut);
        matrice = new Case[(int)larg*2][(int)larg*2];
        prop = p;
    }
    
    public void premierPion(Insecte e){
        matrice[(int)(l-1)][(int)(l-1)].deposePion(e);
    }
    
    public boolean deposePionValide(int type, Point p){
        
        return false;
    }
    public boolean deplacePionValide(Insecte src, Insecte dest, int direction){
        
        return false;
    }
    public boolean deplacePion(Insecte src, Insecte dest, int direction){
        
        return false;
    }
    public boolean deposePion(int type, Point p){
        
        return false;
    }
    
    
    @Override
    public boolean equals(Object o) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public boolean accept(Visiteur v) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
    @Override
    public String toString(){
        return null;
    }
}
