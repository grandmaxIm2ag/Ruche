/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package TestModel;

import Joueurs.Ordinateur;
import org.junit.Test;
import static org.junit.Assert.*;
import Modele.*;
import java.util.Properties;

/**
 *
 * @author grandmax
 */
public class TestArbitre {
    Arbitre a;
    
    public TestArbitre(Properties p) {
        a = new Arbitre(p);
    }
    
   void base(){
       a.init();
       assertEquals(a.type(), Arbitre.JvIA);
       assertEquals(a.jCourant(), Arbitre.J1);
       assertEquals(a.difficulte(), Ordinateur.MOYEN);
   }
   
   void depot(){
       a.joue(new Depot(a.J1, Insecte.FOUR, new Point(0,0))); //OK
       assertEquals(a.jCourant(), Arbitre.J2);
       a.joue(new Depot(a.J2, 0, new Point(9,9)));
       assertEquals(a.jCourant(), Arbitre.J2);
       a.joue(new Depot(a.J2, Insecte.FOUR, new Point(1,0)));//OK
       assertEquals(a.jCourant(), Arbitre.J1);
       a.joue(new Deplacement(a.J2,"(0,0)->(0,1)"));
       assertEquals(a.jCourant(), Arbitre.J1);
       a.joue(new Depot(a.J1, 0, new Point(1,1)));
       assertEquals(a.jCourant(), Arbitre.J1);
       a.joue(new Depot(a.J1, Insecte.REINE, new Point(-1,1)));//OK
       assertEquals(a.jCourant(), Arbitre.J2);
       a.joue(new Depot(a.J2, 0, new Point(2,0)));//OK
       assertEquals(a.jCourant(), Arbitre.J1);
       a.joue(new Depot(a.J1, Insecte.REINE, new Point(9,0)));
       assertEquals(a.jCourant(), Arbitre.J1);
   }
   
   void deplacement(){
       //Bleu j1
       //Gris 12
        a.joue(new Deplacement(Arbitre.J1, "(-1,1)->(0,1)"));
        assertEquals(a.jCourant(), Arbitre.J2);
        a.joue(new Deplacement(Arbitre.J2, "(2,0)->(1,1)"));
        assertEquals(a.jCourant(), Arbitre.J1);
        a.joue(new Deplacement(Arbitre.J1, "(0,0)->(2,0)"));
        assertEquals(a.jCourant(), Arbitre.J2);
        a.joue(new Depot(a.J2, Insecte.SAUT, new Point(1,-1)));
        assertEquals(a.jCourant(), Arbitre.J1);
        a.joue(new Depot(a.J1, Insecte.SCAR, new Point(3,0)));
        assertEquals(a.jCourant(), Arbitre.J2);
        a.joue(new Deplacement(Arbitre.J2, "(1,-1)->(1,2)"));
        assertEquals(a.jCourant(), Arbitre.J1);
        a.joue(new Deplacement(Arbitre.J1, "(3,0)->(2,0)"));
        assertEquals(a.jCourant(), Arbitre.J2);
        a.joue(new Deplacement(Arbitre.J2, "(1,0)->(2,-1)"));
        assertEquals(a.jCourant(), Arbitre.J1);
        a.joue(new Deplacement(Arbitre.J1, "(2,0)->(1,0)"));
        assertEquals(a.jCourant(), Arbitre.J2);
        a.joue(new Deplacement(Arbitre.J2, "(2,-1)->(0,2)"));
        assertEquals(a.jCourant(), Arbitre.J1);
        a.joue(new Depot(a.J1, Insecte.SAUT, new Point(-1,1)));
        assertEquals(a.jCourant(), Arbitre.J2);
        a.joue(new Depot(a.J2, Insecte.ARAI, new Point(-1,3)));
        assertEquals(a.jCourant(), Arbitre.J1);
        assertFalse(a.plateau().estEncerclee(Arbitre.J2));
        a.joue(new Deplacement(Arbitre.J1, "(-1,1)->(2,1)"));
        assertEquals(a.jCourant(), Arbitre.J2);
        assertTrue(a.plateau().estEncerclee(Arbitre.J2));
        //Prendre en compte dans l'arbitre la victoire de J1
        a.joue(new Deplacement(Arbitre.J2, "(-1,3)->(2,2)"));
        assertEquals(a.jCourant(), Arbitre.J1);
        
   }
   @Test
   public void test(){
       base();
       depot();
       deplacement();
   }
    
}
