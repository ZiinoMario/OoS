package bank.exceptions;

///  Exception falls Account bereits existiert
public class AccountAlreadyExistsException extends RuntimeException {
    public AccountAlreadyExistsException(String message) {
        super(message + " hat schon ein Konto");
    }
}
