package com.example.javafxtcpchat.server;


import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

public class MainServer extends Thread {

    // ska skapa upp huvudservern
    public MainServer() throws IOException {


        try (ServerSocket mainSocket = new ServerSocket(11111);
            Socket socket = mainSocket.accept();
            ObjectOutputStream out = new ObjectOutputStream(socket.getOutputStream());
            ObjectInputStream in = new ObjectInputStream(socket.getInputStream())) {

            String[] input = (String[]) in.readObject();
            Database database = new Database(); // skapar databasen (sparas inte i fil nuvarande)
            Protocol protocol = new Protocol(database);

            while ((input = (String[]) in.readObject()) != null) {
                String[] res = protocol.processRequest(input);
                out.writeObject(res);
            }


        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }


    public static void main(String[] args) throws IOException {
        MainServer server = new MainServer();
    }
}
