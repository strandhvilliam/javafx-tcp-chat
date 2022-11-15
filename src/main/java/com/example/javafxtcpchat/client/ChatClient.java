package com.example.javafxtcpchat.client;

import javafx.application.Platform;
import javafx.concurrent.Task;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class ChatClient extends Task{


    public static final String INITIAL_REQUEST = "INITIAL_REQUEST";
    public static final String CREATE_ROOM_REQUEST = "CREATE_ROOM_REQUEST";
    public static final String JOIN_ROOM_REQUEST = "JOIN_ROOM_REQUEST";

    public static final String INITIAL_RESPONSE = "INITIAL_RESPONSE";
    public static final String CREATE_ROOM_RESPONSE = "CREATE_ROOM_RESPONSE";
    public static final String JOIN_ROOM_RESPONSE = "JOIN_ROOM_RESPONSE";

    public static final String SUCCESS_RESPONSE = "SUCCESS_RESPONSE";
    public static final String ERROR_RESPONSE = "ERROR_RESPONSE";

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
        socket = new Socket("127.0.0.1", port);
        out = new PrintWriter(socket.getOutputStream(), true);
        BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));

        out.println(username);

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
