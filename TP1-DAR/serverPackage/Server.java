package serverPackage;

import java.io.IOException;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

public class Server {
    public static void main(String[] args) {
    	//choisir un port 
        final int port = 2400; 

        try (ServerSocket serverSocket = new ServerSocket(port)) {
            System.out.println("Je suis un serveur en attente de la connexion d'un client");

            // Attente d’un client
            try (
            	//le code est blokcer dans accept jusqu'a ce qu'un cliant se connect
                Socket clientSocket = serverSocket.accept();

                BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
                PrintWriter out = new PrintWriter(clientSocket.getOutputStream(), true)
            ) {

                // Deuxième étape : un client est connecté
                System.out.println("Un client est connecté : " + clientSocket.getRemoteSocketAddress());
                
                // Exemple simple de communication : lire une ligne envoyée par le client
                String message = in.readLine(); // bloque jusqu’à ce que le client envoie une ligne terminée par \n
                if (message != null) {
                    System.out.println("Message client: " + message);
                    // Répondre au client
                    out.println("Serveur: message reçu -> " + message);
                    int i=(int)message*3;
                    System.out.println("calcul="+i);

                } else {
                    System.out.println("Le client s'est déconnecté sans envoyer de message.");
                }

                // Dernière étape : fermeture automatique grâce au try-with-resources
                System.out.println("Fermeture du socket client...");
            }

            System.out.println("arrête du serveur");

        } catch (IOException e) {
            System.err.println("Erreur réseau : " + e.getMessage());
            e.printStackTrace();
        }
    }
}