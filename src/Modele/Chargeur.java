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
import java.util.HashMap;
import java.util.List;
import java.util.Properties;
import java.util.Scanner;
import java.util.Stack;
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
    static HashMap<String, String> propSauvegarde;
    static HashMap<String, String> sauvegardes;
    
    /**
     *
     * @param p
     */
    public static void init(Properties p){
        try{
             input = new Scanner(new FileInputStream("Sauvegardes/Sauvegarde")) ;
             if(input.hasNext()){
                String[] plateaux = input.nextLine().split(":");
                String[] format = new String[plateaux.length];
                input.close();
                propSauvegarde = new HashMap();
                sauvegardes = new HashMap();
                for (int i=0; i<plateaux.length; i++) {
                    String plateaux1 = plateaux[i];
                    input = new Scanner(new FileInputStream("Sauvegardes/"+plateaux1)) ;
                    String pl = input.nextLine();
                    String[] str = pl.split("::");

                    switch(Integer.parseInt(str[0])){
                        case FabriqueArbitre.LOCAL_JVJ:
                            format[i] = String.format("%12s %12s %12s",plateaux1, str[2], str[3]);
                            break;
                        case FabriqueArbitre.LOCAL_JVIA:
                            switch(Integer.parseInt(str[2])){
                                case Ordinateur.FACILE_ALEATOIRE:
                                    format[i] = String.format("%s%s%-20s %-20s",plateaux1, " ", str[1], "Très Facile");
                                    break;
                                case Ordinateur.FACILE_HEURISTIQUE:
                                    format[i] = String.format("%s|%-20s %-20s",plateaux1, str[1], "Facile");
                                    break;
                                case Ordinateur.MOYEN:
                                    format[i] = String.format("%s|%-20s %-20s",plateaux1, str[1], "Moyen");
                                    break;
                                case Ordinateur.DIFFICILE:
                                    format[i] = String.format("%s|%-20s %-20s",plateaux1, str[1], "Difficile");
                                    break;

                            }
                            break;
                        case FabriqueArbitre.SIMULATION:

                            break;

                    }

                   sauvegardes.put(plateaux1, pl);
                   String sauv = "";
                   while(input.hasNext()){
                       pl = input.nextLine();
                       if(input.hasNext())
                           sauv += (pl+"\n");
                       else
                           sauv+=pl;
                   }
                   sauvegardes.put(plateaux1, sauv);

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
    public static Plateau charger(String plateau){
        Plateau res = new Plateau(0,0,0,0,prop);
        String[] sauv = sauvegardes.get(plateau).split("\n");
        String[] str = sauv[0].split(":");
        t = Integer.parseInt(str[0]);
        d = Integer.parseInt(str[1]);
        n[0] = Integer.parseInt(str[2]);
        n[1] = Integer.parseInt(str[3]);
        j = Integer.parseInt(str[4]);
        
        String line = "";
        joueurs[0] = sauv[1];
        joueurs[1] = sauv[2];
        
        line = sauv[3];
        int k=3;
        boolean p = false, g = false; int r=0;
        while(k<sauv.length && !line.equals("Historique en cours")){
            k++;
            if(line.equals("plateau")){
                p = true;
                line = sauv[k];
            }else if(line.equals("graphe")){
                g=true;
                p =false;
                line = sauv[k];
            }else if(!p && !g){
                if(!line.equals("null"))
                    res.setReine(r++, new Point(line));
                else
                    r++;
                line = sauv[k];
            }else if(p){
                Point point = new Point(line.split("_")[0]);
                str = line.split("_")[1].split(":");
                Point p2 = new Point(str[0]);
                Case c = new Case(p2.x(), p2.y(), Reglage.lis("lCase"),Reglage.lis("hCase") );
                for(int i=1; i<str.length; i++)
                    c.deposePion(FabriqueInsecte.creer(str[i]));
                res.matrice().put(point, c);
                res.utilises().add(point);
                line = sauv[k];
            }else if(g){
                str = line.split(":");
                Point point = new Point(str[0]);
                List<Point> lp = new ArrayList();
                for(int i=1; i<str.length; i++)
                    lp.add(new Point(str[i]));
                res.voisins().put(point, lp);
                line = sauv[k];
            }
        }
        
        h = new Stack();
        Stack<Coup> hBis = new Stack();
        k++;
        line = sauv[k];
        while(k<sauv.length && !line.equals("Refaire en cours")){
            k++;
            if(line.charAt(0)=='(')
                hBis.push(new Deplacement(0,line));
            else
                hBis.push(new Depot(0,line));
            
            line = sauv[k];
        }
        while(!hBis.isEmpty())
            h.push(hBis.pop());
        
        k++;
        r2 = new Stack();
        while(sauv.length>k){
            line = sauv[k];
            k++;
            if(line.charAt(0)=='(')
                hBis.push(new Deplacement(0,line));
            else
                hBis.push(new Depot(0,line));
            
//            line = sauv[k];
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
