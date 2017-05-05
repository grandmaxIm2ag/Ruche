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
    
    public MainTest() {
        Properties prop = Configuration.proprietes();
        Reglage.init(prop);
    }
    
    @Test
    public void test(){
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
    }
    
}
