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
import Modele.Insecte;
import Modele.Moustique;
import Modele.Plateau;
import Modele.Point;
import static TestModel.TestAraignee.appartient;
import static TestModel.TestFourmie.appartient;
import java.util.HashMap;
import java.util.Map;
import org.junit.Test;
import static org.junit.Assert.*;
import ruche.Reglage;

/**
 *
 * @author grandmax
 */
public class TestMoustique {
    
Moustique f;
    
    /**
     *
     */
    public TestMoustique() {
        f = (Moustique) FabriqueInsecte.creer(Insecte.MOUS, 0, new Point(0,2));
    }
    
    void base(){
        assertEquals(f, f.clone());
        assertEquals(f.position(), new Point(0,2));
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
        Case c1 = new Case(1, 0, Reglage.lis("lCase"), Reglage.lis("hCase"));
        c1.deposePion(FabriqueInsecte.creer(Insecte.REINE, 0, new Point(1,0)));
        plateau.matrice.put(c1.position(), c1);
        Case c3 = new Case(0, 3, Reglage.lis("lCase"), Reglage.lis("hCase"));
        c3.deposePion(FabriqueInsecte.creer(Insecte.FOUR, 0, new Point(0,3)));
        plateau.matrice.put(c3.position(), c3);
        Case c4 = new Case(-1, 4, Reglage.lis("lCase"), Reglage.lis("hCase"));
        c4.deposePion(FabriqueInsecte.creer(Insecte.FOUR, 0, new Point(-1,4)));
        plateau.matrice.put(c4.position(), c4);
        Case c5 = new Case(1, 1, Reglage.lis("lCase"), Reglage.lis("hCase"));
        c5.deposePion(FabriqueInsecte.creer(Insecte.FOUR, 0, new Point(1,1)));
        plateau.matrice.put(c5.position(), c5);
        
        Coup[] d = {
            new Deplacement(0, f.position(), new Point(-1,3)),
            new Deplacement(0, f.position(), new Point(-2,5)),
            new Deplacement(0, f.position(), new Point(-2,4)),
            new Deplacement(0, f.position(), new Point(-1,5)),
            new Deplacement(0, f.position(), new Point(1,3)),
            new Deplacement(0, f.position(), new Point(0,4)),
            new Deplacement(0, f.position(), new Point(1,2)),
            new Deplacement(0, f.position(), new Point(2,1)),
            new Deplacement(0, f.position(), new Point(2,0)),
            new Deplacement(0, f.position(), new Point(2,-1)),
            new Deplacement(0, f.position(), new Point(0,1)),
            new Deplacement(0, f.position(), new Point(1,-1)),
            new Deplacement(0, f.position(), new Point(0,0))
        };
        Coup[] d2 = f.deplacementValide(plateau);
        assertEquals( d.length, d2.length);
        for(int i=0; i<d.length; i++)
            assertTrue(appartient(d, d2[i]));
    }
    void deplacement22(){
        Plateau plateau = new Plateau(0,0,0,0,null);
        Case c = new Case(f.position().x(), f.position().y(), Reglage.lis("lCase"), Reglage.lis("hCase"));
        c.deposePion(f);
        plateau.matrice.put(c.position(), c);
        Case c1 = new Case(1, 0, Reglage.lis("lCase"), Reglage.lis("hCase"));
        c1.deposePion(FabriqueInsecte.creer(Insecte.REINE, 0, new Point(1,0)));
        plateau.matrice.put(c1.position(), c1);
        Case c3 = new Case(0, 3, Reglage.lis("lCase"), Reglage.lis("hCase"));
        c3.deposePion(FabriqueInsecte.creer(Insecte.REINE, 0, new Point(0,3)));
        plateau.matrice.put(c3.position(), c3);
        Case c4 = new Case(-1, 4, Reglage.lis("lCase"), Reglage.lis("hCase"));
        c4.deposePion(FabriqueInsecte.creer(Insecte.REINE, 0, new Point(-1,4)));
        plateau.matrice.put(c4.position(), c4);
        Case c5 = new Case(1, 1, Reglage.lis("lCase"), Reglage.lis("hCase"));
        c5.deposePion(FabriqueInsecte.creer(Insecte.REINE, 0, new Point(1,1)));
        plateau.matrice.put(c5.position(), c5);
        
        Coup[] d = {
            new Deplacement(0, f.position(), new Point(-1,3)),
            new Deplacement(0, f.position(), new Point(0,1))
        };
        Coup[] d2 = f.deplacementValide(plateau);
        assertEquals( d.length, d2.length);
        for(int i=0; i<d.length; i++)
            assertTrue(appartient(d, d2[i]));
    }
    
