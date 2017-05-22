/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Vue;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

/**
 *
 * @author brignone
 */
public class Bulle {
    private VBox box;
    private StackPane stack;
    private Rectangle rect;
    private Label name;
    private Label message;
    
    public Bulle (String nom, String message, Color c, boolean dispName, double width) {
        box = new VBox();
        stack = new StackPane();
        rect = new Rectangle ();
        rect.setFill(c);
        rect.setWidth(width-10);
        rect.setArcHeight(20);
        rect.setArcWidth(20);
        this.message = new Label (message);
        this.message.setWrapText(true);
        this.message.setMaxWidth(rect.getWidth()-10);
        VBox bubbleBox = new VBox();
        bubbleBox.setPadding(new Insets(10,10,10,10));
        bubbleBox.getChildren().add(this.message);
        VBox rectBox = new VBox();
        rectBox.getChildren().add(rect);
        rectBox.setPadding(new Insets (5, 5, 5, 5));
        rect.heightProperty().bind(this.message.heightProperty().add(10));
        stack.getChildren().addAll(rectBox, bubbleBox);
        if (dispName) {
            this.name = new Label (nom + ":");
            box.getChildren().add(name);
        }
        box.getChildren().add(stack);
    }
    
    public VBox getBulle () {
        return box;
    }
}
