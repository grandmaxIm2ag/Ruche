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
       a.joue(new Depot(a.J1, Insecte.FOUR, new Point(0,0)));
       assertEquals(a.jCourant(), Arbitre.J2);
       a.joue(new Depot(a.J2, 0, new Point(9,9)));
       assertEquals(a.jCourant(), Arbitre.J2);
       a.joue(new Depot(a.J2, 0, new Point(1,0)));
       assertEquals(a.jCourant(), Arbitre.J1);
       a.joue(new Deplacement(a.J2,"(0,0)->(0,1)"));
       assertEquals(a.jCourant(), Arbitre.J1);
       a.joue(new Depot(a.J1, 0, new Point(1,1)));
       assertEquals(a.jCourant(), Arbitre.J1);
       a.joue(new Depot(a.J1, 0, new Point(-1,1)));
       assertEquals(a.jCourant(), Arbitre.J2);
       a.joue(new Depot(a.J2, 0, new Point(2,0)));
       assertEquals(a.jCourant(), Arbitre.J1);
       a.joue(new Depot(a.J1, 0, new Point(9,0)));
       assertEquals(a.jCourant(), Arbitre.J1);
   }
   
   @Test
   public void test(){
       base();
       depot();
   }
    
}
