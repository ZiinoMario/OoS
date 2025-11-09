package bank;

public class OutgoingTransfer extends Transfer {

    /**
     * Konstruktor mit drei Argumenten
     *
     * @param dat Datum des Eingangs der Transaktion
     * @param am  Geldbetrag der Transaktion
     * @param des Verwendungszweck
     */
    public OutgoingTransfer(String dat, double am, String des) {
        super(dat, am, des);
    }

    /**
     * Konstruktor mit allen Parametern
     * @param dat Datum an dem die Überweisung eingegangen ist
     * @param am Geldbetrag der Überweisung
     * @param des Verwendungszweck
     * @param sen Sender
     * @param rec Empfänger
     */
    public OutgoingTransfer(String dat, double am, String des, String sen, String rec) {
        super(dat,am,des,sen,rec);
    }

    /**
     * Führt die Berechnung für eine Rechnung durch
     *
     * @return Gibt das Ergebnis der Berechnung zurück
     */
    @Override
    public double calculate() {
        return -getAmount();
    }
}
