/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Modele;

import static Modele.Plateau.cloneList;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

/**
 *
 * @author grandmax
 */
public class Deplacement extends Coup{
    Point source;
    List<Point> route;
    
    public Deplacement(int j,  Point s, Point d){
        super(j);
        source = new Point(s.x(), s.y());
        destination = new Point(d.x(), d.y());
        route = new LinkedList();
        route.add(s);
        route.add(d);
    }
    
    public Deplacement(int j, String str){
        super(j);
        String[] r = str.split("->");
        route = new LinkedList();
        for(int i=0; i<r.length; i++)
            route.add(new Point(r[i]));
        
        source = new Point(route.get(0).x(), route.get(0).y());
        destination = new Point(route.get(route.size()-1).x() , route.get(route.size()-1).y() );
    }
    
    public Point source(){
        return source;
    }
    
    public boolean aDejaVisite(Point p){
        return route.contains(p);
    }
    
    public void add(Point p){
        destination.fixe(p.x(), p.y());
        route.add(destination);
    } 
    
    public List<Point> route(){
        return route;
    }
    
    public boolean equals(Object o){
        if(o instanceof Deplacement){
            Deplacement d = (Deplacement) o;
            return (d.source().equals(source) && d.destination().equals(destination) && d.joueur()==joueur );
        }
        return true;
    }
    
    public Deplacement clone(){
        Deplacement nouv = new Deplacement(joueur, new Point(source.x(), source.y()), new Point(destination.x(), destination.y()));
        
        nouv.route.clear();
        nouv.route = cloneList(route);
        
        return nouv;
    }
    
    @Override
    public String toString(){
        //System.out.println((route.size()-2)+" point intermédiaires");
        String res = "";
        
        Iterator<Point> it = route.iterator();
        res += it.next();
        
        while(it.hasNext()){
            res += "->"+it.next();
        }
        return res;
    }
}
