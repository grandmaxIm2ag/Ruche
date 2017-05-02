/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Modele;

import java.util.Stack;

/**
 *
 * @author grandmax
 */
public class Case extends Composant{
    Point pos;
    Stack<Insecte> insectes;
    
    public final static int EST = 0;
    public final static int OUEST = 1;
    public final static int NEST = 2;
    public final static int NOUEST = 3;
    public final static int SEST = 4;
    public final static int SOUEST = 5;
    
    
    public Case(double x, double y, double larg, double haut){
        super(x,y,larg, haut);
        insectes = new Stack();
    }
    
    public boolean utilise(){
        return !insectes.isEmpty();
    }
    public void deposePion(Insecte e){
        
    }
    public Insecte tete(){
        return null;
    }
    
    @Override
    public boolean equals(Object o) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public boolean accept(Visiteur v) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
}
