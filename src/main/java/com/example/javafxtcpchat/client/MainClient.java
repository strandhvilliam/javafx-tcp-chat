package com.example.javafxtcpchat.client;

import javafx.application.Platform;
import javafx.concurrent.Task;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;

public class MainClient extends Task {

    public static final String INITIAL_REQUEST = "INITIAL_REQUEST";
    public static final String CREATE_ROOM_REQUEST = "CREATE_ROOM_REQUEST";
    public static final String GET_USERS_REQUEST = "GET_USERS_REQUEST";
    public static final String JOIN_ROOM_REQUEST = "JOIN_ROOM_REQUEST";

    public static final String INITIAL_RESPONSE = "INITIAL_RESPONSE";
    public static final String CREATE_ROOM_RESPONSE = "CREATE_ROOM_RESPONSE";
    public static final String JOIN_ROOM_RESPONSE = "JOIN_ROOM_RESPONSE";

    public static final String SUCCESS = "SUCCESS";
    public static final String ERROR = "ERROR";

    private Controller controller;
    private int port;
    private Socket socket;
    private ObjectOutputStream out;

    public MainClient(Controller controller, int port) {
        this.controller = controller;
        this.port = port;
    }

    @Override
    protected Void call() throws Exception {
        socket = new Socket("127.0.0.1", port);
        out = new ObjectOutputStream(socket.getOutputStream());
        ObjectInputStream in = new ObjectInputStream(socket.getInputStream());

        String[] res;

        while ((res = (String[]) in.readObject()) != null) {
            processResponse(res);
        }
        return null;
    }

    public void sendObject(String[] object) throws IOException {
        out.writeObject(object);
    }

    public void requestJoinRoom(String roomPort) throws IOException {
        String[] req = {JOIN_ROOM_REQUEST, roomPort};
        sendObject(req);
    }

    public void requestInitial() throws IOException {
        String[] request = {INITIAL_REQUEST};
        sendObject(request);
    }

    public void requestCreateRoom(String roomName, int roomPort) throws IOException {
        String[] request = {CREATE_ROOM_REQUEST, roomName, String.valueOf(roomPort)};
        sendObject(request);
    }

    public void requestUsersInRoom(String roomPort) throws IOException {
        String[] request = {GET_USERS_REQUEST, roomPort};
        sendObject(request);
    }

    public void processResponse(String[] res) {
        if (res[0].equals(CREATE_ROOM_RESPONSE)) {
            if (res[1].equals(SUCCESS)) {
                Platform.runLater(() -> controller.addRoomToGUI(res[2], res[3]));
            } else {
                System.out.println("Error: " + (res[2]));
            }
        } else if (res[0].equals(JOIN_ROOM_RESPONSE)) {
            if (res[1].equals(SUCCESS)) {
                Platform.runLater(() -> controller.openRoomGUI(Integer.parseInt(res[2]), res[3]));
            } else {
                System.out.println("Error: " + (res[2]));
            }
        }
    }

}
