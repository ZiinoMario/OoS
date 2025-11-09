package bank.exceptions;

/// Exception falls Account nicht existiert
public class AccountDoesNotExistException extends RuntimeException {
    public AccountDoesNotExistException(String message) {
        super(message + " hat keinen Account");
    }
}
