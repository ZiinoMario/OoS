package bank.exceptions;

/// Exception falls Account nicht existiert
public class AccountDoesNotExistException extends Exception {
    public AccountDoesNotExistException(String message) {
        super(message + " hat keinen Account");
    }
}
