import java.io.Serializable;

public class Calcul extends Thread implements Serializable {
    String op;
    int n1;
    int n2;

    public void setN1(int n1) {
        this.n1 = n1;}
    public void setN2(int n2) {
        this.n2 = n2;}
    public void setOp(String op) {
        this.op = op;}

    public boolean verif (Calcul c){
        try{
            if(c.op.indexOf('+') != -1|| c.op.indexOf('-') != -1|| c.op.indexOf('*') != -1 ||c.op.indexOf('/') !=-1){
                return true;
            }else{
                System.out.println("False Format");
                return false;}
        }catch(Exception e){
            System.out.println("Error"+e.getMessage());
        }
        return false;}

    public void Cal (Calcul c) {
            if (c.op.indexOf('+')!=-1){
                System.out.println(c.n1+c.n2);
            } else if (c.op.indexOf('-')!=-1){
                System.out.println(c.n1-c.n2);
            } else if (c.op.indexOf('*')!=-1){
                System.out.println(c.n1*c.n2);
            } else if (c.op.indexOf('/')!=-1){
                if(c.n2 != 0) {
                    System.out.println(c.n1 / c.n2);
                } else {
                    System.out.println("Division par zéro impossible");
                }
            } else {
                System.out.println("Opérateur non reconnu");
        }

        }
}
