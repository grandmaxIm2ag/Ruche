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
    static int t=0, d=0, j=0;
    static int[] n = {0,0};
    static String[] joueurs = {"null", "null"};
    static Stack<Coup> h, r, r2;
    /**
     *
     * @param p
     */
    public static void init(Properties p){
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
/*                          
                            switch(Integer.parseInt(str[2])){
                                
                                case Ordinateur.FACILE_ALEATOIRE:
                                    break;
                                case Ordinateur.FACILE_HEURISTIQUE:
                                    format[i] = new Sauvegarde(plateaux1, str[1], "Facile", Integer.parseInt(str[0]));
                                    break;
                                case Ordinateur.MOYEN:
                                    format[i] = new Sauvegarde(plateaux1, str[1], "Moyen", Integer.parseInt(str[0]));
                                    break;
                                case Ordinateur.DIFFICILE:
                                    format[i] = new Sauvegarde(plateaux1, str[1], "Difficile", Integer.parseInt(str[0]));
                                    break;

                            }*/
                            break;
                            case FabriqueArbitre.SIMULATION:
                                format[i] = new Sauvegarde(plateaux1, "", str[1], Integer.parseInt(str[0]));
                                break;

                        }

               }
               Interface.goLoadGame(format);
             }
        }catch(FileNotFoundException e){
                 
        }
        prop = p;
    }
    
    /**
     *
     * @return
     */
    public static Plateau charger(String plateau, Plateau res){
        try {
            input = new Scanner(new FileInputStream("Sauvegardes/"+plateau));
        } catch (FileNotFoundException ex) {
            Logger.getLogger(Chargeur.class.getName()).log(Level.SEVERE, null, ex);
        }
        input.nextLine();
        String[] str = input.nextLine().split(":");
        System.out.println(Arrays.toString(str));
        t = Integer.parseInt(str[0]);
        d = Integer.parseInt(str[1]);
        n[0] = Integer.parseInt(str[2]);
        n[1] = Integer.parseInt(str[3]);
        j = Integer.parseInt(str[4]);
        
        String line = "";
        joueurs[0] = input.nextLine();
        joueurs[1] = input.nextLine();
        
        line = input.nextLine();
        boolean p = false, g = false; int r=0;
        while(input.hasNext() && !line.equals("Historique en cours")){
            if(line.equals("plateau")){
                p = true;
                line = input.nextLine();
            }else if(line.equals("graphe")){
                g=true;
                p =false;
                line = input.nextLine();
            }else if(!p && !g){
                if(!line.equals("null"))
                    res.setReine(r++, new Point(line));
                else
                    r++;
                line = input.nextLine();
            }else if(p){
                System.out.println("\t"+ line.split("_")[1]);
                Point point = new Point(line.split("_")[0]);
                str = line.split("_")[1].split(":");
                Point p2 = new Point(str[0]);
                Case c = new Case(p2.x(), p2.y(), Reglage.lis("lCase"),Reglage.lis("hCase") );
                for(int i=1; i<str.length; i++)
                    c.deposePion(FabriqueInsecte.creer(str[i]));
                res.matrice().put(point, c);
                res.utilises().add(point);
                line = input.nextLine();
            }else if(g){
                str = line.split(":");
                Point point = new Point(str[0]);
                List<Point> lp = new ArrayList();
                for(int i=1; i<str.length; i++)
                    lp.add(new Point(str[i]));
                res.voisins().put(point, lp);
                line = input.nextLine();
            }
        }
        
        h = new Stack();
        Stack<Coup> hBis = new Stack();
        line = input.nextLine();
        while(input.hasNext() && !line.equals("Refaire en cours")){
            if(line.charAt(0)=='(')
                hBis.push(new Deplacement(0,line));
            else
                hBis.push(new Depot(0,line));
            
            line = input.nextLine();
        }
        while(!hBis.isEmpty())
            h.push(hBis.pop());
        
        r2 = new Stack();
        while(input.hasNext() ){
            if(line.charAt(0)=='(')
                hBis.push(new Deplacement(0,line));
            else
                hBis.push(new Depot(0,line));
            
            line = input.nextLine();
        }
        while(!hBis.isEmpty())
            r2.push(hBis.pop());
        
        return res;
    }
    
    /**
     *
     * @return
     */
    public static int type(){
        return t;
    }

    /**
     *
     * @return
     */
    public static int difficulte(){
        return d;
    }

    /**
     *
     * @return
     */
    public static int jCourant(){
        return j;
    }

    /**
     *
     * @return
     */
    public static int[] nbCourant(){
        return n;
    }

    /**
     *
     * @return
     */
    public static String[] joueur(){
        return joueurs; 
    }

    /**
     *
     * @return
     */
    public static Stack<Coup> historique(){
        return h;
    }

    /**
     *
     * @return
     */
    public static Stack<Coup> refaire(){
        return r2;
    }
    
    public static String propriete(String sauvegarde){
        
        return "";
    }
}
