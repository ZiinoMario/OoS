package bank;

import bank.exceptions.*;
import com.google.gson.*;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.nio.file.Files;
import java.nio.file.Paths;


/**
 * Simuliert eine private Bank mit festgelegten Eingangs- und Ausgangszinsen
 */
public class PrivateBank implements Bank {
    ///  Name der privaten Bank
    private String name;
    /// Zinsen bei Einzahlung in Prozent (zwischen 0 und 1)
    private double incomingInterest;
    /// Zinsen bei Auszahlung in Prozent (zwischen 0 und 1)
    private double outgoingInterest;
    /// Verknüpft Konten mit Überweisungen
    private HashMap<String, ArrayList<Transaction>> accountsToTransactions = new HashMap<String, ArrayList<Transaction>>();
    /// Speicherort der Konten
    private String directoryName;
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
    public void setIncomingInterest(double incInt) throws TransactionAttributeException {
        if (incInt < 0 || incInt > 1) {// Test ob zwischen 0 und 1
            throw new TransactionAttributeException(incInt);
        } else {
            incomingInterest = incInt;
        }
    }

    /**
     * Setzen des Auszahlungszinses
     * @param outInt neuer Zinswert
     */
    public void setOutgoingInterest(double outInt) throws TransactionAttributeException {
        if(outInt < 0 || outInt > 1) { // Test ob zwischen 0 und 1
            throw new TransactionAttributeException(outInt);
        } else {
            outgoingInterest = outInt;
        }
    }

    /// Gibt den Pfad des Ordners zurück, wo die Konten gespeichert werden
    public String getDirectoryName() {
        return directoryName;
    }

    /** Ändert den Pfad des Ordners, in welchem die Konten gespeichert sind
     * @param newDirectoryName neuer Pfad
     */
    private void setDirectoryName(String newDirectoryName) {
        directoryName = newDirectoryName;
    }

    /**
     * Deserialisiert alle persistenten Accounts ein und fügt diese in der Map accountsToTransactions hinzu
     * @throws IOException
     */
    private void readAccounts() throws IOException {
        File[] persistenteAccounts = new File(directoryName).listFiles();
        if(persistenteAccounts == null) return;

        Gson gson = new GsonBuilder()
                .registerTypeAdapter(Transaction.class, new TransactionSerializable())
                .setPrettyPrinting()
                .create();

        // Für alle Persistenten Accounts
        for(File accountDatei : persistenteAccounts)
        {
            // Lies die Accountdatei ein und parse sie zu einem JsonArray
            FileReader reader = new FileReader(
                    directoryName+accountDatei.getName()
            );
            JsonArray transaktionen = JsonParser
                    .parseReader(reader)
                    .getAsJsonArray();

            // Name des Account welcher erstellt wird
            String account = accountDatei.getName().replace("Konto ","").replace(".json","");

            // Gehe jede Transaktion in dem JsonArray durch
            try { createAccount(account); }
            catch (AccountAlreadyExistsException e) {
                System.out.println("Account doppelt: " + e.getMessage()); }

            for(JsonElement jsonTransactionElement : transaktionen.asList())
            {
                try
                {
                    addTransaction(account,gson.fromJson(jsonTransactionElement, Transaction.class));
                }
                catch (AccountDoesNotExistException | TransactionAlreadyExistException | TransactionAttributeException e)
                {
                    System.out.println("Problem beim einlesen des Accounts: " + e.getMessage());
                }
            }
            reader.close();
        }
    }

    /**
     * Serialisiert den angegebenen Account in ein JSON-Objekt
     * @param account zu serialisierender Account
     * @throws IOException
     */
    private void writeAccount(String account) throws IOException {
        Gson gson = new GsonBuilder()
                .registerTypeAdapter(IncomingTransfer.class, new TransactionSerializable())
                .registerTypeAdapter(OutgoingTransfer.class, new TransactionSerializable())
                .registerTypeAdapter(Payment.class, new TransactionSerializable())
                .setPrettyPrinting()
                .create();

        JsonArray acc = new JsonArray();

        for(Transaction t : getTransactions(account))
        {
            acc.add(gson.toJsonTree(t));
        }

        FileWriter writer = new FileWriter(
                directoryName+"Konto "+account+".json"
        );

        writer.write(gson.toJson(acc));
        writer.close();
    }

