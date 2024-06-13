package com.example.homework_module3.Homework01.DAO;

import com.example.homework_module3.Homework01.domain.Account;
import com.example.homework_module3.Homework01.domain.Customer;
import com.fasterxml.jackson.annotation.JsonIgnore;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Repository
public class AccountDaoImpl implements Dao<Account> {
    @JsonIgnore

    private final List<Account> allAccounts = new ArrayList<>();
    private Long currentId = 0L;


    @Override
    public Account save(Account account) {
        Optional<Account> existingAccount = allAccounts.stream()
                .filter(a -> a.getNumber().equals(account.getNumber()))
                .findFirst();

        if (existingAccount.isPresent()) {
            Account existing = existingAccount.get();
            existing.setBalance(account.getBalance());
            Customer customer = existing.getCustomer();
            Optional<Account> customerAcc = customer.getAccounts().stream()
                    .filter(a -> a.getId() == existing.getId())
                    .findFirst();
            customerAcc.ifPresent(value -> value.setBalance(account.getBalance()));
            return existing;
        } else {
            account.setId(currentId + 1);
            incrementId();
            allAccounts.add(account);
            return account;
        }
    }

    @Override
    public boolean delete(Account account) {
        return allAccounts.remove(account);
    }

    @Override
    public void deleteAll(List<Account> entities) {
        allAccounts.removeAll(entities);
    }

    @Override
    public void saveAll(List<Account> entities) {
        for (Account entity : entities) {
            if (!allAccounts.contains(entity)) {
                save(entity);
            }
        }
    }

    @Override
    public List<Account> findAll() {
        return allAccounts;
    }

    @Override
    public boolean deleteById(long id) {
        Account account = getOne(id);
        if (account != null) {
            allAccounts.remove(account);
            return true;
        }
        return false;
    }

    @Override
    public Account getOne(long id) {
        return allAccounts.stream()
                .filter(a -> a.getId() == id)
                .findFirst()
                .orElse(null);
    }

    public Account findById(Long id) {
        return allAccounts.stream()
                .filter(account -> account.getId() == id)
                .findFirst()
                .orElse(null);
    }

    public Account findByNumber(String number) {
        return allAccounts.stream()
                .filter(account -> account.getNumber().equals(number))
                .findFirst()
                .orElse(null);
    }

    public void incrementId() {
        currentId += 1;
    }

}
