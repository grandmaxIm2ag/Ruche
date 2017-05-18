/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Modele.Arbitres.producteurConsommateur;

import Modele.Arbitres.Arbitre;
import Vue.Interface;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author grandmax
 */
public class Consommateur implements Runnable {
    File actions[];
    private BufferedReader in;
    
    public Consommateur(File[] a, BufferedReader i){
        actions = a;
        in = i;
    }
    
    @Override
    public void run() {
        while(true){
            try {
                String li = in.readLine();
                System.out.println("Coup reçu "+ li);
                actions[Arbitre.J2].inserer(li);
            } catch (IOException ex) {
                Logger.getLogger(Consommateur.class.getName()).log(Level.SEVERE, null, ex);
            }
            
        }
    }
    
}
