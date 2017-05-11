/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controleur;

import Modele.Arbitre;
import Vue.Pointeur;
import javafx.event.EventHandler;
import javafx.scene.canvas.Canvas;
import javafx.scene.input.MouseEvent;


/**
 *
 * @author grandmax
 */
public class Souris implements EventHandler<MouseEvent>{
    
    public final static int SOURIS_BOUGEE = 0;
    
    Arbitre a;
    int value;
    Canvas c;
    
    public Souris (Arbitre a, int v, Canvas c) {
        this.a = a;
        this.value = v;
        this.c = c;
    }

    @Override
    public void handle(MouseEvent t) {
        //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
        Pointeur p = new Pointeur(c, t);
        a.accept(p);
    }
    
}
