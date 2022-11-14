package com.example.javafxtcpchat.server;

import java.net.ServerSocket;
import java.net.Socket;

public class RoomListener {


    private static Database database = new Database();

    public RoomListener() {

        try (ServerSocket serverSocket = new ServerSocket(55555)) {
            while (true) {
                Socket socket = serverSocket.accept();
                UserHandler userHandler = new UserHandler(socket, database);
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
