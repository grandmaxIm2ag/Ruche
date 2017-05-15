/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package TestModel;

import Modele.Arbitres.*;
import Modele.Deplacement;
import Modele.Depot;
import Modele.FabriqueInsecte;
import Modele.Insecte;
import Modele.Plateau;
import Modele.Point;
import java.util.Properties;
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
    }
    
    void testCoupDepot(){
        assertTrue(p.aucunCoup(0));
        Point orig = new Point(0,0);
        p.premierPion(FabriqueInsecte.creer(Insecte.REINE, Arbitre.J1, orig));
        
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
        
        assertTrue(p.deposePionValide(new Depot(Arbitre.J2, Insecte.REINE, new Point(orig.x(), orig.y()+2))));
        assertFalse(p.deposePionValide(new Depot(Arbitre.J1, Insecte.REINE, new Point(orig.x(), orig.y()+2))));
        
        p.deposePion(new Depot(Arbitre.J2, Insecte.REINE, new Point(orig.x(), orig.y()+5)));
    }

    void testCoupDeplacement(){
        p = new Plateau(0,0,0,0, prop);
        Point orig = new Point(0,0);
        p.deposePion(new Depot(Arbitre.J2, Insecte.REINE, new Point(orig.x(), orig.y())));
        p.deposePion(new Depot(Arbitre.J2, Insecte.FOUR, new Point(orig.x()-1, orig.y())));
        
        assertFalse(p.deplacePionValide(new Deplacement(Arbitre.J2, new Point(orig.x()-1, orig.y()), new Point(orig.x()-1, orig.y()))));
        assertTrue(p.deplacePionValide(new Deplacement(Arbitre.J2, new Point(orig.x()-1, orig.y()), new Point(orig.x(), orig.y()-1))));
    
        p.deplacePion(new Deplacement(Arbitre.J2, new Point(orig.x()-1, orig.y()), new Point(orig.x(), orig.y()-1)));
        assertTrue(p.deposePionValide(new Depot(Arbitre.J2, Insecte.FOUR, new Point(orig.x()-1, orig.y()))));
        assertFalse(p.deposePionValide(new Depot(Arbitre.J2, Insecte.FOUR, new Point(orig.x(), orig.y()-1))));
    }
    
    void testConnexe(){
        p = new Plateau(0,0,0,0, prop);
        Point orig = new Point(0,0);
        p.premierPion(FabriqueInsecte.creer(Insecte.REINE, Arbitre.J1, orig));
        assertFalse(p.estEncerclee(Arbitre.J1));
        p.deposePion(new Depot(Arbitre.J1, Insecte.REINE, new Point(orig.x()+1, orig.y()-1)));
        p.deposePion(new Depot(Arbitre.J1, Insecte.REINE, new Point(orig.x(), orig.y()-1)));
        assertFalse(p.voisinage(orig, Plateau.SUD) );
        assertFalse(p.voisinage(orig, Plateau.EST) );
        assertFalse(p.voisinage(orig, Plateau.OUEST) );
        assertFalse(p.voisinage(orig, Plateau.SEST) );
        assertFalse(p.voisinage(orig, Plateau.SOUEST) );
        assertFalse(p.voisinage(orig, Plateau.NEST) );
        assertFalse(p.voisinage(orig, Plateau.NOUEST) );
        assertFalse(p.voisinage(orig, Plateau.ESTE) );
        assertFalse(p.voisinage(orig, Plateau.OUESTE) );
        assertTrue(p.voisinage(orig, Plateau.NORD) );
        assertFalse(p.estEncerclee(Arbitre.J1));
        //Nord
        
        p = new Plateau(0,0,0,0, prop);
        p.premierPion(FabriqueInsecte.creer(Insecte.REINE, Arbitre.J1, orig));
        p.deposePion(new Depot(Arbitre.J1, Insecte.REINE, new Point(orig.x()+1, orig.y()))); //test pour tout à l'heure
        p.deposePion(new Depot(Arbitre.J1, Insecte.REINE, new Point(orig.x()-1, orig.y())));
        assertFalse(p.voisinage(orig, Plateau.SUD) );
        assertTrue(p.voisinage(orig, Plateau.EST) );
        assertTrue(p.voisinage(orig, Plateau.OUEST) );
        assertFalse(p.voisinage(orig, Plateau.SEST) );
        assertFalse(p.voisinage(orig, Plateau.SOUEST) );
        assertFalse(p.voisinage(orig, Plateau.NEST) );
        assertFalse(p.voisinage(orig, Plateau.NOUEST) );
        assertFalse(p.voisinage(orig, Plateau.ESTE) );
        assertFalse(p.voisinage(orig, Plateau.OUESTE) );
        assertFalse(p.voisinage(orig, Plateau.NORD) );
        assertFalse(p.estEncerclee(Arbitre.J1));
        //Est Ouest
        
        p = new Plateau(0,0,0,0, prop);
        p.premierPion(FabriqueInsecte.creer(Insecte.REINE, Arbitre.J1, orig));
        p.deposePion(new Depot(Arbitre.J1, Insecte.REINE, new Point(orig.x()-1, orig.y()+1)));
        p.deposePion(new Depot(Arbitre.J1, Insecte.REINE, new Point(orig.x(), orig.y()+1)));
        assertTrue(p.voisinage(orig, Plateau.SUD) );
        assertFalse(p.voisinage(orig, Plateau.EST) );
        assertFalse(p.voisinage(orig, Plateau.OUEST) );
        assertFalse(p.voisinage(orig, Plateau.SEST) );
        assertFalse(p.voisinage(orig, Plateau.SOUEST) );
        assertFalse(p.voisinage(orig, Plateau.NEST) );
        assertFalse(p.voisinage(orig, Plateau.NOUEST) );
        assertFalse(p.voisinage(orig, Plateau.ESTE) );
        assertFalse(p.voisinage(orig, Plateau.OUESTE) );
        assertFalse(p.voisinage(orig, Plateau.NORD) );
        assertFalse(p.estEncerclee(Arbitre.J1));
        //Sud
        
        p = new Plateau(0,0,0,0, prop);
        p.premierPion(FabriqueInsecte.creer(Insecte.REINE, Arbitre.J1, orig));
        p.deposePion(new Depot(Arbitre.J1, Insecte.REINE, new Point(orig.x()+1, orig.y())));
        p.deposePion(new Depot(Arbitre.J1, Insecte.REINE, new Point(orig.x(), orig.y()+1)));
        p.deposePion(new Depot(Arbitre.J1, Insecte.REINE, new Point(orig.x()-1, orig.y())));
        p.deposePion(new Depot(Arbitre.J1, Insecte.REINE, new Point(orig.x(), orig.y()-1)));
        assertFalse(p.voisinage(orig, Plateau.SUD) );
        assertTrue(p.voisinage(orig, Plateau.EST) );
        assertTrue(p.voisinage(orig, Plateau.OUEST) );
        assertTrue(p.voisinage(orig, Plateau.SEST) );
        assertFalse(p.voisinage(orig, Plateau.SOUEST) );
        assertFalse(p.voisinage(orig, Plateau.NEST) );
        assertTrue(p.voisinage(orig, Plateau.NOUEST) );
        assertFalse(p.voisinage(orig, Plateau.ESTE) );
        assertFalse(p.voisinage(orig, Plateau.OUESTE) );
        assertFalse(p.voisinage(orig, Plateau.NORD) );
        assertFalse(p.estEncerclee(Arbitre.J1));
        //S-Est N-Ouest
        
        p = new Plateau(0,0,0,0, prop);
        p.premierPion(FabriqueInsecte.creer(Insecte.REINE, Arbitre.J1, orig));
        p.deposePion(new Depot(Arbitre.J1, Insecte.REINE, new Point(orig.x()-1, orig.y()+1)));
        p.deposePion(new Depot(Arbitre.J1, Insecte.REINE, new Point(orig.x()+1, orig.y())));
        p.deposePion(new Depot(Arbitre.J1, Insecte.REINE, new Point(orig.x()-1, orig.y())));
        p.deposePion(new Depot(Arbitre.J1, Insecte.REINE, new Point(orig.x()+1, orig.y()-1)));
        assertFalse(p.voisinage(orig, Plateau.SUD) );
        assertTrue(p.voisinage(orig, Plateau.EST) );
        assertTrue(p.voisinage(orig, Plateau.OUEST) );
        assertFalse(p.voisinage(orig, Plateau.SEST) );
        assertTrue(p.voisinage(orig, Plateau.SOUEST) );
        assertTrue(p.voisinage(orig, Plateau.NEST) );
        assertFalse(p.voisinage(orig, Plateau.NOUEST) );
        assertFalse(p.voisinage(orig, Plateau.ESTE) );
        assertFalse(p.voisinage(orig, Plateau.OUESTE) );
        assertFalse(p.voisinage(orig, Plateau.NORD) );
        assertFalse(p.estEncerclee(Arbitre.J1));
        //S-Ouest N-Est
        
        p = new Plateau(0,0,0,0, prop);
        p.premierPion(FabriqueInsecte.creer(Insecte.REINE, Arbitre.J1, orig));
        p.deposePion(new Depot(Arbitre.J1, Insecte.REINE, new Point(orig.x(), orig.y()+1)));
        p.deposePion(new Depot(Arbitre.J1, Insecte.REINE, new Point(orig.x()+1, orig.y())));
        p.deposePion(new Depot(Arbitre.J1, Insecte.REINE, new Point(orig.x()+1, orig.y()-1)));
        assertFalse(p.voisinage(orig, Plateau.SUD) );
        assertTrue(p.voisinage(orig, Plateau.EST) );
        assertFalse(p.voisinage(orig, Plateau.OUEST) );
        assertTrue(p.voisinage(orig, Plateau.SEST) );
        assertFalse(p.voisinage(orig, Plateau.SOUEST) );
        assertTrue(p.voisinage(orig, Plateau.NEST) );
        assertFalse(p.voisinage(orig, Plateau.NOUEST) );
        assertTrue(p.voisinage(orig, Plateau.ESTE) );
        assertFalse(p.voisinage(orig, Plateau.OUESTE) );
        assertFalse(p.voisinage(orig, Plateau.NORD) );
        assertFalse(p.estEncerclee(Arbitre.J1));
        //Est-Etendu
        
        p = new Plateau(0,0,0,0, prop);
        p.premierPion(FabriqueInsecte.creer(Insecte.REINE, Arbitre.J1, orig));
        p.deposePion(new Depot(Arbitre.J1, Insecte.REINE, new Point(orig.x(), orig.y()-1)));
        p.deposePion(new Depot(Arbitre.J1, Insecte.REINE, new Point(orig.x()-1, orig.y())));
        p.deposePion(new Depot(Arbitre.J1, Insecte.REINE, new Point(orig.x()-1, orig.y()+1)));
        assertFalse(p.voisinage(orig, Plateau.SUD) );
        assertFalse(p.voisinage(orig, Plateau.EST) );
        assertTrue(p.voisinage(orig, Plateau.OUEST) );
        assertFalse(p.voisinage(orig, Plateau.SEST) );
        assertTrue(p.voisinage(orig, Plateau.SOUEST) );
        assertFalse(p.voisinage(orig, Plateau.NEST) );
        assertTrue(p.voisinage(orig, Plateau.NOUEST) );
        assertFalse(p.voisinage(orig, Plateau.ESTE) );
        assertTrue(p.voisinage(orig, Plateau.OUESTE) );
        assertFalse(p.voisinage(orig, Plateau.NORD) );
        assertFalse(p.estEncerclee(Arbitre.J1));
        //Ouest-Etendu
        
        p = new Plateau(0,0,0,0, prop);
        p.premierPion(FabriqueInsecte.creer(Insecte.REINE, Arbitre.J1, orig));
        p.deposePion(new Depot(Arbitre.J1, Insecte.FOUR, new Point(orig.x(), orig.y()-1)));
        p.deposePion(new Depot(Arbitre.J1, Insecte.FOUR, new Point(orig.x()-1, orig.y())));
        p.deposePion(new Depot(Arbitre.J1, Insecte.FOUR, new Point(orig.x()-1, orig.y()+1)));
        p.deposePion(new Depot(Arbitre.J1, Insecte.FOUR, new Point(orig.x(), orig.y()+1)));
        p.deposePion(new Depot(Arbitre.J1, Insecte.FOUR, new Point(orig.x()+1, orig.y())));
        p.deposePion(new Depot(Arbitre.J1, Insecte.FOUR, new Point(orig.x()+1, orig.y()-1)));
        assertTrue(p.voisinage(orig, Plateau.SUD) );
        assertTrue(p.voisinage(orig, Plateau.EST) );
        assertTrue(p.voisinage(orig, Plateau.OUEST) );
        assertTrue(p.voisinage(orig, Plateau.SEST) );
        assertTrue(p.voisinage(orig, Plateau.SOUEST) );
        assertTrue(p.voisinage(orig, Plateau.NEST) );
        assertTrue(p.voisinage(orig, Plateau.NOUEST) );
        assertTrue(p.voisinage(orig, Plateau.ESTE) );
        assertTrue(p.voisinage(orig, Plateau.OUESTE) );
        assertTrue(p.voisinage(orig, Plateau.NORD) );
        assertTrue(p.estEncerclee(Arbitre.J1));
        //Encercle
        
        p = new Plateau(0,0,0,0, prop);
        p.premierPion(FabriqueInsecte.creer(Insecte.REINE, Arbitre.J1, orig));
        
        //Insecte Bougeable
        p.deposePion(new Depot(Arbitre.J1, Insecte.FOUR, new Point(orig.x()-1, orig.y()+1)));//E
        p.deposePion(new Depot(Arbitre.J1, Insecte.FOUR, new Point(orig.x()-1, orig.y())));//E
        p.deposePion(new Depot(Arbitre.J1, Insecte.FOUR, new Point(orig.x()-2, orig.y()+1)));//E
        p.deposePion(new Depot(Arbitre.J1, Insecte.FOUR, new Point(orig.x()-2, orig.y()+2)));//E
    
        //Insecte Non-Bougeable
        p.deposePion(new Depot(Arbitre.J1, Insecte.FOUR, new Point(orig.x()+1, orig.y())));
        p.deposePion(new Depot(Arbitre.J1, Insecte.FOUR, new Point(orig.x()+2, orig.y())));
        p.deposePion(new Depot(Arbitre.J1, Insecte.FOUR, new Point(orig.x()+3, orig.y()-1)));
        
        assertTrue(p.estConnexe(p.matrice().get(new Point(orig.x()-1, orig.y()+1)).tete()));
        assertTrue(p.estConnexe(p.matrice().get(new Point(orig.x()-1, orig.y())).tete()));
        assertTrue(p.estConnexe(p.matrice().get(new Point(orig.x()-2, orig.y()+1)).tete()));
        assertTrue(p.estConnexe(p.matrice().get(new Point(orig.x()-2, orig.y()+2)).tete()));
        
        assertFalse(p.estConnexe(p.matrice().get(new Point(orig.x()+1, orig.y())).tete()));
        assertFalse(p.estConnexe(p.matrice().get(new Point(orig.x()+2, orig.y())).tete()));
        assertFalse(p.estConnexe(p.matrice().get(orig).tete()));
        assertTrue(p.estConnexe(p.matrice().get(new Point(orig.x()+3, orig.y()-1)).tete()));
    }
    @Test
    public void test() {
        base();
        testCoupDepot();
        testCoupDeplacement();
        testConnexe();
    }
}
