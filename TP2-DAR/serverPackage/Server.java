package serverPackage;

import java.io.IOException;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

public class Server {
    public static void main(String[] args) {
        try (ServerSocket serverSocket = new ServerSocket(2400)) {
            System.out.println("Je suis un serveur en attente de la connexion d'un client");
            try (
                Socket clientSocket = serverSocket.accept();
                BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
                PrintWriter out = new PrintWriter(clientSocket.getOutputStream(), true)
            ) {
                System.out.println("Un client est connecté : " + clientSocket.getRemoteSocketAddress());
                String message = in.readLine();
                if (message != null) {
                    System.out.println("Message reçu ");
                    int r=Calcul.Cal(message);
                    out.println(r);
                } else {
                    System.out.println("Le client s'est déconnecté sans envoyer de message.");
                }
                System.out.println("Fermeture du socket client...");
            }
            System.out.println("arrête du serveur");
        } catch (IOException e) {
            System.err.println("Erreur réseau : " + e.getMessage());

        }
    }
}