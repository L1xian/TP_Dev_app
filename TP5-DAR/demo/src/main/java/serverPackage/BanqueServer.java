package corbaServer;
import org.omg.CORBA.ORB;
import org.omg.CosNaming.NameComponent;
import org.omg.CosNaming.NamingContextExt;
import org.omg.CosNaming.NamingContextExtHelper;
import org.omg.PortableServer.POA;
import org.omg.PortableServer.POAHelper;
import service.BanqueImpl;
public class BanqueServer {
    public static void main(String[] args) {
        try {
            ORB orb = ORB.init(args, null);
            POA rootpoa = POAHelper.narrow(orb.resolve_initial_references("RootPOA"));
            rootpoa.the_POAManager().activate();
            BanqueImpl banqueImpl = new BanqueImpl();
            org.omg.CORBA.Object ref = rootpoa.servant_to_reference(banqueImpl);
            org.omg.CORBA.Object objRef = orb.resolve_initial_references("NameService");
            NamingContextExt ncRef = NamingContextExtHelper.narrow(objRef);
            String name = "Banque";
            NameComponent path[] = ncRef.to_name(name);
            ncRef.rebind(path, ref);
            System.out.println("Le serveur de la banque est prêt et en attente de requêtes...");
            orb.run();
        } catch (Exception e) {
            System.err.println("ERREUR : " + e.getMessage());
            e.printStackTrace(System.out);}
        System.out.println("Le serveur de la banque s'arrête.");
    }}
