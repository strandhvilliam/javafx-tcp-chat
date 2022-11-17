package com.example.javafxtcpchat.server;

import java.util.ArrayList;
import java.util.List;

public class Room {

    private final String roomName;
    private final int port;

    private List<User> users;

    public Room(String roomName, int port) {
        this.roomName = roomName;
        this.port = port;
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

    public int getPort() {
        return port;
    }


    public User getUserByName(String name) {
        for (User u : users) {
            if (u.getUsername().equals(name)) {
                return u;
            }
        }
        return null;
    }
}
