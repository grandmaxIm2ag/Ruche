/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Vue;

import static Vue.Interface.root;
import java.io.InputStream;
import javafx.animation.AnimationTimer;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;

/**
 *
 * @author brignone
 */
public class TestCanvas extends AnimationTimer{
    Canvas c;
    
    /**
     *
     * @param can
     */
    public TestCanvas(Canvas can){
        c=can;
    }
    
    /**
     *
     */
    public void draw(){
        System.out.println("coucou");
        GraphicsContext gc = c.getGraphicsContext2D();
        gc.setFill(Color.WHITESMOKE);
        gc.fillRect(0, 0, 500, 500);
        gc.strokeRect(0, 0, 500, 500);
        gc.setFill(Color.BLUE);
        gc.setFont(Font.font("Tahoma", FontWeight.NORMAL, 33));
        gc.fillText("Ceci est le terrain de jeu", 50, 250);
        InputStream image = null;
        image =  ClassLoader.getSystemClassLoader().getResourceAsStream("Images/ant3.png");
        
        //Background back = new Background(new BackgroundFill(Color.LIGHTGREEN, CornerRadii.EMPTY, null));
        //root.setBackground(back);
        Image img = new Image(image);
        //ImageView view = new ImageView();
        //view.setImage(img);
        gc.drawImage(img, 50, 50);
        
    }

    /**
     *
     * @param now
     */
    @Override
    public void handle(long now) {
        draw();
    }
    
}
