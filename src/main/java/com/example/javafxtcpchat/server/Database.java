package com.example.javafxtcpchat.server;


import java.util.ArrayList;
import java.util.List;

//bygg om till databas med static som är en lista med alla users och rooms
public class Database {

    private static List<User> users = new ArrayList<>();

    public void addUser(User user) {
        users.add(user);
    }

    public List<User> getUsers() {
        return users;
    }
}
