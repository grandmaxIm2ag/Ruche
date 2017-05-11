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
    Stack<Insecte> insectes;
    
    public final static int EST = 0;
    public final static int OUEST = 1;
    public final static int NEST = 2;
    public final static int NOUEST = 3;
    public final static int SEST = 4;
    public final static int SOUEST = 5;
    
    public static boolean EST_POINTE = false;
    
    
    public Case(double x, double y, double larg, double haut){
        super(x,y,larg, haut);
        insectes = new Stack();
    }
    
    public void pointe() {
        //EST_POINTE = true;
        this.tete().pointe();
    }
    
    public void depointe() {
        EST_POINTE = false;
    }
    
    public boolean estpointe () {
        return EST_POINTE;
    }
    
    public boolean utilise(){
        return !insectes.isEmpty();
    }
    public void deposePion(Insecte e){
        insectes.push(e);
    }
    public Insecte retirePion(){
        return insectes.pop();
    }
    public Insecte tete(){
        return insectes.peek();
    }
    public Stack insectes(){
        return insectes;
    }
    
    @Override
    public boolean equals(Object o) {
        if(o instanceof Case){
            Case c = (Case)o;
            return (c.position().equals(pos) && l==c.l() && h==c.h() && c.insectes().equals(insectes)) ;
        }
        return false;
    }

    @Override
    public boolean accept(Visiteur v) {
        boolean b = v.visite(this);
        if(utilise())
            b |= tete().accept(v);
        return b;
    }
    
    public Case clone(){
        Case nouv = new Case(pos.x(), pos.y(), l, h);
        
        if(utilise()){
            Stack<Insecte> tmp = new Stack();
            while(utilise())
                tmp.push(retirePion());
            
            while(!tmp.isEmpty()){
                Insecte e = tmp.pop();
                deposePion(e);
                nouv.deposePion(e.clone());
            }
        }
        
        return nouv;
    }
    
    
    @Override
    public String toString(){
        String str = pos.toString();
        if(utilise()){
            Stack<Insecte> tmp = new Stack();
            while(utilise())
                tmp.push(retirePion());
            
            while(!tmp.isEmpty()){
                Insecte e = tmp.pop();
                deposePion(e);
                str+=":"+e;
            }
        }
        return str;
    }
}
