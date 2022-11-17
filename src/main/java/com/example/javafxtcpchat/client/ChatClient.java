package com.example.javafxtcpchat.client;

import javafx.application.Platform;
import javafx.concurrent.Task;

import java.io.*;
import java.net.Socket;
import java.util.Arrays;

public class ChatClient extends Task {


    private static final String USER_CONNECTED = "USER_CONNECTED";
    private static final String USER_DISCONNECTED = "USER_DISCONNECTED";
    private static final String GET_USERS_REQUEST = "GET_USERS_REQUEST";
    private static final String GET_USERS_RESPONSE = "GET_USERS_RESPONSE";
    private static final String MESSAGE = "MESSAGE";

    private final int port;
    private final String username;
    private ObjectOutputStream out;

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
        out = new ObjectOutputStream(socket.getOutputStream());
        ObjectInputStream in = new ObjectInputStream((socket.getInputStream()));

        String[] connectRequest = {USER_CONNECTED, username};
        out.writeObject(connectRequest);

        String[] fromServer;

        while ((fromServer = (String[]) in.readObject()) != null) {

            if (fromServer[0].equals(MESSAGE)) {
                String senderName = fromServer[1];
                String message = fromServer[2];
                Platform.runLater(() -> controller.printMessage(senderName, message));

            } else if (fromServer[0].equals(USER_CONNECTED)) {
                String connectedUser = fromServer[1];
                Platform.runLater(() -> controller.printInformation("User " + connectedUser + " connected"));
                requestUserList();

            } else if (fromServer[0].equals(USER_DISCONNECTED)) {
                String disconnectedUser = fromServer[1];
                Platform.runLater(() -> controller.printInformation("User " + disconnectedUser + " disconnected"));
                requestUserList();

            } else if (fromServer[0].equals(GET_USERS_RESPONSE)) {
                System.out.println(fromServer[1]);
                String[] users = Arrays.copyOfRange(fromServer, 1, fromServer.length);
                Platform.runLater(() -> controller.updateUserList(users));
            }

        }
        return null;
    }

    private void requestUserList() {
        String[] request = {GET_USERS_REQUEST};
        try {
            out.writeObject(request);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void sendMessage(String message) throws IOException {
        String[] toServer = {MESSAGE, username, message};
        out.writeObject(toServer);
    }

}
