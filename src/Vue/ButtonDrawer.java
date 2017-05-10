/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Vue;

import Modele.Araignee;
import Modele.Arbitre;
import Modele.Cloporte;
import Modele.Coccinelle;
import Modele.Etendeur;
import Modele.Fourmie;
import Modele.Insecte;
import Modele.Moustique;
import Modele.Plateau;
import Modele.Reine;
import Modele.Sauterelle;
import Modele.Scarabee;
import Modele.Visiteur;
import java.io.InputStream;
import java.util.Iterator;
import java.util.LinkedList;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;

/**
 *
 * @author brignone
 */
public class ButtonDrawer extends Visiteur {
    static Canvas canvasPlayer1, canvasPlayer2;
    Etendeur etendeur;
    GraphicsContext gcp1, gcp2;
    LinkedList<Insecte> lp1, lp2;
    Plateau p1;
    
    public ButtonDrawer (Canvas cp1, Canvas cp2) {
        canvasPlayer1 = cp1;
        canvasPlayer2 = cp2;
        gcp1 = canvasPlayer1.getGraphicsContext2D();
        gcp2 = canvasPlayer2.getGraphicsContext2D();
        etendeur = new Etendeur();    
        lp1 = new LinkedList();
        lp2 = new LinkedList();
        
        p1 = new Plateau(0,0,10,10, null);
        
        lp1.add(new Reine(0,0,1,0));
        lp1.add(new Scarabee(0,1,1,0,0));
        lp1.add(new Sauterelle(-1,2,1,0,0));
        lp1.add(new Fourmie(-1,3,1,0,0));
        lp1.add(new Araignee(-2,4,1,0,0));
        lp1.add(new Coccinelle(-2,5,1,0,0));
        lp1.add(new Moustique(-3,6,1,0,0));
        lp1.add(new Cloporte(-3,7,1,0,0));
        
        lp2.add(new Reine(0,0,1,1));
        lp2.add(new Scarabee(0,1,1,0,1));
        lp2.add(new Sauterelle(-1,2,1,0,1));
        lp2.add(new Fourmie(-1,3,1,0,1));
        lp2.add(new Araignee(-2,4,1,0,1));
        lp2.add(new Coccinelle(-2,5,1,0,1));
        lp2.add(new Moustique(-3,6,1,0,1));
        lp2.add(new Cloporte(-3,7,1,0,1));
    }
    
    @Override
    public boolean visite (Arbitre a) {
        etendeur.fixeEchelle(canvasPlayer1, lp1);
        Iterator it = lp1.iterator();
        visite((Reine) it.next(), gcp1, canvasPlayer1);
        visite((Scarabee) it.next(), gcp1, canvasPlayer1);
        visite((Sauterelle) it.next(), gcp1, canvasPlayer1);
        visite((Fourmie) it.next(), gcp1, canvasPlayer1);
        visite((Araignee) it.next(), gcp1, canvasPlayer1);
        visite((Coccinelle) it.next(), gcp1, canvasPlayer1);
        visite((Moustique) it.next(), gcp1, canvasPlayer1);
        visite((Cloporte) it.next(), gcp1, canvasPlayer1);
        it = lp2.iterator();
        visite((Reine) it.next(), gcp2, canvasPlayer2);
        visite((Scarabee) it.next(), gcp2, canvasPlayer2);
        visite((Sauterelle) it.next(), gcp2, canvasPlayer2);
        visite((Fourmie) it.next(), gcp2, canvasPlayer2);
        visite((Araignee) it.next(), gcp2, canvasPlayer2);
        visite((Coccinelle) it.next(), gcp2, canvasPlayer2);
        visite((Moustique) it.next(), gcp2, canvasPlayer2);
        visite((Cloporte) it.next(), gcp2, canvasPlayer2);
        
        return false;
        
    }
    
    public boolean visite (Reine i, GraphicsContext gc, Canvas c) {
        etendeur.fixeComposant(i);
        double [][] coords = Interface.hex_corner(etendeur.x(), etendeur.y(), etendeur.h()/2);
        Color couleur = Color.WHITE;
        if (i.joueur() == 0)
            couleur = Color.GREEN;
        else
            couleur = Color.CORNFLOWERBLUE;
        gc.setFill(couleur);
        gc.fillPolygon(coords[0], coords[1], 6);
        InputStream image = null;
        image =  ClassLoader.getSystemClassLoader().getResourceAsStream("Images/bee.png");
        Image img = new Image(image,((etendeur.h()/2)*1.75),((etendeur.h()/2)*1.75),true, true);
        gc.drawImage(img,etendeur.x()-(img.getWidth()/2), etendeur.y()-(img.getHeight()/2));
        return false;
    }
    
    public boolean visite (Scarabee i, GraphicsContext gc, Canvas c) {
        etendeur.fixeComposant(i);
        double [][] coords = Interface.hex_corner(etendeur.x(), etendeur.y(), etendeur.h()/2);
        Color couleur = Color.WHITE;
        if (i.joueur() == 0)
            couleur = Color.GREEN;
        else
            couleur = Color.CORNFLOWERBLUE;
        gc.setFill(couleur);
        gc.fillPolygon(coords[0], coords[1], 6);
        InputStream image = null;
        image =  ClassLoader.getSystemClassLoader().getResourceAsStream("Images/beetle.png");
        Image img = new Image(image,((etendeur.h()/2)*1.5),((etendeur.h()/2)*1.5),true, true);
        gc.drawImage(img,etendeur.x()-(img.getWidth()/2), etendeur.y()-(img.getHeight()/2));
        return false;
    }
    
