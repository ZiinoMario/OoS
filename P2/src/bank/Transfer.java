package bank;

// Klasse beschreibt eine Überweisung
public class Transfer {
    // //////////////
    // Private Argumente
    // //////////////

    // Datum der Ein- oder Auszahlung
    private String date; // DD.MM.YYYY
    // Geldmenge - ACHTUNG! darf hier nur positiv sein
    private double amount;
    // Zusätzliche Beschreibung
    private String description;
    // Sender welcher überweist
    private String sender;
    // Empfänger welcher das Geld bekommt
    private String recipient;

    // Getter
    public double getAmount() {
        return amount;
    }
    public String getDescription() {
        return description;
    }
    public String getDate() {
        return date;
    }
    public String getRecipient() {
        return recipient;
    }
    public String getSender() {
        return sender;
    }
    // Setter
    public void setAmount(double a) {
        if(amount < 0) { // Test ob Wert unter 0€
            System.out.println("Geldbetrag keine positive Zahl");
        } else {
            amount = a;
        }
    }
    public void setDate(String d) {
        date = d;
    }
    public void setDescription(String des) {
        description = des;
    }
    public void setRecipient(String rec) {
        recipient = rec;
    }
    public void setSender(String sen) {
        sender=sen;
    }

    // //////////////////
    // Konstruktoren
    // //////////////////

    // Konstruktor mit nur 3 Argumenten => Verweist auf Konstruktor mit meisten Argumenten
    public Transfer(String dat, double am, String des) {
        setDate(dat);
        setAmount(am);
        setDescription(des);
        setSender("Sender");
        setRecipient("Empfaenger");
    }

    // Konstruktor mit allen Argumenten
    public Transfer(String dat, double am, String des, String sen, String rec) {
        this(dat,0,des);
        setRecipient(rec);
        setSender(sen);
        setAmount(am);
    }

    // Copy Konstruktor
    public Transfer(Transfer t) {
        this(t.getDate(),t.getAmount(),t.getDescription(),t.getSender(),t.getRecipient());
    }

    // ////////////////
    // Funktionen
    // ////////////////

    // Ausgabe aller Transferdaten
    public void printObject() {
        System.out.println("Datum: "+getDate());
        System.out.println("Geldmenge: "+getAmount());
        System.out.println("Beschreibung: "+getDescription());
        System.out.println("Sender: "+getSender());
        System.out.println("Empfänger: "+getRecipient());
        System.out.println();
    }
}
