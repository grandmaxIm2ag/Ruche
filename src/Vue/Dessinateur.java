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
import javafx.scene.text.Text;

/**
 *
 * @author brignone
 */
public class Dessinateur extends Visiteur{
    
    static Canvas c;
    Etendeur etendeur;
    GraphicsContext gc;
    
    Dessinateur (Canvas c) {
        this.c = c;
        etendeur = new Etendeur();
        gc = c.getGraphicsContext2D();
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
        etendeur.fixeComposant(c);
        double [][] coords = Interface.hex_corner(etendeur.x(), etendeur.y(), etendeur.h()/2);
        if (c.estpointe()) {
            gc.setStroke(Color.RED);
            System.out.println(c.position().x() + " " + c.position().y());
        }
        gc.strokePolygon(coords[0], coords[1], 6);
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
        Color couleur = Color.WHITE;
        if (i.joueur() == 0)
            couleur = Color.GREEN;
        else
            couleur = Color.CORNFLOWERBLUE;
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
    
    /**
     *
     * @param i
     * @return
     */
    @Override
    public boolean visite (Scarabee i) {
        etendeur.fixeComposant(i);
        double [][] coords = Interface.hex_corner(etendeur.x(), etendeur.y(), etendeur.h()/2);
        Color couleur = Color.WHITE;
        if (i.joueur() == Arbitre.J1)
            couleur = Color.GREEN;
        else
            couleur = Color.CORNFLOWERBLUE;
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
    
    /**
     *
     * @param i
     * @return
     */
    @Override
    public boolean visite (Coccinelle i) {
        etendeur.fixeComposant(i);
        double [][] coords = Interface.hex_corner(etendeur.x(), etendeur.y(), etendeur.h()/2);
        Color couleur = Color.WHITE;
        if (i.joueur() == 0)
            couleur = Color.GREEN;
        else
            couleur = Color.CORNFLOWERBLUE;
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
    
    /**
     *
     * @param i
     * @return
     */
    @Override
    public boolean visite (Moustique i) {
        etendeur.fixeComposant(i);
        double [][] coords = Interface.hex_corner(etendeur.x(), etendeur.y(), etendeur.h()/2);
        Color couleur = Color.WHITE;
        if (i.joueur() == 0)
            couleur = Color.GREEN;
        else
            couleur = Color.CORNFLOWERBLUE;
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
    
    /**
     *
     * @param i
     * @return
     */
    @Override
    public boolean visite (Cloporte i) {
        etendeur.fixeComposant(i);
        double [][] coords = Interface.hex_corner(etendeur.x(), etendeur.y(), etendeur.h()/2);
        Color couleur = Color.WHITE;
        if (i.joueur() == 0)
            couleur = Color.GREEN;
        else
            couleur = Color.CORNFLOWERBLUE;
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
    
    /**
     *
     * @param i
     * @return
     */
    @Override
    public boolean visite (Fourmie i) {
        etendeur.fixeComposant(i);
        double [][] coords = Interface.hex_corner(etendeur.x(), etendeur.y(), etendeur.h()/2);
        Color couleur = Color.WHITE;
        if (i.joueur() == 0)
            couleur = Color.GREEN;
        else
            couleur = Color.CORNFLOWERBLUE;
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
    
    /**
     *
     * @param i
     * @return
     */
    @Override
    public boolean visite (Araignee i) {
        etendeur.fixeComposant(i);
        double [][] coords = Interface.hex_corner(etendeur.x(), etendeur.y(), etendeur.h()/2);
        Color couleur = Color.WHITE;
        if (i.joueur() == 0)
            couleur = Color.GREEN;
        else
            couleur = Color.CORNFLOWERBLUE;
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
    
    /**
     *
     * @param i
     * @return
     */
    @Override
    public boolean visite (Sauterelle i) {
        etendeur.fixeComposant(i);
        double [][] coords = Interface.hex_corner(etendeur.x(), etendeur.y(), etendeur.h()/2);
        Color couleur = Color.WHITE;
        if (i.joueur() == 0)
            couleur = Color.GREEN;
        else
            couleur = Color.CORNFLOWERBLUE;
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
    
    /*
    public void print (Insecte i) {
        if (i.)
    }*/
    
    public boolean print (Reine i) {
        etendeur.fixeComposant(i);
        double [][] coords = Interface.hex_corner(etendeur.x(), etendeur.y(), etendeur.h()/2);
        Color couleur = Color.WHITE;
        if (i.joueur() == 0)
            couleur = Color.GREEN;
        else
            couleur = Color.CORNFLOWERBLUE;
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
        Color couleur = Color.WHITE;
        if (i.joueur() == Arbitre.J1)
            couleur = Color.GREEN;
        else
            couleur = Color.CORNFLOWERBLUE;
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
        Color couleur = Color.WHITE;
        if (i.joueur() == 0)
            couleur = Color.GREEN;
        else
            couleur = Color.CORNFLOWERBLUE;
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
        Color couleur = Color.WHITE;
        if (i.joueur() == 0)
            couleur = Color.GREEN;
        else
            couleur = Color.CORNFLOWERBLUE;
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
        Color couleur = Color.WHITE;
        if (i.joueur() == 0)
            couleur = Color.GREEN;
        else
            couleur = Color.CORNFLOWERBLUE;
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
        Color couleur = Color.WHITE;
        if (i.joueur() == 0)
            couleur = Color.GREEN;
        else
            couleur = Color.CORNFLOWERBLUE;
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
        Color couleur = Color.WHITE;
        if (i.joueur() == 0)
            couleur = Color.GREEN;
        else
            couleur = Color.CORNFLOWERBLUE;
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
        Color couleur = Color.WHITE;
        if (i.joueur() == 0)
            couleur = Color.GREEN;
        else
            couleur = Color.CORNFLOWERBLUE;
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
}
