package servicePackage;

import corbaBanque.IBanqueRemotePOA;
import corbaBanque.Compte;


import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class BanqueImpl extends IBanqueRemotePOA {
    private final List<Compte> comptes;

    public BanqueImpl() {
        comptes = new ArrayList<>();
        // Ajout de quelques comptes pour la démonstration
        comptes.add(new Compte(1, 1000.50f));
        comptes.add(new Compte(2, 2500.00f));
    }
    @Override
    public void creerCompte(Compte cpte) {
        // Recherche si un compte avec le même code existe déjà
        boolean existe = comptes.stream().anyMatch(c -> c.code == cpte.code);
        if (existe) {
            System.out.println("Erreur : Le compte avec le code " + cpte.code + " existe déjà.");
        } else {
            comptes.add(cpte);
            System.out.println("Compte créé avec succès. Code: " + cpte.code + ", Solde initial: " + cpte.solde);
        }
    }

    @Override
    public void verser(float mt, int code) {
        Optional<Compte> compteOpt = comptes.stream().filter(c -> c.code == code).findFirst();
        if (compteOpt.isPresent()) {
            Compte compte = compteOpt.get();
            compte.solde += mt;
            System.out.println("Versement de " + mt + " sur le compte " + code +
                ". Nouveau solde : " + compte.solde);
        } else {
            System.out.println("Erreur : Compte avec le code " + code + " introuvable.");
        }
    }

    @Override
    public void retirer(float mt, int code) {
        Optional<Compte> compteOpt = comptes.stream().filter(c -> c.code == code).findFirst();
        if (compteOpt.isPresent()) {
            Compte compte = compteOpt.get();
            if (compte.solde >= mt) {
                compte.solde -= mt;
                System.out.println("Retrait de " + mt + " du compte " + code +
                    ". Nouveau solde : " + compte.solde);
            } else {
                System.out.println("Opération échouée : Solde insuffisant pour le compte " + code);
            }
        } else {
            System.out.println("Erreur : Compte avec le code " + code + " introuvable.");
        }
    }

    @Override
    public Compte getCompte(int code) {
        return comptes.stream()
            .filter(c -> c.code == code)
            .findFirst()
            .orElse(null); // Retourne null si aucun compte ne correspond au code.
    }

    @Override
    public Compte[] getComptes() {
        // La méthode toArray avec un constructeur de tableau est la manière standard
        // de convertir une liste en tableau du type souhaité.
        return comptes.toArray(new Compte[0]);
    }

    @Override
    public double conversion(float mt) {
        // Taux de conversion fixe pour l'exemple (ex: 1 TND = 0.30 EUR)
        final double TAUX_CONVERSION = 0.30;
        System.out.println("Conversion de " + mt + " au taux de " + TAUX_CONVERSION);
        return mt * TAUX_CONVERSION;
    }
}
