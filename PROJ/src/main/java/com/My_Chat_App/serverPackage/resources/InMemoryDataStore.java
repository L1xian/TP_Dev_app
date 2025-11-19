package com.My_Chat_App.serverPackage.resources;

import com.My_Chat_App.userPackage.User;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class InMemoryDataStore {

    // A map to store users, with the username as the key.
    public static final Map<String, User> users = new ConcurrentHashMap<>();

    // A map to store active session tokens, with the token as the key and username as the value.
    public static final Map<String, String> activeTokens = new ConcurrentHashMap<>();
}
