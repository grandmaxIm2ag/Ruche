/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package TestModel;

import Modele.Arbitres.*;
import java.util.Properties;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author grandmax
 */
public class TestFabriqueArbitre {
    Arbitre a;
    FabriqueArbitre fab;
    
    /**
     *
     * @param p
     */
    public TestFabriqueArbitre(Properties p) {
        FabriqueArbitre.init(p);
    }
    
    /**
     *
     */
    public void testFabrique(){
        FabriqueArbitre.initType(FabriqueArbitre.LOCAL_JVJ);
        a = FabriqueArbitre.nouveau();
        assertTrue(a instanceof Local);
        FabriqueArbitre.initType(FabriqueArbitre.LOCAL_JVIA);
        a = FabriqueArbitre.nouveau();
        assertTrue(a instanceof Local);
        FabriqueArbitre.initType(FabriqueArbitre.SIMULATION);
        a = FabriqueArbitre.nouveau();
        assertTrue(a instanceof SimulationIA);
        FabriqueArbitre.initType(FabriqueArbitre.RESEAU_CLIENT);
        a = FabriqueArbitre.nouveau();
        assertTrue(a instanceof ReseauClient);
        FabriqueArbitre.initType(FabriqueArbitre.RESEAU_SERVER);
        a = FabriqueArbitre.nouveau();
        assertTrue(a instanceof ReseauServer);
        
        
        FabriqueArbitre.initType(FabriqueArbitre.LOCAL_JVIA);
        FabriqueArbitre.initDiff(2);
        a = FabriqueArbitre.nouveau();
        assertTrue(a.difficulte()==2);
        FabriqueArbitre.initDiff(3);
        a = FabriqueArbitre.nouveau();
        assertTrue(a.difficulte()==3);
    }
    
    /**
     *
     */
    @Test
    public void test(){
        testFabrique();
    }
    
}
