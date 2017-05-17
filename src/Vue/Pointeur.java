/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Vue;

import Modele.Araignee;
import Modele.Arbitres.Arbitre;
import Modele.Case;
import Modele.Cloporte;
import Modele.Coccinelle;
import Modele.Coup;
import Modele.Deplacement;
import Modele.Depot;
import Modele.Etendeur;
import Modele.Fourmie;
import Modele.Insecte;
import Modele.Moustique;
import Modele.Plateau;
import Modele.Point;
import Modele.Reine;
import Modele.Sauterelle;
import Modele.Scarabee;
import Modele.Visiteur;
import static Vue.Dessinateur.c;
import java.io.InputStream;
import java.util.Iterator;
import java.util.List;
import javafx.geometry.Insets;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;
import javafx.stage.Popup;

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
    public Popup popup;
    public boolean initPopup;
    
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
        popup = new Popup();

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
        initPopup = false;
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
                //if (popup.isShowing())
                    //popup.hide();
                //System.out.println("[" + c.position().x()+ ";" + c.position().y()+ "]");
                c.pointe();
                if (c.utilise())
                    c.tete().pointe();
                //i(c.insectes().)
                if (c.tete().classement() > 1) {
                    System.err.println("JeanClaudeVanDamn");
                    
                    Rectangle rect = new Rectangle(125,125);
                    //rect.setWidth(100*c.tete().classement() + 12.5*c.tete().classement());
                    //rect.setArcWidth(20);
                    //rect.setArcHeight(20);
                    rect.setFill(Color.WHITESMOKE);
                    popup.setX(me.getX());
                    popup.setY(me.getY());
                    StackPane stack = new StackPane();
                    HBox box = new HBox();
                    box.setPadding(new Insets(12.5,12.5,12.5,12.5));
                    box.setSpacing(12.5);
                    //Canvas canvas = print(c.tete());
                    for (Object ins : c.insectes()) {
                        box.getChildren().add(print (((Insecte) ins)));
                    }
                    rect.widthProperty().bind(stack.widthProperty());
                    stack.getChildren().addAll(rect, box);
                    popup.getContent().addAll(rect, box);
                    
                    
                    
                    //rect.setWidth(y2);
                    
                    popup.show(Interface.stage);
                    initPopup = true;
                    //popup.show(Interface.scene, me.getX(), me.getY());
                } else if (popup.isShowing())
                    popup.hide();
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
                            if (arbitre.getInitClopDepl() == null)
                                arbitre.joue(new Deplacement(arbitre.jCourant(), arbitre.initDeplacement().position(), c.position()));
                            else 
                                arbitre.joue(new Deplacement(arbitre.jCourant(), arbitre.getInitClopDepl().position(), c.position()));
                            arbitre.reinitDepl();
                            depl = false;
                            return true;
                        }

                    } else if (arbitre.initDeplacement().position().equals(c.position())) {
                        System.out.println ("Chocrotte");
                        depl = false;
                        return true;
                    } else if (arbitre.initDeplacement() instanceof Cloporte) {
                        System.out.println ("Cacahahaha");
                        Coup[] coups = arbitre.deplacementPossible(arbitre.initDeplacement());
                        for (Coup coup : coups) {
                            Deplacement deplacement = (Deplacement) coup;
                            if (deplacement.source().equals(c.position()))
                                //arbitre.initDeplacement(c.tete());
                                arbitre.initClopDepl(c.tete());
                        }
                        
                    }
                } else if (c.tete().joueur() == arbitre.jCourant()) {
                    
                    System.err.println(arbitre.plateau().aide());
                    System.out.println(c.tete());
                    System.err.println("caca2");
                    arbitre.initDeplacement(c.tete());
                    List<Case> tchup = arbitre.plateau().aide();
                    for (Case cas : tchup) {
                        System.out.println(cas);
                    }
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
    
    public Canvas print (Insecte i) {
        Canvas canvas = new Canvas (100,100);
        GraphicsContext gc = canvas.getGraphicsContext2D();
        double [][] coords = Interface.hex_corner(50, 50, 50);
        Color couleur = Color.WHITE;
        if (i.joueur() == 0)
            couleur = Color.GREEN;
        else
            couleur = Color.CORNFLOWERBLUE;
        gc.setFill(couleur);
        gc.fillPolygon(coords[0], coords[1], 6);
        Image img = img(i);
        gc.drawImage(img,50-(img.getWidth()/2), 50-(img.getHeight()/2));
        return canvas;
    }
    
    private Image img (Insecte i) {
        String s = "";
        double mod = 0;
        if (i instanceof Reine) {
            s = "bee";
            mod = 1.75;
        } else if (i instanceof Scarabee) {
            s = "beetle";
            mod = 1.5;
        } else if (i instanceof Coccinelle) {
            s = "ladybug";
            mod = 1.6;
        } else if (i instanceof Moustique) {
            s = "moskito";
            mod = 1.75;
        } else if (i instanceof Cloporte) {
            s = "woodlouse";
            mod = 1.75;
        } else if (i instanceof Fourmie) {
            s = "ant";
            mod = 1.75;
        } else if (i instanceof Araignee) {
            s = "spider";
            mod = 1.75;
        } else if (i instanceof Sauterelle) {
            s = "grasshopper";
            mod = 1.75;
        }
        InputStream image = null;
        image =  ClassLoader.getSystemClassLoader().getResourceAsStream("Images/"+s+".png");
        Image img = new Image(image,((50)*mod),((50)*mod),true, true);
        return img;
    }
    
}
