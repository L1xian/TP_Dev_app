
public class Calcul {
    String op;
    public boolean verif (String op){
        try{
            if(op.indexOf('+') != -1|| op.indexOf('-') != -1|| op.indexOf('*') != -1 ||op.indexOf('/') !=-1){
        String a1=substring(0,op.indexOf(opr)-1);
        String b1=substring(op.indexOf(opr)+1);
        int a=Integer.parseInt(a1);
        int b=Integer.parseInt(b1);
        return true;
        }
        else{
            System.out.println("False Format");
            return false;
        }
        }catch(Exception e){
            System.out.println("Error"+e.getMessage());
        }
    }


    public int Cal (String s) {
    String a1=substring(0,op.indexOf(opr)-1);
    String b1=substring(op.indexOf(opr)+1);
        int a=Integer.parseInt(a1);
        int b=Integer.parseInt(b1);
        switch (o) {
            case (o=op.indexOf('+')!=-1):
                return a+b;
            case (o=op.indexOf('-')!=-1):
                return a-b;
            case (o=op.indexOf('*')!=-1):
                return a*b;
            case (o=op.indexOf('/')!=-1):
                if(b != 0) {
                    return(a / b);
                } else {
                    System.out.println("Division par zéro impossible");
                }
            default:
                System.out.println("Opérateur non reconnu");
                return 0;
        }

        }
}
