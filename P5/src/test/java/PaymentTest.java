import bank.Payment;
import bank.exceptions.*;
import org.junit.jupiter.api.*;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import static org.junit.jupiter.api.Assertions.*;


public class PaymentTest {

    private Payment positivePayment;

    @BeforeEach
    public void init() throws TransactionAttributeException {
        positivePayment = new Payment("23.11.2025",100,"Einzahlung",0.5,0.5);
    }

    @Test
    public void testConstructorThreeArguments() {
        assertDoesNotThrow(
                () -> new Payment("23.11.2025",100,"Einzahlung")
        );
        assertEquals("23.11.2025",positivePayment.getDate());
        assertEquals(100,positivePayment.getAmount());
        assertEquals("Einzahlung",positivePayment.getDescription());
    }

    @Test
    public void testConstructorAllArguments() {
        assertDoesNotThrow(
                () -> new Payment("23.11.2025",100,"Einzahlung",0.5,0.5)
        );
    }

    @DisplayName("Testet Exceptions bei ungültigen Interestwerte")
    @ParameterizedTest
    @ValueSource(doubles = {1.1,-0.1,20000,-578931})
    public void testThrowsTransactionAttributeException(double interest) {
        assertThrows(TransactionAttributeException.class,
                () -> new Payment("23.11.2025",100,"Test",interest,0.5)
        );
        assertThrows(TransactionAttributeException.class,
                () -> new Payment("23.11.2025",100,"Test",0.5,interest)
        );
    }

    @DisplayName("Testet korrekte Interestwerte")
    @ParameterizedTest
    @ValueSource(doubles = {0,0.5314,1,0.99999,0.000001})
    public void testDoesNotThrowTransactionAttributeException(double interest) {
        assertDoesNotThrow(
                () -> new Payment("23.11.2025",100,"Test",interest,0.5)
        );
        assertDoesNotThrow(
                () -> new Payment("23.11.2025",100,"Test",0.5,interest)
        );
    }


    @Test
    public void testCopyConstructor() {
        assertDoesNotThrow(
                () -> {
                    Payment copyPayment = new Payment(positivePayment);
                    assertEquals(copyPayment, positivePayment);
                }
        );
    }

    @Test
    public void testCalculate() {
        assertEquals(50, positivePayment.calculate());
        assertDoesNotThrow(
                ()-> {
                    Payment negativePayment = new Payment("23.11.2025",-100,"Einzahlung",0.5,0.5);
                    assertEquals(-150,negativePayment.calculate());
                }
        );
    }

    @Test
    public void testIncomingInterest() {
        assertEquals(0.5, positivePayment.getIncomingInterest());
    }

    @Test
    public void testOutgoingInterest() {
        assertEquals(0.5, positivePayment.getOutgoingInterest());
    }

    @Test
    public void testEquals() {
        assertEquals(positivePayment,positivePayment);
    }

    @Test
    public void testToString() {
        assertEquals(
                "Datum: 23.11.2025 Rechnung: 50.0 Beschreibung: Einzahlung Einzahlungszinsen: 0.5 Auszahlungszinsen: 0.5",
                positivePayment.toString()
        );
    }
}
