/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
   https://fr.boardgamearena.com/ 
 */
package Modele;

import Modele.Arbitres.Arbitre;
import Vue.Pointeur;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import ruche.Reglage;

/**
 *
 * @author grandmax
 */
public class Plateau extends Composant {

    /**
     *
     */
    public class UnionFind{
        int[] parent;
        int[] rang;
            
        /**
         *
         * @param idx
         */
        public UnionFind(int idx){
            parent = new int[idx];
            rang = new int[idx];
            for(int i=0; i<idx; i++){
                parent[i] = i;
                rang[i]=0;
            }   
        }
        
        int find(int p) {
            while (p != parent[p]) {
                parent[p] = parent[parent[p]];    // path compression by halving
                p = parent[p];
            }
            return p;
        }
        
        void union(int i, int j){
            int iRacine = find(i);
            int jRacine = find(j);
            if(jRacine!=iRacine)
                if(rang[iRacine]<rang[jRacine])
                    parent[iRacine]=jRacine;
                else{
                    parent[jRacine]=iRacine;
                    if(rang[iRacine]==rang[jRacine])
                        rang[iRacine]++;
                }
                    
        }
        boolean connecter(int i, int j){
            return find(i)==find(j);
        }
    }
    
    /**
     *
     */
    public final static int EST = 0;

    /**
     *
     */
    public final static int OUEST = 1;

    /**
     *
     */
    public final static int NORD = 2;

    /**
     *
     */
    public final static int SUD = 3;

    /**
     *
     */
    public final static int ESTE = 4;

    /**
     *
     */
    public final static int NEST = 5;

    /**
     *
     */
    public final static int SEST = 6;

    /**
     *
     */
    public final static int OUESTE = 7;

    /**
     *
     */
    public final static int NOUEST = 8;

    /**
     *
     */
    public final static int SOUEST = 9;
    
    /**
     *
     */
    public Map<Point, Case> matrice;
    Point[] reines;
    List<Point> utilises;
    List<Case> aide;
    Map<Point, List<Point>> voisins;
    
    int xMin, yMin, xMax, yMax;
    Properties prop;
    int jCourant;
    
    /**
     *
     * @param x
     * @param y
     * @param larg
     * @param haut
     * @param p
     */
    public Plateau(double x, double y, double larg, double haut, Properties p) {
        super(x, y, larg, haut);
        matrice = new HashMap();
        prop = p;
        
        voisins = new HashMap();
        utilises = new ArrayList();
        reines = new Point[2];
        xMin=0; xMax = 0; yMin=0; yMax=0;
        jCourant = Arbitre.J1;
        aide= new ArrayList();
    }
    
    /**
     *
     * @param idx
     * @param p
     */
    public void setReine(int idx, Point p){
        reines[idx] = p;
    }

    /**
     *
     * @param e
     */
    public void premierPion(Insecte e){
        Case c = new Case(0,0, 1, 1);
        c.deposePion(e);
        if(e.type()==Insecte.REINE){
            reines[e.joueur()] = new Point(0,0);
        }
        matrice.put(new Point(0,0), c);
        utilises.add(new Point(0,0));
        voisins.put(new Point(0,0), new ArrayList());
    }
    
    // Cette fonction est là pour tester hein

    /**
     *
     */
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
    
    /**
     *
     * @param d
     * @return
     */
    public boolean premierPionValide(Depot d){
        return !(d.destination().x < -1 || d.destination().y < -1 || d.destination().x > 1 || d.destination().y > 1 || (d.destination().x == -1 && d.destination().y == -1 )
                || (d.destination().x == 1 && d.destination().y == 1 ) || (d.destination().x == 0 && d.destination().y == 0 ));
    }

    /**
     *
     * @param d
     * @return
     */
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

