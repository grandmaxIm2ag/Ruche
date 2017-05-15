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
public class TestArbitre {
    Arbitre a;
    FabriqueArbitre fab;
    
    public TestArbitre(Properties p) {
        fab = new FabriqueArbitre(p);
    }
    
    public void testFabrique(){
        fab.initType(FabriqueArbitre.LOCAL_JVJ);
        a = fab.nouveau();
        assertTrue(a instanceof Local);
        fab.initType(FabriqueArbitre.LOCAL_JVIA);
        a = fab.nouveau();
        assertTrue(a instanceof Local);
        fab.initType(FabriqueArbitre.SIMULATION);
        a = fab.nouveau();
        assertTrue(a instanceof SimulationIA);
        fab.initType(FabriqueArbitre.RESEAU_CLIENT);
        a = fab.nouveau();
        assertTrue(a instanceof ReseauClient);
        fab.initType(FabriqueArbitre.RESEAU_SERVER);
        a = fab.nouveau();
        assertTrue(a instanceof ReseauServer);
        
        
        fab.initType(FabriqueArbitre.LOCAL_JVIA);
        fab.initDiff(2);
        a = fab.nouveau();
        assertTrue(a.difficulte()==2);
        fab.initDiff(5);
        a = fab.nouveau();
        assertTrue(a.difficulte()==5);
    }
    
    public void testLocalJvj(){
        fab.initDiff(FabriqueArbitre.LOCAL_JVJ);
        
        
        
    }
    
    
    @Test
    public void test(){
        testFabrique();
        testLocalJvj();
    }
    
}
