/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Modele;

import java.util.ArrayList;
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
    Scanner input;
    
    Properties prop;
    int t=0, d=0, j=0;
    int[] n = {0,0};
    String[] joueurs = {"null", "null"};
    Stack<Coup> h, r, r2;
    
    public void init(Properties p, String plateau){
        input = new Scanner(ClassLoader.getSystemClassLoader().getResourceAsStream("Sauvegardes/"+plateau));
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
    
    public int type(){
        return t;
    }
    public int difficulte(){
        return d;
    }
    public int jCourant(){
        return j;
    }
    public int[] nbCourant(){
        return n;
    }
    public String[] joueur(){
        return joueurs; 
    }
    public Stack<Coup> historique(){
        return h;
    }
    public Stack<Coup> refaire(){
        return r2;
    }
}
