package lukaszzielinski.creditcard;

import java.math.BigDecimal;

public class CreditCard {
    private BigDecimal balance;
    private BigDecimal credit;
    private BigDecimal debt = BigDecimal.valueOf(0);
    private BigDecimal limit;


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

    public void repay(BigDecimal repayAmount){
        debt = debt.subtract(repayAmount);
    }

    public BigDecimal getLimit() {
        return limit;
    }

    public BigDecimal getDebt() {
        return debt;
    }

    public void setBalance(BigDecimal balance) {
        this.balance = balance;
    }

    public BigDecimal getCurrentBalance() {
        return balance;
    }
    public BigDecimal getCurrentCredit(){
        return credit;
    }
}
