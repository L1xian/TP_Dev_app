package rmiClient;

import metier.Compte;
import rmiService.IBanque;

import javax.naming.Context;
import javax.naming.InitialContext;
import java.util.Hashtable;

public class BanqueClient {
    public static void main(String[] args) {
        try {
            Hashtable<String, String> ht = new Hashtable<String, String>();
            ht.put(Context.INITIAL_CONTEXT_FACTORY, "com.sun.jndi.rmi.registry.RegistryContextFactory");
            ht.put(Context.PROVIDER_URL, "rmi://localhost:1099");
            Context ctx = new InitialContext(ht);
            IBanque stub = (IBanque) ctx.lookup("BanqueService");

            Compte cp = new Compte(2000.5);
            System.out.println(stub.creerCompte(cp));

            System.out.println(stub.getInfoCompte(1));

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
