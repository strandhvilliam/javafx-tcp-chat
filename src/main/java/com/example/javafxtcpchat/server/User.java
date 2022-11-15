package com.example.javafxtcpchat.server;

import java.io.OutputStream;
import java.io.PrintWriter;

public class User {

    private String username;
    private PrintWriter writer;

    public User(String username, PrintWriter writer) {
        this.writer = writer;
    }

    public void printMessage(String message) {
        writer.println(message);
    }

    public String getUsername() {
        return username;
    }

}
