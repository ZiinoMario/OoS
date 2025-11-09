package bank.exceptions;

///  Exception falls die Transaktion bereits existiert
public class TransactionAlreadyExistException extends RuntimeException {
    public TransactionAlreadyExistException(String message) {
        super("Die Transaktion existiert bereits: " + message);
    }
}
