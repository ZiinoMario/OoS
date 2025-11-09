package bank;

import bank.exceptions.*;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;

public class PrivateBank implements Bank {
    ///  Name der privaten Bank
    private String name;
    /// Zinsen bei Einzahlung in Prozent (zwischen 0 und 1)
    private double incomingInterest;
    /// Zinsen bei Auszahlung in Prozent (zwischen 0 und 1)
    private double outgoingInterest;
    /// Verknüpft Konten mit Überweisungen
    private HashMap<String, ArrayList<Transaction>> accountsToTransactions = new HashMap<String, ArrayList<Transaction>>();

    /**
     * Rückgabe des Namens der privaten Bank
     * @return Name
     */
    public String getName() {
        return name;
    }

    /**
     * Ändert den Namen der privaten Bank
     * @param name neuer Name
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Rückgabe des Einzahlungszinses
     * @return Einzahlungszinses
     */
    public double getIncomingInterest() {
        return incomingInterest;
    }

    /**
     * Rückgabe des Auszahlungszinses
     * @return Auszahlungszinses
     */
    public double getOutgoingInterest() {
        return outgoingInterest;
    }

    /**
     * Setzen des Einzahlungszinses
     * @param incInt neuer Zinswert
     */
    public void setIncomingInterest(double incInt) {
        if (incInt < 0 || incInt > 1) {// Test ob zwischen 0 und 1
            System.out.println("Zinswert nicht okay");
        } else {
            incomingInterest = incInt;
        }
    }

    /**
     * Setzen des Auszahlungszinses
     * @param outInt neuer Zinswert
     */
    public void setOutgoingInterest(double outInt) {
        if(outInt < 0 || outInt > 1) { // Test ob zwischen 0 und 1
            System.out.println("Zinswert nicht okay");
        } else {
            outgoingInterest = outInt;
        }
    }

    /**
     * Konstruktor für eine private Bank
     * @param n Name der Bank
     * @param incInt Einzahlungszins
     * @param outInt Auszahlungszins
     */
    public PrivateBank(String n, double incInt, double outInt) {
        setName(n);
        setIncomingInterest(incInt);
        setOutgoingInterest(outInt);
    }

    /**
     * Copy Konstruktor
     * @param pb Element welches kopiert werden soll
     */
    public PrivateBank(PrivateBank pb) {
        this(pb.getName(),pb.getIncomingInterest(),pb.getOutgoingInterest());
    }

    /**
     * Gibt alle Parameter in einem String zurück
     * @return String mit allen Parametern
     */
    @Override
    public String toString() {
        return "Name: " + getName() + " IncomingInterest: " + getIncomingInterest() + " OutgoingInterest: " + getOutgoingInterest();
    }

    /**
     * Vergleicht bei zwei Instanzen alle Parameter auf Gleichheit
     * @param obj   the reference object with which to compare.
     * @return
     */
    @Override
    public boolean equals(Object obj) {
        /*if(obj instanceof PrivateBank pb) {
            return ( getName().equals(pb.getName()) &&
                    getIncomingInterest() == pb.getIncomingInterest() &&
                    getOutgoingInterest() == pb.getOutgoingInterest() &&
                    this.accountsToTransactions.equals(pb.accountsToTransactions)
                    );
        }*/
        return false;
    }


    /**
     * Adds an account to the bank.
     *
     * @param account the account to be added
     * @throws AccountAlreadyExistsException if the account already exists
     */
    @Override
    public void createAccount(String account) throws AccountAlreadyExistsException {
        if(accountsToTransactions.containsKey(account))
            throw new AccountAlreadyExistsException(account);
        accountsToTransactions.put(account,new ArrayList<Transaction>());
    }

    /**
     * Adds an account (with specified transactions) to the bank.
     * Important: duplicate transactions must not be added to the account!
     *
     * @param account      the account to be added
     * @param transactions a list of already existing transactions which should be added to the newly created account
     * @throws AccountAlreadyExistsException    if the account already exists
     * @throws TransactionAlreadyExistException if the transaction already exists
     * @throws TransactionAttributeException    if the validation check for certain attributes fail
     */
    @Override
    public void createAccount(String account, List<Transaction> transactions) throws AccountAlreadyExistsException, TransactionAlreadyExistException, TransactionAttributeException {
        if(accountsToTransactions.containsKey(account))
            throw new AccountAlreadyExistsException(account);
        createAccount(account);
        for(Transaction t : transactions) {
            addTransaction(account,t);
        }
    }

