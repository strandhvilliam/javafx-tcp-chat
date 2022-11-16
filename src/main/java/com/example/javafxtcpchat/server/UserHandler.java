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
                if (input[0].equals(MESSAGE)) {
                    for (User u : room.getUsers()) {
                        u.sendObject(input);
                    }
                } else if (input[0].equals(USER_CONNECTED)) {
                    for (User u : room.getUsers()) {
                        u.sendObject(input);
                    }
                } else if (input[0].equals(USER_DISCONNECTED)) {

                    for (User u : room.getUsers()) {
                        u.sendObject(input);
                    }
                } else if (input[0].equals(GET_USERS_REQUEST)) {
                    List<User> userList = room.getUsers();
                    String[] users = new String[userList.size() + 1];
                    for (int i = 0; i < userList.size(); i++) {
                        users[i+1] = userList.get(i).getUsername();
                        System.out.println(userList.get(i).getUsername());
                    }
                    users[0] = GET_USERS_RESPONSE;
                    user.sendObject(users);

                }

                }
            } catch (IOException | ClassNotFoundException ex) {
            throw new RuntimeException(ex);
        }

    }
}
