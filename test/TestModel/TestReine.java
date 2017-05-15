/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package TestModel;

import Modele.Case;
import Modele.Coup;
import Modele.Deplacement;
import Modele.FabriqueInsecte;
import Modele.Fourmie;
import Modele.Insecte;
import Modele.Point;
import Modele.Reine;
import java.util.HashMap;
import java.util.Map;
import org.junit.Test;
import static org.junit.Assert.*;
import ruche.Reglage;

/**
 *
 * @author grandmax
 */
public class TestReine {
    
    Reine f;
    
    /**
     *
     */
    public TestReine() {
        f = (Reine) FabriqueInsecte.creer(Insecte.REINE, 0, new Point(2,2));
    }
    
    void base(){
        assertEquals(f, f.clone());
        assertEquals(f.position(), new Point(2,2));
        assertEquals(f.toString(), "["+Insecte.REINE+"/0/"+f.position()+"]");
    }
    
    
    void deplacement1(){
        Map<Point, Case> plateau = new HashMap();
        Case c = new Case(f.position().x(), f.position().y(), Reglage.lis("lCase"), Reglage.lis("hCase"));
        c.deposePion(f);
        plateau.put(c.position(), c);
        Coup[] d = new Coup[0];
        Coup[] d2 = f.deplacementValide(plateau);
        assertEquals( d.length, d2.length);
    }
    void deplacement2(){
        Map<Point, Case> plateau = new HashMap();
        Case c = new Case(f.position().x(), f.position().y(), Reglage.lis("lCase"), Reglage.lis("hCase"));
        c.deposePion(f);
        plateau.put(c.position(), c);
        Case c1 = new Case(3, 1, Reglage.lis("lCase"), Reglage.lis("hCase"));
        c1.deposePion(FabriqueInsecte.creer(Insecte.REINE, 0, new Point(3,1)));
        plateau.put(c1.position(), c1);
        Case c2 = new Case(3, 2, Reglage.lis("lCase"), Reglage.lis("hCase"));
        c2.deposePion(FabriqueInsecte.creer(Insecte.REINE, 0, new Point(3,2)));
        plateau.put(c2.position(), c2);
        
        Coup[] d = {new Deplacement(0,f.position(),new Point(2, 1)),
                    new Deplacement(0,f.position(),new Point(2,3))
                    };
        Coup[] d2 = f.deplacementValide(plateau);
        assertEquals( d.length, d2.length);
        for(int i=0; i<d.length; i++){
            assertTrue(appartient(d2, d[i]));
        }
    }
    
    void deplacement3(){
        Map<Point, Case> plateau = new HashMap();
        Case c = new Case(f.position().x(), f.position().y(), Reglage.lis("lCase"), Reglage.lis("hCase"));
        c.deposePion(f);
        plateau.put(c.position(), c);
        Case c1 = new Case(3, 1, Reglage.lis("lCase"), Reglage.lis("hCase"));
        c1.deposePion(FabriqueInsecte.creer(Insecte.REINE, 0, new Point(3,1)));
        plateau.put(c1.position(), c1);
        Case c2 = new Case(3, 2, Reglage.lis("lCase"), Reglage.lis("hCase"));
        c2.deposePion(FabriqueInsecte.creer(Insecte.REINE, 0, new Point(3,2)));
        plateau.put(c2.position(), c2);
        Case c3 = new Case(2, 3, Reglage.lis("lCase"), Reglage.lis("hCase"));
        c3.deposePion(FabriqueInsecte.creer(Insecte.REINE, 0, new Point(2,3)));
        plateau.put(c3.position(), c3);
        Case c4 = new Case(1, 3, Reglage.lis("lCase"), Reglage.lis("hCase"));
        c4.deposePion(FabriqueInsecte.creer(Insecte.REINE, 0, new Point(1,3)));
        plateau.put(c4.position(), c4);
        Case c5 = new Case(1, 2, Reglage.lis("lCase"), Reglage.lis("hCase"));
        c5.deposePion(FabriqueInsecte.creer(Insecte.REINE, 0, new Point(1,2)));
        plateau.put(c5.position(), c5);
        
        Coup[] d = new Coup[0];
        Coup[] d2 = f.deplacementValide(plateau);
        assertEquals( d.length, d2.length);
        
    }
    
    void deplacement4(){
        Map<Point, Case> plateau = new HashMap();
        Case c = new Case(f.position().x(), f.position().y(), Reglage.lis("lCase"), Reglage.lis("hCase"));
        c.deposePion(f);
        plateau.put(c.position(), c);
        Case c1 = new Case(3, 1, Reglage.lis("lCase"), Reglage.lis("hCase"));
        c1.deposePion(FabriqueInsecte.creer(Insecte.REINE, 0, new Point(3,1)));
        plateau.put(c1.position(), c1);
        Case c2 = new Case(3, 2, Reglage.lis("lCase"), Reglage.lis("hCase"));
        c2.deposePion(FabriqueInsecte.creer(Insecte.REINE, 0, new Point(3,2)));
        plateau.put(c2.position(), c2);
        Case c3 = new Case(2, 3, Reglage.lis("lCase"), Reglage.lis("hCase"));
        c3.deposePion(FabriqueInsecte.creer(Insecte.REINE, 0, new Point(2,3)));
        plateau.put(c3.position(), c3);
        Case c4 = new Case(1, 3, Reglage.lis("lCase"), Reglage.lis("hCase"));
        c4.deposePion(FabriqueInsecte.creer(Insecte.REINE, 0, new Point(1,3)));
        plateau.put(c4.position(), c4);
        Case c5 = new Case(1, 2, Reglage.lis("lCase"), Reglage.lis("hCase"));
        c5.deposePion(FabriqueInsecte.creer(Insecte.REINE, 0, new Point(1,2)));
        plateau.put(c5.position(), c5);
        Case c6 = new Case(2, 1, Reglage.lis("lCase"), Reglage.lis("hCase"));
        c6.deposePion(FabriqueInsecte.creer(Insecte.REINE, 0, new Point(2,1)));
        plateau.put(c6.position(), c6);
        
        Coup[] d = new Coup[0];
        Coup[] d2 = f.deplacementValide(plateau);
        assertEquals( d.length, d2.length);
        
    } 
    static boolean appartient(Coup[] d, Coup c){
        boolean b = false;
        
        for(int i=0; i<d.length; i++)
            b|=c.equals(d[i]);
        return b;
    }

    /**
     *
     */
    @Test
    public void test(){
        base();
        deplacement1();
        deplacement2();
        deplacement3();
        deplacement4();
    }
    
}
