package com.example.homework_module3.Homework03.domain.dto;

import com.example.homework_module3.Homework03.domain.Currency;

public class AccountDto {

    private Currency currency;
    private double balance;

    public Currency getCurrency() {
        return currency;
    }

    public void setCurrency(Currency currency) {
        this.currency = currency;
    }

    public double getBalance() {
        return balance;
    }

    public void setBalance(double balance) {
        this.balance = balance;
    }
}
