package clientPackage;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.Socket;

public class Client {
    public static void main(String[] args) {
        try (
            Socket socket = new Socket("","");
            BufferedWriter out = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
            BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            BufferedReader console = new BufferedReader(new InputStreamReader(System.in));
        ){
            // envoie du message au serveur
            String line;
            do{
            System.out.println("Entrez l équation a calculer : \n");
            line = console.readLine();
            }while(Calcul.verif(line)==false);
              
            out.write(line);
            out.flush();

            // Lecture de la réponse du serveur
                String response = in.readLine();
                System.out.println("le resultat ="+ response);
            }

        catch (IOException e) {
            System.err.println("Erreur client : " + e.getMessage());
        }
    }
}