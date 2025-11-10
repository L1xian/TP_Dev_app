package ClientPackage;

import org.omg.CORBA.ORB;
import org.omg.CosNaming.NamingContextExt;
import org.omg.CosNaming.NamingContextExtHelper;
import corbaBanque.Compte;
import corbaBanque.IBanqueRemote;
import corbaBanque.IBanqueRemoteHelper;

public class BanqueClient {
    public static void main(String[] args) {
        try {
            ORB orb = ORB.init(args, null);
            org.omg.CORBA.Object objRef = orb.resolve_initial_references("NameService");
            NamingContextExt ncRef = NamingContextExtHelper.narrow(objRef);
            String name = "Banque"; // Assurez-vous que ce nom est bien celui utilisé par le serveur
            // --- CHANGEMENT 2 : Utiliser le bon Helper et le bon type pour le stub ---
            IBanqueRemote stubBanque = IBanqueRemoteHelper.narrow(ncRef.resolve_str(name));
            System.out.println("--- Client connecté au serveur CORBA ---");
            // Le reste de votre code est correct et n'a pas besoin de changer
            System.out.println("\n1. Création d'un nouveau compte avec le code 3 et un solde de 7500.0f...");
            stubBanque.creerCompte(new Compte(3, 7500.0f));
            System.out.println("=> Demande de création envoyée.");
            System.out.println("\n2. Consultation du compte avec le code 1...");
            Compte cl = stubBanque.getCompte(1);
            if (cl != null) {
                System.out.println("=> Compte récupéré : Code=" + cl.code + ", Solde=" + cl.solde);
            } else {
                System.out.println("=> Compte avec le code 1 introuvable.");
            }

            System.out.println("\n3. Versement de 250.0f sur le compte 1...");
            stubBanque.verser(250.0f, 1);
            Compte compteApresVersement = stubBanque.getCompte(1);
            if (compteApresVersement != null)
                System.out.println("=> Opération effectuée. Nouveau solde du compte 1 : " + compteApresVersement.solde);

            System.out.println("\n4. Retrait de 100.0f du compte 1...");
            stubBanque.retirer(100.0f, 1);
            Compte compteApresRetrait = stubBanque.getCompte(1);
            if (compteApresRetrait != null)
                System.out.println("=> Opération effectuée. Nouveau solde du compte 1 : " + compteApresRetrait.solde);

            System.out.println("\n5. Conversion de 100 unités...");
            double montantConverti = stubBanque.conversion(100.0f);
            System.out.println("=> 100.0f converti donne : " + montantConverti);

            System.out.println("\n6. Liste de tous les comptes :");
            Compte[] tousLesComptes = stubBanque.getComptes();
            for (Compte compte : tousLesComptes) {
                System.out.println(" - Compte Code=" + compte.code + ", Solde=" + compte.solde);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
