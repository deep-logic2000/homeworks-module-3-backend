package com.example.homework_module3.Homework03.Controller;

import com.example.homework_module3.Homework03.Service.AccountServiceImpl;
import com.example.homework_module3.Homework03.domain.Account;
import com.example.homework_module3.Homework03.domain.dto.AccountDepositDto;
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
        return accountServiceImpl.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getById(@PathVariable Long id) {
        if (accountServiceImpl.getOne(id) == null) {
            return ResponseEntity.badRequest().body(String.format("Account with id %d was not found", id));
        }
        return ResponseEntity.ok(accountServiceImpl.getOne(id));
    }

    @PostMapping("/{id}/deposit")
    public ResponseEntity<?> deposit(@PathVariable Long id, @Valid @RequestBody AccountDepositDto accountDepositDto ) {
        if (accountDepositDto.getDeposit() <= 0) {
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
        if (amount == null || amount <= 0) {
            return ResponseEntity.badRequest().body("Amount must be greater than zero.");
        }

        try {
            Account updatedAccount = accountServiceImpl.withdraw(id, amount);
            return ResponseEntity.ok(updatedAccount);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(500).body("An unexpected error occurred.");
        }
    }

    @PatchMapping("/transfer")
    public ResponseEntity<?> transfer(@RequestParam String fromAccountNumber, @RequestParam String toAccountNumber, @RequestBody Double amount) {
        if (amount == null || amount <= 0) {
            return ResponseEntity.badRequest().body("Amount must be greater than zero.");
        }

        try {
            List<Account> updatedAccounts = accountServiceImpl.transfer(fromAccountNumber, toAccountNumber, amount);
            return ResponseEntity.ok(updatedAccounts);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(500).body("An unexpected error occurred.");
        }
    }

}
