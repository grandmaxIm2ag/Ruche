/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package TestModel;

import Modele.Arbitres.Apprentissage;
import Modele.Coup;
import Modele.Deplacement;
import Modele.Depot;
import Modele.Insecte;
import Modele.Plateau;
import Modele.Point;
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
public class TestTranslate {
    
    public TestTranslate() {
    }
    
    int conf1(){
        Plateau p = new Plateau(0,0,0,0,null);
        p.deposePion(new Depot(0,Insecte.REINE, new Point(0,0)));
        p.deposePion(new Depot(0,Insecte.FOUR, new Point(1,0)));
        p.deposePion(new Depot(0,Insecte.FOUR, new Point(-1,0)));
        p.deposePion(new Depot(0,Insecte.FOUR, new Point(0,1)));
        p.deposePion(new Depot(0,Insecte.FOUR, new Point(0,-1)));
        return p.hashCode();
    }
    
    int conf2(){
        Plateau p = new Plateau(0,0,0,0,null);
        p.deposePion(new Depot(0,Insecte.REINE, new Point(0,1)));
        p.deposePion(new Depot(0,Insecte.FOUR, new Point(1,1)));
        p.deposePion(new Depot(0,Insecte.FOUR, new Point(-1,1)));
        p.deposePion(new Depot(0,Insecte.FOUR, new Point(0,2)));
        p.deposePion(new Depot(0,Insecte.FOUR, new Point(0,0)));
        return p.hashCode();
    }
    
    int conf3(){
        Plateau p = new Plateau(0,0,0,0,null);
        p.deposePion(new Depot(0,Insecte.REINE, new Point(1,0)));
        p.deposePion(new Depot(0,Insecte.FOUR, new Point(2,0)));
        p.deposePion(new Depot(0,Insecte.FOUR, new Point(0,0)));
        p.deposePion(new Depot(0,Insecte.FOUR, new Point(1,1)));
        p.deposePion(new Depot(0,Insecte.FOUR, new Point(1,-1)));
        return p.hashCode();
    }
    
    int conf4(){
        Plateau p = new Plateau(0,0,0,0,null);
        p.deposePion(new Depot(0,Insecte.REINE, new Point(-1,0)));
        p.deposePion(new Depot(0,Insecte.FOUR, new Point(0,0)));
        p.deposePion(new Depot(0,Insecte.FOUR, new Point(-2,0)));
        p.deposePion(new Depot(0,Insecte.FOUR, new Point(-1,1)));
        p.deposePion(new Depot(0,Insecte.FOUR, new Point(-1,-1)));
        return p.hashCode();
    }
    
    int conf5(){
        Plateau p = new Plateau(0,0,0,0,null);
        p.deposePion(new Depot(0,Insecte.REINE, new Point(0,-1)));
        p.deposePion(new Depot(0,Insecte.FOUR, new Point(1,-1)));
        p.deposePion(new Depot(0,Insecte.FOUR, new Point(-1,-1)));
        p.deposePion(new Depot(0,Insecte.FOUR, new Point(0,0)));
        p.deposePion(new Depot(0,Insecte.FOUR, new Point(0,-2)));
        return p.hashCode();
    }

    void testHashCode(){
        assertEquals(conf2(),conf1());
        assertEquals(conf5(),conf1());
        assertEquals(conf4(),conf1());
        assertEquals(conf3(),conf1());
    }
    
    void testTranlateCoup(){
        Plateau p = new Plateau(0,0,0,0,null);
        p.deposePion(new Depot(0,Insecte.REINE, new Point(0,0)));
        p.deposePion(new Depot(0,Insecte.FOUR, new Point(1,0)));
        p.deposePion(new Depot(0,Insecte.FOUR, new Point(-1,0)));
        p.deposePion(new Depot(0,Insecte.FOUR, new Point(0,1)));
        p.deposePion(new Depot(0,Insecte.FOUR, new Point(0,-1)));
        
        Plateau p1 = new Plateau(0,0,0,0,null);
        p1.deposePion(new Depot(0,Insecte.REINE, new Point(-1,0)));
        p1.deposePion(new Depot(0,Insecte.FOUR, new Point(0,0)));
        p1.deposePion(new Depot(0,Insecte.FOUR, new Point(-2,0)));
        p1.deposePion(new Depot(0,Insecte.FOUR, new Point(-1,1)));
        p1.deposePion(new Depot(0,Insecte.FOUR, new Point(-1,-1)));
        
        Coup c1 = new Deplacement(0,new Point(1,0), new Point(1,-1));
        Coup c2 = new Deplacement(0,new Point(0,0), new Point(0,-1));
        Coup abs1 = Apprentissage.translateConcreteToAbstract(c1, 0, p);
        Coup abs2 = Apprentissage.translateConcreteToAbstract(c2, 0, p1);
        assertEquals(abs1, abs2);
        
        Coup cont11 = Apprentissage.translateAbstractToConcrete(abs1, 0, p);
        Coup cont12 = Apprentissage.translateAbstractToConcrete(abs2, 0, p);
        assertEquals(cont11, cont12);
        assertEquals(cont12, c1);
        
        Coup cont21 = Apprentissage.translateAbstractToConcrete(abs1, 0, p1);
        Coup cont22 = Apprentissage.translateAbstractToConcrete(abs2, 0, p1);
        assertEquals(cont21, cont22);
        assertEquals(cont22, c2);
    }
    
    @Test
    public void test(){
        testHashCode();
        testTranlateCoup();
    }
    // TODO add test methods here.
    // The methods must be annotated with annotation @Test. For example:
    //
    // @Test
    // public void hello() {}
}