    /**
     * Konstruktor für eine private Bank
     * @param name Name der Bank
     * @param incInt Einzahlungszins
     * @param outInt Auszahlungszins
     */
    public PrivateBank(String name, double incInt, double outInt, String directory) throws TransactionAttributeException, TransactionAlreadyExistException, AccountDoesNotExistException, IOException {
        setName(name);
        setIncomingInterest(incInt);
        setOutgoingInterest(outInt);
        setDirectoryName(directory);
        readAccounts();
    }

    /**
     * Copy Konstruktor
     * @param pb PrivateBank welche kopiert werden soll
     */
    public PrivateBank(PrivateBank pb) throws TransactionAttributeException, TransactionAlreadyExistException, AccountDoesNotExistException, IOException {
        this(pb.getName(),pb.getIncomingInterest(),pb.getOutgoingInterest(),pb.getDirectoryName());
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
     * @return true bei Gleichheit aller Argumente
     */
    @Override
    public boolean equals(Object obj) {
        if(obj instanceof PrivateBank pb) {
            return ( getName().equals(pb.getName()) &&
                    getIncomingInterest() == pb.getIncomingInterest() &&
                    getOutgoingInterest() == pb.getOutgoingInterest() &&
                    this.accountsToTransactions.equals(pb.accountsToTransactions)
                    );
        }
        return false;
    }


    /**
     * Adds an account to the bank.
     *
     * @param account the account to be added
     * @throws AccountAlreadyExistsException if the account already exists
     */
    @Override
    public void createAccount(String account) throws AccountAlreadyExistsException, IOException {
        if(accountsToTransactions.containsKey(account))
            throw new AccountAlreadyExistsException(account);
        accountsToTransactions.put(account,new ArrayList<Transaction>());
        writeAccount(account);
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
    public void createAccount(String account, List<Transaction> transactions) throws AccountAlreadyExistsException, TransactionAlreadyExistException, TransactionAttributeException, IOException {
        if(accountsToTransactions.containsKey(account))
            throw new AccountAlreadyExistsException(account);
        createAccount(account);
        for(Transaction t : transactions) {
            try {
                addTransaction(account,t);
            } catch (AccountDoesNotExistException e) {
                throw new RuntimeException(e);
            }
        }
        writeAccount(account);
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
    public void addTransaction(String account, Transaction transaction) throws TransactionAlreadyExistException, AccountDoesNotExistException, TransactionAttributeException, IOException {
        if(!accountsToTransactions.containsKey(account))
            throw new AccountDoesNotExistException(account);
        if(containsTransaction(account, transaction))
            throw new TransactionAlreadyExistException(transaction.toString());
        accountsToTransactions.get(account).add(transaction);
        writeAccount(account);
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
    public void removeTransaction(String account, Transaction transaction) throws AccountDoesNotExistException, TransactionDoesNotExistException, IOException {
        if(!accountsToTransactions.containsKey(account))
            throw new AccountDoesNotExistException(account);
        if(!containsTransaction(account, transaction))
            throw new TransactionDoesNotExistException(transaction.toString());
        accountsToTransactions.get(account).remove(transaction);
        writeAccount(account);
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
            } else if (!positive && t.calculate()<0) {
                tbt.add(t);
            }
        }
        return tbt;
    }

    /**
     * Deletes a given account
     *
     * @param account the selected account
     * @throws AccountDoesNotExistException if the specified account does not exist
     */
    @Override
    public void deleteAccount(String account) throws AccountDoesNotExistException, IOException {
        if(!getAllAccounts().contains(account))
            throw new AccountDoesNotExistException(account);
        accountsToTransactions.remove(account);
        File file = new File(getDirectoryName() + "Konto " + account + ".json");
        if(!file.delete())
            throw new IOException();
    }

    /**
     * Returns a list of all accounts
     *
     * @return the list of all accounts
     */
    @Override
    public List<String> getAllAccounts() {
        return accountsToTransactions.keySet().stream().toList();
    }
}
