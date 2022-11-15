package com.example.javafxtcpchat.server;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class UserHandler extends Thread {

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

        try (PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
             BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()))) {

            //sätt in.readLine() för att läsa av klientens inmatade namn från textfielden
            //skapa sen user med namnet och out

            String username = in.readLine();

            User user = new User(username, out);
            Room room = database.getRoomByPort(port);
            room.addUser(user);

            while (true) {
                String input = in.readLine();
                if (input == null) {
                    return;
                }
                for (User u : room.getUsers()) {
                    u.printMessage(input);
                }
            }


        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }
}
