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
import java.util.Stack;
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
        
        xMin=0; xMax = 0; yMin=0; yMax=0;
    }
    
    public void setReine(int idx, Point p){
        reines[idx] = p;
    }
    public void premierPion(Insecte e){
        Case c = new Case(0,0, 1, 1);
        c.deposePion(e);
        if(e.type()==Insecte.REINE)
            reines[Arbitre.J1] = new Point(0,0);
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
        Case ca = matrice.get(e.position());
        ca.retirePion();
        if(ca.utilise())
            matrice.put(e.position(), ca);
        else{
            voisins.remove(e.position());
        }
        boolean b = estConnexe(e);
        deposePion(new Depot(e.joueur(), e.type(), e.position()));
        if(b){
            for(int i=0; i<coups.length; i++){
                b |= d.equals(coups[i]);
            }
        }
        return b;
        
    }
    public void deplacePion(Deplacement d){
        if(d.source().equals(reines[d.joueur()]))
            reines[d.joueur()] = d.destination();
        Case c = matrice.get(d.source());
        Insecte e = c.retirePion();
        Case c2;
        if(matrice.get(d.destination())==null)
            c2 = new Case(d.destination().x(), d.destination.y(), Reglage.lis("lCase"), Reglage.lis("hCase"));
        else
            c2 = matrice.get(d.destination());
        
        e.position().fixe(c2.position().x(), c2.position().y());
        c2.deposePion(e);
        matrice.put(d.destination(), c2);
        if(c.utilise())
            matrice.put(d.source(), c);
        else{
            matrice.remove(c.position());
            majGraphe(d);
        }
    }
    
    public void afficheGraphe(){
        System.out.println("NB PIECES POSEES "+voisins.size());
        for(Map.Entry<Point, List<Point>> entry : voisins.entrySet()){
                List<Point> l = entry.getValue();
                String str = entry.getKey().toString();
                for(Point point : l)
                    str+=":"+point;
                System.out.println(str);
            }
    }
    
    public void majGraphe(Point source){
        if(matrice.get(source)==null){
           voisins.remove(source);
           utilises.remove(source);
           for(Map.Entry<Point, List<Point> >  entry : voisins.entrySet()){
               List<Point> v2 = entry.getValue();
               v2.remove(source);
               voisins.put(entry.getKey(), v2);
           }
        }
    }
    
    public void majGraphe(Deplacement d){
        
        if(matrice.get(d.source())==null){
           voisins.remove(d.source());
           utilises.remove(d.source());
           for(Map.Entry<Point, List<Point> >  entry : voisins.entrySet()){
               List<Point> v2 = entry.getValue();
               v2.remove(d.source());
               voisins.put(entry.getKey(), v2);
           }
           
        }
        
        if(voisins.get(d.destination())==null){
            List<Point> v = new ArrayList();
            for(int i=(int)d.destination().x()-1 ; i<= (int)d.destination().x()+1; i++ )
                for(  int j= (int)d.destination().y()-1 ; j<= (int)d.destination().y()+1; j++ ){
                    if(!((i==(int)d.destination().x()-1 && j==(int)d.destination().y()-1) || (i==(int)d.destination().x()+1 && j==(int)d.destination().y()+1) ))
                        if(voisins.get(new Point(i,j))!=null){
                            v.add(new Point(i,j));
                        }
                }
            Iterator<Point> it = v.iterator();
            while(it.hasNext()){
                Point tmp = it.next();
                List<Point> v2 = voisins.get(tmp);
                v2.add(d.destination());
                voisins.put(tmp, v2);
            }
            voisins.put(d.destination(), cloneList(v));
            utilises.add(d.destination());
        }
        
    }
    public void majGraphe(Depot d){
        List<Point> p = new ArrayList();
        
        for(int i=(int)d.destination().x()-1 ; i<= (int)d.destination().x()+1; i++ )
            for(  int j= (int)d.destination().y()-1 ; j<= (int)d.destination().y()+1; j++ )
                if(!((i==(int)d.destination().x()-1 && j==(int)d.destination().y()-1) || (i==(int)d.destination().x()+1 && j==(int)d.destination().y()+1) ))
                    if(voisins.get(new Point(i,j))!=null)
                        p.add(new Point(i,j));
        
        Iterator<Point> it = p.iterator();
        while(it.hasNext()){
            Point tmp = it.next();
            List<Point> tmp2 = voisins.get(tmp);
            tmp2.add(d.destination());
            voisins.put(tmp, tmp2);
        }
        voisins.put(d.destination(), p);
        utilises.add(d.destination());
        
    }
    
    public void deposePion(Depot d){
        if(d.type()==Insecte.REINE)
            reines[d.joueur()] = d.destination();
        Case c2 = new Case(d.destination().x(), d.destination.y(), Reglage.lis("lCase"), Reglage.lis("hCase"));
        c2.deposePion(FabriqueInsecte.creer(d.type(), d.joueur(), d.destination()));
        matrice.put(d.destination(), c2);
        
        if(xMin > d.destination.x())
            xMin=(int) d.destination.x();
        if(xMax < d.destination.x())
            xMax=(int) d.destination.x();
        if(yMin > d.destination.y())
            yMin=(int) d.destination.y();
        if(yMax < d.destination.y())
            yMax=(int) d.destination.y();
        
        majGraphe(d);
    }
    public boolean estEncerclee(int j){
        boolean b = true;
        
        if(reines[j]==null)
            return false;
        
        for(int i=(int)reines[j].x()-1 ; i<= (int)reines[j].x()+1; i++ )
            for(int k=(int)reines[j].y()-1 ; k<= (int)reines[j].y()+1; k++ )
                if(!((i==(int)reines[j].x()-1 && k==(int)reines[j].y()-1) || (i==(int)reines[j].x()+1 && k==(int)reines[j].y()+1) ) && !reines[j].equals(new Point(i,k)))
                    b &= matrice.get(new Point(i,k))!=null;
        
        return b;
    }
    
    public boolean estConnexe(Insecte e){
        boolean b = true;
        Iterator<Point> it1 = voisins.get(e.position()).iterator();
        while(it1.hasNext()){
            Iterator<Point> it2 = utilises.iterator();
            Point tmp = it1.next();
            while(it2.hasNext()){
                Point tmp2 = it2.next();
                if(!tmp.equals(tmp2) && !voisins.get(tmp).contains(tmp2)){
                    b&=voisin(tmp, tmp2, cloneList(utilises));
                }
            }
                
        }
        
        utilises.remove(e.position());
        voisins.remove(e.position());
        return b;
    }
    public boolean voisin(Point p1, Point p2, List<Point> k){
        if(k.isEmpty()){
            if(voisins.get(p1)!=null)
                return voisins.get(p1).contains(p2);
            return false;
         }else{
            Point t = k.get(k.size()-1);
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
        Coup[] c = deplacementPossible(joueur);
        return c.length > 0;
    }
    
    public Coup[] deplacementPossible(int j){
        List<Coup> c = new ArrayList();
        Iterator<Point> u = cloneList(utilises).iterator();
        while(u.hasNext()){
            Point tmp = u.next();
            if(matrice.get(tmp).tete().joueur()==j){
                List<Coup> cBis = deplacementPossible(matrice.get(tmp).tete());
                if(cBis != null)
                    c.addAll(cBis);
            }
        }
        
        Coup[] coups = new Coup[c.size()];
        Iterator<Coup> it = c.iterator();
        int i=0; 
        while(it.hasNext() && i<coups.length)
            coups[i++]=it.next();
        return coups;
    }
    
    public List<Coup> deplacementPossible(Insecte e){
        if(matrice.get(e.position()) == null || !e.equals(matrice.get(e.position()).tete()) )
            return null;
        
        List<Coup[]> tab = new ArrayList();
        List<Coup> c = new ArrayList();
        boolean b = true;
        Case ca = matrice.get(e.position());
        ca.retirePion();
        if(ca.utilise())
            matrice.put(e.position(), ca);
        else{
            matrice.remove(e.position());
            b = estConnexe(e);
        }
        this.deposePion(new Depot(e.joueur(), e.type(), e.position()));
        
        if(b){
            Coup[] cp = matrice.get(e.position()).tete().deplacementValide(this.matrice());

            for(int k=0; k<cp.length; k++){
                if(cp[k] instanceof Deplacement){
                    Deplacement d = (Deplacement) cp[k];
                    c.add(d);
                }
            }

            return c;
        }
        return null;
    }
    
    public Coup[] depotPossible(int joueur, int t){
        
        if(utilises.isEmpty()){
            Coup[] res = new Coup[1];
            res[0] = new Depot(joueur, t, new Point(0,0));
            return res;
        }else if(utilises.size()==1){
            List<Coup> c = new ArrayList();
            for(int i=xMin-1; i<=xMax+1; i++)
                for(int j=yMin-1; j<=yMax+1; j++){
                    Depot d = new Depot(joueur, t, new Point(i,j));
                    if(premierPionValide(d)){
                        c.add(d);
                    }
                }
            Coup[] coups = new Coup[c.size()];
            Iterator<Coup> it = c.iterator();
            int i=0; 
            while(it.hasNext() && i<coups.length)
                coups[i++]=it.next();
            return coups;
        }else{
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
    public List<Point> voisins(Point p){
        return voisins.get(p);
    }
    public List<Point> utilises(){
        return utilises;
    }
    
    @Override
    public int hashCode(){
        return toString().hashCode();
    }
    
}
