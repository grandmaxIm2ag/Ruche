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
public class TestFabrique {
    
    public TestFabrique() {
        Properties prop = Configuration.proprietes();
        Reglage.init(prop);
    }
    
    @Test
    public void test(){
        Insecte e = FabriqueInsecte.creer(Insecte.REINE, 0, new Point(0,0));
        assertTrue(e instanceof Reine);
        e = FabriqueInsecte.creer(Insecte.SCAR, 0, new Point(0,0));
        assertTrue(e instanceof Scarabee);
        e = FabriqueInsecte.creer(Insecte.ARAI, 0, new Point(0,0));
        assertTrue(e instanceof Araignee);
        e = FabriqueInsecte.creer(Insecte.COCC, 0, new Point(0,0));
        assertTrue(e instanceof Coccinelle);
        e = FabriqueInsecte.creer(Insecte.CLOP, 0, new Point(0,0));
        assertTrue(e instanceof Cloporte);
        e = FabriqueInsecte.creer(Insecte.FOUR, 0, new Point(0,0));
        assertTrue(e instanceof Fourmie);
        e = FabriqueInsecte.creer(Insecte.MOUS, 0, new Point(0,0));
        assertTrue(e instanceof Moustique);
        e = FabriqueInsecte.creer(Insecte.SAUT, 0, new Point(0,0));
        assertTrue(e instanceof Sauterelle);
        
    }
    
}