    /**
     *
     * @param d
     * @return
     */
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
            //System.out.println("coucou");
        Coup[] coups = e.deplacementValide(clone());
            for (Coup coup : coups) {
                //System.out.println(coup + " " + d.equals(coup));
                b |= d.equals(coup);
            }
        }
        return b;
    }

    /**
     *
     * @param d
     */
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
    
    /**
     *
     * @param v
     */
    public void afficheGraphe(Map<Point, List<Point>> v){
        v.entrySet().stream().map((entry) -> {
            List<Point> l = entry.getValue();
            String str = entry.getKey().toString();
            str = l.stream().map((point) -> ":"+point).reduce(str, String::concat);
            return str;
        }).forEach((str) -> {
            System.out.println(str);
        });
    }
    
    /**
     *
     * @param source
     */
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
    
    /**
     *
     * @param d
     */
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

    /**
     *
     * @param d
     */
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
    
    /**
     *
     * @param d
     */
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

    /**
     *
     * @param j
     * @return
     */
    public boolean estEncerclee(int j){
        if(reines[j]==null)
            return false;
        return voisins.get(reines[j]).size()>=6;
    }
    
    /**
     *
     * @param e
     * @return
     */
    public boolean estConnexe(Insecte e){
        boolean b = true;
        List<Point> u = cloneList(utilises);
        Map<Point, List<Point>> v = new HashMap();
        Map<Point, Integer> m = new HashMap();
        int idx = 0;
        for(Map.Entry<Point, List<Point>> entry : voisins.entrySet()){
            List<Point> v2 = cloneList(entry.getValue());
            if(v2.contains(e.position()))
                v2.remove(e.position());
            v.put(entry.getKey().clone(), v2);
            m.put(entry.getKey().clone(), idx++);
            
        }
        
        v.remove(e.position());
        u.remove(e.position());
        UnionFind uf = new UnionFind(idx);
        
        List<Point> marquer = new ArrayList();
        for(Map.Entry<Point, List<Point>> entry : v.entrySet()){
            marquer.add(entry.getKey());
            List<Point> v2 = entry.getValue();
            Iterator<Point> it = v2.iterator();
            while(it.hasNext()){
                Point p = it.next().clone();
                if(!marquer.contains(p))
                    uf.union(m.get(entry.getKey()), m.get(p));
            }
        }
        
        b = true;
        Point p = u.get(0).clone();
        for(Point p2 : u)
            b&=uf.connecter(m.get(p), m.get(p2));
        
        return b;
        
        /*
        
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
*/    }
    
    //Voisins directs et indirects

    /**
     *
     * @param p1
     * @param p2
     * @param k
     * @param v
     * @return
     */
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

    /**
     *
     * @param v
     * @return
     */
    @Override
    public boolean accept(Visiteur v) {
        boolean b=v.visite(this);
        for(Map.Entry<Point,Case> entry : matrice.entrySet()) {
            b|= entry.getValue().accept(v);//v.visite(entry.getValue());
        }
        Iterator<Case> it = aide.iterator();
        while(it.hasNext())// && !b)
            b|=it.next().accept(v);
        if (v instanceof Pointeur && ((Pointeur) v).initPopup == false) {
            ((Pointeur) v).popup.hide();
            ((Pointeur) v).reinitPopup();
        }
        return b;
    }
    
    /**
     *
     */
    public void depointe () {
        for(Map.Entry<Point,Case> entry : matrice.entrySet()) {
            entry.getValue().depointe();
            entry.getValue().tete().depointe();
        }
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

    /**
     *
     * @param list
     * @return
     */
    public static List<Point> cloneList(List<Point> list) {
    List<Point> clone = new ArrayList<>(list.size());
    list.forEach((item) -> {
        clone.add(item.clone());
        });
    return clone;
    }
    
    /**
     *
     * @param list
     * @return
     */
    public static List<Case> cloneList2(List<Case> list) {
    List<Case> clone = new ArrayList<>(list.size());
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
                tmp+=":"+it.next().toString();
            }
            str+=entry2.getKey()+":"+tmp+"\n";
        }
        return str;
    }

    /**
     *
     * @param joueur
     * @return
     */
    public boolean aucunCoup(int joueur){
        Coup[] c = deplacementPossible(joueur);
        return c.length <= 0;
    }
    
    /**
     *
     * @param j
     * @return
     */
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
                }else {
                }
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
    
    /**
     *
     * @param e
     * @return
     */
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
            Coup[] cp = matrice.get(e.position()).tete().deplacementValide(this.clone());

            for (Coup cp1 : cp) {
                if (cp1 instanceof Deplacement) {
                    Deplacement d = (Deplacement) cp1;
                    c.add(d);
                }
            }
            return c;
        }
        return null;
    }
    
    /**
     *
     * @param joueur
     * @param t
     * @return
     */
    public Coup[] depotPossible(int joueur, int t){
        
        if(utilises.isEmpty()){
            if(joueur == 1){
                //System.err.println("coucou");
            }
            Coup[] res = new Coup[1];
            res[0] = new Depot(joueur, t, new Point(0,0));
            return res;
        }else if(utilises.size()==1){
            if(joueur == 1){
                //System.err.println("coucou 2 "+utilises.get(0));
            }
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
    
    /**
     *
     * @param pos
     */
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
    
    /**
     *
     * @param idx
     * @return
     */
    public Point reine(int idx){
        return reines[idx];
    }
    
    /**
     *
     * @return
     */
    public Map<Point, Case> matrice(){
        return matrice;
    }

    /**
     *
     * @return
     */
    public Map<Point, List<Point>> voisins(){
        return voisins;
    }

    /**
     *
     * @param p
     * @return
     */
    public List<Point> voisins(Point p){
        return voisins.get(p);
    }

    /**
     *
     * @return
     */
    public List<Point> utilises(){
        return utilises;
    }
    
    /**
     *
     * @param a
     */
    public void setAide(List<Case> a){
        aide = cloneList2(a);
    }

    /**
     *
     */
    public void clearAide(){
        aide.clear();
    }
    
    @Override
    public int hashCode(){
        if(reines[jCourant]==null)
            return matrice.hashCode();
        
        double diffx = 0 - reines[jCourant].x();
        double diffy = 0 - reines[jCourant].y();
        
        Map<Point, Case> nouv = new HashMap();
        matrice.entrySet().stream().forEach((entry) -> {
            Point p = new Point(entry.getKey().x() + diffx, entry.getKey().y() + diffy );
            Case c = entry.getValue().clone();
            c.position().fixe(c.position().x()+diffx, c.position().y()+diffy);
            Iterator<Insecte> it = c.insectes().iterator();
            while(it.hasNext()){
                Insecte e = it.next();
                e.position().fixe(e.position().x()+diffx, e.position().y()+diffy);
            }
            nouv.put(p,c);
        });
        
        for(Map.Entry<Point, Case> entry : nouv.entrySet())
            System.out.println(entry.getKey()+" "+entry.getValue());
        
        return nouv.hashCode();
    }
    
    /**
     *
     * @param p
     * @param dir
     * @return
     */
    public boolean voisinage(Point p, int dir){
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
    
    /**
     *
     * @param j
     */
    public void setJoueur(int j){
        jCourant = j;
    }
    
    public boolean deplEntame () {
        return !Plateau.cloneList2(aide).isEmpty();
    }
    
    public List<Case> aide () {
        return aide;
    }
}
