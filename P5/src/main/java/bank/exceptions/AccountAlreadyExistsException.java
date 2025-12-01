package bank.exceptions;

///  Exception falls Account bereits existiert
public class AccountAlreadyExistsException extends Exception {
    public AccountAlreadyExistsException(String message) {
        super(message + " hat schon ein Konto");
    }
}
