package tp31.clientPackage;
import java.net.*;
import java.io.*;

public class ClientProcess implements Runnable {
    private Socket clientSocket;
    private int clientNumber;

    public ClientProcess(Socket socket, int clientNumber) {
        this.clientSocket = socket;
        this.clientNumber = clientNumber;
    }

    @Override
    public void run() {
        try {
            String clientIP = clientSocket.getRemoteSocketAddress().toString();
            System.out.println("Client #" + clientNumber + " connected from: " + clientIP);

            PrintWriter out = new PrintWriter(clientSocket.getOutputStream(), true);

            out.println("You are client #" + clientNumber);

            
            System.out.println("Client #" + clientNumber + " disconnected");
            clientSocket.close();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}