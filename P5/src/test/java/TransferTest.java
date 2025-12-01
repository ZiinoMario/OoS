import bank.IncomingTransfer;
import bank.OutgoingTransfer;
import bank.Transfer;
import bank.exceptions.*;
import org.junit.jupiter.api.*;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import static org.junit.jupiter.api.Assertions.*;


public class TransferTest {

    private Transfer transferTest;

    @BeforeEach
    public void init() throws TransactionAttributeException {
        transferTest = new Transfer("07.11.2025",20,"Taschengeld","Voss","Tutor");
    }

    @Test
    public void testConstructorThreeArguments() {
        assertDoesNotThrow(
                () -> {
                    Transfer tt = new Transfer("10.11.2025", 50,"Tgeld");
                    assertEquals("10.11.2025",tt.getDate());
                    assertEquals(50,tt.getAmount());
                    assertEquals("Tgeld",tt.getDescription());
                }
        );

    }

    @Test
    public void testConstructorAllArguments() {
        assertEquals("07.11.2025",transferTest.getDate());
        assertEquals(20,transferTest.getAmount());
        assertEquals("Taschengeld",transferTest.getDescription());
        assertEquals("Voss",transferTest.getSender());
        assertEquals("Tutor",transferTest.getRecipient());
    }

    @DisplayName("Testet Exceptions bei ungültigen Amountwerten")
    @ParameterizedTest
    @ValueSource(doubles = {-0.1,-578931,-2,-789348.59})
    public void testThrowsTransactionAttributeException(double amount) {
        assertThrows(TransactionAttributeException.class,
                () -> new Transfer("23.11.2025",amount,"Test","Voss","Tutor")
        );
    }

    @DisplayName("Testet korrekte Interestwerte")
    @ParameterizedTest
    @ValueSource(doubles = {0,1.20,20.75,385071283})
    public void testDoesNotThrowTransactionAttributeException(double amount) {
        assertDoesNotThrow(
                () -> new Transfer("23.11.2025",amount,"Test","Voss","Tutor")
        );
    }


    @Test
    public void testCopyConstructor() {
        assertDoesNotThrow(
                () -> {
                    Transfer copyTransfer = new Transfer(transferTest);
                    assertEquals(copyTransfer, transferTest);
                }
        );
    }

    @Test
    public void testCalculate() {
        assertDoesNotThrow(
                () -> {
                    IncomingTransfer incTrans = new IncomingTransfer(
                            transferTest.getDate(),
                            transferTest.getAmount(),
                            transferTest.getDescription()
                    );
                    OutgoingTransfer outTrans = new OutgoingTransfer(
                            transferTest.getDate(),
                            transferTest.getAmount(),
                            transferTest.getDescription()
                    );
                    assertEquals(20, incTrans.calculate());
                    assertEquals(-20, outTrans.calculate());
                }
        );
    }

    @Test
    public void testEquals() {
        assertEquals(transferTest, transferTest);
        assertDoesNotThrow(
            () -> {
                assertEquals(transferTest, new Transfer(transferTest));
            }
        );
    }

    @Test
    public void testToString() {
        assertEquals(
                "Datum: 07.11.2025 ;Rechnung: 20.0 ;Beschreibung: Taschengeld ;Sender: Voss ;Empfänger: Tutor",
                transferTest.toString()
        );
    }
}
