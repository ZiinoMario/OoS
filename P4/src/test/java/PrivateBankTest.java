import bank.*;
import bank.exceptions.*;
import org.junit.jupiter.api.*;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Comparator;

import static org.junit.jupiter.api.Assertions.*;

public class PrivateBankTest {

    private final String DIRECTORY = ".\\testAccounts\\";
    private PrivateBank pb;
    private IncomingTransfer incTrans;
    private OutgoingTransfer outTrans;
    private Payment p;


    @BeforeEach
    public void init() throws TransactionAlreadyExistException, AccountDoesNotExistException, TransactionAttributeException, IOException, AccountAlreadyExistsException {
        pb = new PrivateBank("Oeffentliche Bank",0.1,0.1,DIRECTORY);
        pb.createAccount("Test");
    }

    public void addTestTransactions() throws TransactionAttributeException, TransactionAlreadyExistException, AccountDoesNotExistException, IOException {
        incTrans = new IncomingTransfer("24.11.2025",80,"Beschreibung","Irgendwer","Test");
        pb.addTransaction("Test",incTrans);
        outTrans = new OutgoingTransfer("24.11.2025",30,"Beschreibung","Test","Irgendwen");
        pb.addTransaction("Test",outTrans);
        p = new Payment("24.11.2025",50,"Beschreibung");
        pb.addTransaction("Test",p);
    }

    @Test
    public void testConstructor() {
        assertDoesNotThrow(
            () -> {
                PrivateBank test = new PrivateBank("Oeffentliche Bank",0.1,0.1,DIRECTORY);
                PrivateBank copy = new PrivateBank(test);
                assertEquals("Oeffentliche Bank",test.getName());
                assertEquals(0.1,test.getIncomingInterest());
                assertEquals(0.1,test.getOutgoingInterest());
                assertEquals(DIRECTORY,test.getDirectoryName());
                assertEquals("Oeffentliche Bank",copy.getName());
                assertEquals(0.1,copy.getIncomingInterest());
                assertEquals(0.1,copy.getOutgoingInterest());
                assertEquals(DIRECTORY,copy.getDirectoryName());
            }
        );
    }

    @Test
    public void testReadAccounts() {
        assertDoesNotThrow(
                () -> {
                    addTestTransactions();
                    PrivateBank test = new PrivateBank("Oeffentliche Bank",0.2,0.2,DIRECTORY);
                    assertTrue(test.containsTransaction("Test",p));
                    assertTrue(test.containsTransaction("Test",incTrans));
                    assertTrue(test.containsTransaction("Test",outTrans));
                }

        );

    }

    @Test
    public void testCreateAccount() {
        assertDoesNotThrow(
            () -> {
                pb.createAccount("Voss");
            }
        );
        assertThrows(
                AccountAlreadyExistsException.class,
                () -> pb.createAccount("Voss")
        );
    }

    @Test
    public void testCreateAccountWithTransactions() {
        ArrayList<Transaction> transactions = new ArrayList<>();
        assertDoesNotThrow(
                () -> {
                    addTestTransactions();

                    transactions.add(incTrans);
                    transactions.add(outTrans);
                    transactions.add(p);


                    pb.createAccount("Voss",transactions);
                    assertTrue(pb.containsTransaction("Voss",incTrans));
                    assertTrue(pb.containsTransaction("Voss",outTrans));
                    assertTrue(pb.containsTransaction("Voss",p));
                }
        );
        assertThrows(
                AccountAlreadyExistsException.class,
                () -> pb.createAccount("Voss",transactions)
        );
    }

    @Test
    public void testAddTransaction() {
        assertDoesNotThrow(
            () -> {
                addTestTransactions();
            }
        );
        assertThrows(
                AccountDoesNotExistException.class,
                () -> pb.addTransaction("GibtsNicht",p)
        );
        assertThrows(
                TransactionAlreadyExistException.class,
                () -> pb.addTransaction("Test",p)
        );
    }

    @Test
    public void testRemoveTransaction() {
        assertDoesNotThrow(
            () -> {
                addTestTransactions();
                pb.getTransactions("Test");
                pb.removeTransaction("Test",pb.getTransactions("Test").getFirst());
                pb.removeTransaction("Test",pb.getTransactions("Test").getFirst());
                pb.removeTransaction("Test",pb.getTransactions("Test").getFirst());
            }
        );
        assertThrows(TransactionDoesNotExistException.class,
            () -> pb.removeTransaction("Test", incTrans)
        );
        assertThrows(AccountDoesNotExistException.class,
                () -> pb.removeTransaction("Gibtsnicht",p)
        );
    }

