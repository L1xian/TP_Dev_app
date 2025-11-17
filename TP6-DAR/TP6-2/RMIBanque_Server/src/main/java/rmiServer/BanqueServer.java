package rmiServer;

import java.rmi.Naming;
import java.rmi.registry.LocateRegistry;
import rmiService.BanqueImpl;

public class BanqueServer {
    public static void main(String[] args) {
        try {
            LocateRegistry.createRegistry(1099);
            BanqueImpl bi = new BanqueImpl();
            System.out.println(bi.toString());
            Naming.rebind("rmi://localhost:1099/BanqueService", bi);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
