package bank;

/**
 * Oberklasse die eine Transaktion darstellt
 * Diese Klasse stellt Transaktionen dar mit einem Datum, der Geldbetrag und der Beschreibung der Transaktion
 * @see CalculateBill
 */
public abstract class Transaction implements CalculateBill {

    /// Datum der Ein- oder Auszahlung
    protected String date; // DD.MM.YYYY
    /// Geldmenge
    protected double amount;
    /// Zusätzliche Beschreibung
    protected String description;

    /**
     * Ändert das Datum
     * @param d das neue Datum
     */
    public void setDate(String d) {
        date = d;
    }
    /**
     * Zwingt Unterklassen, diese Methode zu implementieren, da sie sich unterschiedlich für
     * Ein-/Auszahlungen und Überweisungen verhalten soll
     * @param a der neue Geldbetrag
     */
    abstract public void setAmount(double a);
    /**
     * Ändert die Beschreibung
     * @param des die neue Beschreibung
     */
    public void setDescription(String des) {
        description = des;
    }

    /**
     * Gibt den Geldbetrag zurück
     * @return Gibt Geldbetrag als double zurück
     */
    public double getAmount() {
        return amount;
    }
    /**
     * Gibt die Beschreibung zurück
     * @return Gibt Beschreibung als String zurück
     */
    public String getDescription() {
        return description;
    }

    /**
     * Gibt das Datum als String in dem Format DD.MM.YYYY zurück
     * @return Datum im Format DD.MM.YYYY
     */
    public String getDate() {
        return date;
    }

    /**
     * Konstruktor mit drei Argumenten
     * @param dat Datum des Eingangs der Transaktion
     * @param am Geldbetrag der Transaktion
     * @param des Verwendungszweck
     */
    public Transaction (String dat, double am, String des) {
        setDate(dat);
        setAmount(am);
        setDescription(des);
    }

    /**
     * Erzeugt einen String mit allen Argumenten aneinander gehangen
     * @return Gibt String mit allen Argumenten zurück
     */
    @Override
    public String toString() {
        return ( ("Datum: "+getDate())
                + " " + ("Geldmenge: "+getAmount())
                + " " + ("Beschreibung: "+getDescription()) );
    }

    /**
     * vergleicht die Parameter mit einer anderen Instanz auf Gleichheit
     * @param obj das zu vergleichende Objekt
     * @return Gibt True zurück bei Gleichheit
     */
    @Override
    public boolean equals(Object obj) {
        if(obj instanceof Transaction otherT) {
            return (this.getDate().equals(otherT.getDate())
                    && this.getAmount() == otherT.getAmount()
                    && this.getDescription().equals(otherT.getDescription())
            );
        }
        return false;
    }
}
