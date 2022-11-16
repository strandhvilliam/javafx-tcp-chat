package com.example.javafxtcpchat.server;

import java.util.List;

public class Protocol {

    // Protocol REQUESTS
    // ----------------
    // INITIAL_REQUEST
    // CREATE_ROOM_REQUEST
    // JOIN_ROOM_REQUEST


    // Protocol RESPONSES
    // -----------------
    // INITIAL_RESPONSE
    // CREATE_ROOM_RESPONSE
    // JOIN_ROOM_RESPONSE

    public static final String INITIAL_REQUEST = "INITIAL_REQUEST";
    public static final String CREATE_ROOM_REQUEST = "CREATE_ROOM_REQUEST";
    public static final String GET_ROOMS_REQUEST = "GET_ROOMS_REQUEST";
    public static final String GET_USERS_REQUEST = "GET_USERS_REQUEST";
    public static final String JOIN_ROOM_REQUEST = "JOIN_ROOM_REQUEST";
    public static final String INITIAL_RESPONSE = "INITIAL_RESPONSE";

    public static final String CREATE_ROOM_RESPONSE = "CREATE_ROOM_RESPONSE";
    public static final String GET_ROOMS_RESPONSE = "GET_ROOMS_RESPONSE";
    public static final String GET_USERS_RESPONSE = "GET_USERS_RESPONSE";
    public static final String JOIN_ROOM_RESPONSE = "JOIN_ROOM_RESPONSE";
    public static final String SUCCESS = "SUCCESS";
    public static final String ERROR = "ERROR";

    public static Database database;

    public Protocol(Database database) {
        this.database = database;
    }

    public String[] processRequest(String[] req) {
        String[] res;
        if (req[0].equals(CREATE_ROOM_REQUEST)) {
            res = initRoomListener(req[1], Integer.parseInt(req[2]));
        } else if (req[0].equals(JOIN_ROOM_REQUEST)) {
            res = joinRoom(req[1]);
        } else if (req[0].equals(GET_ROOMS_REQUEST)) {
            res = getRooms();
        } else if (req[0].equals(GET_USERS_REQUEST)) {
            res = getUsers(req[1]);
        } else {
            res = new String[]{ERROR};
        }
        return res;
    }

    private String[] joinRoom(String roomPort) {
        String[] res;
        try {

            int port = Integer.parseInt(roomPort);
            Room room = database.getRoomByPort(port);

            if (room != null) {
                res = new String[]{JOIN_ROOM_RESPONSE, SUCCESS, String.valueOf(room.getPort()), room.getRoomName()};
            } else {
                res = new String[]{JOIN_ROOM_RESPONSE, ERROR, ">>>Room not found"};
            }
            return res;
        } catch (NumberFormatException e) {
            res = new String[]{JOIN_ROOM_RESPONSE, ERROR, ">>>Invalid port number"};
            return res;
        }

    }

    private String[] getUsers(String roomPort) {
        try {
            int port = Integer.parseInt(roomPort);
            Room room = database.getRoomByPort(port);
            if (room != null) {
                List<User> users = room.getUsers();
                String[] res = new String[users.size() + 2];
                res[0] = GET_USERS_RESPONSE;
                res[1] = SUCCESS;
                for (int i = 2; i < users.size(); i++) {
                    res[i] = users.get(i).getUsername();
                }
                return res;
            } else {
                return new String[]{GET_USERS_RESPONSE, ERROR, ">>>Room not found"};
            }
        } catch (NumberFormatException e) {
            return new String[]{GET_USERS_RESPONSE, ERROR, ">>>Invalid port number"};
        }
    }


    private String[] getRooms() {
        List<Room> rooms = database.getAllRooms();
        String[] res = new String[rooms.size() + 2];
        res[0] = GET_ROOMS_RESPONSE;
        res[1] = SUCCESS;
        for (int i = 2; i < rooms.size(); i++) {
            res[i] = rooms.get(i).getRoomName();
        }

        return res;
    }

    private String[] initRoomListener(String name, int port) {
        String[] res;
        if (database.getRoomByPort(port) != null
                || database.getRoomByName(name) != null) {
            res = new String[]{CREATE_ROOM_RESPONSE, ERROR, ">>> Room already exists"};
        } else {
            Room room = new Room(name, port);
            database.addRoom(room);
            new Thread(new RoomListener(port)).start();
            res = new String[]{CREATE_ROOM_RESPONSE, SUCCESS, String.valueOf(port), name};

        }
        return res;

    }


}
