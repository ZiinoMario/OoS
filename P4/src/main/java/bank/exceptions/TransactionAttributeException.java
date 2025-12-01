package bank.exceptions;

///  Exception bei einem Fehler in den Attributen der Transaktion
public class TransactionAttributeException extends Exception {
    public TransactionAttributeException(double message) {
        super(message + " ist kein gültiger Wert");
    }
}
