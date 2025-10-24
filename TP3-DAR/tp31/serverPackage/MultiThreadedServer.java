package tp31.serverPackage;

import java.io.*;
import java.net.*;


public class MultiThreadedServer {
    private static final int PORT = 1234;
    private static int nbr = 0; 

    public static void main(String[] args) {
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

    public static class ClientProcess implements Runnable {
        private Socket clientSocket;
        private int clientNumber;
        
        public ClientProcess(Socket socket, int clientNumber) {
            this.clientSocket = socket;
            this.clientNumber = clientNumber;
        }

        @Override
        public void run() {
            try (PrintWriter out = new PrintWriter(clientSocket.getOutputStream(), true)) {
                String clientIP = clientSocket.getRemoteSocketAddress().toString();
                System.out.println("Client #" + clientNumber + " connected from: " + clientIP);

                out.println("You are client #" + clientNumber);

                System.out.println("Client #" + clientNumber + " disconnected");
                clientSocket.close();

            } catch (IOException e) {
                e.printStackTrace();
            }
        }

    }
}