    public boolean visite (Coccinelle i, GraphicsContext gc, Canvas c) {
        etendeur.fixeComposant(i);
        double [][] coords = Interface.hex_corner(etendeur.x(), etendeur.y(), etendeur.h()/2);
        Color couleur = Color.WHITE;
        if (i.joueur() == 0)
            couleur = Color.GREEN;
        else
            couleur = Color.CORNFLOWERBLUE;
        gc.setFill(couleur);
        gc.fillPolygon(coords[0], coords[1], 6);
        InputStream image = null;
        image =  ClassLoader.getSystemClassLoader().getResourceAsStream("Images/ladybug.png");
        Image img = new Image(image,((etendeur.h()/2)*1.6),((etendeur.h()/2)*1.6),true, true);
        gc.drawImage(img,etendeur.x()-(img.getWidth()/2), etendeur.y()-(img.getHeight()/2));
        return false;
    }
    
    public boolean visite (Moustique i, GraphicsContext gc, Canvas c) {
        etendeur.fixeComposant(i);
        double [][] coords = Interface.hex_corner(etendeur.x(), etendeur.y(), etendeur.h()/2);
        Color couleur = Color.WHITE;
        if (i.joueur() == 0)
            couleur = Color.GREEN;
        else
            couleur = Color.CORNFLOWERBLUE;
        gc.setFill(couleur);        
        gc.fillPolygon(coords[0], coords[1], 6);
        InputStream image = null;
        image =  ClassLoader.getSystemClassLoader().getResourceAsStream("Images/moskito.png");
        Image img = new Image(image,((etendeur.h()/2)*1.75),((etendeur.h()/2)*1.75),true, true);
        gc.drawImage(img,etendeur.x()-(img.getWidth()/2), etendeur.y()-(img.getHeight()/2));
        return false;
    }
    
    public boolean visite (Cloporte i, GraphicsContext gc, Canvas c) {
        etendeur.fixeComposant(i);
        double [][] coords = Interface.hex_corner(etendeur.x(), etendeur.y(), etendeur.h()/2);
        Color couleur = Color.WHITE;
        if (i.joueur() == 0)
            couleur = Color.GREEN;
        else
            couleur = Color.CORNFLOWERBLUE;
        gc.setFill(couleur);
        gc.fillPolygon(coords[0], coords[1], 6);
        InputStream image = null;
        image =  ClassLoader.getSystemClassLoader().getResourceAsStream("Images/woodlouse.png");
        Image img = new Image(image,((etendeur.h()/2)*1.75),((etendeur.h()/2)*1.75),true, true);
        gc.drawImage(img,etendeur.x()-(img.getWidth()/2), etendeur.y()-(img.getHeight()/2));
        return false;
    }
    
    public boolean visite (Fourmie i, GraphicsContext gc, Canvas c) {
        etendeur.fixeComposant(i);
        double [][] coords = Interface.hex_corner(etendeur.x(), etendeur.y(), etendeur.h()/2);
        Color couleur = Color.WHITE;
        if (i.joueur() == 0)
            couleur = Color.GREEN;
        else
            couleur = Color.CORNFLOWERBLUE;
        gc.setFill(couleur);
        gc.fillPolygon(coords[0], coords[1], 6);
        InputStream image = null;
        image =  ClassLoader.getSystemClassLoader().getResourceAsStream("Images/ant.png");
        Image img = new Image(image,((etendeur.h()/2)*1.75),((etendeur.h()/2)*1.75),true, true);
        gc.drawImage(img,etendeur.x()-(img.getWidth()/2), etendeur.y()-(img.getHeight()/2));
        return false;
    }
    
    public boolean visite (Araignee i, GraphicsContext gc, Canvas c) {
        etendeur.fixeComposant(i);
        double [][] coords = Interface.hex_corner(etendeur.x(), etendeur.y(), etendeur.h()/2);
        Color couleur = Color.WHITE;
        if (i.joueur() == 0)
            couleur = Color.GREEN;
        else
            couleur = Color.CORNFLOWERBLUE;
        gc.setFill(couleur);
        gc.fillPolygon(coords[0], coords[1], 6);
        InputStream image = null;
        image =  ClassLoader.getSystemClassLoader().getResourceAsStream("Images/spider.png");
        Image img = new Image(image,((etendeur.h()/2)*1.75),((etendeur.h()/2)*1.75),true, true);
        gc.drawImage(img,etendeur.x()-(img.getWidth()/2), etendeur.y()-(img.getHeight()/2));
        return false;
    }
    
    public boolean visite (Sauterelle i, GraphicsContext gc, Canvas c) {
        etendeur.fixeComposant(i);
        double [][] coords = Interface.hex_corner(etendeur.x(), etendeur.y(), etendeur.h()/2);
        Color couleur = Color.WHITE;
        if (i.joueur() == 0)
            couleur = Color.GREEN;
        else
            couleur = Color.CORNFLOWERBLUE;
        gc.setFill(couleur);
        gc.fillPolygon(coords[0], coords[1], 6);
        InputStream image = null;
        image =  ClassLoader.getSystemClassLoader().getResourceAsStream("Images/grasshopper.png");
        Image img = new Image(image,((etendeur.h()/2)*1.75),((etendeur.h()/2)*1.75),true, true);
        gc.drawImage(img,etendeur.x()-(img.getWidth()/2), etendeur.y()-(img.getHeight()/2));
        return false;
    }
    
    
}
