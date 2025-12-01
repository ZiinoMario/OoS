package bank.exceptions;

///  Exception falls die Transaktion bereits existiert
public class TransactionAlreadyExistException extends Exception {
    public TransactionAlreadyExistException(String message) {
        super("Die Transaktion existiert bereits: " + message);
    }
}
