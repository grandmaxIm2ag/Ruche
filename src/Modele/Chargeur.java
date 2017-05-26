/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Modele;

import Joueurs.Ordinateur;
import Modele.Arbitres.FabriqueArbitre;
import Vue.Interface;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
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
    //static HashMap<String, String> propSauvegarde;
    //static HashMap<String, String> sauvegardes;
    static HashMap<String, Sauvegarde> sauvegardes2;
    
    /**
     *
     * @param p
     */
    public static void init(Properties p){
        sauvegardes2 = new HashMap();
        boolean vide = true;
        try{
             input = new Scanner(new FileInputStream("Sauvegardes/Sauvegarde")) ;
             if(input.hasNext()){
                 vide = false;
                String[] plateaux = input.nextLine().split(":");
                Sauvegarde[] format = new Sauvegarde[plateaux.length];
                input.close();
                //propSauvegarde = new HashMap();
                //sauvegardes = new HashMap();
                for (int i=0; i<plateaux.length; i++) {
                    String plateaux1 = plateaux[i];
                    input = new Scanner(new FileInputStream("Sauvegardes/"+plateaux1)) ;
                    String pl = input.nextLine();
                    String[] str = pl.split("::");
                    Sauvegarde s=null;
                    switch(Integer.parseInt(str[0])){
                        case FabriqueArbitre.LOCAL_JVJ:
                            s = new Sauvegarde(plateaux1, str[1], str[2]);
                            s.setPropriete(Integer.parseInt(str[0]));
                            sauvegardes2.put(plateaux1, s);
                            break;
                        case FabriqueArbitre.LOCAL_JVIA:
                            s = new Sauvegarde(plateaux1,str[1],"");
                            s.setPropriete(Integer.parseInt(str[0]),Integer.parseInt(str[2]));
                            sauvegardes2.put(plateaux1, s);
                            break;
                        case FabriqueArbitre.LOCAL_IAVJ:
                            s = new Sauvegarde(plateaux1,"", str[2]);
                            s.setPropriete(Integer.parseInt(str[0]),Integer.parseInt(str[1]));
                            sauvegardes2.put(plateaux1, s);
                            break;
                        case FabriqueArbitre.SIMULATION:
                            s = new Sauvegarde(plateaux1, str[1], "Très Facile");
                            s.setPropriete(Integer.parseInt(str[0]),Ordinateur.FACILE_ALEATOIRE);
                            sauvegardes2.put(plateaux1, s);
                            break;

                    }
                    /*switch(Integer.parseInt(str[0])){
                        case FabriqueArbitre.LOCAL_JVJ:
                            format[i] = String.format("%s:%s:%s",plateaux1, str[2], str[3]);
                            s = new Sauvegarde(plateaux1, str[2], str[3]);
                            s.setPropriete(0);
                            sauvegardes2.put(plateaux1, s);
                            break;
                        case FabriqueArbitre.LOCAL_JVIA:
                            switch(Integer.parseInt(str[2])){
                                case Ordinateur.FACILE_ALEATOIRE:
                                    format[i] = String.format("%s:%s:%s",plateaux1, str[1], "Très Facile");
                                    s = new Sauvegarde(plateaux1, str[1], "Très Facile");
                                    s.setPropriete(0,Ordinateur.FACILE_ALEATOIRE);
                                    sauvegardes2.put(plateaux1, s);
                                    break;
                                case Ordinateur.FACILE_HEURISTIQUE:
                                    format[i] = String.format("%s:%s:%s",plateaux1, str[1], "Facile");
                                    s = new Sauvegarde(plateaux1, str[1], "Facile");
                                    s.setPropriete(0,Ordinateur.FACILE_HEURISTIQUE);
                                    sauvegardes2.put(plateaux1, s);
                                    break;
                                case Ordinateur.MOYEN:
                                    format[i] = String.format("%s:%s:%s",plateaux1, str[1], "Moyen");
                                    s = new Sauvegarde(plateaux1, str[1], "Moyen");
                                    s.setPropriete(0,Ordinateur.MOYEN);
                                    sauvegardes2.put(plateaux1, s);
                                    break;
                                case Ordinateur.DIFFICILE:
                                    format[i] = String.format("%s:%s:%s",plateaux1, str[1], "Difficile");
                                    s = new Sauvegarde(plateaux1, str[1], "Difficile");
                                    s.setPropriete(0,Ordinateur.DIFFICILE);
                                    sauvegardes2.put(plateaux1, s);
                                    break;

                            }
                            break;
                        case FabriqueArbitre.LOCAL_IAVJ:
                            switch(Integer.parseInt(str[1])){
                                case Ordinateur.FACILE_ALEATOIRE:
                                    format[i] = String.format("%s:%s:%s",plateaux1, "Très Facile", str[2]);
                                    s = new Sauvegarde(plateaux1, str[1], "Très Facile");
                                    s.setPropriete(0,Ordinateur.FACILE_ALEATOIRE);
                                    sauvegardes2.put(plateaux1, s);
                                    break;
                                case Ordinateur.FACILE_HEURISTIQUE:
                                    format[i] = String.format("%s:%s:%s",plateaux1, str[1], "Facile");
                                    s = new Sauvegarde(plateaux1, str[1], "Facile");
                                    s.setPropriete(0,Ordinateur.FACILE_HEURISTIQUE);
                                    sauvegardes2.put(plateaux1, s);
                                    break;
                                case Ordinateur.MOYEN:
                                    format[i] = String.format("%s:%s:%s",plateaux1, str[1], "Moyen");
                                    s = new Sauvegarde(plateaux1, str[1], "Moyen");
                                    s.setPropriete(0,Ordinateur.MOYEN);
                                    sauvegardes2.put(plateaux1, s);
                                    break;
                                case Ordinateur.DIFFICILE:
                                    format[i] = String.format("%s:%s:%s",plateaux1, str[1], "Difficile");
                                    s = new Sauvegarde(plateaux1, str[1], "Difficile");
                                    s.setPropriete(0,Ordinateur.DIFFICILE);
                                    sauvegardes2.put(plateaux1, s);
                                    break;

                            }
                            break;
                        case FabriqueArbitre.SIMULATION:
                            s = new Sauvegarde(plateaux1, str[1], "Très Facile");
                            s.setPropriete(0,Ordinateur.FACILE_ALEATOIRE);
                            sauvegardes2.put(plateaux1, s);
                            break;

                    }

                   sauvegardes.put(plateaux1, pl);*/
                   String sauv = "";
                   while(input.hasNext()){
                       pl = input.nextLine();
                       if(input.hasNext())
                           sauv += (pl+"\n");
                       else
                           sauv+=pl;
                   }
                   s.setDonne(sauv);
                   format[i] = s;
                   //sauvegardes.put(plateaux1, sauv);

               }
               Interface.goLoadGame(format);
             }
        }catch(FileNotFoundException e){
                Interface.goLoadGame(new Sauvegarde[0]);
        }
        if(vide)
            Interface.goLoadGame(new Sauvegarde[0]);
        prop = p;
    }
    
    /**
     *
     * @return
     */
    public static Plateau charger(String plateau){
        Plateau res = new Plateau(0,0,0,0,prop);
        String[] sauv = sauvegardes2.get(plateau).getDonnee().split("\n");
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
                System.out.println(p2+" "+Arrays.toString(str));
                for(int i=1; i<str.length; i++)
                    c.deposePion(FabriqueInsecte.creer(str[i]));
                res.matrice().put(point, c);
                res.utilises().add(point);
                line = sauv[k];
                System.gc();
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
    
    
    public static void add(String p, Sauvegarde s){
        
        sauvegardes2.put(p,s);
    }
    public static void sauver(){
        PrintWriter out1 = null;
        try {
            boolean premier = true;
            out1 = new PrintWriter(new File("Sauvegardes/Sauvegarde"));
            for(Map.Entry<String, Sauvegarde> entry : sauvegardes2.entrySet() ){
                if(entry.getValue().sauver){
                    PrintWriter out2 = new PrintWriter(new File("Sauvegardes/"+entry.getKey()));
                    out2.print(entry.getValue().propriete()+"\n"+entry.getValue().getDonnee());
                    out2.close();
                    if(premier){
                        out1.print(entry.getKey());
                        premier = false;
                    }else{
                        out1.print(":"+entry.getKey());
                    }
                }else{
                    File f = new File(entry.getKey());
                    if(f.exists())
                        f.delete();
                }
            }
            out1.close();
        } catch (FileNotFoundException ex) {
            Logger.getLogger(Chargeur.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            out1.close();
        }
    }
    
    public static HashMap<String, Sauvegarde> sauvegardes(){
        return sauvegardes2;
    }
}
