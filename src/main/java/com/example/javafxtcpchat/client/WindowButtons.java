package com.example.javafxtcpchat.client;

import javafx.application.Platform;
import javafx.scene.control.Button;
import javafx.scene.layout.HBox;

public class WindowButtons extends HBox {
    public WindowButtons() {
        Button closeBtn = new Button("X");
        closeBtn.setOnAction(event -> Platform.exit());
        this.getChildren().add(closeBtn);
    }

}

