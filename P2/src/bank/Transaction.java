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

    // Setter
    public void setDate(String d) {
        date = d;
    }
    public void setAmount(double a) {
        amount=a;
    }
    public void setDescription(String des) {
        description = des;
    }

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
