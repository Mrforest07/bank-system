
package org.example;

import java.math.BigDecimal;

public class Account {

    private int accountId;
    private String holderName;
    private BigDecimal balance;

    public Account(int accountId, String holderName, BigDecimal balance) {
        this.accountId = accountId;
        this.holderName = holderName;
        this.balance = balance;
    }

    public int getAccountId() {
        return accountId;
    }

    public String getHolderName() {
        return holderName;
    }

    public BigDecimal getBalance() {
        return balance;
    }

    @Override
    public String toString() {
        return "Account{" +
                "accountId=" + accountId +
                ", holderName='" + holderName + '\'' +
                ", balance=" + balance +
                '}';
    }
}
