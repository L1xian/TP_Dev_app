package rmiClient;

import rmiService.IConversion;

import java.rmi.Naming;

public class ConversionClient {
    public static void main(String[] args) {
        try {
            // Lookup the remote object from the RMI registry
            IConversion conversionService = (IConversion) Naming.lookup("rmi://localhost:1099/ConversionService");

            // Call the remote method
            double amountToConvert = 500.0;
            double convertedAmount = conversionService.convertirMontant(amountToConvert);
            System.out.println("Conversion de " + amountToConvert + " : " + convertedAmount);

        } catch (Exception e) {
            System.err.println("Erreur : " + e.getMessage());
        }
    }
}
