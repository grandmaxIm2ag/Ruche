/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Vue;

import Modele.Arbitres.Arbitre;
import Modele.Case;
import Modele.Deplacement;
import Modele.Depot;
import Modele.Etendeur;
import Modele.Insecte;
import Modele.Plateau;
import Modele.Point;
import Modele.Visiteur;
import static Vue.Dessinateur.c;
import java.util.Iterator;
import java.util.List;
import javafx.scene.canvas.Canvas;
import javafx.scene.input.MouseEvent;

/**
 *
 * @author brignone
 */
public class Pointeur extends Visiteur {
    static Canvas c;
    Etendeur etendeur;
    Arbitre arbitre;
    MouseEvent me;
    private boolean depl = false;
    
    /**
     *
     * @param c
     * @param me
     */
    public Pointeur (Canvas c, MouseEvent me) {
        this.c = c;
        this.me = me;
        etendeur = new Etendeur();
    }
    
    public Pointeur (Canvas c, Arbitre a) {
        this.c = c;
        this.me = me;
        etendeur = new Etendeur();
        this.arbitre = a;
    }
    
    /**
     *
     * @param me
     */
    public void addEvent (MouseEvent me) {
        this.me = me;
    }
    
    /**
     *
     * @param p
     * @return
     */
    @Override
    public boolean visite (Plateau p) {
        etendeur.fixeEchelle(c, p);
        p.depointe();
        return false;
    }
    
    /**
     *
     * @param c
     * @return
     */
    @Override
    public boolean visite (Case c) {
        boolean b = true;
        double x1, x2, y1, y2;
        etendeur.fixeComposant(c);
        double [][] coords = Interface.hex_corner(etendeur.x(), etendeur.y(), etendeur.h()/2);
        for (int i = 0; i < 6; i++) {
            x1 = coords[0][(i+1)%6] - coords[0][i];
            y1 = coords[1][(i+1)%6] - coords[1][i];

            x2 = me.getX() - coords[0][i];
            y2 = me.getY() - coords[1][i];

            b = b&&((x1*y2) - (x2*y1))>0;
        }
            
        
        if (b) {
            if (me.getEventType() == MouseEvent.MOUSE_MOVED) {
                //System.out.println("[" + c.position().x()+ ";" + c.position().y()+ "]");
                c.pointe();
                if (c.utilise())
                    c.tete().pointe();
            } else if (me.getEventType() == MouseEvent.MOUSE_CLICKED) {

                if (arbitre.plateau().deplEntame()) {
                    System.out.println("arbitre.plateau().deplEntame() : " + arbitre.plateau().deplEntame() + "\narbitre.plateau().aide()" + arbitre.plateau().aide());
                    System.out.println ("c.estJouable() " + c.estJouable());
                    if (c.estJouable()) {
                        if (!depl) {
                            PaneToken.getInstance().uncheck();
                            arbitre.joue(new Depot(arbitre.jCourant(), arbitre.initDepot(), c.position()));
                            return true;
                        } else {
                            
                            PaneToken.getInstance().uncheck();
                            arbitre.joue(new Deplacement(arbitre.jCourant(), arbitre.initDeplacement().position(), c.position()));
                            depl = false;
                            return true;
                        }

                    } else if (arbitre.initDeplacement().position().equals(c.position())) {
                        System.out.println ("Chocrotte");
                        depl = false;
                        return true;
                    }
                } else if (c.tete().joueur() == arbitre.jCourant()) {
                    
                    System.err.println(arbitre.plateau().aide());
                    System.out.println(c.tete());
                    System.err.println("caca2");
                    arbitre.initDeplacement(c.tete());
                    depl = true;
                }
            }
        }
            
       

    
        
        return false;
    }
    
    /**
     *
     * @param b
     * @return
     */
    @Override
    public boolean visite (ButtonDrawer b) {
        Iterator<Insecte> it = b.lp1.iterator();
        while (it.hasNext()) {
            it.next().accept(this);
        }
        it = b.lp2.iterator();
        while (it.hasNext()) {
            it.next().accept(this);
        }
        return false;
    }
    
    /**
     *
     * @param c
     * @return
     */
    @Override
    public boolean visite (Insecte c) {
        boolean b = true;
        double x1, x2, y1, y2;
        etendeur.fixeComposant(c);
        double [][] coords = Interface.hex_corner(etendeur.x(), etendeur.y(), etendeur.h()/2);
        for (int i = 0; i < 6; i++) {
            x1 = coords[0][(i+1)%6] - coords[0][i];
            y1 = coords[1][(i+1)%6] - coords[1][i];

            x2 = me.getX() - coords[0][i];
            y2 = me.getY() - coords[1][i];
            
            b = b&&((x1*y2) - (x2*y1))>0;
        }
        if (b) {
            //System.out.println("[" + c.position().x()+ ";" + c.position().y()+ "]");
            c.pointe();
        }
        return false;
    }
    
    public MouseEvent event(){
        return me;
    }
    public void traiter(){
        me = null;
    }
}
