package tp32.serverPackage;

import java.io.*;
import java.net.*;

import tp31.clientPackage.ClientProcess;

public class MultiThreadedServer {
    private static final int PORT = 1234;
    private static int nbr = 0;

    public static synchronized void main(String[] args) {
        try (ServerSocket serverSocket = new ServerSocket(PORT)) {
            System.out.println("Serveur en écoute sur le port " + PORT);

            while (true) {
                Socket clientSocket = serverSocket.accept();
                nbr++;
                System.out.println("Client n°" + nbr + " connecté : " + clientSocket.getRemoteSocketAddress());
                new Thread(new ClientProcess(clientSocket, nbr)).start();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    
}
}
