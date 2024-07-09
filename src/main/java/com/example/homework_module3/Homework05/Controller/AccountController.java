package com.example.homework_module3.Homework05.Controller;

import com.example.homework_module3.Homework05.Service.AccountServiceImpl;
import com.example.homework_module3.Homework05.domain.Account;
import com.example.homework_module3.Homework05.domain.dto.AccountDepositDto;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/accounts")
@CrossOrigin(origins = "http://localhost:3000")
@Slf4j
public class AccountController {

    AccountServiceImpl accountServiceImpl;

    @Autowired
    public AccountController(AccountServiceImpl accountServiceImpl) {
        this.accountServiceImpl = accountServiceImpl;
    }

    @GetMapping
    public List<Account> getAll() {
        log.info("getAll method in Account controller triggered.");
        return accountServiceImpl.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getById(@PathVariable Long id) {
        log.info("getById method in Account controller triggered.");
        if (accountServiceImpl.getOne(id) == null) {
            log.warn("Account with id {} was not found", id);
            return ResponseEntity.badRequest().body(String.format("Account with id %d was not found", id));
        }
        return ResponseEntity.ok(accountServiceImpl.getOne(id));
    }

    @PostMapping("/{id}/deposit")
    public ResponseEntity<?> deposit(@PathVariable Long id, @Valid @RequestBody AccountDepositDto accountDepositDto ) throws Exception {
        log.info("deposit method in Account controller triggered.");
        if (accountDepositDto.getDeposit() <= 0) {
            log.warn("Amount must be greater than zero");
            return ResponseEntity.badRequest().body("Amount must be greater than zero.");
        }

        Account updatedAccount = accountServiceImpl.deposit(id, accountDepositDto.getDeposit());
        if (updatedAccount != null) {
            return ResponseEntity.ok(updatedAccount);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping("/{id}/withdraw")
    public ResponseEntity<?> withdrawFromAccount(@PathVariable Long id, @RequestBody Double amount) {
        log.info("withdrawFromAccount method in Account controller triggered.");
        if (amount == null || amount <= 0) {
            log.warn("Amount must be greater than zero.");
            return ResponseEntity.badRequest().body("Amount must be greater than zero.");
        }

        try {
            Account updatedAccount = accountServiceImpl.withdraw(id, amount);
            return ResponseEntity.ok(updatedAccount);
        } catch (IllegalArgumentException e) {
            log.warn("Invalid account id: {}", id, e);
            return ResponseEntity.badRequest().body(e.getMessage());
        } catch (Exception e) {
            log.error("An unexpected error occurred.", e);
            return ResponseEntity.status(500).body("An unexpected error occurred.");
        }
    }

    @PatchMapping("/transfer")
    public ResponseEntity<?> transfer(@RequestParam String fromAccountNumber, @RequestParam String toAccountNumber, @RequestBody Double amount) {
        log.info("transfer method in Account controller triggered.");
        if (amount == null || amount <= 0) {
            log.warn("Amount must be greater than zero.");
            return ResponseEntity.badRequest().body("Amount must be greater than zero.");
        }

        try {
            List<Account> updatedAccounts = accountServiceImpl.transfer(fromAccountNumber, toAccountNumber, amount);
            return ResponseEntity.ok(updatedAccounts);
        } catch (IllegalArgumentException e) {
            log.warn(String.valueOf(e));
            return ResponseEntity.badRequest().body(e.getMessage());
        } catch (Exception e) {
            log.error(String.valueOf(e));
            return ResponseEntity.status(500).body("An unexpected error occurred.");
        }
    }

}
