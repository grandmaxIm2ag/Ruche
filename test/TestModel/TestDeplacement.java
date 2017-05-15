/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package TestModel;

import Modele.Deplacement;
import Modele.Point;
import java.util.LinkedList;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author grandmax
 */
public class TestDeplacement {
    Deplacement d;

    /**
     *
     */
    public TestDeplacement() {
        d = new Deplacement(0, new Point(4,4), new Point(5,5));
        
    }
    
    void base(){
        assertEquals(2, d.route().size());
        LinkedList<Point> tmp = new LinkedList();
        tmp.add(new Point(4,4));
        tmp.add(new Point(5,5));
        assertEquals(tmp, d.route());
        assertEquals(d, d.clone());
    }
    
    void ajout(){
        assertFalse(d.aDejaVisite(new Point(0,0)));
        assertTrue(d.aDejaVisite(new Point(4,4)));
        
        assertEquals(new Point(5,5), d.destination());
        d.add(new Point(0,0));
        assertTrue(d.aDejaVisite(new Point(0,0)));
        assertEquals(new Point(4,4), d.source());
        assertEquals(new Point(0,0), d.destination());
        
        LinkedList<Point> tmp = new LinkedList();
        tmp.add(new Point(4,4));
        tmp.add(new Point(5,5));
        tmp.add(new Point(0,0));
        assertEquals(tmp, d.route());
    }
            
    /**
     *
     */
    @Test
    public void test(){
        base();
        ajout();
    }
    
}
