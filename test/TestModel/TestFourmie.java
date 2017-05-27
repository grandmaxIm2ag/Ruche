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
import org.junit.Assert;
import org.junit.Test;
import static org.junit.Assert.*;
import ruche.Reglage;

/**
 *
 * @author grandmax
 */
public class TestFourmie {
    Fourmie f;
    
    /**
     *
     */
    public TestFourmie() {
        f = (Fourmie) FabriqueInsecte.creer(Insecte.FOUR, 0, new Point(2,2));
    }
    
    void base(){
        assertEquals(f, f.clone());
        assertEquals(f.position(), new Point(2,2));
        assertEquals(f.toString(), "["+Insecte.FOUR+"/0/"+f.position()+"]");
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
        
        Coup[] d = {new Deplacement(0,f.position(),new Point(2, 1)),
                    new Deplacement(0,f.position(),new Point(3,0)),
                    new Deplacement(0,f.position(),new Point(4,0)),
                    new Deplacement(0,f.position(),new Point(4,1)),
                    new Deplacement(0,f.position(),new Point(4,2)),
                    new Deplacement(0,f.position(),new Point(3,3)),
                    new Deplacement(0,f.position(),new Point(2,3))
                    };
        Coup[] d2 = f.deplacementValide(plateau);
        assertEquals( d.length, d2.length);
        for(int i=0; i<d.length; i++){
            assertTrue(appartient(d2, d[i]));
        }
    }
    
    void deplacement3(){
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
        Case c3 = new Case(2, 3, Reglage.lis("lCase"), Reglage.lis("hCase"));
        c3.deposePion(FabriqueInsecte.creer(Insecte.REINE, 0, new Point(2,3)));
        plateau.matrice.put(c3.position(), c3);
        Case c4 = new Case(1, 3, Reglage.lis("lCase"), Reglage.lis("hCase"));
        c4.deposePion(FabriqueInsecte.creer(Insecte.REINE, 0, new Point(1,3)));
        plateau.matrice.put(c4.position(), c4);
        Case c5 = new Case(1, 2, Reglage.lis("lCase"), Reglage.lis("hCase"));
        c5.deposePion(FabriqueInsecte.creer(Insecte.REINE, 0, new Point(1,2)));
        plateau.matrice.put(c5.position(), c5);
        
        Coup[] d = new Coup[0];
        Coup[] d2 = f.deplacementValide(plateau);
        assertEquals( d.length, d2.length);
        
    }
    
    void deplacement4(){
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
        Case c3 = new Case(2, 3, Reglage.lis("lCase"), Reglage.lis("hCase"));
        c3.deposePion(FabriqueInsecte.creer(Insecte.REINE, 0, new Point(2,3)));
        plateau.matrice.put(c3.position(), c3);
        Case c4 = new Case(1, 3, Reglage.lis("lCase"), Reglage.lis("hCase"));
        c4.deposePion(FabriqueInsecte.creer(Insecte.REINE, 0, new Point(1,3)));
        plateau.matrice.put(c4.position(), c4);
        Case c5 = new Case(1, 2, Reglage.lis("lCase"), Reglage.lis("hCase"));
        c5.deposePion(FabriqueInsecte.creer(Insecte.REINE, 0, new Point(1,2)));
        plateau.matrice.put(c5.position(), c5);
        Case c6 = new Case(2, 1, Reglage.lis("lCase"), Reglage.lis("hCase"));
        c6.deposePion(FabriqueInsecte.creer(Insecte.REINE, 0, new Point(2,1)));
        plateau.matrice.put(c6.position(), c6);
        
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
