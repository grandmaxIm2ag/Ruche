/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import TestModel.*;
import java.util.Properties;
import org.junit.Test;
import static org.junit.Assert.*;
import ruche.Configuration;
import ruche.Reglage;

/**
 *
 * @author grandmax
 */
public class MainTest {
    Properties prop;
    public MainTest() {
        prop = Configuration.proprietes();
        Reglage.init(prop);
    }
    
    void insecte(){
        TestCase tc = new TestCase();
        tc.test();
        TestFourmie tf = new TestFourmie();
        tf.test();
        TestReine tr = new TestReine();
        tr.test();
        TestAraignee ta = new TestAraignee();
        ta.test();
        TestSauterelle tsa = new TestSauterelle();
        tsa.test();
        TestScarabee tsc = new TestScarabee();
        tsc.test();
        TestCoccinelle tco = new TestCoccinelle();
        tco.test();
        TestMoustique tm = new TestMoustique();
        tm.test();
        TestCloporte cloporte = new TestCloporte();
        cloporte.test();
    }
    @Test
    public void test(){
        insecte();
        TestCase tc = new TestCase();
        tc.test();
        TestFabrique tf = new TestFabrique();
        tf.test();
        TestPlateau tp = new TestPlateau(prop);
        tp.test();
        //TestArbitre ta = new TestArbitre(prop);
        //ta.test();
    }
    
}
