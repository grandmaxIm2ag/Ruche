/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
   https://fr.boardgamearena.com/ 
 */
package Modele;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.TreeMap;

/**
 *
 * @author grandmax
 */
public class Plateau extends Composant {
    public Case[][] matrice;
    int xMin, yMin;
    Properties prop;
    Point[] reines;

    List<Point> utilises;
    Map<Point, List<Point>> voisins;
    
    public Plateau(double x, double y, double larg, double haut, Properties p) {
        super(x, y, larg, haut);
        matrice = new Case[(int)larg*2][(int)larg*2];
        prop = p;
        
        voisins = new HashMap();
        utilises = new ArrayList();
    }
    
    public void premierPion(Insecte e){
        matrice[(int)(l-1)][(int)(l-1)].deposePion(e);
    }
    
    public boolean deposePionValide(Depot d){
        boolean b = !matrice[(int)d.destination.x()][(int)d.destination.y()].utilise();
        if(b)
            for(int i=(int)d.destination().x()-1 ; i<= (int)d.destination().x()+1 && b; i++ )
                 for(  int j= (int)d.destination().y()-1 ; j<= (int)d.destination().y()+1 && b; j++ )
                    if(!((i==(int)pos.x()-1 && j==(int)pos.y()-1) || (i==(int)pos.x()+1 && j==(int)pos.y()+1) ))
                        if(matrice[i][j].utilise())
                            b&=(d.joueur()==matrice[i][j].tete().joueur());
        return b ;
    }
    public boolean deplacePionValide(Deplacement d){
        Insecte e = matrice[(int)d.source().x()][(int)d.source().y()].tete();
        Coup[] coups = e.deplacementValide(matrice);
        
        boolean b = false;
        for(int i=0; i<coups.length; i++){
            b |= d.equals(coups[i]);
        }
        
        if(b){
            Plateau p = clone();
            p.matrice[(int)d.source.x()][(int)d.source.y()].retirePion();
            p.majGraphe(d.source());
            b &= p.estConnexe(); 
        }
        
        return b;
    }
    public void deplacePion(Deplacement d){
        Insecte e = matrice[(int)d.source().x()][(int)d.source().y()].retirePion();
        matrice[(int)d.destination().x()][(int)d.destination().y()].deposePion(e);
        majGraphe(d);
    }
    
    public void majGraphe(Point source){
        if(!matrice[(int)source.x()][(int)source.y()].utilise()){
           List<Point> v = voisins.get(source);
           Iterator<Point> it = v.iterator();
           while(it.hasNext()){
               Point tmp = it.next();
               List<Point> v2 = voisins.get(tmp);
               v2.remove(source);
               voisins.put(tmp,v2);
           }
           voisins.remove(source);
           utilises.remove(source);
        }
    }
    
    public void majGraphe(Deplacement d){
        if(!matrice[(int)d.source().x()][(int)d.source().y()].utilise()){
           List<Point> v = voisins.get(d.source());
           Iterator<Point> it = v.iterator();
           while(it.hasNext()){
               Point tmp = it.next();
               List<Point> v2 = voisins.get(tmp);
               v2.remove(d.source());
               voisins.put(tmp,v2);
           }
           voisins.remove(d.source());
           utilises.remove(d.source());
        }
        
        if(voisins.get(d.destination())==null){
            List<Point> v = new ArrayList();
            for(int i=(int)d.destination().x()-1 ; i<= (int)d.destination().x()+1; i++ )
                for(  int j= (int)d.destination().y()-1 ; j<= (int)d.destination().y()+1; j++ ){
                    if(!((i==(int)pos.x()-1 && j==(int)pos.y()-1) || (i==(int)pos.x()+1 && j==(int)pos.y()+1) ))
                        if(matrice[i][j].utilise()){
                            v.add(new Point(i,j));
                        }
                }
            voisins.put(d.destination(), v);
            Iterator<Point> it = v.iterator();
            while(it.hasNext()){
                Point tmp = it.next();
                List<Point> v2 = voisins.get(tmp);
                v2.add(d.destination());
                voisins.put(tmp, v2);
            }
            utilises.add(d.destination());
        }
    }
    public void deposePion(Depot d){
        matrice[(int)d.destination().x()][(int)d.destination().y()].deposePion(FabriqueInsecte.creer(d.type(), d.joueur(), d.destination()));
        utilises.add(d.destination());
        voisins.put(d.destination(), new ArrayList());
    }
    public boolean estEncerclee(int j){
        boolean b = true;
        
        for(int i=(int)reines[j].x()-1 ; i<= (int)reines[j].x()+1; i++ )
            for(int k=(int)reines[j].y()-1 ; k<= (int)reines[j].y()+1; k++ )
                if(!((i==(int)pos.x()-1 && j==(int)pos.y()-1) || (i==(int)pos.x()+1 && j==(int)pos.y()+1) ) && !matrice[i][k].position().equals(reines[j]))
                    b &= matrice[i][k].utilise();
        
        return b;
    }
    
