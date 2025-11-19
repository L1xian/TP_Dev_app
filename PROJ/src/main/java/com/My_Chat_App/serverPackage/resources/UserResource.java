package com.My_Chat_App.serverPackage.resources;

import com.My_Chat_App.userPackage.User;
import com.My_Chat_App.serverPackage.resources.security.PasswordUtil;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.Map;
import java.util.UUID;

@Path("/users")
public class UserResource {

    @POST
    @Path("/register")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response registerUser(Map<String, String> credentials) {
        String username = credentials.get("username");
        String password = credentials.get("password");

        if (username == null || username.trim().isEmpty() || password == null || password.isEmpty()) {
            return Response.status(Response.Status.BAD_REQUEST).entity("{\"error\":\"Username and password are required.\"}").build();
        }

        if (InMemoryDataStore.users.containsKey(username)) {
            return Response.status(Response.Status.CONFLICT).entity("{\"error\":\"Username already exists.\"}").build();
        }

        String passwordHash = PasswordUtil.hashPassword(password);
        User newUser = new User(username, passwordHash);
        InMemoryDataStore.users.put(username, newUser);

        System.out.println("User registered: " + username);
        return Response.status(Response.Status.CREATED).entity("{\"message\":\"User registered successfully.\"}").build();
    }

    @POST
    @Path("/login")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response loginUser(Map<String, String> credentials) {
        String username = credentials.get("username");
        String password = credentials.get("password");

        User user = InMemoryDataStore.users.get(username);
        if (user == null || !PasswordUtil.verifyPassword(password, user.getPasswordHash())) {
            return Response.status(Response.Status.UNAUTHORIZED).entity("{\"error\":\"Invalid username or password.\"}").build();
        }

        String token = UUID.randomUUID().toString();
        InMemoryDataStore.activeTokens.put(token, username);

        System.out.println("User logged in: " + username);
        return Response.ok("{\"token\":\"" + token + "\"}").build();
    }
}
