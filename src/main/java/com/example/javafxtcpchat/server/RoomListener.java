package com.example.javafxtcpchat.server;

import java.net.ServerSocket;
import java.net.Socket;

public class RoomListener implements Runnable {


    private static Database database = new Database();
    private int port;

    public RoomListener(int port) {
        this.port = port;
    }

    @Override
    public void run() {
        try (ServerSocket serverSocket = new ServerSocket(port)) {

            while (true) {
                Socket socket = serverSocket.accept();
                UserHandler userHandler = new UserHandler(socket, database, port);
                userHandler.start();
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
