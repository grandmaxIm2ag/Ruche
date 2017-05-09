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
import ruche.Reglage;

/**
 *
 * @author grandmax
 */
public class Plateau extends Composant {
    public Map<Point, Case> matrice;
    int xMin, yMin, xMax, yMax;
    Properties prop;
    Point[] reines;
    List<Point> utilises;
    Map<Point, List<Point>> voisins;
    
    public Plateau(double x, double y, double larg, double haut, Properties p) {
        super(x, y, larg, haut);
        matrice = new HashMap();
        prop = p;
        
        voisins = new HashMap();
        utilises = new ArrayList();
        reines = new Point[2];
        
    }
    
    public void setReine(int idx, Point p){
        reines[idx] = p;
    }
    public void premierPion(Insecte e){
        Case c = new Case(0,0, 1, 1);
        c.deposePion(e);
        matrice.put(new Point(0,0), c);
        utilises.add(new Point(0,0));
        voisins.put(new Point(0,0), new ArrayList());
    }
    
    // Cette fonction est là pour tester hein
    public void addPion () {
        Case c = new Case(1,0,1,1);
        c.deposePion(new Reine(1,0,1,1));
        matrice.put(new Point(1,0), c);
        
        c = new Case(0,1,1,1);
        c.deposePion(new Reine(0,1,1,1));
        matrice.put(new Point(0,1), c);
        
        c = new Case(1,1,1,1);
        c.deposePion(new Reine(1,1,1,1));
        matrice.put(new Point(1,1), c);
        
        c = new Case(1,1,1,1);
        c.deposePion(new Reine(1,1,1,1));
        matrice.put(new Point(-1,2), c);
        
        c = new Case(-1,2,1,1);
        c.deposePion(new Reine(-1,2,1,1));
        matrice.put(new Point(-1,2), c);
        
        xMax = 1;
        yMax = 2;
    }
    
    public boolean premierPionValide(Depot d){
        if(d.destination().x < -1 || d.destination().y < -1 || d.destination().x > 1 || d.destination().y > 1 || (d.destination().x == -1 && d.destination().y == -1 )
                || (d.destination().x == 1 && d.destination().y == 1 ))
            return false;
        return true;
    }
    public boolean deposePionValide(Depot d){
        boolean b = (matrice.get(d.destination())==null);
        int degres = 0;
        //System.out.println(matrice.get(d.destination()).utilise());
        if(b)
            for(int i=(int)d.destination().x()-1 ; i<= (int)d.destination().x()+1 && b; i++ )
                 for(  int j= (int)d.destination().y()-1 ; j<= (int)d.destination().y()+1 && b; j++ )
                    if(!((i==(int)d.destination().x()-1 && j==(int)d.destination().y()-1) || (i==(int)d.destination().x()+1 && j==(int)d.destination().y()+1) ))
                        if(matrice.get(new Point(i,j))!=null){
                            b&=(d.joueur()==matrice.get(new Point(i,j)).tete().joueur());
                            degres++;
                        }
        return b && (degres>0) ;
    }
    public boolean deplacePionValide(Deplacement d){
        Insecte e = matrice.get(d.source()).tete();
        Coup[] coups = e.deplacementValide(matrice);
        
        boolean b = false;
        for(int i=0; i<coups.length; i++){
            b |= d.equals(coups[i]);
        }
        
        if(b){
            Plateau p = clone();
            Case c = p.matrice.get(d.source());
            c.retirePion();
            if(c.utilise())
                p.matrice.put(d.source, c);
            else
                p.matrice.remove(d.source());
            p.majGraphe(d.source());
            b &= p.estConnexe(); 
        }
        
        return b;
    }
    public void deplacePion(Deplacement d){
        Case c = matrice.get(d.source());
        Insecte e = c.retirePion();
        Case c2 = new Case(d.destination().x(), d.destination.y(), Reglage.lis("lCase"), Reglage.lis("hCase"));
        c2.deposePion(e);
        matrice.put(d.destination(), c2);
        if(c.utilise())
            matrice.put(d.source(), c);
        else{
            voisins.remove(c.position());
            utilises.remove(c.position());
            matrice.remove(c.position());
        }
            
                
        majGraphe(d);
    }
    
