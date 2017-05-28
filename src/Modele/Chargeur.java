/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Modele;

import Joueurs.Ordinateur;
import Modele.Arbitres.FabriqueArbitre;
import Vue.Interface;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Properties;
import java.util.Scanner;
import java.util.Stack;
import java.util.logging.Level;
import java.util.logging.Logger;
import ruche.Reglage;

/**
 *
 * @author grandmax
 */
public class Chargeur {
    static Scanner input;
    
    static Properties prop;
    static String[] joueurs = {"null", "null"};
    /**
     *
     * @param p
     */
    public static void init(Properties p){
        boolean vide = true;
        try{
             input = new Scanner(new FileInputStream("Sauvegardes/Sauvegarde")) ;
             if(input.hasNext()){
                String[] plateaux = input.nextLine().split(":");
                Sauvegarde[] format = new Sauvegarde[plateaux.length];
                input.close();
                for (int i=0; i<plateaux.length; i++) {
                    String plateaux1 = plateaux[i];
                    input = new Scanner(new FileInputStream("Sauvegardes/"+plateaux1)) ;
                    String pl = input.nextLine();
                    String[] str = pl.split("::");
                    switch(Integer.parseInt(str[0])){
                        case FabriqueArbitre.LOCAL_JVJ:
                            format[i] = new Sauvegarde(plateaux1, str[1], str[2],Integer.parseInt(str[0]));
                            break;
                        case FabriqueArbitre.LOCAL_JVIA:
                            format[i] = new Sauvegarde(plateaux1, str[1], "", Integer.parseInt(str[0]));
                            format[i].setPropriete(Integer.parseInt(str[0]), Integer.parseInt(str[2]));
                            break;
                        case FabriqueArbitre.LOCAL_IAVJ:
                            format[i] = new Sauvegarde(plateaux1, "", str[2], Integer.parseInt(str[0]));
                            format[i].setPropriete(Integer.parseInt(str[0]), Integer.parseInt(str[1]));
                            break;
                        case FabriqueArbitre.SIMULATION:
                            format[i] = new Sauvegarde(plateaux1, Integer.parseInt(str[1]), Integer.parseInt(str[2]), Integer.parseInt(str[0]));
                            break;

                        }

               }
               Interface.goLoadGame(format);
               vide = false;
             }
        }catch(FileNotFoundException e){
                 
        }
        if(vide){
            Interface.goLoadGame(new Sauvegarde[0]);
        }
        prop = p;
    }
    
    /**
     *
     * @return
     */
    public static Stack<Coup> charger(String plateau){
        try {
            input = new Scanner(new FileInputStream("Sauvegardes/"+plateau));
        } catch (FileNotFoundException ex) {
            Logger.getLogger(Chargeur.class.getName()).log(Level.SEVERE, null, ex);
        }
        String line = input.nextLine();
        String[] str = line.split("::");
        switch(Integer.parseInt(str[0])){
            case FabriqueArbitre.LOCAL_JVJ:
                joueurs[0]= str[1];
                joueurs[1]=str[2];
                break;
            case FabriqueArbitre.LOCAL_JVIA:
                joueurs[0]= str[1];
                joueurs[1]=FabriqueArbitre.diff(Integer.parseInt(str[2]));
                break;
            case FabriqueArbitre.LOCAL_IAVJ:
                joueurs[0]= FabriqueArbitre.diff(Integer.parseInt(str[1]));
                joueurs[1]=str[2];
                break;
            case FabriqueArbitre.SIMULATION:
                joueurs[0]= FabriqueArbitre.diff(Integer.parseInt(str[1]));
                joueurs[1]= FabriqueArbitre.diff(Integer.parseInt(str[2]));
                break;
        }
        FabriqueArbitre.newConf(input.nextLine());
        Stack<Coup> hBis = new Stack();
        while(input.hasNext()){
            line = input.nextLine();
            if(line.charAt(0)=='('){
                hBis.push(new Deplacement(0,line));
            }else{
                hBis.push(new Depot(0,line));
            }
            
        }
         
        return hBis;
    }

    /**
     *
     * @return
     */
    public static String[] joueur(){
        return joueurs; 
    }

}