    @Test
    public void testContainsTransaction() {
        assertDoesNotThrow(
                () -> {
                    addTestTransactions();
                    pb.containsTransaction("Test",incTrans);
                    pb.containsTransaction("Test",outTrans);
                    pb.containsTransaction("Test",p);
                    pb.removeTransaction("Test",incTrans);
                }
        );
        assertFalse(
                pb.containsTransaction("Test",incTrans)
        );
    }

    @Test
    public void testGetAccountBalance() {
        assertDoesNotThrow(
                () -> {
                    addTestTransactions();
                    assertEquals(80-30+45, pb.getAccountBalance("Test"));
                }
        );
    }

    @Test
    public void testGetTransactions() {
        assertDoesNotThrow(
                () -> {
                    addTestTransactions();

                    ArrayList<Transaction> testTransactions = new ArrayList<Transaction>();
                    testTransactions.add(incTrans);
                    testTransactions.add(outTrans);
                    testTransactions.add(p);
                    testTransactions.sort(Comparator.comparing(Transaction::calculate));
                    assertEquals(
                            testTransactions,
                            pb.getTransactionsSorted("Test",true)
                    );
                    assertEquals(
                            testTransactions.reversed(),
                            pb.getTransactionsSorted("Test",false)
                    );
                }
        );
    }

    @Test
    public void testGetSortedTransactions() {
        assertDoesNotThrow(
                () -> {
                    addTestTransactions();
                    assertEquals("[Datum: 24.11.2025 ;Rechnung: -30.0 ;Beschreibung: Beschreibung ;Sender: Test ;Empfänger: Irgendwen," +
                                    " Datum: 24.11.2025 Rechnung: 45.0 Beschreibung: Beschreibung Einzahlungszinsen: 0.1 Auszahlungszinsen: 0.1," +
                                    " Datum: 24.11.2025 ;Rechnung: 80.0 ;Beschreibung: Beschreibung ;Sender: Irgendwer ;Empfänger: Test]",
                            pb.getTransactionsSorted("Test",true).toString());
                    assertEquals("[Datum: 24.11.2025 ;Rechnung: 80.0 ;Beschreibung: Beschreibung ;Sender: Irgendwer ;Empfänger: Test," +
                                    " Datum: 24.11.2025 Rechnung: 45.0 Beschreibung: Beschreibung Einzahlungszinsen: 0.1 Auszahlungszinsen: 0.1," +
                                    " Datum: 24.11.2025 ;Rechnung: -30.0 ;Beschreibung: Beschreibung ;Sender: Test ;Empfänger: Irgendwen]",
                            pb.getTransactionsSorted("Test",false).toString());
                }
        );
    }

    @Test
    public void testGetTransactionsByType() {
        assertDoesNotThrow(
                () -> {
                    addTestTransactions();
                    ArrayList<Transaction> transactionsByType = (ArrayList<Transaction>) pb.getTransactionsByType("Test",true);
                    assertTrue(transactionsByType.contains(p));
                    assertTrue(transactionsByType.contains(incTrans));
                    assertFalse(transactionsByType.contains(outTrans));
                    transactionsByType = (ArrayList<Transaction>) pb.getTransactionsByType("Test",false);
                    assertFalse(transactionsByType.contains(p));
                    assertFalse(transactionsByType.contains(incTrans));
                    assertTrue(transactionsByType.contains(outTrans));
                }
        );
    }

    @Test
    public void testEquals() {
        assertDoesNotThrow(
                () -> {
                    PrivateBank copy = new PrivateBank(pb);
                    assertEquals(copy,pb);
                }
        );
        assertEquals(pb,pb);
        try {
            PrivateBank ungleich = new PrivateBank("Andere Bank",1,1,".\\testAccounts\\");
            assertNotEquals(ungleich,pb);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    public void testToString() {
        assertEquals(
            "Name: Oeffentliche Bank IncomingInterest: 0.1 OutgoingInterest: 0.1",
            pb.toString()
        );
    }

    @AfterEach
    public void cleanUp() {
        File[] persistierterAccounts = new File(DIRECTORY).listFiles();
        for( File account : persistierterAccounts ) {
            account.delete();
        }
    }

}
