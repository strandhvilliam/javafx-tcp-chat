package com.example.javafxtcpchat.client;


import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.IOException;
import java.net.URL;
import java.util.HashMap;
import java.util.ResourceBundle;

public class Controller implements Initializable {


    @FXML
    protected HBox mainTopContainer;

    @FXML
    private VBox mainRoomsContainer;

    @FXML
    private TextField usernameTextField;
    @FXML
    private TextField joinRoomTextField;

    @FXML
    private TextField chatRoomNameTextField;
    @FXML
    private TextField chatRoomPortTextField;

    private MainClient mainClient;

    @FXML
    private void closeAction() {
        Platform.exit();
    }

    @FXML
    public void maximizeAction() {
        Stage stage = (Stage) mainTopContainer.getScene().getWindow();
        stage.setMaximized(!stage.isMaximized());
    }

    @FXML
    public void minimizeAction() {
        Stage stage = (Stage) mainTopContainer.getScene().getWindow();
        stage.setIconified(true);
    }


    @FXML
    public void createChatroomAction() {
        try {
            mainClient.requestCreateRoom(chatRoomNameTextField.getText(), Integer.parseInt(chatRoomPortTextField.getText()));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    public void joinRoomAction() {
        try {
            String strPort = joinRoomTextField.getText();
            mainClient.requestJoinRoom(strPort);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    private HashMap<String, TitledPane> openRoomsMap = new HashMap<>();


    public void addRoomToGUI(String roomName, String strPort) {
        if (mainRoomsContainer.getChildren().size() != 11) {
            System.out.println("Adding room to GUI");
            TitledPane room = new TitledPane();
            BorderPane borderPane = new BorderPane();
            Label roomNameLabel = new Label(roomName);
            Label roomPortLabel = new Label(strPort);
            roomNameLabel.setStyle("-fx-text-fill: #000000");
            roomPortLabel.setStyle("-fx-text-fill: #000000");
            borderPane.setLeft(roomNameLabel);
            borderPane.setRight(roomPortLabel);
            borderPane.prefWidthProperty().bind(room.widthProperty().subtract(40));
            room.setGraphic(borderPane);
            room.setAnimated(false);

            openRoomsMap.put(strPort, room);

            mainRoomsContainer.getChildren().add(room);

            requestRoomUsers(strPort);

        } else {
            System.out.println("Max rooms reached");
        }
        chatRoomNameTextField.clear();
        chatRoomPortTextField.clear();
    }

    public void updateRoomUsers(String strPort, String[] users) {

        //openRoomsMap.get(strPort).setContent(null);
        //openRoomsMap.get(strPort).setContent(roomUsersList);


        for (int i = 0; i < mainRoomsContainer.getChildren().size(); i++) {
            if (mainRoomsContainer.getChildren().get(i).equals(openRoomsMap.get(strPort))) {
                ListView<String> roomUsersList = new ListView<>();
                roomUsersList.getItems().addAll(users);
                roomUsersList.getStyleClass().add("room-list-view");

                openRoomsMap.get(strPort).setContent(roomUsersList);
                //mainRoomsContainer.getChildren().add(i, openRoomsMap.get(strPort));

                roomUsersList.setCellFactory(param -> new ListCell<String>() {
                    private ImageView imageView = new ImageView();

                    @Override
                    protected void updateItem(String name, boolean empty) {
                        super.updateItem(name, empty);

                        if (empty) {
                            setStyle("-fx-background-color: transparent; -fx-border-color: transparent;");
                            setText(null);
                            setGraphic(null);
                        } else {
                            setStyle("-fx-background-color: #90a8ed; -fx-border-color: #000000");
                            setText(name);
                            setGraphic(imageView);
                        }
                    }
                });
            }

        }

    }


    public void openRoomGUI(String strPort, String roomName) {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("chatroom.fxml"));
        Stage stage = new Stage(StageStyle.UNDECORATED);
        Scene scene;
        try {
            scene = new Scene(fxmlLoader.load());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        stage.setTitle("Chatroom: " + roomName);

        ChatController controller = fxmlLoader.getController();
        HBox topContainer = controller.chatTopContainer;
        Delta d = new Delta();
        d.initMovableWindow(stage, scene, topContainer);

        String username = usernameTextField.getText();
        controller.initData(strPort, username, roomName, this);

        usernameTextField.clear();
        joinRoomTextField.clear();

        Platform.runLater(() -> {
            try {
                mainClient.requestUsersInRoom(strPort);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        });

        stage.setScene(scene);
        stage.show();
    }

    public void requestRoomUsers(String strPort) {
        try {
            mainClient.requestUsersInRoom(strPort);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        mainClient = new MainClient(this, 11111);
        Thread th = new Thread(mainClient);
        th.setDaemon(true);
        th.start();
    }
}

