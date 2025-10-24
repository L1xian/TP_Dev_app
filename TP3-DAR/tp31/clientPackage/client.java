package tp31.clientPackage;
import java.net.*;
import java.io.*;

public class client {
    public static void main(String[] args) {
        try {
        System.out.println("Client pas encore connecte..");
        Socket socket = new Socket("localhost", 1234);
        System.out.println("Client connecte");
        

        socket.close();
    }catch (IOException e) {
        e.printStackTrace();
    }
    }

}
