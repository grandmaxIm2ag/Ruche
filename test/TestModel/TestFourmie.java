/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package TestModel;


import Modele.*;
import java.util.HashMap;
import java.util.Map;
import java.util.TreeMap;
import org.junit.Test;
import static org.junit.Assert.*;
import ruche.Reglage;

/**
 *
 * @author grandmax
 */
public class TestFourmie {
    Fourmie f;
    
    public TestFourmie() {
        f = (Fourmie) FabriqueInsecte.creer(Insecte.FOUR, 0, new Point(2,2));
    }
    
    void base(){
        assertEquals(f, f.clone());
        assertEquals(f.position(), new Point(2,2));
    }
    
    
    void deplacement1(){
        Map<Point, Case> plateau = new HashMap();
        Case c = new Case(f.position().x(), f.position().y(), Reglage.lis("lCase"), Reglage.lis("hCase"));
        c.deposePion(f);
        plateau.put(c.position(), c);
        assertEquals(new Coup[0] , f.deplacementValide(plateau) );
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
                    new Deplacement(0,f.position(),new Point(3,0)),
                    new Deplacement(0,f.position(),new Point(4,0)),
                    new Deplacement(0,f.position(),new Point(4,1)),
                    new Deplacement(0,f.position(),new Point(4,2)),
                    new Deplacement(0,f.position(),new Point(3,3)),
                    new Deplacement(0,f.position(),new Point(2,3))
                    };
        assertEquals( d,f.deplacementValide(plateau) );
    }
    
    @Test
    public void test(){
        base();
        //deplacement1();
        deplacement2();
    }
    
}
