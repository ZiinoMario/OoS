package bank.exceptions;

///  Exception falls die angegebene Transaktion nicht existiert
public class TransactionDoesNotExistException extends Exception {
    public TransactionDoesNotExistException(String message) {
        super("Diese Transaktion existiert nicht: " + message);
    }
}
