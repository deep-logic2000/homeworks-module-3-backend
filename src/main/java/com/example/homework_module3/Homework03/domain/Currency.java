package com.example.homework_module3.Homework03.domain;

public enum Currency {
    USD("USD"),
    EUR("EUR"),
    UAH("UAH"),
    CHF("CHF"),
    GBP("GBP");

    public String currency;

    Currency(String currency) {
        this.currency = currency;
    }
}
