/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Vue;

import Modele.Araignee;
import Modele.Arbitres.*;
import Modele.Case;
import Modele.Cloporte;
import Modele.Coccinelle;
import Modele.Coup;
import Modele.Deplacement;
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
import static Vue.Interface.hex_corner;
import java.io.InputStream;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import java.util.Stack;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;
import javafx.stage.Popup;

/**
 *
 * @author brignone
 */
public class Dessinateur extends Visiteur{
    
    static Canvas c;
    Etendeur etendeur;
    GraphicsContext gc;
    Arbitre arbitre;
    private static final Color [] colorTable = {Color.HOTPINK, Color.LIMEGREEN, Color.WHITESMOKE, Color.ORANGERED, Color.STEELBLUE, Color.DARKGOLDENROD, Color.DARKMAGENTA, Color.MEDIUMBLUE, Color.MAROON};
    
    Dessinateur (Canvas c, Arbitre a) {
        this.c = c;
        etendeur = new Etendeur();
        gc = c.getGraphicsContext2D();
        this.arbitre = a;
    }
    
    /**
     *
     * @param p
     * @return
     */
    @Override 
    public boolean visite (Plateau p) {
        //etendeur.fixeEchelle(c.getWidth()/arbitre.plateau().l(), c.getHeight()/arbitre.plateau().h(), c.getWidth()/2, c.getHeight()/2);
        etendeur.fixeEchelle(c, p);
        gc.clearRect(0, 0, c.getWidth(), c.getHeight());
        
        
        return false;
    }
    
    /**
     *
     * @param c
     * @return
     */
    @Override
    public boolean visite (Case c) {
        System.out.println(c);
        etendeur.fixeComposant(c);
        double [][] coords = Interface.hex_corner(etendeur.x(), etendeur.y(), etendeur.h()/2);
        //if (c.estpointe()) {
            //gc.setStroke(Color.RED);
            //System.out.println(c.position().x() + " " + c.position().y());
            //Popup popup = new Popup();
            //Rectangle rect = new Rectangle(100,100);
            //popup.xProperty().
        //}
        if (arbitre.initDeplacement() != null && arbitre.initDeplacement() instanceof Cloporte) {
            Coup[] coupPossible = arbitre.deplacementPossible(arbitre.initDeplacement());
            if(coupPossible !=null)
                for (Coup coup : coupPossible) {
                    Deplacement d = (Deplacement)coup;
                    if (d.source().equals(arbitre.initDeplacement().position()) && c.position().equals(d.destination())) 
                        gc.strokePolygon(coords[0], coords[1], 6);
                    else if (arbitre.getInitClopDepl() != null && d.source().equals(arbitre.getInitClopDepl().position()) && c.position().equals(d.destination()))
                        gc.strokePolygon(coords[0], coords[1], 6);
                }
        } else
            gc.fillPolygon(coords[0], coords[1], 6);
        gc.setStroke(Color.BLACK);
        if (c.utilise() && c.estpointe() && !c.insectes().empty() && (c.tete().classement() > 1)) {
                gc.setFill(Color.WHITESMOKE);
                gc.setStroke(Color.BLACK);
                Stack s = c.insectes();
                Iterator<Insecte> it = s.iterator();
                Insecte i;
                while (it.hasNext()) {
                    i = it.next();
                    
                }
            }
        
        return false;
    }
    
    /**
     *
     * @param i
     * @return
     */
    @Override
    public boolean visite (Reine i) {
        etendeur.fixeComposant(i);
        double [][] coords = Interface.hex_corner(etendeur.x(), etendeur.y(), etendeur.h()/2);
        Color couleur = getColor(i.joueur());
        if (i.estpointe())
            couleur = Color.RED;
        gc.setFill(couleur);
        gc.fillPolygon(coords[0], coords[1], 6);
        InputStream image = null;
        image =  ClassLoader.getSystemClassLoader().getResourceAsStream("Images/bee.png");
        Image img = new Image(image,((etendeur.h()/2)*1.75),((etendeur.h()/2)*1.75),true, true);
        gc.drawImage(img,etendeur.x()-(img.getWidth()/2), etendeur.y()-(img.getHeight()/2));
        focus(i, gc, coords);
        return false;
    }
    
