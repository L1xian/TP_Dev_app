package rmiClient;

import rmiService.IBanque;
import metier.Compte;
import java.rmi.Naming;

public class BanqueClient {
    public static void main(String[] args) {
        try {
            IBanque stub = (IBanque) Naming.lookup("rmi://localhost:1099/BanqueService");
            
            Compte cp = new Compte(2000.5);
            System.out.println(stub.creerCompte(cp));
            
            System.out.println(stub.getInfoCompte(1));

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
