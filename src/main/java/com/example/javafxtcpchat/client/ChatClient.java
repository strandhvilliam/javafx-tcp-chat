package com.example.javafxtcpchat.client;

import javafx.application.Platform;
import javafx.concurrent.Task;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class ChatClient extends Task{


    private static final String USER_CONNECTED = "USER_CONNECTED";

    private final int port;
    private final String username;
    private PrintWriter out;

    private Socket socket;

    private final ChatController controller;

    public ChatClient(ChatController controller, int port, String username) {
        this.controller = controller;
        this.port = port;
        this.username = username;
    }

    @Override
    protected Void call() throws Exception {
        System.out.println("Connecting to server...");
        socket = new Socket("127.0.0.1", port);
        out = new PrintWriter(socket.getOutputStream(), true);
        BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));

        sendMessage(username);

        String fromServer;
        while ((fromServer = in.readLine()) != null) {
            String finalFromServer = fromServer;
            Platform.runLater(() -> controller.printMessage(finalFromServer));

            if (fromServer.startsWith(USER_CONNECTED)) {

            }
        }
        return null;
    }

    public void sendMessage(String message) {
        out.println(message);
    }

}
