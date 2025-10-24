package tp31.serverPackage;
import java.io.*;
import java.net.*;
import java.util.concurrent.*;

import tp31.clientPackage.ClientProcess;

public class MultiThreadedServer {
    private static final int PORT = 1234;
    private static ExecutorService pool = Executors.newFixedThreadPool(10); // Pool de threads

    public static void main(String[] args) {
        try (ServerSocket serverSocket = new ServerSocket(PORT)) {
            System.out.println("Serveur en écoute sur le port " + PORT);
            int clientCount = 0;

            while (true) {
                Socket clientSocket = serverSocket.accept();
                clientCount++;
                System.out.println("Client n°" + clientCount + " connecté : " + clientSocket.getRemoteSocketAddress());
                pool.execute(new ClientProcess(clientSocket, clientCount));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
