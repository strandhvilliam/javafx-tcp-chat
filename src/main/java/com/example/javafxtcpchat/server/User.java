package com.example.javafxtcpchat.server;

import java.io.OutputStream;
import java.io.PrintWriter;

public class User {

    private PrintWriter writer;

    public User(PrintWriter writer) {
        this.writer = writer;
    }

    public void printMessage(String message) {
        writer.println(message);
    }

}
