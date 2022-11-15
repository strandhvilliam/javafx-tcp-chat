package com.example.javafxtcpchat.server;

import java.net.ServerSocket;
import java.net.Socket;

public class RoomListener implements Runnable {


    private static Database database = new Database();
    private int port;

    public RoomListener(int port) {
        this.port = port;
        //int port = 55555; temp port
    }

    @Override
    public void run() {
        try (ServerSocket serverSocket = new ServerSocket(port)) {

            System.out.println("lets go");

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
