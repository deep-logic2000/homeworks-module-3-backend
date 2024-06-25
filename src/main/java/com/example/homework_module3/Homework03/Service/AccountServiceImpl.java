package com.example.homework_module3.Homework03.Service;

import com.example.homework_module3.Homework03.DAO.AccountJpaRepository;
import com.example.homework_module3.Homework03.domain.Account;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Slf4j
@Transactional
public class AccountServiceImpl implements AccountService {

    AccountJpaRepository accountJpaRepository;

    @Autowired
    public AccountServiceImpl(AccountJpaRepository accountJpaRepository) {
        this.accountJpaRepository = accountJpaRepository;
    }

    @Override
    public Account save(Account account) {
        return accountJpaRepository.save(account);
    }

    @Override
    public boolean delete(Account account) {
        if (accountJpaRepository.existsById(account.getId())) {
            accountJpaRepository.delete(account);
            return true;
        }
        return false;
    }

    @Override
    public void deleteAll(List<Account> entities) {
        accountJpaRepository.deleteAll(entities);
    }

    @Override
    public void saveAll(List<Account> entities) {
        accountJpaRepository.saveAll(entities);
    }

    @Override
    public List<Account> findAll() {
        return accountJpaRepository.findAll();
    }

    @Override
    public boolean deleteById(long id) {
        if (accountJpaRepository.existsById(id)) {
            accountJpaRepository.deleteById(id);
            return true;
        }
        return false;
    }

    @Override
    @Transactional(readOnly = true)
    public Account getOne(Long id) {
        return accountJpaRepository.getOne(id);
    }

    @Override
    public Account deposit(Long id, Double amount) {
        Account account = accountJpaRepository.findById(id).orElse(null);

        if (account != null) {
            account.setBalance(account.getBalance() + amount);
            accountJpaRepository.save(account);
            return account;
        }
        return null;
    }

    @Override
    public Account withdraw(Long id, double amount) {
        Account account = accountJpaRepository.findById(id).orElse(null);
        if (account == null) {
            throw new IllegalArgumentException("Account not found.");
        }
        if (account.getBalance() < amount) {
            throw new IllegalArgumentException("Insufficient funds.");
        }

        account.setBalance(account.getBalance() - amount);
        accountJpaRepository.save(account);
        return account;
    }

    @Override
    public List<Account> transfer(String fromAccountNumber, String toAccountNumber, Double amount) {
        Account sourceAccount = accountJpaRepository.findByNumber(fromAccountNumber);
        Account destinationAccount = accountJpaRepository.findByNumber(toAccountNumber);

        if (sourceAccount == null) {
            throw new IllegalArgumentException("Source account not found.");
        }
        if (destinationAccount == null) {
            throw new IllegalArgumentException("Destination account not found.");
        }
        if (sourceAccount.getBalance() < amount) {
            throw new IllegalArgumentException("Insufficient funds in source account.");
        }

        sourceAccount.setBalance(sourceAccount.getBalance() - amount);
        destinationAccount.setBalance(destinationAccount.getBalance() + amount);

        Account updatedSourceAccount = accountJpaRepository.save(sourceAccount);
        Account updatedDestinationAccount = accountJpaRepository.save(destinationAccount);

        return List.of(updatedSourceAccount, updatedDestinationAccount);
    }

}
