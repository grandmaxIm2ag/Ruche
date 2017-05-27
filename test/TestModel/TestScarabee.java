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
import Modele.Plateau;
import Modele.Point;
import Modele.Scarabee;
import static TestModel.TestFourmie.appartient;
import java.util.HashMap;
import java.util.Map;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import org.junit.Test;
import ruche.Reglage;

/**
 *
 * @author grandmax
 */
public class TestScarabee {
    Scarabee f;
    
    /**
     *
     */
    public TestScarabee() {
        f = (Scarabee) FabriqueInsecte.creer(Insecte.SCAR, 0, new Point(2,2));
    }
    
    void base(){
        assertEquals(f, f.clone());
        assertEquals(f.position(), new Point(2,2));
        assertEquals(f.toString(), "["+Insecte.SCAR+"/0/"+f.position()+"]");
    }
    
    void deplacement1(){
        Plateau plateau = new Plateau(0,0,0,0,null);
        Case c = new Case(f.position().x(), f.position().y(), Reglage.lis("lCase"), Reglage.lis("hCase"));
        c.deposePion(f);
        plateau.matrice.put(c.position(), c);
        Coup[] d = new Coup[0];
        Coup[] d2 = f.deplacementValide(plateau);
        assertEquals( d.length, d2.length);
    }
    
    void deplacement2(){
        Plateau plateau = new Plateau(0,0,0,0,null);
        Case c = new Case(f.position().x(), f.position().y(), Reglage.lis("lCase"), Reglage.lis("hCase"));
        c.deposePion(f);
        plateau.matrice.put(c.position(), c);
        Case c1 = new Case(3, 1, Reglage.lis("lCase"), Reglage.lis("hCase"));
        c1.deposePion(FabriqueInsecte.creer(Insecte.REINE, 0, new Point(3,1)));
        plateau.matrice.put(c1.position(), c1);
        Case c2 = new Case(3, 2, Reglage.lis("lCase"), Reglage.lis("hCase"));
        c2.deposePion(FabriqueInsecte.creer(Insecte.REINE, 0, new Point(3,2)));
        plateau.matrice.put(c2.position(), c2);
        
        Coup[] d = {
            new Deplacement(0, f.position(), new Point(2,1)),
            new Deplacement(0, f.position(), new Point(3,1)),
            new Deplacement(0, f.position(), new Point(3,2)),
            new Deplacement(0, f.position(), new Point(2,3))
        };
        Coup[] d2 = f.deplacementValide(plateau);
        assertEquals( d.length, d2.length);
        for(int i=0; i<d.length; i++){
            assertTrue(appartient(d2, d[i]));
        }
    }
    
    void deplacement3(){
        Plateau plateau = new Plateau(0,0,0,0,null);
        f.position().fixe(3,1);
        Case c = new Case(f.position().x(), f.position().y(), Reglage.lis("lCase"), Reglage.lis("hCase"));
        c.deposePion(FabriqueInsecte.creer(Insecte.REINE, 0, new Point(3,1)));
        c.deposePion(f);
        plateau.matrice.put(c.position(), c);
        Case c2 = new Case(3, 2, Reglage.lis("lCase"), Reglage.lis("hCase"));
        c2.deposePion(FabriqueInsecte.creer(Insecte.REINE, 0, new Point(3,2)));
        plateau.matrice.put(c2.position(), c2);
        
        Coup[] d = {
            new Deplacement(0, f.position(), new Point(2,1)),
            new Deplacement(0, f.position(), new Point(2,2)),
            new Deplacement(0, f.position(), new Point(3,2)),
            new Deplacement(0, f.position(), new Point(4,1)),
            new Deplacement(0, f.position(), new Point(3,0)),
            new Deplacement(0, f.position(), new Point(4,0))
        };
        Coup[] d2 = f.deplacementValide(plateau);
        assertEquals( d.length, d2.length);
        for(int i=0; i<d.length; i++){
            assertTrue(appartient(d2, d[i]));
        }
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
    }
}
