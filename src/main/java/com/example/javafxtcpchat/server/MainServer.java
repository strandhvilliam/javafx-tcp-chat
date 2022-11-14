package com.example.javafxtcpchat.server;


import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class MainServer extends Thread {

    // ska skapa upp huvudservern
    public MainServer() throws IOException {


        try (ServerSocket mainSocket = new ServerSocket(11111)) {

            Socket socket = mainSocket.accept();


        }
    }


    public static void main(String[] args) throws IOException {
        MainServer server = new MainServer();
    }
}
