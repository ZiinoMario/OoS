package bank;

/**
 * Klasse beschreibt eine Überweisung,
 * die Klasse erbt von Transaction
 * @see Transaction
 */
public class Transfer extends Transaction {
    // //////////////
    // Private Argumente
    // //////////////

    /// Sender welcher überweist
    private String sender;
    /// Empfänger welcher das Geld bekommt
    private String recipient;

    // Getter
    public String getRecipient() {
        return recipient;
    }
    public String getSender() {
        return sender;
    }

    /**
     * Ändert den Geldbetrag mit Überprüfung ob dieser positiv ist
     * @param a der neue Geldbetrag
     */
    @Override
    public void setAmount(double a) {
        if(amount < 0) { // Test ob Wert unter 0€
            System.out.println("Geldbetrag keine positive Zahl");
        } else {
            amount = a;
        }
    }
    public void setRecipient(String rec) {
        recipient = rec;
    }
    public void setSender(String sen) {
        sender=sen;
    }

    /**
     * Implementation der Umrechnung
     * @return gibt den Geldbetrag der Überweisung zurück
     */
    @Override
    public double calculateBill() {
        return getAmount();
    }

    // //////////////////
    // Konstruktoren
    // //////////////////

    /**
     * Konstruktor mit drei Parametern
     * @param dat Datum an dem die Überweisung eingegangen ist
     * @param am Geldbetrag der Überweisung
     * @param des Verwendungszweck der Überweisung
     */
    public Transfer(String dat, double am, String des) {
        super(dat, 0, des);
        setSender("Sender");
        setRecipient("Empfaenger");
        setAmount(am);
    }

    /**
     * Konstruktor mit allen Parametern
     * @param dat Datum an dem die Überweisung eingegangen ist
     * @param am Geldbetrag der Überweisung
     * @param des Verwendungszweck
     * @param sen Sender
     * @param rec Empfänger
     */
    public Transfer(String dat, double am, String des, String sen, String rec) {
        this(dat,am,des);
        setRecipient(rec);
        setSender(sen);
    }

    /**
     * Copy-Konstruktor
     * @param t die zu kopierende Instanz
     */
    public Transfer(Transfer t) {
        this(t.getDate(),t.getAmount(),t.getDescription(),t.getSender(),t.getRecipient());
    }

    // ////////////////
    // Funktionen
    // ////////////////

    /**
     * Gibt einen String mit allen Argumenten zurück
     * @return String mit allen Argumenten
     */
    @Override
    public String toString() {
        return ( ("Datum: "+getDate())
                + " " + ("Geldmenge: "+calculateBill())
                + " " + ("Beschreibung: "+getDescription()) )
                + " " + ("Sender: "+getSender())
                + " " + ("Empfänger: "+getRecipient());
    }

    /**
     * Vergleicht die Parameter mit einer anderen Instanz auf Gleichheit
     * @param obj die zu vergleichende Instanz
     * @return Gibt true zurück bei Gleichheit
     */
    @Override
    public boolean equals(Object obj) {
        if(obj instanceof Transfer) {
            Transfer otherTf = (Transfer) obj;
            return (super.equals( (Transaction) otherTf )
                    && this.getSender().equals(otherTf.getSender())
                    && this.getRecipient().equals(otherTf.getRecipient())
            );
        }
        return false;
    }
}
