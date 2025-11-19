package com.My_Chat_App.serverPackage;

import com.My_Chat_App.serverPackage.resources.InMemoryDataStore;
import org.json.JSONObject;

import com.My_Chat_App.serverPackage.resources.InMemoryDataStore;

import javax.websocket.*;
import javax.websocket.server.ServerEndpoint;
import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;



@ServerEndpoint(value = "/chat")
public class ChatEndpoint {

    private static final Queue<Session> waitingUsers = new ConcurrentLinkedQueue<>();
    private static final Object matchmakingLock = new Object();

    @OnOpen
    public void onOpen(Session session, EndpointConfig config) throws IOException {
        Map<String, List<String>> params = session.getRequestParameterMap();
        String token = params.get("token") != null ? params.get("token").get(0) : null;

        String username = InMemoryDataStore.activeTokens.get(token);

        if (token == null || username == null) {
            System.err.println("Authentication failed: No valid token provided.");
            session.close(new CloseReason(CloseReason.CloseCodes.VIOLATED_POLICY, "Authentication required."));
            return;
        }

        System.out.println("Session opened for user: " + username);
        session.getUserProperties().put("username", username);

        waitingUsers.add(session);
        pairUsers(session);
    }

    private void pairUsers(Session session) throws IOException {
        synchronized (matchmakingLock) {
            if (waitingUsers.size() >= 2) {
                Session user1 = waitingUsers.poll();
                Session user2 = waitingUsers.poll();

                if (user1 != null && user2 != null) {
                    user1.getUserProperties().put("partner", user2);
                    user2.getUserProperties().put("partner", user1);

                    String username1 = (String) user1.getUserProperties().get("username");
                    String username2 = (String) user2.getUserProperties().get("username");

                    sendMessage(user1, createSystemMessage("You are connected with " + username2 + "."));
                    sendMessage(user2, createSystemMessage("You are connected with " + username1 + "."));
                    
                    System.out.println("Paired " + username1 + " with " + username2);
                }
            } else {
                sendMessage(session, createSystemMessage("Waiting for another user to connect..."));
            }
        }
    }

    @OnMessage
    public void onMessage(String message, Session session) throws IOException {
        String senderUsername = (String) session.getUserProperties().get("username");
        Session partner = (Session) session.getUserProperties().get("partner");

        if (partner != null && partner.isOpen()) {
            sendMessage(partner, createChatMessage(senderUsername, message));
            System.out.println("Message from " + senderUsername + " to partner.");
        } else {
            sendMessage(session, createSystemMessage("You are not connected to anyone."));
        }
    }

    @OnClose
    public void onClose(Session session, CloseReason closeReason) throws IOException {
        String username = (String) session.getUserProperties().get("username");
        if (username == null) { // User might not have been authenticated
            System.out.println("Unauthenticated session closed: " + closeReason.getReasonPhrase());
            return;
        }
        System.out.println("Session closed for " + username + ": " + closeReason.getReasonPhrase());

        waitingUsers.remove(session);

        Session partner = (Session) session.getUserProperties().get("partner");
        if (partner != null && partner.isOpen()) {
            sendMessage(partner, createSystemMessage(username + " has disconnected."));
            partner.getUserProperties().remove("partner");
            System.out.println("Notified partner of disconnection.");
        }
    }

    @OnError
    public void onError(Session session, Throwable throwable) {
        String username = (String) session.getUserProperties().get("username");
        System.err.println("Error for session " + (username != null ? username : "UNKNOWN") + ": " + throwable.getMessage());
        throwable.printStackTrace(System.err);

        try {
            session.close(new CloseReason(CloseReason.CloseCodes.UNEXPECTED_CONDITION, "An error occurred."));
        } catch (IOException e) {
            System.err.println("Failed to close session on error: " + e.getMessage());
        }
    }

    // --- Helper Methods ---

    private String createChatMessage(String nickname, String message) {
        return new JSONObject().put("type", "chat").put("nickname", nickname).put("message", message).toString();
    }

    private String createSystemMessage(String message) {
        return new JSONObject().put("type", "system").put("message", message).toString();
    }

    private void sendMessage(Session session, String message) throws IOException {
        if (session.isOpen()) {
            session.getBasicRemote().sendText(message);
        }
    }
}
