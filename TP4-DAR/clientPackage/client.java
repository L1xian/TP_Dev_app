package clientPackage;

import java.io.*;
import java.net.*;

public class client extends Thread {
    private DatagramSocket socket;

    
    public client(int port) throws SocketException {
        socket = new DatagramSocket(port);

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