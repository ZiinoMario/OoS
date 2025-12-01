package bank;

import bank.exceptions.TransactionAttributeException;

/**
 * Die abstrakte Oberklasse stellt eine Transaktion dar
 * Da hierbei zwingend eine Rechnung erforderlich ist, implementiert die Klasse das Interface {@link CalculateBill}
 * @see CalculateBill
 */
public abstract class Transaction implements CalculateBill {

    /// Datum der Ein- oder Auszahlung
    private String date; // DD.MM.YYYY
    /// Geldmenge
    protected double amount;
    /// Zusätzliche Beschreibung
    private String description;

    /**
     * Ändert das Datum
     * @param d das neue Datum
     */
    public void setDate(String d) {
        date = d;
    }

    /**
     * Ändert den Geldbetrag
     * @param a der neue Geldbetrag
     */
    public void setAmount(double a) throws TransactionAttributeException {
        amount = a;
    }

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
    public Transaction (String dat, double am, String des) throws TransactionAttributeException {
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
                + " " + (";Geldmenge: "+getAmount())
                + " " + (";Beschreibung: "+getDescription()) );
    }

    /**
     * vergleicht die Parameter mit einer anderen Instanz auf Gleichheit
     * @param obj das zu vergleichende Objekt
     * @return Gibt True zurück bei Gleichheit
     */
    @Override
    public boolean equals(Object obj) {
        if(obj instanceof Transaction otherT) { // Vergleich ob otherT auch von der Klasse Transaction ist
            return (this.getDate().equals(otherT.getDate()) // Vergleich der Attribute
                    && Double.compare(this.getAmount(), otherT.getAmount())==0
                    && this.getDescription().equals(otherT.getDescription())
            );
        }
        return false;
    }
}
