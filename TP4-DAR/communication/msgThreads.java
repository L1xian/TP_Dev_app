package communication;

import java.net.DatagramPacket;
import java.nio.Buffer;

public class msgThreads extends Thread {
    private DatagramSocket socket;
    private byte[] buffer = new byte[1024];

    void receive() {
        try{
        DatagramPacket requestPacket = new DatagramPacket(buffer, Buffer.length);
        socket.receive(requestPacket);

        String receivedData = new String(requestPacket.getData(), 0, requestPacket.getLength());
        System.out.println("Reçu: " + receivedData);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    void send() {
        try {
            DatagramPacket responsePacket = new DatagramPacket(
                responseData.getBytes(), responseData.length(),
                socket.getInetAddress(), socket.getPort()
            );
            socket.send(responsePacket);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


}