    void deplacement3(){
        Plateau plateau = new Plateau(0,0,0,0,null);
        f.position().fixe(1,2);
        Case c = new Case(f.position().x(), f.position().y(), Reglage.lis("lCase"), Reglage.lis("hCase"));
        c.deposePion(f);
        plateau.matrice.put(c.position(), c);
        Case c1 = new Case(1, 3, Reglage.lis("lCase"), Reglage.lis("hCase"));
        c1.deposePion(FabriqueInsecte.creer(Insecte.SAUT, 0, new Point(1,3)));
        plateau.matrice.put(c1.position(), c1);
        Case c2 = new Case(2, 1, Reglage.lis("lCase"), Reglage.lis("hCase"));
        c2.deposePion(FabriqueInsecte.creer(Insecte.SAUT, 0, new Point(2,1)));
        plateau.matrice.put(c2.position(), c2);
        Case c3 = new Case(1, 1, Reglage.lis("lCase"), Reglage.lis("hCase"));
        c3.deposePion(FabriqueInsecte.creer(Insecte.SAUT, 0, new Point(1,1)));
        plateau.matrice.put(c3.position(), c3);
        
        
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
    
    void deplacement4(){
        Plateau plateau = new Plateau(0,0,0,0,null);
        f.position().fixe(2, 2);
        Case c = new Case(f.position().x(), f.position().y(), Reglage.lis("lCase"), Reglage.lis("hCase"));
        c.deposePion(f);
        plateau.matrice.put(c.position(), c);
        Case c1 = new Case(3, 1, Reglage.lis("lCase"), Reglage.lis("hCase"));
        c1.deposePion(FabriqueInsecte.creer(Insecte.COCC, 0, new Point(3,1)));
        plateau.matrice.put(c1.position(), c1);
        Case c2 = new Case(3, 2, Reglage.lis("lCase"), Reglage.lis("hCase"));
        c2.deposePion(FabriqueInsecte.creer(Insecte.REINE, 0, new Point(3,2)));
        plateau.matrice.put(c2.position(), c2);
        
        Coup[] d = {
            new Deplacement(0,"(2.0,2.0)->(3.0,1.0)->(3.0,2.0)->(4.0,2.0)"),
            new Deplacement(0, "(2.0,2.0)->(3.0,1.0)->(3.0,2.0)->(4.0,1.0)"),
            new Deplacement(0, "(2.0,2.0)->(3.0,1.0)->(3.0,2.0)->(3.0,3.0)"),
            new Deplacement(0, "(2.0,2.0)->(3.0,1.0)->(3.0,2.0)->(2.0,3.0)"),
            new Deplacement(0, "(2.0,2.0)->(3.0,2.0)->(3.0,1.0)->(4.0,1.0)"),
            new Deplacement(0, "(2.0,2.0)->(3.0,2.0)->(3.0,1.0)->(4.0,0.0)"),
            new Deplacement(0, "(2.0,2.0)->(3.0,2.0)->(3.0,1.0)->(3.0,0.0)"),
            new Deplacement(0, "(2.0,2.0)->(3.0,2.0)->(3.0,1.0)->(2.0,1.0)"),
            new Deplacement(0, "(2.0,2.0)->(2.0,1.0)"),
            new Deplacement(0, "(2.0,2.0)->(2.0,3.0)")
        };
        Coup[] d2 = f.deplacementValide(plateau);
        assertEquals( d.length, d2.length);
        for(int i=0; i<d.length; i++){
            assertTrue(appartient(d2, d[i]));
        }
    }
    
    void deplacement5(){
        Plateau plateau = new Plateau(0,0,0,0,null);
        f.position().fixe(2, 2);
        Case c1 = new Case(f.position().x(), f.position().y(), Reglage.lis("lCase"), Reglage.lis("hCase"));
        c1.deposePion(FabriqueInsecte.creer(Insecte.REINE, 0, new Point(2,2)));
        c1.deposePion(f);
        plateau.matrice.put(new Point(2,2), c1);
        
        Coup[] d = {
            new Deplacement(0, "(2.0,2.0)->(2.0,3.0)"),
            new Deplacement(0, "(2.0,2.0)->(1.0,3.0)"),
            new Deplacement(0, "(2.0,2.0)->(1.0,2.0)"),
            new Deplacement(0, "(2.0,2.0)->(2.0,1.0)"),
            new Deplacement(0, "(2.0,2.0)->(3.0,2.0)"),
            new Deplacement(0, "(2.0,2.0)->(3.0,1.0)")
        };
        
        Coup[] d2 = f.deplacementValide(plateau);
        assertEquals( d.length, d2.length);
        for(int i=0; i<d.length; i++){
            assertTrue(appartient(d2, d[i]));
        }
    }
    
    void deplacement6(){
        Plateau plateau = new Plateau(0,0,0,0,null);
        f.position().fixe(2,1);
        Case c = new Case(f.position().x(), f.position().y(), Reglage.lis("lCase"), Reglage.lis("hCase"));
        c.deposePion(f);
        plateau.matrice.put(c.position(), c);
        Case c1 = new Case(4, 1, Reglage.lis("lCase"), Reglage.lis("hCase"));
        c1.deposePion(FabriqueInsecte.creer(Insecte.REINE, 0, new Point(4,1)));
        plateau.matrice.put(c1.position(), c1);
        Case c2 = new Case(3, 2, Reglage.lis("lCase"), Reglage.lis("hCase"));
        c2.deposePion(FabriqueInsecte.creer(Insecte.REINE, 0, new Point(3,2)));
        plateau.matrice.put(c2.position(), c2);
        Case c3 = new Case(2, 2, Reglage.lis("lCase"), Reglage.lis("hCase"));
        c3.deposePion(FabriqueInsecte.creer(Insecte.ARAI, 0, new Point(2,3)));
        plateau.matrice.put(c3.position(), c3);
        Case c4 = new Case(1, 2, Reglage.lis("lCase"), Reglage.lis("hCase"));
        c4.deposePion(FabriqueInsecte.creer(Insecte.REINE, 0, new Point(1,2)));
        plateau.matrice.put(c4.position(), c4);
        Case c5 = new Case(4, 0, Reglage.lis("lCase"), Reglage.lis("hCase"));
        c5.deposePion(FabriqueInsecte.creer(Insecte.REINE, 0, new Point(4,0)));
        plateau.matrice.put(c5.position(), c5);
        Case c6 = new Case(3, 0, Reglage.lis("lCase"), Reglage.lis("hCase"));
        c6.deposePion(FabriqueInsecte.creer(Insecte.REINE, 0, new Point(3,0)));
        plateau.matrice.put(c6.position(), c6);
        
        Coup[] d = {
            new Deplacement(0, f.position(), new Point(4,-1)),
            new Deplacement(0, f.position(), new Point(0,3)),
            new Deplacement(0, f.position(), new Point(2,0)),
            new Deplacement(0, f.position(), new Point(1,1))
        };
        Coup[] d2 = f.deplacementValide(plateau);
        assertEquals( d.length, d2.length);
        for(int i=0; i<d.length; i++){
            assertTrue(appartient(d, d2[i]));
        }
            
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
        deplacement22();
        deplacement2();
        deplacement4();
        deplacement3();
        deplacement5();
        deplacement6();
    }    
}
