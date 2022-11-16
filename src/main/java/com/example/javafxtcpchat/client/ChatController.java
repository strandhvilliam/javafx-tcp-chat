package com.example.javafxtcpchat.client;


import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.concurrent.Task;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.text.Font;
import javafx.scene.text.TextAlignment;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class ChatController implements Initializable {

    @FXML
    private TextArea chatTextArea;

    @FXML
    private TextField inputTextField;

    @FXML
    private ListView<HBox> roomUsersList;

    @FXML
    protected ToolBar chatToolBar;

    @FXML
    private Label usernameLabel;

    @FXML
    public void closeAction() {
        //send request to disconnect user from room
        chatToolBar.getScene().getWindow().hide();
    }

    @FXML
    void sendAction(ActionEvent event) {
        String message = inputTextField.getText();
        inputTextField.clear();
        try {
            chatClient.sendMessage(message);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @FXML
    public void updateUserList(String[] users) {
        roomUsersList.getItems().clear();


        List<HBox> hBoxList = new ArrayList<>();
        for (String user : users) {
            HBox hBox = new HBox();
            hBox.setAlignment(Pos.CENTER_LEFT);
            hBox.getStylesheets().add(getClass().getResource("styles.css").toExternalForm());
            hBox.getStyleClass().add("user-list-item");

            String letter = user.substring(0, 1).toUpperCase();
            Label circle = new Label();
            circle.setText(letter);
            circle.setAlignment(Pos.CENTER);
            circle.getStyleClass().add("circle-label");
            hBox.getChildren().add(circle);

            Label label = new Label(user);
            label.setAlignment(Pos.CENTER_LEFT);
            label.getStyleClass().add("user-list-label");
            hBox.getChildren().add(label);
            hBoxList.add(hBox);
        }

        ObservableList<HBox> items = FXCollections.observableArrayList(hBoxList);

        roomUsersList.setItems(items);

        roomUsersList.setCellFactory(param -> new ListCell<>() {
            @Override
            protected void updateItem(HBox item, boolean empty) {
                super.updateItem(item, empty);
                if (!empty || item != null) {
                    setStyle("-fx-background-color: #ffffff; -fx-border-color: #000000 ");
                    setGraphic(item);
                } else {
                    setStyle("-fx-background-color: transparent; -fx-border-color: transparent ");
                    setGraphic(null);
                }
            }
        });

    }

    @FXML
    public void removeUserFromList(String user) {
        roomUsersList.getItems().remove(user);
    }

    private ChatClient chatClient;


    public void printMessage(String message) {
        chatTextArea.appendText(message + "\n");
    }

    public void initData(int port, String username) {
        chatClient = new ChatClient(this, port, username);
        Thread th = new Thread(chatClient);
        th.setDaemon(true);
        th.start();
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {


    }


}

