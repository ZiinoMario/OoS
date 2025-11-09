package bank.exceptions;

public class TransactionAttributeException extends RuntimeException {
    public TransactionAttributeException(double message) {
        super(message + " ist kein gültiger Wert");
    }
}
