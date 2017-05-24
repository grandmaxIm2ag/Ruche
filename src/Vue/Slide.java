/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Vue;

import java.util.ArrayList;
import java.util.List;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;

/**
 *
 * @author grandmax
 */
public class Slide {
    
    int index;
    List<ImageView> slides;
    ImageView currentImage;
    Label currentRegle;
    BorderPane pane;
    String[] regles;
    
    public Slide(){
        index=0;
        String[] r = {
            "Le but du jeu est d'entourer la reine Abeille adverse sans se faire entourer les sienne. Dans ce  cas, le joueur bleu a perdu.",
            "poser une pièce qu'il a en réserve sur le plateau, a côté d'une de ses pièces sans toucher une pièce adverse. (ici c'est le tour de bleu qui va poser une pièce, en rouge les coups possibles)",
            "Déplacer une de ses pièces en respectant les déplacement de chacun des insectes et en ne scindant pas la ruche. (ici la fourmi ne peux pas se déplacer)",
            "Déplacement d'une case autour d'elle",
            "Peut se déplacer autour de la ruche de n'importe quel nombre de case",
            "Saute par dessus une rangée de pion alignés",
            "Déplacement d'une case autour de lui et peut monter sur une pièce adjacente",
            "se déplace de exactement 3 cases autour de la ruche",
            "Sse déplace de exactement 3 cases autour de la ruche",
            "utilise le déplacement d'une pièce adjacente. (dans ce cas là le moustique peut utiliser les déplacements du scarabée ou de la sauterelle)",
             "déplacement d'une case autour de lui ou peut déplacer une pièce adjacente a un autre endroit autour de lui (sans briser la ruche. Ainsi il peut déplacer la reine verte mais pas l'araignee bleue)."
        };
        regles = r;
        currentRegle = new Label();
        
        pane = new BorderPane();
        slides = new ArrayList();
        for(int i=1; i<12; i++)
            slides.add(i-1, new ImageView(new Image(ClassLoader.getSystemClassLoader().getResourceAsStream("Images/Didacticielle/Diapo"+i+".png"))));
        currentRegle.setText(regles[index]);
        currentImage=slides.get(index);
        
        currentRegle.setMaxWidth(90);
        currentRegle.setWrapText(true);
        currentRegle.setTextFill(Color.WHITE);
        
        majPane();
    }
    
    public boolean next(){
        index = ++index;
         if(index >= slides.size()){
            index=slides.size()-1;
            return false;
        }
        currentImage = slides.get(index);
        currentRegle.setText(regles[index]);
        majPane();
        return true;
    }
    public boolean previous(){
        index--;
        
        if(index < 0){
            index=0;
            return false;
        }
            
        System.out.println(index);
        currentImage = slides.get(index);
        currentRegle.setText(regles[index]);
        majPane();
        
        return true;
    }
    
    public void majPane(){
        pane.setCenter(currentImage);
        pane.setLeft(currentRegle);
    }
    
    public Pane pane(){
        return pane;
    }
    /*
    public ImageView current(){
        return current;
    }*/
    
    
}
