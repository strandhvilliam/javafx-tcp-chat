package com.example.javafxtcpchat.server;

import java.io.IOException;
import java.io.ObjectOutputStream;

public class User {

    private final String username;
    private final ObjectOutputStream output;

    public User(String username, ObjectOutputStream output) {
        this.username = username;
        this.output = output;
    }

    public void sendObject(String[] message) throws IOException {
        output.writeObject(message);
    }

    public String getUsername() {
        return username;
    }

}
