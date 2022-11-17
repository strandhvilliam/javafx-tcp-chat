package com.example.javafxtcpchat.server;


import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketException;

public class MainServer extends Thread {
    public MainServer() throws IOException {


        try (ServerSocket mainSocket = new ServerSocket(11111);
            Socket socket = mainSocket.accept();
            ObjectOutputStream out = new ObjectOutputStream(socket.getOutputStream());
            ObjectInputStream in = new ObjectInputStream(socket.getInputStream())) {

            String[] input;
            Database database = new Database(); // skapar databasen (sparas inte i fil nuvarande)
            MainProtocol mainProtocol = new MainProtocol(database);

            while ((input = (String[]) in.readObject()) != null) {
                String[] res = mainProtocol.processRequest(input);
                System.out.println(res[0]);
                out.reset();
                out.writeObject(res);
            }


        } catch (SocketException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }


    public static void main(String[] args) throws IOException {
        MainServer server = new MainServer();
    }
}
