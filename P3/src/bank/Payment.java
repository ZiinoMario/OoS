package bank;

/**
 * Die Klasse stellt eine Ein-/Auszahlung dar und erbt von {@link Transaction}.
 * Wenn Amount negativ ist, ist das eine Auszahlung, sonst eine Einzahlung.
 * @see Transaction
 */
public class Payment extends Transaction {
    // /////////////
    // Private Argumente
    // /////////////

    /// Zinsen bei Einzahlung in Prozent (zwischen 0 und 1)
    private double incomingInterest;
    /// Zinsen bei Auszahlung in Prozent (zwischen 0 und 1)
    private double outgoingInterest;

    /**
     * Rückgabe des Einzahlungszinses
     * @return Einzahlungszinses
     */
    public double getIncomingInterest() {
        return incomingInterest;
    }

    /**
     * Rückgabe des Auszahlungszinses
     * @return Auszahlungszinses
     */
    public double getOutgoingInterest() {
        return outgoingInterest;
    }

    /**
     * Setzen des Einzahlungszinses
     * @param incInt neuer Zinswert
     */
    public void setIncomingInterest(double incInt) {
        if (incInt < 0 || incInt > 1) {// Test ob zwischen 0 und 1
            System.out.println("Zinswert nicht okay");
        } else {
            incomingInterest = incInt;
        }
    }

    /**
     * Setzen des Auszahlungszinses
     * @param outInt neuer Zinswert
     */
    public void setOutgoingInterest(double outInt) {
        if(outInt < 0 || outInt > 1) { // Test ob zwischen 0 und 1
            System.out.println("Zinswert nicht okay");
        } else {
            outgoingInterest = outInt;
        }
    }

    /**
     * Geldbetrag nach Berechnung des Zinses
     * @return berechneter
     */
    @Override
    public double calculate() {
        if(amount < 0) // Bei einer Auszahlung outgoingInterest draufrechnen
            return amount*(1+outgoingInterest);
        else // Bei einer Einzahlung incomingInterest abziehen
            return amount*(1-incomingInterest);
    }

    // //////////////////
    // Konstruktoren
    // //////////////////

    /**
     * Konstruktor mit drei Parametern
     * @param dat Datum der Ein-/Auszahlung
     * @param am Geldbetrag positiv bei Einzahlung, negativ bei Auszahlung
     * @param des Verwendungszweck
     */
    public Payment(String dat, double am, String des) {
        super(dat, am, des);
        setIncomingInterest(0.1);
        setOutgoingInterest(0.1);
    }

    /**
     * Allgemeiner Konstruktor mit allen Parametern
     * @param dat Datum der Ein-/Auszahlung
     * @param am Geldbetrag positiv bei Einzahlung, negativ bei Auszahlung
     * @param des Verwendungszweck
     * @param incInt Gebühren bei Einzahlung
     * @param outInt Gebühren bei Auszahlung
     */
    public Payment(String dat, double am, String des, double incInt, double outInt) {
        this(dat,am,des);
        setIncomingInterest(incInt);
        setOutgoingInterest(outInt);
    }

    /**
     * Copy-Konstruktor
     * @param p die zu kopierende Instanz
     */
    public Payment(Payment p) {
        this(p.getDate(),p.getAmount(),p.getDescription(),p.getIncomingInterest(),p.getOutgoingInterest());
    }

    // ////////////////
    // Funktionen
    // ////////////////

    /**
     * Erzeugt einen String mit allen Argumenten aneinander gehangen
     * @return Gibt String mit allen Argumenten zurück
     */
    @Override
    public String toString() {
        return ( ("Datum: "+getDate())
                + " " + ("Rechnung: "+ calculate())
                + " " + ("Beschreibung: "+getDescription()) )
                + " " + ("Einzahlungszinsen: "+getIncomingInterest())
                + " " + ("Auszahlungszinsen: "+getOutgoingInterest());
    }

    /**
     * vergleicht die Parameter einer anderen Instanz auf Gleichheit
     * @param obj das zu vergleichende Objekt
     * @return Gibt True zurück bei Gleichheit
     */
    @Override
    public boolean equals(Object obj) {
        if(obj instanceof Payment otherTf) {
            return (super.equals( otherTf )
                    && this.getIncomingInterest() == otherTf.getIncomingInterest()
                    && this.getOutgoingInterest() == otherTf.getOutgoingInterest()
            );
        }
        return false;
    }
}
