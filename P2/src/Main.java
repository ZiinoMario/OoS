import bank.Payment;
import bank.Transfer;

public class Main {
    public static void main(String[] args) {
        System.out.println("\nTest Payment\n");
        // Konstruktor für drei Argumente der auf den für alle verweist
        Payment dreiArgsPayment = new Payment("01.01.2025",100,"Neujahrsgeld");
        System.out.println(dreiArgsPayment.toString());

        // Konstruktor für alle Argumente
        Payment alleArgsPayment = new Payment("07.01.2025",1000,"Gehalt",2,0.2);
        System.out.println("Berechnete Rechnung: "+alleArgsPayment.calculate()+" "+alleArgsPayment.toString());
        // Auszahlung
        Payment alleArgsAuszahlungPayment = new Payment("07.01.2025",-1000,"Gehalt",1,0.2);
        System.out.println("Berechnete Rechnung: "+alleArgsAuszahlungPayment.calculate()+" "+alleArgsAuszahlungPayment.toString());

        // Copy-Konstruktor Payment Test
        Payment copyPayment = new Payment(alleArgsPayment);
        System.out.println(copyPayment.toString());
        System.out.println("Gleichheit mit Copy-Element: " + alleArgsPayment.equals(copyPayment));

        System.out.println("\nTest Transfer\n");
        // Konstruktor für drei Argumente der auf den für alle verweist
        Transfer dreiArgsTransfer = new Transfer("03.03.2025",2.01,"Taschengeld");
        System.out.println(dreiArgsTransfer.toString());

        // Konstruktor für alle Argumente
        Transfer alleArgsTransfer = new Transfer("04.03.2025",20,"Taschengeld","Vater","Kind");
        System.out.println("Berechnete Rechnung: "+alleArgsTransfer.calculate()+" "+alleArgsTransfer.toString());

        // Konstruktor mit negativem Überweisungsbetrag
        Transfer alleArgsNegativTestTransfer = new Transfer("04.03.2025",-20,"Taschengeld","Vater","Kind");
        System.out.println("Berechnete Rechnung: "+alleArgsNegativTestTransfer.calculate()+" "+alleArgsNegativTestTransfer.toString());

        // Copy-Konstruktor Transfer Test
        Transfer copyTransfer = new Transfer(dreiArgsTransfer);
        System.out.println(copyTransfer.toString());

        System.out.println("Gleichheit mit Copy-Element: " + dreiArgsTransfer.equals(copyTransfer));

    }
}