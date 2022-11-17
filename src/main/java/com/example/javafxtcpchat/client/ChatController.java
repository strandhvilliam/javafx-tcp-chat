package com.example.javafxtcpchat.client;



import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.control.*;

import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.util.converter.LocalDateStringConverter;


import java.io.IOException;
import java.net.URL;
import java.time.LocalTime;
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
    private ListView<GridPane> chatListView;

    @FXML
    protected HBox chatTopContainer;

    @FXML
    private Label usernameLabel;

    @FXML
    public void closeAction() {
        chatClient.requestDisconnectUser();
        chatTopContainer.getScene().getWindow().hide();
    }

    @FXML
    public void maximizeAction() {
        Stage stage = (Stage) chatTopContainer.getScene().getWindow();
        stage.setMaximized(!stage.isMaximized());
    }

    @FXML
    public void minimizeAction() {
        Stage stage = (Stage) chatTopContainer.getScene().getWindow();
        stage.setIconified(true);
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


    public void printInformation(String message) {
        GridPane gridPane = new GridPane();
        gridPane.setAlignment(Pos.CENTER);
        VBox vBox = new VBox();
        vBox.setFillWidth(true);
        Label label = new Label(message);
        label.setStyle("-fx-text-fill: #313131");
        vBox.getChildren().add(label);
        label.setAlignment(Pos.CENTER);
        gridPane.add(vBox, 0, 0);
        chatListView.getItems().add(gridPane);
        chatListView.scrollTo(chatListView.getItems().size() - 1);
    }

    public void printMessage(String username, String message) {

        boolean isUser = username.equals(usernameLabel.getText());

        GridPane messagePane = new MessageBubble(message,
                username, isUser, chatListView.getWidth());

        chatListView.getItems().add(messagePane);
        chatListView.scrollTo(chatListView.getItems().size() - 1);

    }

    public void initData(int port, String username) {
        chatClient = new ChatClient(this, port, username);
        Thread th = new Thread(chatClient);
        th.setDaemon(true);
        th.start();

        usernameLabel.setText(username);
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {


    }


}

