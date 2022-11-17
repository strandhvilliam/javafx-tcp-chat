package com.example.javafxtcpchat.server;

import java.io.*;
import java.net.Socket;
import java.util.List;

public class UserHandler extends Thread {


    private static final String USER_CONNECTED = "USER_CONNECTED";
    private static final String USER_DISCONNECTED = "USER_DISCONNECTED";
    private static final String MESSAGE = "MESSAGE";

    private static final String GET_USERS_REQUEST = "GET_USERS_REQUEST";
    private static final String GET_USERS_RESPONSE = "GET_USERS_RESPONSE";

    public static final String ERROR = "ERROR";

    private Socket socket;

    private Database database;

    private int port;

    public UserHandler(Socket socket, Database database, int port) {
        this.socket = socket;
        this.database = database;
        this.port = port;
    }

    @Override
    public void run() {

        try (ObjectOutputStream out = new ObjectOutputStream(socket.getOutputStream());
             ObjectInputStream in = new ObjectInputStream(socket.getInputStream())) {


            String[] connectionMessage = (String[]) in.readObject();
            String username = connectionMessage[1];

            User user = new User(username, out);
            Room room = database.getRoomByPort(port);
            room.addUser(user);

            for (User u : room.getUsers()) {
                u.sendObject(connectionMessage);
            }

            String[] input;
            while ((input = (String[]) in.readObject()) != null) {

                ChatProtocol chatProtocol = new ChatProtocol();
                String[] response = chatProtocol.processRequest(input, room);
                if (response[0].equals(ERROR) || response[0].equals(GET_USERS_RESPONSE)) {
                    user.sendObject(response);
                } else {
                    for (User u : room.getUsers()) {
                        u.sendObject(response);
                    }
                }

            }


        } catch (IOException | ClassNotFoundException ex) {
            throw new RuntimeException(ex);
        }

    }
}
