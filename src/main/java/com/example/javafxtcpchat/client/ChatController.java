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
        chatToolBar.getScene().getWindow().hide();
    }

    @FXML
    void sendAction(ActionEvent event) {
        String message = inputTextField.getText();
        inputTextField.clear();
        chatClient.sendMessage(message);
    }

    private ChatClient chatClient;

    private int port;
    private String username;


    public void printMessage(String message) {
        chatTextArea.appendText(message + "\n");
    }

    public void initData(int port, String username) {
        this.port = port;
        this.username = username;
        chatClient = new ChatClient(this, port, username);
        Thread th = new Thread(chatClient);
        th.setDaemon(true);
        th.start();
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {


    }


}

