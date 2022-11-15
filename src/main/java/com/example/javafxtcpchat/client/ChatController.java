package com.example.javafxtcpchat.client;


import javafx.application.Platform;
import javafx.concurrent.Task;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.text.Font;
import javafx.stage.Stage;

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
    protected ToolBar chatToolBar;

    @FXML
    private Label usernameLabel;

    @FXML
    public void closeAction() {
        Platform.exit();
    }

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

