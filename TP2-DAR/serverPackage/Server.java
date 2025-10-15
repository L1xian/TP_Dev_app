package serverPackage;
import clientPackage.Calcul;
import java.io.Serializable;

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
                Calcul c = new Calcul();
                c = (Calcul) in.readObject();
                int r = Calcul.Cal(c);
                out.println(r);
                } catch (ClassNotFoundException e) {
                    System.err.println("Erreur de classe : " + e.getMessage());
            }

            System.out.println("arrête du serveur");
        } catch (IOException e) {
            System.err.println("Erreur réseau : " + e.getMessage());

        }
    }
}