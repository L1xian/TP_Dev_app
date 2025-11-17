package com.example.chatapp;

import org.json.JSONObject;

import javax.websocket.*;
import javax.websocket.server.ServerEndpoint;
import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;

@ServerEndpoint(value = "/chat")
public class ChatEndpoint {

    // A thread-safe queue for users waiting to be paired.
    private static final Queue<Session> waitingUsers = new ConcurrentLinkedQueue<>();
    // A lock object to synchronize access to the matchmaking logic.
    private static final Object matchmakingLock = new Object();

    @OnOpen
    public void onOpen(Session session, EndpointConfig config) throws IOException {
        System.out.println("New session opened: " + session.getId());

        // Extract nickname from the query string.
        Map<String, List<String>> params = session.getRequestParameterMap();
        String nickname = "Anonymous"; // Default nickname
        if (params.containsKey("nickname") && params.get("nickname") != null && !params.get("nickname").isEmpty()) {
            nickname = params.get("nickname").get(0);
        }
        
        session.getUserProperties().put("nickname", nickname);
        System.out.println("Session " + session.getId() + " is now known as " + nickname);

        // Add the user to the waiting queue and attempt to pair them.
        waitingUsers.add(session);
        pairUsers(session);
    }

    private void pairUsers(Session session) throws IOException {
        // Synchronize the matchmaking logic to prevent race conditions.
        synchronized (matchmakingLock) {
            if (waitingUsers.size() >= 2) {
                Session user1 = waitingUsers.poll();
                Session user2 = waitingUsers.poll();

                if (user1 != null && user2 != null) {
                    // Pair the users by storing a reference to each other's session.
                    user1.getUserProperties().put("partner", user2);
                    user2.getUserProperties().put("partner", user1);

                    String nickname1 = (String) user1.getUserProperties().get("nickname");
                    String nickname2 = (String) user2.getUserProperties().get("nickname");

                    // Notify both users that they have been connected.
                    sendMessage(user1, createSystemMessage("You are connected with " + nickname2 + "."));
                    sendMessage(user2, createSystemMessage("You are connected with " + nickname1 + "."));
                    
                    System.out.println("Paired " + nickname1 + " with " + nickname2);
                }
            } else {
                // If no pair was found, notify the user that they are waiting.
                sendMessage(session, createSystemMessage("Waiting for another user to connect..."));
            }
        }
    }

    @OnMessage
    public void onMessage(String message, Session session) throws IOException {
        String senderNickname = (String) session.getUserProperties().get("nickname");
        Session partner = (Session) session.getUserProperties().get("partner");

        if (partner != null && partner.isOpen()) {
            // Forward the chat message to the user's partner.
            sendMessage(partner, createChatMessage(senderNickname, message));
            System.out.println("Message from " + senderNickname + " to partner: " + message);
        } else {
            // If the user has no partner, notify them.
            sendMessage(session, createSystemMessage("You are not connected to anyone."));
        }
    }

    @OnClose
    public void onClose(Session session, CloseReason closeReason) throws IOException {
        String nickname = (String) session.getUserProperties().get("nickname");
        System.out.println("Session closed for " + nickname + ": " + closeReason.getReasonPhrase());

        // Ensure the user is removed from the waiting queue if they were there.
        waitingUsers.remove(session);

        Session partner = (Session) session.getUserProperties().get("partner");
        if (partner != null && partner.isOpen()) {
            // Notify the partner about the disconnection.
            sendMessage(partner, createSystemMessage(nickname + " has disconnected."));
            // Remove the partner reference from the remaining user.
            partner.getUserProperties().remove("partner");
            System.out.println("Notified partner of disconnection.");
        }
    }

    @OnError
    public void onError(Session session, Throwable throwable) {
        String nickname = (String) session.getUserProperties().get("nickname");
        System.err.println("Error for session " + nickname + ": " + throwable.getMessage());
        throwable.printStackTrace(System.err);

        // Attempt to close the session gracefully on error.
        try {
            session.close(new CloseReason(CloseReason.CloseCodes.UNEXPECTED_CONDITION, "An error occurred."));
        } catch (IOException e) {
            System.err.println("Failed to close session on error: " + e.getMessage());
        }
    }

    // --- Helper Methods for JSON construction ---

    private String createChatMessage(String nickname, String message) {
        return new JSONObject()
                .put("type", "chat")
                .put("nickname", nickname)
                .put("message", message)
                .toString();
    }

    private String createSystemMessage(String message) {
        return new JSONObject()
                .put("type", "system")
                .put("message", message)
                .toString();
    }

    private void sendMessage(Session session, String message) throws IOException {
        if (session.isOpen()) {
            session.getBasicRemote().sendText(message);
        }
    }
}
