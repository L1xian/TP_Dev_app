package rmiServer;

import java.rmi.registry.LocateRegistry;
import javax.naming.Context;
import javax.naming.InitialContext;
import rmiService.BanqueImpl;

public class BanqueServer {
    public static void main(String[] args) {
        try {
            LocateRegistry.createRegistry(1099);
            BanqueImpl bi = new BanqueImpl();
            System.out.println(bi.toString());

            // Utilisation de JNDI pour l'enregistrement
            Context ctx = new InitialContext();
            ctx.bind("rmi://localhost:1099/BanqueService", bi)
            System.out.println("Serveur RMI prêt avec JNDI.");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