    /**
     * Adds a transaction to an already existing account.
     *
     * @param account     the account to which the transaction is added
     * @param transaction the transaction which should be added to the specified account
     * @throws TransactionAlreadyExistException if the transaction already exists
     * @throws AccountDoesNotExistException     if the specified account does not exist
     * @throws TransactionAttributeException    if the validation check for certain attributes fail
     */
    @Override
    public void addTransaction(String account, Transaction transaction) throws TransactionAlreadyExistException, AccountDoesNotExistException, TransactionAttributeException {
        if(!accountsToTransactions.containsKey(account))
            throw new AccountDoesNotExistException(account);
        if(containsTransaction(account, transaction))
            throw new TransactionAlreadyExistException(transaction.toString());
        /*if(transaction instanceof Transfer)
            if(transaction.getAmount() < 0)
                throw new TransactionAttributeException(transaction.getAmount());
        if (transaction instanceof Payment p) {
            p.setIncomingInterest(this.getIncomingInterest());
            p.setOutgoingInterest(this.getOutgoingInterest());
        }*/
        accountsToTransactions.get(account).add(transaction);
    }

    /**
     * Removes a transaction from an account. If the transaction does not exist, an exception is
     * thrown.
     *
     * @param account     the account from which the transaction is removed
     * @param transaction the transaction which is removed from the specified account
     * @throws AccountDoesNotExistException     if the specified account does not exist
     * @throws TransactionDoesNotExistException if the transaction cannot be found
     */
    @Override
    public void removeTransaction(String account, Transaction transaction) throws AccountDoesNotExistException, TransactionDoesNotExistException {
        if(!accountsToTransactions.containsKey(account))
            throw new AccountDoesNotExistException(account);
        if(!containsTransaction(account, transaction))
            throw new TransactionDoesNotExistException(transaction.toString());
        accountsToTransactions.get(account).remove(transaction);
    }

    /**
     * Checks whether the specified transaction for a given account exists.
     *
     * @param account     the account from which the transaction is checked
     * @param transaction the transaction to search/look for
     */
    @Override
    public boolean containsTransaction(String account, Transaction transaction) {
        return accountsToTransactions.get(account).contains(transaction);
    }

    /**
     * Calculates and returns the current account balance.
     *
     * @param account the selected account
     * @return the current account balance
     */
    @Override
    public double getAccountBalance(String account) {
        double sum=0;
        for(Transaction t : getTransactions(account)) {
            sum += t.calculate();
        }
        return sum;
    }

    /**
     * Returns a list of transactions for an account.
     *
     * @param account the selected account
     * @return the list of all transactions for the specified account
     */
    @Override
    public List<Transaction> getTransactions(String account) {
        return accountsToTransactions.get(account);
    }

    /**
     * Returns a sorted list (-> calculated amounts) of transactions for a specific account. Sorts the list either in ascending or descending order
     * (or empty).
     *
     * @param account the selected account
     * @param asc     selects if the transaction list is sorted in ascending or descending order
     * @return the sorted list of all transactions for the specified account
     */
    @Override
    public List<Transaction> getTransactionsSorted(String account, boolean asc) {
        getTransactions(account).sort(Comparator.comparing(Transaction::calculate));
        return asc ? getTransactions(account) : getTransactions(account).reversed();
    }

    /**
     * Returns a list of either positive or negative transactions (-> calculated amounts).
     *
     * @param account  the selected account
     * @param positive selects if positive or negative transactions are listed
     * @return the list of all transactions by type
     */
    @Override
    public List<Transaction> getTransactionsByType(String account, boolean positive) {
        ArrayList<Transaction> tbt = new ArrayList<Transaction>();
        for(Transaction t : getTransactions(account)) {
            if(positive && t.calculate()>0) {
                tbt.add(t);
            } else {
                tbt.add(t);
            }
        }
        return tbt;
    }
}