    public void majGraphe(Point source){
        if(matrice.get(source)==null){
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
        if(voisins.get(d.source())==null){
           Iterator<Point> it = utilises.iterator();
           while(it.hasNext()){
                Point tmp = it.next();
                if(!tmp.equals(d.source())){
                    List<Point> v2 = voisins.get(tmp);
                    v2.remove(d.source());
                    voisins.put(tmp,v2);
                }
           }
           voisins.remove(d.source());
           utilises.remove(d.source());
        }
        
        if(voisins.get(d.destination())==null){
            List<Point> v = new ArrayList();
            for(int i=(int)d.destination().x()-1 ; i<= (int)d.destination().x()+1; i++ )
                for(  int j= (int)d.destination().y()-1 ; j<= (int)d.destination().y()+1; j++ ){
                    if(!((i==(int)pos.x()-1 && j==(int)pos.y()-1) || (i==(int)pos.x()+1 && j==(int)pos.y()+1) ))
                        if(voisins.get(new Point(i,j))!=null){
                            v.add(new Point(i,j));
                        }
                }
            
            voisins.put(d.destination(), v);
            Iterator<Point> it = v.iterator();
            while(it.hasNext()){
                Point tmp = it.next();
                List<Point> v2 = cloneList(voisins.get(tmp));
                v2.add(d.destination());
                voisins.put(tmp, v2);
            }
            utilises.add(d.destination());
        }
    }
    public void majGraphe(Depot d){
        List<Point> p = new ArrayList();
        for(int i=(int)d.destination().x()-1 ; i<= (int)d.destination().x()+1; i++ )
            for(  int j= (int)d.destination().y()-1 ; j<= (int)d.destination().y()+1; j++ )
                if(!((i==(int)pos.x()-1 && j==(int)pos.y()-1) || (i==(int)pos.x()+1 && j==(int)pos.y()+1) ))
                    if(voisins.get(new Point(i,j))!=null)
                        p.add(new Point(i,j));
        voisins.put(d.destination(), p);
        Iterator<Point> it = p.iterator();
        while(it.hasNext()){
            Point tmp = it.next();
            List<Point> tmp2 = voisins.get(tmp);
            tmp2.add(d.destination());
            voisins.put(tmp, tmp2);
        }
    }
    
    public void deposePion(Depot d){
        Case c2 = new Case(d.destination().x(), d.destination.y(), Reglage.lis("lCase"), Reglage.lis("hCase"));
        c2.deposePion(FabriqueInsecte.creer(d.type(), d.joueur(), d.destination()));
        matrice.put(d.destination(), c2);
        utilises.add(d.destination());
        
        majGraphe(d);
    }
    public boolean estEncerclee(int j){
        boolean b = true;
        
        for(int i=(int)reines[j].x()-1 ; i<= (int)reines[j].x()+1; i++ )
            for(int k=(int)reines[j].y()-1 ; k<= (int)reines[j].y()+1; k++ )
                if(!((i==(int)pos.x()-1 && k==(int)pos.y()-1) || (i==(int)pos.x()+1 && k==(int)pos.y()+1) ) && !reines[j].equals(new Point(i,k)))
                    b &= matrice.get(new Point(i,k))==null;
        
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
            k.remove(t);
            boolean b1 = voisin(p1,t,cloneList(k));
            boolean b2 = voisin(t,p2,cloneList(k));
            boolean b3 = voisin(p1,p2,cloneList(k));
            
            return ((b1&&b2)||b3);
         }
    }
    @Override
    public boolean equals(Object o) {
        if(o instanceof Plateau){
            Plateau pl = (Plateau) o;
            return matrice.equals(pl.matrice);
        }
        return false;
    }

    @Override
    public boolean accept(Visiteur v) {
        boolean b=v.visite(this);
        for(Map.Entry<Point,Case> entry : matrice.entrySet()) {
            b|=v.visite(entry.getValue());
        }
            
        return b;
    }
    
