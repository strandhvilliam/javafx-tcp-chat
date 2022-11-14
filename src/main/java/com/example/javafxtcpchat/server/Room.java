package com.example.javafxtcpchat.server;

import java.util.ArrayList;
import java.util.List;

public class Room {

    private final String roomName;

    private List<User> users;

    public Room(String roomName) {
        this.roomName = roomName;
        this.users = new ArrayList<>();
    }

    public void addUser(User user) {
        users.add(user);
    }

    public void removeUser(User user) {
        users.remove(user);
    }

    public List<User> getUsers() {
        return users;
    }

    public String getRoomName() {
        return roomName;
    }


}
