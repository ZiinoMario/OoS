import bank.*;
import bank.exceptions.*;

import java.io.IOException;
import java.util.ArrayList;

public class Main {
    public static void out(String message) {
        System.out.println(message);
    }
    public static void main(String[] args) throws TransactionAlreadyExistException, AccountDoesNotExistException, TransactionAttributeException, AccountAlreadyExistsException {
        OutgoingTransfer t1 = new OutgoingTransfer("07.11.2025",20,"Taschengeld","Voss","Tutor");
        IncomingTransfer t2 = new IncomingTransfer("07.11.2025",10,"Bestechung","Mario","Voss");
        Payment p1 = new Payment("23.11.2025",-40,"Auszahlung");

        out("");
        try {
            PrivateBank pb = new PrivateBank("Tolle Bank",0.5,0.5,"C:\\Users\\ziino\\OneDrive\\Studium\\OoS\\OoS\\P4\\accounts\\");
            Payment positivePayment = new Payment("23.11.2025",100,"Einzahlung",0.5,0.5);
            out(positivePayment.toString());
        } catch (IOException e) {
            out("IOException: " + e.getMessage());
        } catch (RuntimeException e) {
            out("RuntimeExc" + e.getMessage());
        }


        /*
        PrivateBank cp = new PrivateBank(pb);
        PrivateBank pb2 = new PrivateBank("Andere Bank",0.5,0.5);
        out("equals mit copy: " + pb.equals(cp));
        out("equals mit anderer Bank: " + pb.equals(pb2));

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
            pb.createAccount("Voss", transaktionen);
            pb.createAccount("Voss", transaktionen);
        } catch (AccountAlreadyExistsException | TransactionAlreadyExistException | TransactionAttributeException e) {
            out(e.getMessage());
        } try {
            Transfer tt = new Transfer(t1);
            pb.addTransaction("Voss",tt);
        } catch (TransactionAlreadyExistException | AccountDoesNotExistException | TransactionAttributeException e) {
            out(e.getMessage());
        } try {
            Transfer tt = new Transfer(t1);
            t2.setAmount(-20);
        }
        catch (TransactionAttributeException e) {
            out(e.getMessage());
        }

        out("");
        out("//getTransaction Test");
        out(pb.getTransactions("Voss").toString());


        // Bei Payment incInt und outInt an Bank anpassen
        out("");
        out("//Payment Parameter anpassen Test");
        try {
            Payment p = new Payment("07.11.2025",100,"Einzahlung",1,1);
            pb.addTransaction("Mario",p);
            out(p.toString());
        } catch (AccountDoesNotExistException | TransactionAlreadyExistException | TransactionAttributeException e) {
            out(e.getMessage());
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
        pb.addTransaction("Voss", t1);
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


        out("");
        out("//get Transactions By Type Test (1. positive/2. negative)");
        out("Positive: " + pb.getTransactionsByType("Voss",true).toString());
        out("Negative: " + pb.getTransactionsByType("Voss",false).toString());
*/

    }
}