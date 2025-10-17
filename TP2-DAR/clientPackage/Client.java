package clientPackage;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.Socket;

// object
import java.io.ObjectOutputStream;
import Calcul.java;

//** string
public class Client {
    public static void main(String[] args) {
        try (
            Socket socket = new Socket("","");
            BufferedWriter out = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
            BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            BufferedReader console = new BufferedReader(new InputStreamReader(System.in));
        ){
            String line;
                    System.out.println("saisir l equation");
                    String op = in.readLine();
        }
        catch (IOException e) {
            System.err.println("Erreur client : " + e.getMessage());
        }
    }


/*  object
public class Client {
    @Override
    public static void main(String[] args) {
        try (
            Socket socket = new Socket("local","");
            BufferedWriter out = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
            BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            BufferedReader console = new BufferedReader(new InputStreamReader(System.in));
        ){
            String line;
            Calcul c = new Calcul();
            do{
                    System.out.println("saisir l operateur (+,-,*,/)");
                    String op = in.readLine();
                    System.out.println("saisir le premier nombre"); 
                    int n1=(Integer.parseInt(in.readLine()));
                    System.out.println("saisir le deuxieme nombre");
                    int n2=(Integer.parseInt(in.readLine()));
            }while(Calcul.verif(op,n1,n2)==false);
            c.setOp(op);
            c.setN1(n1);
            c.setN2(n2);
            out.writeObject(c);
            out.flush();

                String response = in.readLine();
                System.out.println("le resultat ="+ response);
            }

        catch (IOException e) {
            System.err.println("Erreur client : " + e.getMessage());
        }
    }
}
*/

}