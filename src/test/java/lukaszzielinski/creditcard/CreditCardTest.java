package lukaszzielinski.creditcard;

import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.*;

public class CreditCardTest {

    @Test
    void itAllowsToAssignCreditLimit(){
        //Arrange
        CreditCard card1 = new CreditCard("1234-5678");
        CreditCard card2 = new CreditCard("1234-5679");
        //Act
        card1.assignCredit(BigDecimal.valueOf(1000));
        card2.assignCredit(BigDecimal.valueOf(1100));
        //Assert
        assertEquals(BigDecimal.valueOf(1000), card1.getBalance());
        assertEquals(BigDecimal.valueOf(1000), card2.getBalance());
    }

    @Test
    void itCantAssignLimitBelowCertainThreshold(){
        CreditCard card1 = new CreditCard("1234-5678");

        try {
            card1.assignCredit(BigDecimal.valueOf(10));
            fail("Should throw exception");
        } catch (CreditLimitBelowThresholdException e) {
            assertTrue(true); }
    
            assertThrows(CreditLimitBelowThresholdException.class,
                () -> card1.assignCredit(BigDecimal.valueOf(10)));

            assertThrows(CreditLimitBelowThresholdException.class,
                () -> card1.assignCredit(BigDecimal.valueOf(99)));

            assertDoesNotThrow(
                () -> card1.assignCredit(BigDecimal.valueOf(100)));
    }

    @Test
    void checkDoublesAndFloats(){
        double x1 = 0.01;
        double x2 = 0.03;
        double result = x2 - x1;

        float y1 = 0.01f;
        float y2 = 0.03f;
        float yresult = y2 - y1;
    }
}
