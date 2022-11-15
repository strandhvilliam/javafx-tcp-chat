package com.example.javafxtcpchat.client;


import javafx.application.Platform;
import javafx.concurrent.Task;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.text.Font;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class ChatController implements Initializable {

    @FXML
    private TextArea chatTextArea;

    @FXML
    private TextField inputTextField;

    @FXML
    private ListView<?> roomUsersList;

    @FXML
    private BorderPane testBorderPane;

    @FXML
    private Label usernameLabel;

    @FXML
    void sendAction(ActionEvent event) {
        String message = inputTextField.getText();
        inputTextField.clear();
        chatClient.sendMessage(message);
    }

    private ChatClient chatClient;


    public void printMessage(String message) {
        chatTextArea.appendText(message + "\n");
    }


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        chatClient = new ChatClient(this);
        Thread th = new Thread(chatClient);
        th.setDaemon(true);
        th.start();


    }
}

