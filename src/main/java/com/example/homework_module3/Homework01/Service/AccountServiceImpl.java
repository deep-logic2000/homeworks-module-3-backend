package com.example.homework_module3.Homework01.Service;

import com.example.homework_module3.Homework01.DAO.AccountDaoImpl;
import com.example.homework_module3.Homework01.domain.Account;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
public class AccountServiceImpl implements AccountService {

    AccountDaoImpl accountDao;
    @Autowired
    public AccountServiceImpl(AccountDaoImpl accountDao) {
        this.accountDao = accountDao;
    }

    @Override
    public Account save(Account account) {
        return accountDao.save(account);
    }

    @Override
    public boolean delete(Account account) {
        return accountDao.delete(account);
    }

    @Override
    public void deleteAll(List<Account> entities) {
        accountDao.deleteAll(entities);
    }

    @Override
    public void saveAll(List<Account> entities) {
        accountDao.saveAll(entities);
    }

    @Override
    public List<Account> findAll() {
        return accountDao.findAll();
    }

    @Override
    public boolean deleteById(long id) {
        return accountDao.deleteById(id);
    }

    @Override
    public Account getOne(long id) {
        return accountDao.getOne(id);
    }

    @Override
    public Account deposit(Long id, Double amount) {
        Account account = accountDao.findById(id);

        if (account != null) {
            account.setBalance(account.getBalance() + amount);
            accountDao.save(account);
            return account;
        }
        return null;
    }

    @Override
    public Account withdraw(Long id, double amount) {
        Account account = accountDao.findById(id);
        if (account == null) {
            throw new IllegalArgumentException("Account not found.");
        }
        if (account.getBalance() < amount) {
            throw new IllegalArgumentException("Insufficient funds.");
        }

        account.setBalance(account.getBalance() - amount);
        accountDao.save(account);
        return account;
    }

    @Override
    public List<Account> transfer(String fromAccountNumber, String toAccountNumber, Double amount) {
        Account sourceAccount = accountDao.findByNumber(fromAccountNumber);
        Account destinationAccount = accountDao.findByNumber(toAccountNumber);

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

        Account updatedSourceAccount = accountDao.save(sourceAccount);
        Account updatedDestinationAccount = accountDao.save(destinationAccount);

        return List.of(updatedSourceAccount, updatedDestinationAccount);
    }

}