    /**
     *
     * @param i
     * @return
     */
    @Override
    public boolean visite (Scarabee i) {
        etendeur.fixeComposant(i);
        double [][] coords = Interface.hex_corner(etendeur.x(), etendeur.y(), etendeur.h()/2);
        Color couleur = getColor(i.joueur());
        if (i.estpointe())
            couleur = Color.RED;
        gc.setFill(couleur);
        gc.fillPolygon(coords[0], coords[1], 6);
        InputStream image = null;
        image =  ClassLoader.getSystemClassLoader().getResourceAsStream("Images/beetle.png");
        Image img = new Image(image,((etendeur.h()/2)*1.5),((etendeur.h()/2)*1.5),true, true);
        gc.drawImage(img,etendeur.x()-(img.getWidth()/2), etendeur.y()-(img.getHeight()/2));
        if (i.classement() > 1) {
            Text t = new Text("" + i.classement());
            gc.setFill(Color.WHITE);
            gc.fillText(Integer.toString(i.classement()), coords[0][3], coords[1][3]+etendeur.h()/4);
        }
        focus(i, gc, coords);
        return false;
    }
    
    /**
     *
     * @param i
     * @return
     */
    @Override
    public boolean visite (Coccinelle i) {
        etendeur.fixeComposant(i);
        double [][] coords = Interface.hex_corner(etendeur.x(), etendeur.y(), etendeur.h()/2);
        Color couleur = getColor(i.joueur());
        if (i.estpointe())
            couleur = Color.RED;
        gc.setFill(couleur);
        gc.fillPolygon(coords[0], coords[1], 6);
        InputStream image = null;
        image =  ClassLoader.getSystemClassLoader().getResourceAsStream("Images/ladybug.png");
        Image img = new Image(image,((etendeur.h()/2)*1.6),((etendeur.h()/2)*1.6),true, true);
        gc.drawImage(img,etendeur.x()-(img.getWidth()/2), etendeur.y()-(img.getHeight()/2));
        if (i.classement() > 1) {
            Text t = new Text("" + i.classement());
            gc.setFill(Color.WHITE);
            gc.fillText(Integer.toString(i.classement()), coords[0][3], coords[1][3]+etendeur.h()/4);
        }
        focus(i, gc, coords);
        return false;
    }
    
    /**
     *
     * @param i
     * @return
     */
    @Override
    public boolean visite (Moustique i) {
        etendeur.fixeComposant(i);
        double [][] coords = Interface.hex_corner(etendeur.x(), etendeur.y(), etendeur.h()/2);
        Color couleur = getColor(i.joueur());
        if (i.estpointe())
            couleur = Color.RED;
        gc.setFill(couleur);        
        gc.fillPolygon(coords[0], coords[1], 6);
        InputStream image = null;
        image =  ClassLoader.getSystemClassLoader().getResourceAsStream("Images/moskito.png");
        Image img = new Image(image,((etendeur.h()/2)*1.75),((etendeur.h()/2)*1.75),true, true);
        gc.drawImage(img,etendeur.x()-(img.getWidth()/2), etendeur.y()-(img.getHeight()/2));
        if (i.classement() > 1) {
            Text t = new Text("" + i.classement());
            gc.setFill(Color.WHITE);
            gc.fillText(Integer.toString(i.classement()), coords[0][3], coords[1][3]+etendeur.h()/4);
        }
        focus(i, gc, coords);
        return false;
    }
    
