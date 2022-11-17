package com.example.javafxtcpchat.client;

import javafx.application.Application;

import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;

import javafx.scene.control.ToolBar;

import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.IOException;
import java.util.concurrent.atomic.AtomicReference;

public class Main extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("main.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        stage.initStyle(StageStyle.UNDECORATED);
        stage.setTitle("Chat!");

        Controller controller = fxmlLoader.getController();
        HBox topBar = controller.mainTopContainer;
        Delta d = new Delta();
        d.initMovableWindow(stage, scene, topBar);

        stage.setScene(scene);


        stage.show();
    }



    public static void main(String[] args) {
        launch();
    }


}