package com.example.homework_module3.Homework01.Controller;

import com.example.homework_module3.Homework01.Service.CustomerServiceImpl;
import com.example.homework_module3.Homework01.domain.Account;
import com.example.homework_module3.Homework01.domain.Customer;
import com.example.homework_module3.Homework01.dto.AccountDto;
import com.example.homework_module3.Homework01.dto.CustomerDto;
import io.swagger.v3.oas.annotations.Operation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/customers")
@CrossOrigin(origins = "http://localhost:3000")
@Slf4j
public class CustomerController {

CustomerServiceImpl customerServiceImpl;

@Autowired
    public CustomerController(CustomerServiceImpl customerServiceImpl) {
        this.customerServiceImpl = customerServiceImpl;
    }

    @GetMapping
    public List<Customer> getAll() {
        return customerServiceImpl.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getById(@PathVariable Long id) {
        if (customerServiceImpl.getOne(id) == null) {
            return ResponseEntity.badRequest().body(String.format("User with id %d was not found", id));
        }
        return ResponseEntity.ok(customerServiceImpl.getOne(id));
    }

    @PostMapping
    public ResponseEntity<?> createCustomer(@RequestBody CustomerDto customer) {
        if (customer.getName() == null || customer.getEmail() == null || customer.getAge() == null) {
            return ResponseEntity.badRequest().body("Name, email, and age are required.");
        }

        if (customer.getAge() < 0 || customer.getAge() > 120) {
            return ResponseEntity.badRequest().body("Enter age in correct format.");
        }

        Customer newCustomer = new Customer(customer.getName(), customer.getEmail(), customer.getAge());
        Customer createdCustomer = customerServiceImpl.createCustomer(newCustomer);
        return ResponseEntity.ok(createdCustomer);
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateCustomer(@PathVariable Long id, @RequestBody CustomerDto customerDto) {
        try {
            Customer updatedCustomer = customerServiceImpl.update(id, customerDto);
            return ResponseEntity.ok(updatedCustomer);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteCustomer(@PathVariable Long id) {
        try {
            customerServiceImpl.deleteCustomer(id);
            return ResponseEntity.ok("Customer deleted successfully.");
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @Operation(summary = "Add an account", description = "Add an account with the provided details")
    @PostMapping("/{id}/accounts")
    public ResponseEntity<?> createAccount(@PathVariable Long id, @RequestBody AccountDto accountDto) {
        try {
            Account newAccount = customerServiceImpl.createAccount(id, accountDto);
            return ResponseEntity.ok(newAccount);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @DeleteMapping("/{id}/accounts/{accountId}")
    public ResponseEntity<?> deleteAccount(@PathVariable Long id, @PathVariable Long accountId) {
        try {
            customerServiceImpl.deleteAccount(id, accountId);
            return ResponseEntity.ok("Account deleted successfully.");
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

}