    public Plateau clone(){
        Plateau nouv = new Plateau(pos.x(), pos.y(), l, h, prop);
        
        for(int i=0; i<reines.length; i++){
            if(reines[i]!=null){
                nouv.reines[i]= new Point(0,0);
                nouv.reines[i].fixe(reines[i].x(), reines[i].y());
            }
        }
        
        for(Map.Entry<Point,Case> entry : matrice.entrySet()) {
            nouv.matrice.put(entry.getKey(), entry.getValue().clone());
        }
        
        nouv.utilises = cloneList(utilises);
        for(Map.Entry<Point,List<Point>> entry : voisins.entrySet()) {
            nouv.voisins.put(entry.getKey(), cloneList(entry.getValue()));
        }
        
        return nouv;
    }
    public static List<Point> cloneList(List<Point> list) {
    List<Point> clone = new ArrayList<>(list.size());
    list.forEach((item) -> {
        clone.add(item.clone());
        });
    return clone;
}
    @Override
    public String toString(){
        String str = "";
        for (Point reine : reines) {
            str += reine + "\n";
        }
        
        str+="plateau"+"\n";
        for(Map.Entry<Point,Case> entry : matrice.entrySet()) {
            str+=entry.getKey()+"_"+entry.getValue()+"\n";
        }
        str+="graphe"+"\n";
        for(Map.Entry<Point,List<Point>> entry2 : voisins.entrySet()) {
            Iterator<Point> it = entry2.getValue().iterator();
            String tmp = it.next().toString();
            while(it.hasNext()){
                tmp+=":"+it.next();
            }
            str+=entry2.getKey()+":"+tmp;
        }
        return str;
    }

    public boolean aucunCoup(int joueur){
        boolean b = this.accept(new Visiteur(){
           public boolean visite(Insecte e){
               return e.deplacementValide(matrice)!=null;
           } 
        });
        return !b;
    }
    
    public Coup[] deplacementPossible(int j){
        List<Coup[]> tab = new ArrayList();
        List<Coup> c = new ArrayList();
        Iterator<Point> u = utilises.iterator();
        while(u.hasNext()){
            Point tmp = u.next();
            if(matrice.get(tmp).tete().joueur()==j)
                tab.add(matrice.get(tmp).tete().deplacementValide(this.clone().matrice()));
        }
        Iterator<Coup[]> t = tab.iterator();
        while(t.hasNext()){
            Coup[] tmp = t.next();
            for(int k=0; k<tmp.length; k++){
                if(tmp[k] instanceof Deplacement){
                    Deplacement d = (Deplacement) tmp[k];
                    Plateau p = clone();
                    Case ca = p.matrice.get(d.source());
                    ca.retirePion();
                    if(ca.utilise())
                        p.matrice.put(d.source, ca);
                    else
                        p.matrice.remove(d.source());
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
    
    public Coup[] deplacementPossible(Insecte e){
        List<Coup[]> tab = new ArrayList();
        List<Coup> c = new ArrayList();
        
        if(matrice.get(e.position()) == null || !e.equals(matrice.get(e.position())))
            return null;
        
        Coup[] cp = matrice.get(e.position()).tete().deplacementValide(this.clone().matrice());
        
        for(int k=0; k<cp.length; k++){
            if(cp[k] instanceof Deplacement){
                Deplacement d = (Deplacement) cp[k];
                Plateau p = clone();
                Case ca = p.matrice.get(d.source());
                ca.retirePion();
                if(ca.utilise())
                    p.matrice.put(d.source, ca);
                else
                    p.matrice.remove(d.source());
               boolean b = p.estConnexe(); 
               if(b){
                    c.add(d);
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
        for(int i=xMin-1; i<=xMax+1; i++)
            for(int j=yMin-1; j<=yMax+1; j++){
                Depot d = new Depot(joueur, t, new Point(i,j));
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
    
    public void retirerPion(Point pos){
        Case c = matrice.get(pos);
        c.retirePion();
        if(!c.utilise()){
            matrice.remove(c.position());
            voisins.remove(c.position());
            utilises.remove(c.position());
        }else{
            matrice.put(pos, c);
        }
    }
    
    public Point reine(int idx){
        return reines[idx];
    }
    
    public Map<Point, Case> matrice(){
        return matrice;
    }
    public Map<Point, List<Point>> voisins(){
        return voisins;
    }
    public List<Point> utilises(){
        return utilises;
    }
    
    
}
