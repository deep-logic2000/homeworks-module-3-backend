package com.example.homework_module3.Homework02.Service;

import com.example.homework_module3.Homework02.domain.Account;

import java.util.List;

public interface AccountService {
    Account save(Account account);
    boolean delete(Account account);
    void deleteAll(List<Account> entities);
    void saveAll(List<Account> entities);
    List<Account> findAll();
    boolean deleteById(long id);
    Account getOne(Long id);
    Account deposit(Long id, Double amount);
    Account withdraw(Long id, double amount);
    List<Account> transfer(String fromAccountNumber, String toAccountNumber, Double amount);
}
