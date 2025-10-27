package serverPackage;

import java.io.*;
import java.net.*;

import communication.msgThreads;

public class udpServer extends Thread {

    public udpServer(int port) throws SocketException {
        DatagramSocket socket = new DatagramSocket(port);

        while (true) {
            try {
               msgThreads.receive(socket);
            } catch (IOException e) {
               e.printStackTrace();
            }


            try {
               msgThreads.send(socket);
            } catch (IOException e) {
               e.printStackTrace();
            }

        }
    }
} 