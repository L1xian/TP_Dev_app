package clientPackage;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class Client {
    public static void main(String[] args) {
        String serverAddress = "0.0.0.0";
        //le meme port de serveur
        int port = 2400;
        // Première étape
        System.out.println("Je suis un client pas encore connecté…");
        //try avec des ressource  assure la ferméture du serversocket a la sortir du block 
        //meme s'il y a une exception 
        try (
        	//ouverture une connexion tcp avec le serveur
            Socket socket = new Socket(serverAddress, port);
        	//envoiyer le text au serveur
            PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
        	//resevoir la répence de serveur
            BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        	//permet de lire ce que l'utilisateur tape dans le console 
        	BufferedReader consoleInput = new BufferedReader(new InputStreamReader(System.in))
        ) {
            // Connexion réussie
            System.out.println("un client connecté à " + serverAddress + ":" + port);
            // Envoyer un message
            System.out.print("Tapez un message: ");
            //récupère le texte tapez 
            String userMessage = consoleInput.readLine();
            //evoi le text au serveur
            out.println(userMessage);

            // Lire la réponse
            //attend un message de serveur
            String response = in.readLine();
            //affiche le message au console client
            System.out.println("Réponse serveur : " + response);

            // Fermeture automatique
            System.out.println("Déconnexion");

        } catch (IOException e) {
            System.err.println("Erreur client : " + e.getMessage());
            e.printStackTrace();
        }
    }
}
