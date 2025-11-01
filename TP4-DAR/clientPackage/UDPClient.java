package clientPackage;

import java.net.*;
import java.io.*;
import java.util.Scanner;

public class UDPClient{
    private static final int SERVER_PORT = 1234;
    private static final String SERVER_ADDRESS = "localhost";

    public static void main(String[] args) {
        try {
            DatagramSocket clientSocket = new DatagramSocket();
            InetAddress serverAddress = InetAddress.getByName(SERVER_ADDRESS);

            Scanner scanner = new Scanner(System.in);
            System.out.print("Entrez votre nom d'utilisateur : ");
            String username = scanner.nextLine();

            Thread senderThread = new Thread(() -> {
                System.out.println("Entrez vos messages (tapez 'quit' pour quitter) :");
                String message;
                while (true) {
                    message = scanner.nextLine();
                    if (message.equalsIgnoreCase("quit")) {
                        clientSocket.close();
                        break;
                    }
                    String formattedMessage = "[" + username + "] : " + message;
                    try {
                        byte[] sendData = formattedMessage.getBytes();
                        DatagramPacket sendPacket = new DatagramPacket(sendData, sendData.length, serverAddress, SERVER_PORT);
                        clientSocket.send(sendPacket);
                    } catch (IOException e) {
                        System.out.println("Erreur lors de l'envoi du message : " + e.getMessage());
                        break;
                    }
                }
            });

            Thread receiverThread = new Thread(() -> {
                byte[] receiveData = new byte[1024];
                while (!clientSocket.isClosed()) {
                    try {
                        DatagramPacket receivePacket = new DatagramPacket(receiveData, receiveData.length);
                        clientSocket.receive(receivePacket);
                        String receivedMessage = new String(receivePacket.getData(), 0, receivePacket.getLength());
                        System.out.println(receivedMessage);
                    } catch (SocketException e) {
                        System.out.println("Arrêt du thread récepteur.");
                        break;
                    } catch (IOException e) {
                        System.out.println("Erreur lors de la réception du message : " + e.getMessage());
                        break;
                    }
                }
            });

            senderThread.start();
            receiverThread.start();
            senderThread.join();
            receiverThread.join();
            scanner.close();
            System.out.println("Client déconnecté.");
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
    }
}