    /**
     *
     * @param i
     * @return
     */
    @Override
    public boolean visite (Cloporte i) {
        etendeur.fixeComposant(i);
        double [][] coords = Interface.hex_corner(etendeur.x(), etendeur.y(), etendeur.h()/2);
        Color couleur = getColor(i.joueur());
        if (i.estpointe())
            couleur = Color.RED;
        gc.setFill(couleur);
        gc.fillPolygon(coords[0], coords[1], 6);
        InputStream image = null;
        image =  ClassLoader.getSystemClassLoader().getResourceAsStream("Images/woodlouse.png");
        Image img = new Image(image,((etendeur.h()/2)*1.75),((etendeur.h()/2)*1.75),true, true);
        gc.drawImage(img,etendeur.x()-(img.getWidth()/2), etendeur.y()-(img.getHeight()/2));
        focus(i, gc, coords);
        return false;
    }
    
    /**
     *
     * @param i
     * @return
     */
    @Override
    public boolean visite (Fourmie i) {
        etendeur.fixeComposant(i);
        double [][] coords = Interface.hex_corner(etendeur.x(), etendeur.y(), etendeur.h()/2);
        Color couleur = getColor(i.joueur());
        if (i.estpointe())
            couleur = Color.RED;
        gc.setFill(couleur);
        gc.fillPolygon(coords[0], coords[1], 6);
        InputStream image = null;
        image =  ClassLoader.getSystemClassLoader().getResourceAsStream("Images/ant.png");
        Image img = new Image(image,((etendeur.h()/2)*1.75),((etendeur.h()/2)*1.75),true, true);
        gc.drawImage(img,etendeur.x()-(img.getWidth()/2), etendeur.y()-(img.getHeight()/2));
        focus(i, gc, coords);
        return false;
    }
    
    /**
     *
     * @param i
     * @return
     */
    @Override
    public boolean visite (Araignee i) {
        etendeur.fixeComposant(i);
        double [][] coords = Interface.hex_corner(etendeur.x(), etendeur.y(), etendeur.h()/2);
        Color couleur = getColor(i.joueur());
        if (i.estpointe())
            couleur = Color.RED;
        gc.setFill(couleur);
        gc.fillPolygon(coords[0], coords[1], 6);
        InputStream image = null;
        image =  ClassLoader.getSystemClassLoader().getResourceAsStream("Images/spider.png");
        Image img = new Image(image,((etendeur.h()/2)*1.75),((etendeur.h()/2)*1.75),true, true);
        gc.drawImage(img,etendeur.x()-(img.getWidth()/2), etendeur.y()-(img.getHeight()/2));
        focus(i, gc, coords);
        return false;
    }
    
    /**
     *
     * @param i
     * @return
     */
    @Override
    public boolean visite (Sauterelle i) {
        etendeur.fixeComposant(i);
        double [][] coords = Interface.hex_corner(etendeur.x(), etendeur.y(), etendeur.h()/2);
        Color couleur = getColor(i.joueur());
        if (i.estpointe())
            couleur = Color.RED;
        gc.setFill(couleur);
        gc.fillPolygon(coords[0], coords[1], 6);
        InputStream image = null;
        image =  ClassLoader.getSystemClassLoader().getResourceAsStream("Images/grasshopper.png");
        Image img = new Image(image,((etendeur.h()/2)*1.75),((etendeur.h()/2)*1.75),true, true);
        gc.drawImage(img,etendeur.x()-(img.getWidth()/2), etendeur.y()-(img.getHeight()/2));
        focus(i, gc, coords);
        return false;
    }
    
    /*
    public void print (Insecte i) {
        if (i.)
    }*/
    
