package com.example.javafxtcpchat.client;

import com.example.javafxtcpchat.server.RoomListener;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class Controller implements Initializable {




    @FXML
    protected ToolBar mainToolBar;

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
            mainClient.requestJoinRoom(joinRoomTextField.getText());
        } catch (IOException e) {
            e.printStackTrace();
        }

    }


    public void addRoomToGUI(String roomName, String strPort) {
        if (mainRoomsContainer.getChildren().size() != 11) {
            System.out.println("Adding room to GUI");
            TitledPane room = new TitledPane();
            ListView<String> users = new ListView<>();
            room.setText(roomName + " - " + strPort);
            room.setAnimated(false);
            room.setContent(users);
            mainRoomsContainer.getChildren().add(room);
        } else {
            System.out.println("Max rooms reached");
        }
        chatRoomNameTextField.clear();
        chatRoomPortTextField.clear();
    }


    public void openRoomGUI(int port, String roomName){
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
        ToolBar t = controller.chatToolBar;
        Delta d = new Delta();
        d.initMovableWindow(stage, scene, t);

        String username = usernameTextField.getText();
        controller.initData(port, username);

        usernameTextField.clear();
        joinRoomTextField.clear();

        stage.setScene(scene);
        stage.show();
    }


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        mainClient = new MainClient(this, 11111);
        Thread th = new Thread(mainClient);
        th.setDaemon(true);
        th.start();
    }
}

//metod för att uppdatera mains lista med användare i rummet

//skapa upp ett rum
    //skapa ny instans av RoomListener med inparametern roomName och port

//för att ansluta till rum
    //skapa en användare som kopplar upp sig som klient till servern
