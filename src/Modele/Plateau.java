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
    public final static int EST = 0;
    public final static int OUEST = 1;
    public final static int NORD = 2;
    public final static int SUD = 3;
    public final static int ESTE = 4;
    public final static int NEST = 5;
    public final static int SEST = 6;
    public final static int OUESTE = 7;
    public final static int NOUEST = 8;
    public final static int SOUEST = 9;
    public Map<Point, Case> matrice;
    int xMin, yMin, xMax, yMax;
    Properties prop;
    Point[] reines;
    List<Point> utilises;
    Map<Point, List<Point>> voisins;
    
    int pass;
    int jCourant;
    
    public Plateau(double x, double y, double larg, double haut, Properties p) {
        super(x, y, larg, haut);
        matrice = new HashMap();
        prop = p;
        
        voisins = new HashMap();
        utilises = new ArrayList();
        reines = new Point[2];
        pass=0;
        xMin=0; xMax = 0; yMin=0; yMax=0;
        jCourant = Arbitre.J1;
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
        Case ca = matrice.get(e.position()).clone();
        ca.retirePion();
        boolean b = false;
        if(!ca.utilise()){
            b |= estConnexe(e);
        }
        if(b){
            b=false;
            System.out.println("coucou");
        Coup[] coups = e.deplacementValide(clone().matrice);
            for(int i=0; i<coups.length; i++){
                System.out.println(coups[i]+" "+d.equals(coups[i]));
                b |= d.equals(coups[i]);
            }
        }
        return b;
        
    }
    public void deplacePion(Deplacement d){
        if(matrice.get(d.source()).tete().position().equals(reines[d.joueur()]) && matrice.get(d.source()).tete().type()==Insecte.REINE)
            reines[d.joueur()] = d.destination();
        
        if(xMin > d.destination.x())
            xMin=(int) d.destination.x();
        if(xMax < d.destination.x())
            xMax=(int) d.destination.x();
        if(yMin > d.destination.y())
            yMin=(int) d.destination.y();
        if(yMax < d.destination.y())
            yMax=(int) d.destination.y();
        
        Case c = matrice.get(d.source());
        Insecte e = c.retirePion();
        Case c2;
        if(matrice.get(d.destination())==null)
            c2 = new Case(d.destination().x(), d.destination.y(), Reglage.lis("lCase"), Reglage.lis("hCase"));
        else
            c2 = matrice.get(d.destination());
        
        
        e = FabriqueInsecte.creer(e.type(), e.joueur(), d.destination());
        c2.deposePion(e);
        matrice.put(d.destination(), c2);
        
        
        if(c.utilise()){
            matrice.put(d.source(), c);
        }else{
            matrice.remove(c.position());
        }
        majGraphe(d);
    }
    
    public void afficheGraphe(Map<Point, List<Point>> v){
        for(Map.Entry<Point, List<Point>> entry : v.entrySet()){
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
        //System.out.println(!matrice.containsKey(d.source()));
        if(!matrice.containsKey(d.source())){
           voisins.remove(d.source());
           utilises.remove(d.source());
           for(Map.Entry<Point, List<Point> >  entry : voisins.entrySet()){
               List<Point> v2 = entry.getValue();
               v2.remove(d.source());
               voisins.put(entry.getKey(), v2);
           }
           
        }
        
        //System.out.println(!voisins.containsKey(d.destination()));
        if(!voisins.containsKey(d.destination())){
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
        if(reines[j]==null)
            return false;
        return voisins.get(reines[j]).size()>=6;
    }
    
    public boolean estConnexe(Insecte e){
        boolean b = true;
        List<Point> u = cloneList(utilises);
        Map<Point, List<Point>> v = new HashMap();
        for(Map.Entry<Point, List<Point>> entry : voisins.entrySet()){
            List<Point> v2 = cloneList(entry.getValue());
            if(v2.contains(e.position()))
                v2.remove(e.position());
            v.put(entry.getKey().clone(), v2);
        }
        
        u.remove(e.position());
        v.remove(e.position());
        if(u.isEmpty()){
            return false;
        }else if(u.size()==1)
            return false;
        else{
            Point tmp = u.get(u.size()-1);
            Iterator<Point> it2 = u.iterator();
            
            if(v.get(tmp).isEmpty())
                    return false;
            
            while(it2.hasNext()){
                Point p = it2.next();
                
                if(v.get(p).isEmpty())
                    return false;
                
                if(!tmp.equals(p))
                    b &= voisin(tmp, p, cloneList(u), v);
            }
        }
        
        return b;
    }
    
    //Voisins directs et indirects
    public boolean voisin(Point p1, Point p2, List<Point> k, Map<Point, List<Point>> v)
    {
        if(k.isEmpty()){
            return v.get(p1).contains(p2);
         }else if(v.get(p1).contains(p2)){
             return true;
         }else{
            Point t = k.get(k.size()-1);
            k.remove(t);
            boolean b1 = voisin(p1,t,cloneList(k),v);
            boolean b2 = voisin(t,p2,cloneList(k),v);

            return b1&&b2;
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
    
    @Override
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
        return c.length <= 0;
    }
    
    public Coup[] deplacementPossible(int j){
        List<Coup> c = new ArrayList();
        List<Thread> threads = new ArrayList();
        DeplacementPartage c2 = new DeplacementPartage();
        Iterator<Point> u = utilises.iterator();
        while(u.hasNext()){
            Point tmp = u.next();
            if(matrice.get(tmp).tete().joueur()==j){
                Insecte e = matrice.get(tmp).tete(); 
                boolean b2 = false;
                if(matrice.get(e.position()) == null || !matrice.get(e.position()).tete().equals(e) )
                    b2 = true;
                else if(voisins.get(e.position()).size()>4 && (matrice.get(e.position()).tete() instanceof Fourmie || matrice.get(e.position()).tete() instanceof Reine || matrice.get(e.position()).tete() instanceof Araignee ))
                    b2 = true;
                else if(voisins.get(e.position()).size()==4 && (
                        (voisinage(e.position(), NORD) && voisinage(e.position(), SUD)) || (voisinage(e.position(), SOUEST) && voisinage(e.position(), NEST)) || (voisinage(e.position(), NOUEST) && voisinage(e.position(), SEST)) 
                        )&& (e instanceof Fourmie || e instanceof Reine || e instanceof Araignee ))
                    b2 = true;
                List<Coup[]> tab = new ArrayList();
                boolean b = false;
                Case ca = matrice.get(e.position()).clone();
                ca.retirePion();

                if(!ca.utilise())
                    if(voisins.get(e.position()).size()<2)
                        b = true;
                    else if(!ca.utilise() && voisins.get(e.position()).size()==2 && 
                            (voisinage(e.position(), NORD) || voisinage(e.position(), SUD) || voisinage(e.position(), SEST)
                            || voisinage(e.position(), SOUEST)|| voisinage(e.position(), NEST) || voisinage(e.position(), NOUEST)
                            ))
                        b = true;
                    else if(voisins.get(e.position()).size()==4 && (
                            ( (voisinage(e.position(), ESTE) ^ voisinage(e.position(), OUESTE) ) && ( voisinage(e.position(), SUD) ^ voisinage(e.position(), NORD)) ) ||
                            ( (voisinage(e.position(), SEST) ^ voisinage(e.position(), SOUEST) ) && voisinage(e.position(), NORD) ) ||
                            ( (voisinage(e.position(), NEST) ^ voisinage(e.position(), NOUEST) ) && voisinage(e.position(), SUD) )
                            ))
                        b = true;
                    else if(voisins.get(e.position()).size()==3 && (
                            voisinage(e.position(), OUESTE)  || voisinage(e.position(), ESTE)
                            || ((voisinage(e.position(), NORD) ^(voisinage(e.position(), SUD)) && (voisinage(e.position(), EST) ^ voisinage(e.position(), OUEST))))
                            ))
                        b = true;
                
                if(!b && !b2){
                    RechercheConcurente rc = new RechercheConcurente(clone(), c2, e.clone());
                    threads.add(new Thread(rc));
                }else if(b && !b2){
                    List<Coup> l = deplacementPossible(e);
                    if(l!=null)
                        c.addAll(l);
                }else
                    continue;
            }
        }
        
        if(!threads.isEmpty()){
            Iterator<Thread> ith = threads.iterator();
            while(ith.hasNext()){
                ith.next().start();
            }
            ith = threads.iterator();
            while(ith.hasNext()){
                try{
                    ith.next().join();
                }catch(InterruptedException e){

                }
            }

            c.addAll(c2.getCoup());
        }
        Coup[] coups = new Coup[c.size()];
        Iterator<Coup> it = c.iterator();
        int i=0; 
        while(it.hasNext() && i<coups.length)
            coups[i++]=it.next();
        
        
        return coups;
    }
    
    public List<Coup> deplacementPossible(Insecte e){
        if(matrice.get(e.position()) == null || !matrice.get(e.position()).tete().equals(e) )
            return null;
        else if(voisins.get(e.position()).size()>4 && (matrice.get(e.position()).tete() instanceof Fourmie || matrice.get(e.position()).tete() instanceof Reine || matrice.get(e.position()).tete() instanceof Araignee ))
            return null;
        else if(voisins.get(e.position()).size()==4 && (
                (voisinage(e.position(), NORD) && voisinage(e.position(), SUD)) || (voisinage(e.position(), SOUEST) && voisinage(e.position(), NEST)) || (voisinage(e.position(), NOUEST) && voisinage(e.position(), SEST)) 
                )&& (e instanceof Fourmie || e instanceof Reine || e instanceof Araignee ))
            return null;
        List<Coup[]> tab = new ArrayList();
        List<Coup> c = new ArrayList();
        boolean b = true;
        Case ca = matrice.get(e.position()).clone();
        ca.retirePion();
        
        if(!ca.utilise())
            if(voisins.get(e.position()).size()<2)
                b = true;
            else if(!ca.utilise() && voisins.get(e.position()).size()==2 && 
                    (voisinage(e.position(), NORD) || voisinage(e.position(), SUD) || voisinage(e.position(), SEST)
                    || voisinage(e.position(), SOUEST)|| voisinage(e.position(), NEST) || voisinage(e.position(), NOUEST)
                    ))
                b = true;
            else if(voisins.get(e.position()).size()==4 && (
                    ( (voisinage(e.position(), ESTE) ^ voisinage(e.position(), OUESTE) ) && ( voisinage(e.position(), SUD) ^ voisinage(e.position(), NORD)) ) ||
                    ( (voisinage(e.position(), SEST) ^ voisinage(e.position(), SOUEST) ) && voisinage(e.position(), NORD) ) ||
                    ( (voisinage(e.position(), NEST) ^ voisinage(e.position(), NOUEST) ) && voisinage(e.position(), SUD) )
                    ))
                b = true;
            else if(voisins.get(e.position()).size()==3 && (
                    voisinage(e.position(), OUESTE)  || voisinage(e.position(), ESTE)
                    || ((voisinage(e.position(), NORD) ^(voisinage(e.position(), SUD)) && (voisinage(e.position(), EST) ^ voisinage(e.position(), OUEST))))
                    ))
                b = true;
            else
                b = estConnexe(e);
        
        if(b){
            Coup[] cp = matrice.get(e.position()).tete().deplacementValide(this.clone().matrice());

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
        if(reines[jCourant]==null)
            return matrice.hashCode();
        
        double diffx = 0 - reines[jCourant].x();
        double diffy = 0 - reines[jCourant].y();
        
        Map<Point, Case> nouv = new HashMap();
        for(Map.Entry<Point, Case> entry : matrice.entrySet() ){
            Point p = new Point(entry.getKey().x() + diffx, entry.getKey().y() + diffy );
            Case c = entry.getValue().clone();
            c.position().fixe(c.position().x()+diffx, c.position().x()+diffx);
            Iterator<Insecte> it = c.insectes().iterator();
            while(it.hasNext()){
                Insecte e = it.next();
                e.position().fixe(e.position().x()+diffx, e.position().y()+diffy);
            }
            nouv.put(p,c);
        }
        
        return nouv.hashCode();
    }
    
    boolean voisinage(Point p, int dir){
        switch(dir){
            case NORD:
                return utilises.contains(new Point(p.x(), p.y()-1)) && utilises.contains(new Point(p.x()+1, p.y()-1));
            case OUEST:
                return utilises.contains(new Point(p.x()-1, p.y()));
            case EST:
                return utilises.contains(new Point(p.x()+1, p.y()));
            case SUD:
                return utilises.contains(new Point(p.x(), p.y()+1)) && utilises.contains(new Point(p.x()-1, p.y()+1));
            case NEST:
                return utilises.contains(new Point(p.x()+1, p.y()-1)) && utilises.contains(new Point(p.x()+1, p.y()));
            case NOUEST:
                return utilises.contains(new Point(p.x(), p.y()-1)) && utilises.contains(new Point(p.x()-1, p.y()));
            case SEST:
                return utilises.contains(new Point(p.x(), p.y()+1)) && utilises.contains(new Point(p.x()+1, p.y()));
            case SOUEST:
                return utilises.contains(new Point(p.x()-1, p.y()+1)) && utilises.contains(new Point(p.x()-1, p.y()));
            case ESTE:
                return utilises.contains(new Point(p.x()+1, p.y()-1)) && utilises.contains(new Point(p.x()+1, p.y())) && utilises.contains(new Point(p.x(), p.y()+1)) ;
            case OUESTE:
                return utilises.contains(new Point(p.x()-1, p.y()+1)) && utilises.contains(new Point(p.x()-1, p.y())) && utilises.contains(new Point(p.x(), p.y()-1)) ;
            default:
                return false;
        }
    }
}
