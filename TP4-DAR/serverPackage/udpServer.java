package serverPackage;

import java.io.*;
import java.net.*;
import java.util.*;
import java.util.concurrent.*;

public class UDPServer {
    private static final int PORT = 1234;
    private static List<SocketAddress> connectedClients = new CopyOnWriteArrayList<>(); // Thread-safe list
    private static DatagramSocket serverSocket;

    public static void main(String[] args) {
        try {
            serverSocket = new DatagramSocket(new InetSocketAddress("localhost", PORT));
            System.out.println("Serveur UDP bidirectionnel démarré sur le port " + PORT);

            byte[] receiveData = new byte[1024];

            while (true) {
                DatagramPacket receivePacket = new DatagramPacket(receiveData, receiveData.length);
                serverSocket.receive(receivePacket);

                InetSocketAddress senderAddress = (InetSocketAddress) receivePacket.getSocketAddress();
                String message = new String(receivePacket.getData(), 0, receivePacket.getLength());

                if (!isClientConnected(senderAddress)) {
                    connectedClients.add(senderAddress);
                    System.out.println("Nouveau client connecté : " + senderAddress.getAddress().getHostAddress() + ":" + senderAddress.getPort());
                }

                System.out.println("Message reçu de " + senderAddress.getAddress().getHostAddress() + ":" + senderAddress.getPort() + " : " + message);

                broadcastMessage(message, senderAddress);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (serverSocket != null && !serverSocket.isClosed()) {
                serverSocket.close();
            }
        }
    }

    private static boolean isClientConnected(SocketAddress address) {
        return connectedClients.contains(address);
    }

    private static void broadcastMessage(String message, SocketAddress senderAddress) throws IOException {
        byte[] sendData = message.getBytes();
        for (SocketAddress clientAddress : connectedClients) {
            if (!clientAddress.equals(senderAddress)) {
                DatagramPacket sendPacket = new DatagramPacket(sendData, sendData.length, clientAddress);
                serverSocket.send(sendPacket);
            }
        }
    }
}
