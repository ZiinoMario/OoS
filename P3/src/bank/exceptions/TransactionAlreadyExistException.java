package bank.exceptions;

public class TransactionAlreadyExistException extends RuntimeException {
    public TransactionAlreadyExistException(String message) {
        super("Die Transaktion existiert bereits: " + message);
    }
}
