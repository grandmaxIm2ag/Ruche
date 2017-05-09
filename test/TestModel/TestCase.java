/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package TestModel;

import Modele.*;
import java.util.Properties;
import org.junit.Test;
import static org.junit.Assert.*;
import ruche.Configuration;
import ruche.Reglage;

/**
 *
 * @author grandmax
 */
public class TestCase {
    Case c;
    public TestCase() {
        c = new Case(0,0,5,5);
        Properties prop = Configuration.proprietes();
        Reglage.init(prop);
    }
    
    void base(){
        assertFalse(c.utilise());
        c.deposePion(FabriqueInsecte.creer(Insecte.REINE,0,c.position()));
        assertTrue(c.utilise());
        assertEquals(c.position(), c.tete().position());
        assertEquals(c, c.clone());
        c.retirePion();
        assertFalse(c.utilise());
        c.deposePion(FabriqueInsecte.creer(Insecte.REINE,0,c.position()));
        c.deposePion(FabriqueInsecte.creer(Insecte.REINE,0,c.position()));
        String str = FabriqueInsecte.creer(Insecte.REINE,0,c.position()).toString();
        assertEquals(c.toString(), c.position()+":"+str+":"+str);
        c.retirePion();
        assertEquals(c.toString(), c.position()+":"+str);
        assertTrue(c.utilise());
        
    }
    
    @Test
    public void test(){
        base();
    }
}
