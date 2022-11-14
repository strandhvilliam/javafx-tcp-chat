package com.example.javafxtcpchat.server;


import java.util.ArrayList;
import java.util.List;

public class Database {

    private static List<Room> rooms = new ArrayList<>();

    public void addRoom(Room room) {
        rooms.add(room);
    }

    public List<User> getAllUsers() {
        List<User> allUsers = new ArrayList<>();
        for (Room r : rooms) {
            allUsers.addAll(r.getUsers());
        }
        return allUsers;
    }

    public List<Room> getAllRooms() {
        return rooms;
    }

    public Room getRoomByName(String roomName) {
        for (Room r : rooms) {
            if (r.getRoomName().equals(roomName)) {
                return r;
            }
        }
        return null;
    }

    public Room getRoomByPort(int port) {
        for (Room r : rooms) {
            if (r.getPort() == port) {
                return r;
            }
        }
        return null;
    }
}
