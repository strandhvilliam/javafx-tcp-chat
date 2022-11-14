package com.example.javafxtcpchat.client;

import javafx.application.Platform;
import javafx.concurrent.Task;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class ChatClient extends Task{


    private Socket socket;
    private PrintWriter out;

    private final ChatController controller;

    public ChatClient(ChatController controller) {
        this.controller = controller;
    }

    @Override
    protected Void call() throws Exception {
        socket = new Socket("127.0.0.1", 55555);
        out = new PrintWriter(socket.getOutputStream(), true);
        BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));

        String fromServer;

        while ((fromServer = in.readLine()) != null) {
            String finalFromServer = fromServer;
            Platform.runLater(() -> controller.printMessage(finalFromServer));
        }
        return null;
    }

    public void sendMessage(String message) {
        out.println(message);
    }

}
