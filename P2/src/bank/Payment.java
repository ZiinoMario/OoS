package bank;

// Klasse beschreibt eine Ein- bzw. Auszahlung
public class Payment {
    // /////////////
    // Private Argumente
    // /////////////

    // Datum der Ein- oder Auszahlung
    private String date; // DD.MM.YYYY
    // Geldmenge
    private double amount;
    // Zusätzliche Beschreibung
    private String description;
    // Zinsen bei Einzahlung in Prozent (zwischen 0 und 1)
    private double incomingInterest;
    // Zinsen bei Auszahlung in Prozent (zwischen 0 und 1)
    private double outgoingInterest;

    // Getter
    public double getAmount() {
        return amount;
    }
    public double getIncomingInterest() {
        return incomingInterest;
    }
    public double getOutgoingInterest() {
        return outgoingInterest;
    }
    public String getDate() {
        return date;
    }
    public String getDescription() {
        return description;
    }

    // Setter
    public void setAmount(double a) {
        amount = a;
    }
    public void setDate(String d) {
        date = d;
    }
    public void setDescription(String des) {
        description = des;
    }
    public void setIncomingInterest(double incInt) {
        if (incInt < 0 || incInt > 1) {// Test ob zwischen 0 und 1
            System.out.println("Zinswert nicht okay");
        } else {
            incomingInterest = incInt;
        }
    }
    public void setOutgoingInterest(double outInt) {
        if(outInt < 0 || outInt > 1) { // Test ob zwischen 0 und 1
            System.out.println("Zinswert nicht okay");
        } else {
            outgoingInterest = outInt;
        }
    }

    // //////////////////
    // Konstruktoren
    // //////////////////

    // Konstruktor mit drei Argumenten => Verweis auf allgemeinen Konstruktor
    public Payment(String dat, double am, String des) {
        setDate(dat);
        setAmount(am);
        setDescription(des);
        setIncomingInterest(0.1);
        setOutgoingInterest(0.1);
    }

    // Allgemeine Konstruktor
    public Payment(String dat, double am, String des, double incInt, double outInt) {
        this(dat,am,des);
        setIncomingInterest(incInt);
        setOutgoingInterest(outInt);
    }

    // Copy-Konstruktor
    public Payment(Payment p) {
        this(p.getDate(),p.getAmount(),p.getDescription(),p.getIncomingInterest(),p.getOutgoingInterest());
    }

    // ////////////////
    // Funktionen
    // ////////////////

    // Ausgabe
    public void printObject() {
        System.out.println("Datum: "+getDate());
        System.out.println("Geldmenge: "+getAmount());
        System.out.println("Beschreibung: "+getDescription());
        System.out.println("Einzahlungszinsen: "+getIncomingInterest());
        System.out.println("Auszahlungszinsen: "+getOutgoingInterest());
        System.out.println();
    }
}
