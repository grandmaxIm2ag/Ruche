/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Vue;

import Modele.Arbitres.*;
import Modele.Arbitres.TestArbitre;
import Modele.Visiteur;
import javafx.animation.AnimationTimer;
import javafx.scene.canvas.Canvas;

/**
 *
 * @author Gueckmooh
 */
public class Animation extends AnimationTimer{

    Arbitre arbitre;
    Canvas can;
    Visiteur dessinateur;
    Visiteur buttonDrawer;
    
    Animation (Arbitre a, Canvas c, Canvas cj1, Canvas cj2) {
        arbitre = a;
        can = c;
        dessinateur = new Dessinateur (c);
        buttonDrawer = new ButtonDrawer(cj1, cj2);
    }
    
    @Override
    public void handle(long l) {
        //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
        arbitre.maj(l);
        arbitre.accept(dessinateur);
        //arbitre.acceptButton(buttonDrawer);
    }
    
}
