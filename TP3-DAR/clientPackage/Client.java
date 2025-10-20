package clientPackage;

import java.io.*;
import java.net.*;

public class Client extends Thread {
    public static void main(String[] args) {
        try (
            Socket socket = new Socket("localhost", 1234);
            BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
        ){
            out.println("");
            
            

            }
        } catch (IOException e) {
            System.err.println("Erreur client : " + e.getMessage());
        }
    }

