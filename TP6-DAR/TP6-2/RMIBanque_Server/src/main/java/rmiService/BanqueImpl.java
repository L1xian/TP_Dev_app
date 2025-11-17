package rmiService;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
import java.util.List;
import metier.Compte;

public class BanqueImpl extends UnicastRemoteObject implements IBanque {

    private List<Compte> comptes = new ArrayList<>();

    public BanqueImpl() throws RemoteException {
        super();
    }

    @Override
    public String creerCompte(Compte c) throws RemoteException {
        comptes.add(c);
        return "Compte créé avec succès. Code: " + c.getCode();
    }

    @Override
    public String getInfoCompte(int code) throws RemoteException {
        for (Compte compte : comptes) {
            if (compte.getCode() == code) {
                return "Informations du compte " + code + ": \n" + compte.toString();
            }
        }
        return "Compte introuvable.";
    }
}
