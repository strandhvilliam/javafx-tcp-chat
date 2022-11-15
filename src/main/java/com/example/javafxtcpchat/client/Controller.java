package com.example.javafxtcpchat.client;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TitledPane;
import javafx.scene.control.ToolBar;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.IOException;

public class Controller {


    @FXML
    protected ToolBar mainToolBar;

    @FXML
    private VBox mainRoomsContainer;

    @FXML
    private void closeAction() {
        Platform.exit();
    }


    @FXML
    void createChatroomAction(ActionEvent event) {
        addRoomToGUI("Room");
    }

    @FXML
    void createUserAction(ActionEvent event) throws IOException {
        openChatWindow(11111);
    }


    private void addRoomToGUI(String roomName) {
        if (mainRoomsContainer.getChildren().size() != 11) {
            System.out.println("Adding room to GUI");
            TitledPane room = new TitledPane();
            ListView users = new ListView();
            room.setText(roomName);
            room.setAnimated(false);
            room.setContent(users);
            mainRoomsContainer.getChildren().add(room);
        }
    }


    private void openChatWindow(int port) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("chatroom.fxml"));
        Stage stage = new Stage(StageStyle.UNDECORATED);
        Scene scene = new Scene(fxmlLoader.load());
        stage.setTitle("Chatroom");

        ChatController controller = fxmlLoader.getController();
        ToolBar t = controller.chatToolBar;
        Delta d = new Delta();
        d.initMovableWindow(stage, scene, t);

        stage.setScene(scene);
        stage.show();
    }


}

//metod för att uppdatera mains lista med användare i rummet

//skapa upp ett rum
    //skapa ny instans av RoomListener med inparametern roomName och port

//för att ansluta till rum
    //skapa en användare som kopplar upp sig som klient till servern
