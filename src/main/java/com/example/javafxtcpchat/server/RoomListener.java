package com.example.javafxtcpchat.server;

import java.net.ServerSocket;
import java.net.Socket;

public class RoomListener {


    private static Database database = new Database();

    public RoomListener() {

        int port = 55555; // temp port

        try (ServerSocket serverSocket = new ServerSocket(port)) {

            database.addRoom(new Room("Room 1", port));

            while (true) {
                Socket socket = serverSocket.accept();
                UserHandler userHandler = new UserHandler(socket, database, port);
                userHandler.start();
                System.out.println("New user connected");
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

    }

    public static void main(String[] args) {
        new RoomListener();
    }
}
