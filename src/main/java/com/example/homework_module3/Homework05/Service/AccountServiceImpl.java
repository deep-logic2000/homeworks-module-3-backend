package com.example.homework_module3.Homework05.Service;

import com.example.homework_module3.Homework05.DAO.AccountJpaRepository;
import com.example.homework_module3.Homework05.domain.Account;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Slf4j
@Transactional
@RequiredArgsConstructor
public class AccountServiceImpl implements AccountService {

    private final AccountJpaRepository accountJpaRepository;

    private final SimpMessagingTemplate messagingTemplate;

    @Override
    public Account save(Account account) {
        return accountJpaRepository.save(account);
    }

    @Override
    public boolean delete(Account account) {
        if (accountJpaRepository.existsById(account.getId())) {
            accountJpaRepository.delete(account);
            changeAccountStatus(account.getNumber(), "was deleted");
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
            changeAccountStatus(id, "was deleted");
            return true;
        }
        return false;
    }

    @Override
    @Transactional(readOnly = true)
    public Account getOne(Long id) {
        return accountJpaRepository.findById(id).orElseThrow();
    }

    @Override
    public Account deposit(Long id, Double amount) {
        Account account = accountJpaRepository.findById(id).orElse(null);

        if (account != null) {
            changeAccountStatus(id, "account was deposited");
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
        changeAccountStatus(id, "was withdrawn");
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
        changeAccountStatus(fromAccountNumber, "was changed");
        changeAccountStatus(toAccountNumber, "was changed");

        return List.of(updatedSourceAccount, updatedDestinationAccount);
    }

    public void changeAccountStatus(Long accountId, String newStatus) {
        String message = "Account " + accountId + " status changed to " + newStatus;
        messagingTemplate.convertAndSend("/topic/accountStatus", message);
    }

    public void changeAccountStatus(String accountNumber, String newStatus) {
        String message = "Account " + accountNumber + " status changed to " + newStatus;
        messagingTemplate.convertAndSend("/topic/accountStatus", message);
    }

}
