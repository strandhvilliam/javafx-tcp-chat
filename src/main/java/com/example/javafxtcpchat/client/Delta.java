package com.example.javafxtcpchat.client;

import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Delta {
    public double x, y;


    public void initMovableWindow(Stage stage, Scene scene, Node node) {
        Delta dragDelta = new Delta();
        node.setOnMousePressed(e -> {
            dragDelta.x = stage.getX() - e.getScreenX();
            dragDelta.y = stage.getY() - e.getScreenY();
        });
        node.setOnMouseDragged(e -> {
            stage.setX(e.getScreenX() + dragDelta.x);
            stage.setY(e.getScreenY() + dragDelta.y);
        });
    }
}

