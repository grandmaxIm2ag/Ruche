/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Modele.Arbitres.producteurConsommateur;

import Modele.Arbitres.Arbitre;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;

/**
 *
 * @author grandmax
 */
public class Producteur implements Runnable{
    private File[] actions;
    private PrintWriter out;
    
    public Producteur(File[] a, PrintWriter o){
        actions = a;
        out = o;
    }
    @Override
    public void run() {
        while(true){
            String l = actions[Arbitre.J1].extraire();
            out.println(l);
        }
    }
    
}
