import bank.*;
import bank.exceptions.*;

import java.util.ArrayList;

public class Main {
    public static void out(String message) {
        System.out.println(message);
    }
    public static void main(String[] args) {
        OutgoingTransfer t1 = new OutgoingTransfer("07.11.2025",20,"Taschengeld","Voss","Tutor");
        IncomingTransfer t2 = new IncomingTransfer("07.11.2025",10,"Bestechung","Mario","Voss");
        IncomingTransfer t3 = new IncomingTransfer("07.11.2025",1000,"Gehalt","Rektor","Voss");

        out("");
        // Equals Test
        out("Equals Test");
        PrivateBank pb = new PrivateBank("Tolle Bank",0.5,0.5);
        PrivateBank cp = new PrivateBank(pb);
        if(pb.equals(cp))
            out("equals funktioniert für copy: " + cp);
        else
            out("equals funktioniert nicht für copy" + cp);

        // // account ohne Transaktionen erstellen
        // Doppelten Account erstellen
        out("");
        out("//Doppelter Account erstellen Test");
        pb.createAccount("Mario");
        try {
            pb.createAccount("Mario");
        } catch (AccountAlreadyExistsException e) {
            out(e.getMessage());
        }

        try {
            pb.createAccount("Tutor");
        } catch (AccountAlreadyExistsException e) {
            out(e.getMessage());
        }

        // // account mit Transaktionen erstellen
        out("");
        out("//Account mit Transaktionen Test");
        try {
            ArrayList<Transaction> transaktionen = new ArrayList<Transaction>();
            transaktionen.add(t1);
            pb.createAccount("Voss",transaktionen);
            out(pb.toString() + "\r\n" + pb.getTransactions("Voss").toString());
            // Transaktion doppelt hinzufügen
            pb.addTransaction("Voss",t1);
        }
        // Multi Catch führt selben Code für verschiedene Catches auf
        catch (AccountAlreadyExistsException | TransactionAlreadyExistException | TransactionAttributeException e) {
            out(e.getMessage());
        }

        out("");
        out("//getTransaction Test");
        out(pb.getTransactions("Voss").toString());


        // Bei Payment incInt und outInt an Bank anpassen
        out("");
        out("//Payment Parameter anpassen Test");
        try {
            Payment p = new Payment("07.11.2025",10,"Einzahlung",1,1);
            pb.addTransaction("Mario",p);
            out(p.toString());
        } catch (AccountAlreadyExistsException | TransactionAlreadyExistException | TransactionAttributeException e) {
            throw new RuntimeException(e);
        }

        out("");
        out("//remove Transaction Test");
        try {
            out("Erste Löschung");
            pb.removeTransaction("Voss",t1);
            out("Zweite Löschung Exception");
            pb.removeTransaction("Voss",t1);
        } catch (TransactionDoesNotExistException | AccountDoesNotExistException e) {
            out(e.getMessage());
        }

        out("");
        out("//contains Transactions Test");
        if (pb.containsTransaction("Voss", t1)) {
            out("Transaktion existiert");
        } else {
            out("Transaktion existiert nicht (korrekt)");
        }
        pb.addTransaction("Voss",t1);
        if(pb.containsTransaction("Voss",t1)) {
            out("Transaktion existiert (korrekt)");
        } else {
            out("Transaktion existiert nicht");
        }

        pb.addTransaction("Voss",t2);
        pb.addTransaction("Voss",t3);

        out("");
        out("//get Transactions Test");
        out(pb.getTransactions("Voss").toString());

        out("");
        out("//get Transactions Sorted Test (1. Asc/2. Desc)");
        out(pb.getTransactionsSorted("Voss",true).toString());
        out(pb.getTransactionsSorted("Voss",false).toString());

        PrivateBankAlt pbalt = new PrivateBankAlt("AlternativBank",0.3,0.3);
        out("");
        out("//get Account Balance Vererbung");
        out("Konstostand: " + pb.getAccountBalance("Voss"));

        pbalt.createAccount("Voss");
        pbalt.addTransaction("Voss", new Transfer("07.11.2025",20,"Taschengeld","Voss","Tutor"));
        pbalt.addTransaction("Voss", t2);
        pbalt.addTransaction("Voss", t3);
        out("");
        out("//get Account Balance instanceof");
        out("Konstostand: " + pbalt.getAccountBalance("Voss"));

    }
}