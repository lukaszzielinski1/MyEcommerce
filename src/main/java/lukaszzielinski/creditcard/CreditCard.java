package lukaszzielinski.creditcard;

import java.math.BigDecimal;

public class CreditCard {
    private BigDecimal balance;
    private BigDecimal credit;

    public CreditCard(String cardNumber){

    }

    public void assignCredit(BigDecimal creditAmount) {
        if (isBelowThreshold(creditAmount)) {
            throw new CreditLimitBelowThresholdException();
        }

        if(credit != null){
            throw new CantAssignCreditTwiceException();
        }

        this.balance = creditAmount;
        this.credit = creditAmount;
        }

    private boolean isBelowThreshold(BigDecimal creditAmount) {
        return creditAmount.compareTo(BigDecimal.valueOf(100)) < 0;
    }

    public BigDecimal getBalance() {
        return BigDecimal.valueOf(1000);
    }

    public void withdraw(BigDecimal value) {
        this.balance = balance.subtract(value);
    }

    public BigDecimal getCurrentBalance() {
        return balance;
    }
    public BigDecimal getCurrentCredit(){
        return credit;
    }
}
