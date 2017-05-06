/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package TestModel;

import Modele.Arbitre;
import Modele.Depot;
import Modele.FabriqueInsecte;
import Modele.Insecte;
import Modele.Plateau;
import Modele.Point;
import java.util.Properties;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author maxence
 */
public class TestPlateau {
    Plateau p;
    Properties prop;
    public TestPlateau(Properties pr) {
        prop = pr;
        this.p = new Plateau(0.0,0.0,0.0,0.0,pr);
    }
    
    void base(){
        assertEquals(p, p.clone());
        //toString
    }
    
    void testCoupDepot(){
        assertTrue(p.aucunCoup(0));
        Point orig = new Point(0,0);
        p.premierPion(FabriqueInsecte.creer(Insecte.REINE, Arbitre.J1, orig));
        assertTrue(p.estConnexe());
        
        Depot d = new Depot(Arbitre.J2, Insecte.REINE, new Point(orig.x()+1, orig.y()+1));
        assertFalse(p.premierPionValide(d));
        d = new Depot(Arbitre.J2, Insecte.REINE, new Point(orig.x()-1, orig.y()-1));
        assertFalse(p.premierPionValide(d));
        
        d = new Depot(Arbitre.J2, Insecte.REINE, new Point(orig.x()+1, orig.y()-1));
        assertTrue(p.premierPionValide(d));
        d = new Depot(Arbitre.J2, Insecte.REINE, new Point(orig.x()-1, orig.y()+1));
        assertTrue(p.premierPionValide(d));
        d = new Depot(Arbitre.J2, Insecte.REINE, new Point(orig.x()+1, orig.y()));
        assertTrue(p.premierPionValide(d));
        d = new Depot(Arbitre.J2, Insecte.REINE, new Point(orig.x()-1, orig.y()));
        assertTrue(p.premierPionValide(d));
        d = new Depot(Arbitre.J2, Insecte.REINE, new Point(orig.x(), orig.y()-1));
        assertTrue(p.premierPionValide(d));
        d = new Depot(Arbitre.J2, Insecte.REINE, new Point(orig.x(), orig.y()+1));
        assertTrue(p.premierPionValide(d));
        
        p.deposePion(d);
        assertTrue(p.estConnexe());
        
        assertTrue(p.deposePionValide(new Depot(Arbitre.J2, Insecte.REINE, new Point(orig.x(), orig.y()+2))));
        assertFalse(p.deposePionValide(new Depot(Arbitre.J1, Insecte.REINE, new Point(orig.x(), orig.y()+2))));
        
        p.deposePion(new Depot(Arbitre.J2, Insecte.REINE, new Point(orig.x(), orig.y()+5)));
        assertFalse(p.estConnexe());
    }

    
    void testCoupDeplacement(){
        p = new Plateau(0,0,0,0, prop);
        Point orig = new Point(0,0);
        p.deposePionValide(new Depot(Arbitre.J2, Insecte.REINE, new Point(orig.x(), orig.y())));
        p.deposePionValide(new Depot(Arbitre.J2, Insecte.FOUR, new Point(orig.x()-1, orig.y())));
        
        assertTrue(p.estConnexe());
    }
    @Test
    public void test() {
        base();
        testCoupDepot();
        testCoupDeplacement();
    }
}
