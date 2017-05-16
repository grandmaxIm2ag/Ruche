/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Modele;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author grandmax
 */
public class DeplacementPartage {
    List<Coup> lc;
    boolean b = true;
    
    /**
     *
     */
    public DeplacementPartage(){
        lc = new ArrayList();
    }
    
    /**
     *
     * @return
     */
    public List<Coup> getCoup(){
        return lc;
    }
    
    /**
     *
     * @param c
     */
    public synchronized void add(List<Coup> c){
        while(!b)
            try{
                wait();
            }catch(InterruptedException e){
                
            }
        b=false;
        
        if(c!=null){
            lc.addAll(c);
        }
        
        b=true;
        notifyAll();
    }
}