    public boolean print (Reine i) {
        etendeur.fixeComposant(i);
        double [][] coords = Interface.hex_corner(etendeur.x(), etendeur.y(), etendeur.h()/2);
        Color couleur = getColor(i.joueur());
        if (i.estpointe())
            couleur = Color.RED;
        gc.setFill(couleur);
        gc.fillPolygon(coords[0], coords[1], 6);
        InputStream image = null;
        image =  ClassLoader.getSystemClassLoader().getResourceAsStream("Images/bee.png");
        Image img = new Image(image,((etendeur.h()/2)*1.75),((etendeur.h()/2)*1.75),true, true);
        gc.drawImage(img,etendeur.x()-(img.getWidth()/2), etendeur.y()-(img.getHeight()/2));
        return false;
    }
    
    
    public boolean print (Scarabee i) {
        etendeur.fixeComposant(i);
        double [][] coords = Interface.hex_corner(etendeur.x(), etendeur.y(), etendeur.h()/2);
        Color couleur = getColor(i.joueur());
        if (i.estpointe())
            couleur = Color.RED;
        gc.setFill(couleur);
        gc.fillPolygon(coords[0], coords[1], 6);
        InputStream image = null;
        image =  ClassLoader.getSystemClassLoader().getResourceAsStream("Images/beetle.png");
        Image img = new Image(image,((etendeur.h()/2)*1.5),((etendeur.h()/2)*1.5),true, true);
        gc.drawImage(img,etendeur.x()-(img.getWidth()/2), etendeur.y()-(img.getHeight()/2));
        if (i.classement() > 1) {
            Text t = new Text("" + i.classement());
            gc.setFill(Color.WHITE);
            gc.fillText(Integer.toString(i.classement()), coords[0][3], coords[1][3]+etendeur.h()/4);
        }
        return false;
    }
    
    
    public boolean print (Coccinelle i) {
        etendeur.fixeComposant(i);
        double [][] coords = Interface.hex_corner(etendeur.x(), etendeur.y(), etendeur.h()/2);
        Color couleur = getColor(i.joueur());
        if (i.estpointe())
            couleur = Color.RED;
        gc.setFill(couleur);
        gc.fillPolygon(coords[0], coords[1], 6);
        InputStream image = null;
        image =  ClassLoader.getSystemClassLoader().getResourceAsStream("Images/ladybug.png");
        Image img = new Image(image,((etendeur.h()/2)*1.6),((etendeur.h()/2)*1.6),true, true);
        gc.drawImage(img,etendeur.x()-(img.getWidth()/2), etendeur.y()-(img.getHeight()/2));
        if (i.classement() > 1) {
            Text t = new Text("" + i.classement());
            gc.setFill(Color.WHITE);
            gc.fillText(Integer.toString(i.classement()), coords[0][3], coords[1][3]+etendeur.h()/4);
        }
        return false;
    }
    
    
    public boolean print (Moustique i) {
        etendeur.fixeComposant(i);
        double [][] coords = Interface.hex_corner(etendeur.x(), etendeur.y(), etendeur.h()/2);
        Color couleur = getColor(i.joueur());
        if (i.estpointe())
            couleur = Color.RED;
        gc.setFill(couleur);        
        gc.fillPolygon(coords[0], coords[1], 6);
        InputStream image = null;
        image =  ClassLoader.getSystemClassLoader().getResourceAsStream("Images/moskito.png");
        Image img = new Image(image,((etendeur.h()/2)*1.75),((etendeur.h()/2)*1.75),true, true);
        gc.drawImage(img,etendeur.x()-(img.getWidth()/2), etendeur.y()-(img.getHeight()/2));
        if (i.classement() > 1) {
            Text t = new Text("" + i.classement());
            gc.setFill(Color.WHITE);
            gc.fillText(Integer.toString(i.classement()), coords[0][3], coords[1][3]+etendeur.h()/4);
        }
        return false;
    }
    
    
    public boolean print (Cloporte i) {
        etendeur.fixeComposant(i);
        double [][] coords = Interface.hex_corner(etendeur.x(), etendeur.y(), etendeur.h()/2);
        Color couleur = getColor(i.joueur());
        if (i.estpointe())
            couleur = Color.RED;
        gc.setFill(couleur);
        gc.fillPolygon(coords[0], coords[1], 6);
        InputStream image = null;
        image =  ClassLoader.getSystemClassLoader().getResourceAsStream("Images/woodlouse.png");
        Image img = new Image(image,((etendeur.h()/2)*1.75),((etendeur.h()/2)*1.75),true, true);
        gc.drawImage(img,etendeur.x()-(img.getWidth()/2), etendeur.y()-(img.getHeight()/2));
        return false;
    }
    
    
    public boolean print (Fourmie i) {
        etendeur.fixeComposant(i);
        double [][] coords = Interface.hex_corner(etendeur.x(), etendeur.y(), etendeur.h()/2);
        Color couleur = getColor(i.joueur());
        if (i.estpointe())
            couleur = Color.RED;
        gc.setFill(couleur);
        gc.fillPolygon(coords[0], coords[1], 6);
        InputStream image = null;
        image =  ClassLoader.getSystemClassLoader().getResourceAsStream("Images/ant.png");
        Image img = new Image(image,((etendeur.h()/2)*1.75),((etendeur.h()/2)*1.75),true, true);
        gc.drawImage(img,etendeur.x()-(img.getWidth()/2), etendeur.y()-(img.getHeight()/2));
        return false;
    }
    
    
    public boolean print (Araignee i) {
        etendeur.fixeComposant(i);
        double [][] coords = Interface.hex_corner(etendeur.x(), etendeur.y(), etendeur.h()/2);
        Color couleur = getColor(i.joueur());
        if (i.estpointe())
            couleur = Color.RED;
        gc.setFill(couleur);
        gc.fillPolygon(coords[0], coords[1], 6);
        InputStream image = null;
        image =  ClassLoader.getSystemClassLoader().getResourceAsStream("Images/spider.png");
        Image img = new Image(image,((etendeur.h()/2)*1.75),((etendeur.h()/2)*1.75),true, true);
        gc.drawImage(img,etendeur.x()-(img.getWidth()/2), etendeur.y()-(img.getHeight()/2));
        return false;
    }
    
    
    public boolean print (Sauterelle i) {
        etendeur.fixeComposant(i);
        double [][] coords = Interface.hex_corner(etendeur.x(), etendeur.y(), etendeur.h()/2);
        Color couleur = getColor(i.joueur());
        if (i.estpointe())
            couleur = Color.RED;
        gc.setFill(couleur);
        gc.fillPolygon(coords[0], coords[1], 6);
        InputStream image = null;
        image =  ClassLoader.getSystemClassLoader().getResourceAsStream("Images/grasshopper.png");
        Image img = new Image(image,((etendeur.h()/2)*1.75),((etendeur.h()/2)*1.75),true, true);
        gc.drawImage(img,etendeur.x()-(img.getWidth()/2), etendeur.y()-(img.getHeight()/2));
        return false;
    }
    
    private Color getColor (int p) {
        int c = 0;
        switch (p) {
            case 0:
                c = Interface.getColorP1();
                break;
            case 1:
                c = Interface.getColorP2();
                break;
            default:
        }
        return colorTable[c];
    }
    
    private void focus (Insecte i, GraphicsContext gc, double[][] coords) {
        if (arbitre.initDeplacement() instanceof Cloporte) {
                        Coup[] coups = arbitre.deplacementPossible(arbitre.initDeplacement());
                        for (Coup coup : coups) {
                            Deplacement deplacement = (Deplacement) coup;
                            if (deplacement.source().equals(i.position()) && !arbitre.initDeplacement().equals(i)) {
                                gc.setStroke(Color.RED);
                                gc.setLineWidth(5);
                                gc.strokePolygon(coords[0], coords[1], 6);
                                gc.setStroke(Color.BLACK);
                                gc.setLineWidth(1);
                            }
                        }
                        
                    }
    }
}
