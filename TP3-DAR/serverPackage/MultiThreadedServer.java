package serverPackage;

import java.io.*;
import java.net.*;
import Calcul.java;
import ClientProcess.java;

import clientPackage.Client;

public class MultiThreadedServer extends Thread {
    private static int clientCounter = 0;

    public static void main(String[] args) {
        try {
            ServerSocket serverSocket = new ServerSocket(1234);
            System.out.println("Multi-threaded Server started on port 1234");
            
            
            while (true) {
                Socket clientSocket = serverSocket.accept();
                if(clientSocket != null) {
                    Thread connect = new Thread(new ClientProcess(clientSocket,++clientCounter));
                    connect.start();

                    PrintWriter out = new PrintWriter(new BufferedWriter(new OutputStreamWriter(clientSocket.getOutputStream())), true);
                    BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
                    try {
                    out.println("enter an  equation :");
                    out.flush();
                    Calcul eq = new Calcul();

                    out.println("enter operator :");
                    eq.setOp(in.readLine());
                    out.println("enter first number :");
                    eq.setN1(Integer.parseInt(in.readLine()));
                    out.println("enter second number :");
                    eq.setN2(Integer.parseInt(in.readLine()));
                    if (eq.verif(eq)) {
                        int result = eq.Cal(eq);
                        out.println("Result: " + result);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }} catch (IOException e) {
            e.printStackTrace();
        }
        

    
    }
}
