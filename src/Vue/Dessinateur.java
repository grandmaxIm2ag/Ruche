/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Vue;

import Modele.Araignee;
import Modele.Cloporte;
import Modele.Coccinelle;
import Modele.Fourmie;
import Modele.Moustique;
import Modele.Reine;
import Modele.Sauterelle;
import Modele.Scarabee;
import Modele.Visiteur;
import static Vue.Interface.hex_corner;
import java.io.InputStream;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;

/**
 *
 * @author grandmax
 */
public class Dessinateur extends Visiteur{
    
    static Canvas c;
    
    Dessinateur (Canvas c) {
        this.c = c;
    }
    
    @Override
    public boolean visite (Reine i) {
        GraphicsContext gc;
        gc = c.getGraphicsContext2D();
        double [][] coords = Interface.hex_corner(i.position().x(),i.position().y(), i.h());
        Color couleur = Color.WHITE;
        if (i.joueur() == 0)
            couleur = Color.GREEN;
        else
            couleur = Color.BLUE;
        gc.setFill(couleur);
        gc.fillPolygon(coords[0], coords[1], 6);
        InputStream image = null;
        image =  ClassLoader.getSystemClassLoader().getResourceAsStream("Images/bee.png");
        Image img = new Image(image,(i.h()*1.75),(i.h()*1.75),true, true);
        gc.drawImage(img,i.position().x()-(img.getWidth()/2), i.position().y()-(img.getHeight()/2));
        return false;
    }
    
    @Override
    public boolean visite (Scarabee i) {
        GraphicsContext gc;
        gc = c.getGraphicsContext2D();
        double [][] coords = Interface.hex_corner(i.position().x(),i.position().y(), i.h());
        Color couleur = Color.WHITE;
        if (i.joueur() == 0)
            couleur = Color.GREEN;
        else
            couleur = Color.BLUE;
        gc.setFill(couleur);
        gc.fillPolygon(coords[0], coords[1], 6);
        InputStream image = null;
        image =  ClassLoader.getSystemClassLoader().getResourceAsStream("Images/beetle.png");
        Image img = new Image(image,(i.h()*1.50),(i.h()*1.50),true, true);
        gc.drawImage(img,i.position().x()-(img.getWidth()/2), i.position().y()-(img.getHeight()/2));
        return false;
    }
    
    @Override
    public boolean visite (Coccinelle i) {
        GraphicsContext gc;
        gc = c.getGraphicsContext2D();
        double [][] coords = Interface.hex_corner(i.position().x(),i.position().y(), i.h());
        Color couleur = Color.WHITE;
        if (i.joueur() == 0)
            couleur = Color.GREEN;
        else
            couleur = Color.BLUE;
        gc.setFill(couleur);
        gc.fillPolygon(coords[0], coords[1], 6);
        InputStream image = null;
        image =  ClassLoader.getSystemClassLoader().getResourceAsStream("Images/ladybug.png");
        Image img = new Image(image,(i.h()*1.50),(i.h()*1.50),true, true);
        gc.drawImage(img,i.position().x()-(img.getWidth()/2), i.position().y()-(img.getHeight()/2));
        return false;
    }
    
    @Override
    public boolean visite (Moustique i) {
        GraphicsContext gc;
        gc = c.getGraphicsContext2D();
        double [][] coords = Interface.hex_corner(i.position().x(),i.position().y(), i.h());
        Color couleur = Color.WHITE;
        if (i.joueur() == 0)
            couleur = Color.GREEN;
        else
            couleur = Color.BLUE;
        gc.setFill(couleur);        
        gc.fillPolygon(coords[0], coords[1], 6);
        InputStream image = null;
        image =  ClassLoader.getSystemClassLoader().getResourceAsStream("Images/moskito.png");
        Image img = new Image(image,(i.h()*2),(i.h()*2),true, true);
        gc.drawImage(img,i.position().x()-(img.getWidth()/2), i.position().y()-(img.getHeight()/2));
        return false;
    }
    
    @Override
    public boolean visite (Cloporte i) {
        GraphicsContext gc;
        gc = c.getGraphicsContext2D();
        double [][] coords = Interface.hex_corner(i.position().x(),i.position().y(), i.h());
        Color couleur = Color.WHITE;
        if (i.joueur() == 0)
            couleur = Color.GREEN;
        else
            couleur = Color.BLUE;
        gc.setFill(couleur);
        gc.fillPolygon(coords[0], coords[1], 6);
        InputStream image = null;
        image =  ClassLoader.getSystemClassLoader().getResourceAsStream("Images/woodlouse.png");
        Image img = new Image(image,(i.h()*1.75),(i.h()*1.75),true, true);
        gc.drawImage(img,i.position().x()-(img.getWidth()/2), i.position().y()-(img.getHeight()/2));
        return false;
    }
    
    @Override
    public boolean visite (Fourmie i) {
        GraphicsContext gc;
        gc = c.getGraphicsContext2D();
        double [][] coords = Interface.hex_corner(i.position().x(),i.position().y(), i.h());
        Color couleur = Color.WHITE;
        if (i.joueur() == 0)
            couleur = Color.GREEN;
        else
            couleur = Color.BLUE;
        gc.setFill(couleur);
        gc.fillPolygon(coords[0], coords[1], 6);
        InputStream image = null;
        image =  ClassLoader.getSystemClassLoader().getResourceAsStream("Images/ant.png");
        Image img = new Image(image,(i.h()*1.60),(i.h()*1.60),true, true);
        gc.drawImage(img,i.position().x()-(img.getWidth()/2), i.position().y()-(img.getHeight()/2));
        return false;
    }
    
    @Override
    public boolean visite (Araignee i) {
        GraphicsContext gc;
        gc = c.getGraphicsContext2D();
        double [][] coords = Interface.hex_corner(i.position().x(),i.position().y(), i.h());
        Color couleur = Color.WHITE;
        if (i.joueur() == 0)
            couleur = Color.GREEN;
        else
            couleur = Color.BLUE;
        gc.setFill(couleur);
        gc.fillPolygon(coords[0], coords[1], 6);
        InputStream image = null;
        image =  ClassLoader.getSystemClassLoader().getResourceAsStream("Images/spider.png");
        Image img = new Image(image,(i.h()*1.60),(i.h()*1.60),true, true);
        gc.drawImage(img,i.position().x()-(img.getWidth()/2), i.position().y()-(img.getHeight()/2));
        return false;
    }
    
    @Override
    public boolean visite (Sauterelle i) {
        GraphicsContext gc;
        gc = c.getGraphicsContext2D();
        double [][] coords = Interface.hex_corner(i.position().x(),i.position().y(), i.h());
        Color couleur = Color.WHITE;
        if (i.joueur() == 0)
            couleur = Color.GREEN;
        else
            couleur = Color.BLUE;
        gc.setFill(couleur);
        gc.fillPolygon(coords[0], coords[1], 6);
        InputStream image = null;
        image =  ClassLoader.getSystemClassLoader().getResourceAsStream("Images/grasshopper.png");
        Image img = new Image(image,(i.h()*1.60),(i.h()*1.60),true ,true);
        gc.drawImage(img,i.position().x()-(img.getWidth()/2), i.position().y()-(img.getHeight()/2));
        return false;
    }
    
}
