package serverPackage;

import java.io.IOException;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

//object imports
import java.io.ObjectInputStream;
import Calcul;

//** string
public class Server {
    public static void main(String[] args) {
        try (ServerSocket serverSocket = new ServerSocket(2400)) {
            System.out.println("Je suis un serveur en attente de la connexion d'un client");
            try (
                Socket clientSocket = serverSocket.accept();
                BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
                PrintWriter out = new PrintWriter(clientSocket.getOutputStream(), true)
            ) {
                //verif
                System.out.println("Un client est connecté : " + clientSocket.getRemoteSocketAddress());
                String line;
                int s;
                line = in.readLine();
                if(line.indexOf('+') != -1|| line.indexOf('-') != -1|| line.indexOf('*') != -1 ||line.indexOf('/') !=-1){
                    String a1=line.substring(0,line.indexOf(line.charAt(1))-1);
                    String b1=line.substring(line.indexOf(line.charAt(1))+1);
                    int a=Integer.parseInt(a1);
                    int b=Integer.parseInt(b1);
                    if(line.indexOf('+')!=-1){
                        s=a+b;
                    out.println(s);}
                    else if(line.indexOf('-')!=-1){
                        s=a-b;
                        out.println(s);}
                    else if(line.indexOf('*')!=-1){
                        s=a*b;
                        out.println(s);
                    }else if(line.indexOf('/')!=-1){
                        if(b != 0) {
                        s=(a / b);
                        out.println(s);
                        } else {
                        System.out.println("Division par zéro impossible");
                        }
                    }
                }else {
                    System.out.println("Opérateur non reconnu");
                }
            } catch(Exception e){
                System.out.println("Error"+e.getMessage());
            }

        }catch (IOException e) {
                System.err.println("Erreur de classe : " + e.getMessage());
        }
    }

/*  object
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
*/

}