/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package TestModel;

import Modele.Case;
import Modele.Cloporte;
import Modele.Coccinelle;
import Modele.Coup;
import Modele.Deplacement;
import Modele.FabriqueInsecte;
import Modele.Insecte;
import Modele.Point;
import static TestModel.TestFourmie.appartient;
import java.util.HashMap;
import java.util.Map;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;
import ruche.Reglage;

/**
 *
 * @author maxence
 */
public class TestCloporte {
    Cloporte f;
    
    public TestCloporte() {
        f = (Cloporte) FabriqueInsecte.creer(Insecte.CLOP, 0, new Point(2,2));
    }
    
    void base(){
        assertEquals(f, f.clone());
        assertEquals(f.position(), new Point(2,2));
        assertEquals(f.toString(), "["+Insecte.CLOP+"/0/"+f.position()+"]");
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
        
        Coup[] d = {
            new Deplacement(0,"(2.0,2.0)->(2.0,1.0)"),
            new Deplacement(0, "(2.0,2.0)->(3.0,2.0)"),
            new Deplacement(0, "(3.0,1.0)->(3.0,2.0)"),
            new Deplacement(0, "(3.0,1.0)->(2.0,3.0)"),
            new Deplacement(0, "(3.0,1.0)->(1.0,3.0)"),
            new Deplacement(0, "(3.0,1.0)->(1.0,2.0)"),
            new Deplacement(0, "(3.0,1.0)->(2.0,1.0)")
        };
        Coup[] d2 = f.deplacementValide(plateau);
        assertEquals( d.length, d2.length);
        for(int i=0; i<d.length; i++){
            assertTrue(appartient(d2, d[i]));
        }
    }
    @Test
    public void test() {
        base();
        deplacement1();
        deplacement2();
    }
}
