package com.example.javafxtcpchat.client;

import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;

import java.time.LocalTime;

public class MessageBubble extends GridPane {

    public MessageBubble(String message, String username, boolean isUser, double listWidth) {
        setVgap(4);
        VBox bubble = new VBox();
        if (isUser) {
            bubble.getStyleClass().add("right-chat-bubble");
        } else {
            bubble.getStyleClass().add("left-chat-bubble");
        }
        Label messageLabel = new Label(message);
        messageLabel.getStyleClass().add("bubble-message-label");
        messageLabel.setMaxWidth(listWidth / 2);

        Label time = new Label(LocalTime.now().toString());
        time.getStyleClass().add("bubble-time-label");
        bubble.getChildren().add(messageLabel);
        bubble.getChildren().add(time);

        Label iconLabel = new Label(username.substring(0, 1).toUpperCase());
        iconLabel.getStyleClass().add("circle-label");
        Label nameLabel = new Label(username);
        nameLabel.getStyleClass().add("bubble-name-label");
        HBox nameBox = new HBox();
        if (isUser) {
            nameBox.setAlignment(Pos.CENTER_RIGHT);
            nameBox.getChildren().add(nameLabel);
            nameBox.getChildren().add(iconLabel);
            iconLabel.setStyle("-fx-background-color: #23a094");
        } else {
            nameBox.setAlignment(Pos.CENTER_LEFT);
            nameBox.getChildren().add(iconLabel);
            nameBox.getChildren().add(nameLabel);
        }

        HBox fill = new HBox();
        GridPane.setHgrow(fill, Priority.ALWAYS);

        if (isUser) {
            add(fill, 0, 1);
            add(bubble, 1, 1);
            add(nameBox, 1, 0);
        } else {
            add(fill, 1, 1);
            add(bubble, 0, 1);
            add(nameBox, 0, 0);
        }

    }
}
