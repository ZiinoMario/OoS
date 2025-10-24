import bank.Payment;
import bank.Transfer;

// Testet Konstruktoren von den Klassen Payment und Transfer
public class Main {
    public static void main(String[] args) {
        System.out.println("\nTest Payment\n");
        // Konstruktor für drei Argumente der auf den für alle verweist
        Payment dreiArgsPayment = new Payment("01.01.2025",100,"Neujahrsgeld");
        dreiArgsPayment.printObject();

        // Konstruktor für alle Argumente
        Payment alleArgsPayment = new Payment("07.01.2025",1000,"Gehalt",2,0.2);
        alleArgsPayment.printObject();

        // Copy-Konstruktor Payment Test
        Payment copyPayment = new Payment(alleArgsPayment);
        copyPayment.printObject();

        System.out.println("\nTest Transfer\n");
        // Konstruktor für drei Argumente der auf den für alle verweist
        Transfer dreiArgsTransfer = new Transfer("03.03.2025",2.01,"Taschengeld");
        dreiArgsTransfer.printObject();

        // Konstruktor für alle Argumente
        Transfer alleArgsTransfer = new Transfer("04.03.2025",20,"Taschengeld","Vater","Kind");
        alleArgsTransfer.printObject();

        // Konstruktor mit negativem Überweisungsbetrag
        Transfer alleArgsNegativTestTransfer = new Transfer("04.03.2025",-20,"Taschengeld","Vater","Kind");
        alleArgsNegativTestTransfer.printObject();

        // Copy-Konstruktor Transfer Test
        Transfer copyTransfer = new Transfer(dreiArgsTransfer);
        copyTransfer.printObject();
    }
}