/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Modele;

import java.util.Properties;
import java.util.Scanner;
import ruche.Reglage;

/**
 *
 * @author grandmax
 */
public class Chargeur {
    Scanner input;
    
    Properties prop;
    int t=0, d=0, j=0;
    int[] n = {0,0};
    String[] joueurs = {"null", "null"};
    
    public void init(Properties p, String plateau){
        input = new Scanner(ClassLoader.getSystemClassLoader().getResourceAsStream(plateau));
        prop = p;
    }
    
    public Plateau charger(){
        Plateau res = new Plateau(0,0,0,0,prop);
        
        String[] str = input.nextLine().split(":");
        t = Integer.parseInt(str[0]);
        d = Integer.parseInt(str[1]);
        n[0] = Integer.parseInt(str[2]);
        n[1] = Integer.parseInt(str[3]);
        j = Integer.parseInt(str[4]);
        
        String line = "";
        joueurs[0] = input.nextLine();
        joueurs[1] = input.nextLine();
        
        boolean p = false, g = false; int r=0;
        while(input.hasNext() && !line.equals("Historique en cours")){
            line = input.nextLine();
                    
            if(line.equals("plateau")){
                p = true;
            }else if(line.equals("graphe")){
                g=true;
                p =false;
            }else if(!p && !g){
                res.setReine(r++, new Point(line));
            }else if(p){
                Point point = new Point(line.split("|")[0]);
                str = line.split("|")[1].split(":");
                Point p2 = new Point(str[0]);
                Case c = new Case(p2.x(), p2.y(), Reglage.lis("lCase"),Reglage.lis("hCase") );
                for(int i=1; i<str.length; i++)
                    c.deposePion(FabriqueInsecte.creer(str[i]));
                res.matrice().put(point, c);
                res.utilises().add(point);
            }else if(g){
                str = line.split(":");
                Point point = new Point(str[0]);
                
            }
        }
        
        return res;
    }
    
    int type(){
        return t;
    }
    int difficulte(){
        return d;
    }
    int jCourant(){
        return j;
    }
    int[] nbCourant(){
        return n;
    }
    String[] joueur(){
        return joueurs; 
    }
}
