package com.example.homework_module3.Homework02.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.Objects;
import java.util.UUID;

@Entity
@Getter
@Setter
@Table(name = "accounts")
public class Account extends AbstractEntity {
    @Column(nullable = false, unique = true)
    private String number = UUID.randomUUID().toString();
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Currency currency;
    @Column(nullable = false)
    private Double balance = 0.0;
    @ManyToOne
    @JoinColumn(name = "customer_id")
    @JsonIgnore
    private Customer customer;

    public Account() {
    }

    public Account(Currency currency) {
        this.currency = currency;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Account account = (Account) o;
        return Objects.equals(getId(), account.getId()) && Objects.equals(number, account.number) && currency == account.currency && Objects.equals(balance, account.balance);
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId(), number, currency, balance);
    }

    @Override
    public String toString() {
        return "Account{" +
                "id=" + getId() +
                ", number='" + number + '\'' +
                ", currency=" + currency +
                ", balance=" + balance +
//                ", customer=" + customer +
                '}';
    }
}
