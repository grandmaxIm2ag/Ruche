/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package TestModel;

import Modele.Araignee;
import Modele.Case;
import Modele.Coup;
import Modele.Deplacement;
import Modele.FabriqueInsecte;
import Modele.Insecte;
import Modele.Point;
import Modele.Sauterelle;
import static TestModel.TestMoustique.appartient;
import java.util.HashMap;
import java.util.Map;
import org.junit.Test;
import static org.junit.Assert.*;
import ruche.Reglage;

/**
 *
 * @author grandmax
 */
public class TestSauterelle {
    Sauterelle f;
    
    public TestSauterelle() {
        f = (Sauterelle) FabriqueInsecte.creer(Insecte.SAUT, 0, new Point(2,1));
    }
    
    void base(){
        assertEquals(f, f.clone());
        assertEquals(f.position(), new Point(2,1));
        assertEquals(f.toString(), "["+Insecte.SAUT+"/0/"+f.position()+"]");
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
        Case c1 = new Case(1, 1, Reglage.lis("lCase"), Reglage.lis("hCase"));
        c1.deposePion(FabriqueInsecte.creer(Insecte.REINE, 0, new Point(1,1)));
        plateau.put(c1.position(), c1);
        Case c2 = new Case(3, 1, Reglage.lis("lCase"), Reglage.lis("hCase"));
        c2.deposePion(FabriqueInsecte.creer(Insecte.REINE, 0, new Point(3,1)));
        plateau.put(c2.position(), c2);
        Case c3 = new Case(2, 2, Reglage.lis("lCase"), Reglage.lis("hCase"));
        c3.deposePion(FabriqueInsecte.creer(Insecte.REINE, 0, new Point(2,3)));
        plateau.put(c3.position(), c3);
        Case c4 = new Case(1, 2, Reglage.lis("lCase"), Reglage.lis("hCase"));
        c4.deposePion(FabriqueInsecte.creer(Insecte.REINE, 0, new Point(1,2)));
        plateau.put(c4.position(), c4);
        Case c5 = new Case(2, 0, Reglage.lis("lCase"), Reglage.lis("hCase"));
        c5.deposePion(FabriqueInsecte.creer(Insecte.REINE, 0, new Point(2,0)));
        plateau.put(c5.position(), c5);
        Case c6 = new Case(3, 0, Reglage.lis("lCase"), Reglage.lis("hCase"));
        c6.deposePion(FabriqueInsecte.creer(Insecte.REINE, 0, new Point(3,0)));
        plateau.put(c6.position(), c6);
        
        Coup[] d = {
            new Deplacement(0, f.position(), new Point(2,-1)),
            new Deplacement(0, f.position(), new Point(4,-1)),
            new Deplacement(0, f.position(), new Point(0,1)),
            new Deplacement(0, f.position(), new Point(0,3)),
            new Deplacement(0, f.position(), new Point(2,3)),
            new Deplacement(0, f.position(), new Point(4,1)),
        };
        
        Coup[] d2 = f.deplacementValide(plateau);
        
        assertEquals(d.length, d2.length);
        for(int i=0; i<d.length; i++)
            assertTrue(appartient(d2, d[i]));
    }
    
     void deplacement3(){
        Map<Point, Case> plateau = new HashMap();
        f.position().fixe(1,2);
        Case c = new Case(f.position().x(), f.position().y(), Reglage.lis("lCase"), Reglage.lis("hCase"));
        c.deposePion(f);
        plateau.put(c.position(), c);
        Case c1 = new Case(1, 3, Reglage.lis("lCase"), Reglage.lis("hCase"));
        c1.deposePion(FabriqueInsecte.creer(Insecte.SAUT, 0, new Point(1,3)));
        plateau.put(c1.position(), c1);
        Case c2 = new Case(2, 1, Reglage.lis("lCase"), Reglage.lis("hCase"));
        c2.deposePion(FabriqueInsecte.creer(Insecte.SAUT, 0, new Point(2,1)));
        plateau.put(c2.position(), c2);
        Case c3 = new Case(1, 1, Reglage.lis("lCase"), Reglage.lis("hCase"));
        c3.deposePion(FabriqueInsecte.creer(Insecte.SAUT, 0, new Point(1,1)));
        plateau.put(c3.position(), c3);
        
        
        Coup[] d = {
            new Deplacement(0, f.position(), new Point(1,4)),
            new Deplacement(0, f.position(), new Point(3,0)),
            new Deplacement(0, f.position(), new Point(1,0))
        };
        Coup[] d2 = f.deplacementValide(plateau);
        assertEquals( d.length, d2.length);
        for(int i=0; i<d2.length; i++)
            assertTrue(appartient(d2, d[i]));
    }
    @Test
    public void test(){
        base();
        //deplacement1();
        //deplacement2();
        deplacement3();
    }
}