    public boolean estConnexe(){
        boolean b = true;
        Iterator<Point> it1 = utilises.iterator();
        
        while(it1.hasNext()){
            Iterator<Point> it2 = cloneList(utilises).iterator();
            Point tmp = it1.next();
            while(it2.hasNext()){
                Point tmp2 = it2.next();
                if(!tmp.equals(tmp2))
                    b&=voisin(tmp, tmp2, cloneList(utilises));
            }
                
        }
        
        return b;
    }
    public boolean voisin(Point p1, Point p2, List<Point> k){
         if(k.isEmpty()){
             return voisins.get(p1).contains(p2);
         }else{
             Point t = k.get(0);
            boolean b1 = voisin(p1,t,cloneList(k));
            boolean b2 = voisin(t,p2,cloneList(k));
            boolean b3 = voisin(p1,p2,cloneList(k));
            
            return ((b1&&b2)||b3);
         }
    }
    @Override
    public boolean equals(Object o) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public boolean accept(Visiteur v) {
        boolean b=v.visite(this);
        for(int i=0; i<matrice.length; i++)
            for(int j=0; j<matrice[i].length; j++)
                b |= v.visite(matrice[i][j]);
            
        return b;
    }
    
    public Plateau clone(){
        Plateau nouv = new Plateau(pos.x(), pos.y(), l, h, prop);
        
        for(int i=0; i<reines.length; i++){
            nouv.reines[i]= new Point(0,0);
            nouv.reines[i].fixe(reines[i].x(), reines[i].y());
        }
        
        for(int i=0; i<matrice.length; i++){
            for(int j=0; j<matrice.length; j++){
                nouv.matrice[i][j] = matrice[i][j].clone();
            }
        }
        
        return nouv;
    }
    public static List<Point> cloneList(List<Point> list) {
    List<Point> clone = new ArrayList<Point>(list.size());
    for (Point item : list) clone.add(item.clone());
    return clone;
}
    @Override
    public String toString(){
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    public boolean aucunCoup(int joueur){
        boolean b = this.accept(new Visiteur(){
           public boolean visite(Insecte e){
               return e.deplacementValide(matrice)!=null;
           } 
        });
        return !b;
    }
    
    public Coup[] deplacementPossible(){
        List<Coup[]> tab = new ArrayList();
        List<Coup> c = new ArrayList();
        Iterator<Point> u = utilises.iterator();
        while(u.hasNext()){
            Point tmp = u.next();
            tab.add(matrice[(int)tmp.x()][(int)tmp.x()].tete().deplacementValide(this.clone().matrice()));
        }
        Iterator<Coup[]> t = tab.iterator();
        while(t.hasNext()){
            Coup[] tmp = t.next();
            for(int k=0; k<tmp.length; k++){
                if(tmp[k] instanceof Deplacement){
                    Deplacement d = (Deplacement) tmp[k];
                    Plateau p = clone();
                    p.matrice[(int)d.source.x()][(int)d.source.y()].retirePion();
                    p.majGraphe(d.source());
                    boolean b = p.estConnexe(); 
                    if(b){
                        c.add(d);
                    }
                }
            }
        }
        Coup[] coups = new Coup[c.size()];
        Iterator<Coup> it = c.iterator();
        int i=0; 
        while(it.hasNext() && i<coups.length)
            coups[i++]=it.next();
        return coups;
    }
    
    public Coup[] depotPossible(int joueur, int t){
        List<Coup> c = new ArrayList();
        for(int i=0; i<matrice.length; i++)
            for(int j=0; j<matrice[i].length; j++){
                Depot d = new Depot(joueur, t, matrice[i][j].position());
                if(deposePionValide(d)){
                    c.add(d);
                }
            }
        Coup[] coups = new Coup[c.size()];
        Iterator<Coup> it = c.iterator();
        int i=0; 
        while(it.hasNext() && i<coups.length)
            coups[i++]=it.next();
        return coups;
    }
    
    
    
    public Case[][] matrice(){
        return matrice;
    }
}
