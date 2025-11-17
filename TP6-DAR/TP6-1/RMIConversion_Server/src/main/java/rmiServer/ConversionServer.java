package rmiServer;

import java.rmi.Naming;
import java.rmi.registry.LocateRegistry;
import rmiService.ConversionImpl;

public class ConversionServer {
    public static void main(String[] args) {
        try {
            LocateRegistry.createRegistry(1099);
            ConversionImpl conversion = new ConversionImpl();
            Naming.rebind("rmi://localhost/ConversionService", conversion);
            System.out.println("ConversionService publié et prêt.");
        } catch (Exception e) {
            System.err.println("Erreur serveur RMI : " + e.getMessage());
            System.out.println(e);
        }
    }
}
