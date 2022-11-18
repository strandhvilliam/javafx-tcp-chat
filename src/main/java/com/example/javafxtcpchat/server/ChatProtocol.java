package com.example.javafxtcpchat.server;

import java.util.Arrays;
import java.util.List;

public class ChatProtocol {

    private static final String USER_CONNECTED = "USER_CONNECTED";
    private static final String USER_DISCONNECTED = "USER_DISCONNECTED";
    private static final String MESSAGE = "MESSAGE";
    private static final String GET_USERS_REQUEST = "GET_USERS_REQUEST";
    private static final String GET_USERS_RESPONSE = "GET_USERS_RESPONSE";

    public static final String ERROR = "ERROR";


    public String[] processRequest(String[] input, Room room) {
        String[] response;
        if (input[0].equals(MESSAGE)) {
            response = Arrays.copyOf(input, input.length);

        } else if (input[0].equals(USER_CONNECTED)) {
            response = Arrays.copyOf(input, input.length);

        } else if (input[0].equals(USER_DISCONNECTED)) {

            User disconnectedUser = room.getUserByName(input[1]);
            if (disconnectedUser != null) {
                room.removeUser(disconnectedUser);
                response = new String[]{USER_DISCONNECTED, input[1]};
            } else {
                response = new String[]{ERROR, "User " + input[1] + " not found"};
            }

        } else if (input[0].equals(GET_USERS_REQUEST)) {
            List<User> userList = room.getUsers();
            response = new String[userList.size() + 1];
            for (int i = 0; i < userList.size(); i++) {
                response[i+1] = userList.get(i).getUsername();
            }
            response[0] = GET_USERS_RESPONSE;
        } else {
            response = new String[]{ERROR, "Unknown request"};
        }
        return response;
    }


}
