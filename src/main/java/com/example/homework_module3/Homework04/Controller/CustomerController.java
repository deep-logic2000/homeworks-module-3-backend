package com.example.homework_module3.Homework04.Controller;

import com.example.homework_module3.Homework04.Service.CustomerServiceImpl;
import com.example.homework_module3.Homework04.Service.mappers.AccountDtoMapperRequest;
import com.example.homework_module3.Homework04.Service.mappers.AccountDtoMapperResponse;
import com.example.homework_module3.Homework04.Service.mappers.CustomerDtoMapperRequest;
import com.example.homework_module3.Homework04.Service.mappers.CustomerDtoMapperResponse;
import com.example.homework_module3.Homework04.domain.Account;
import com.example.homework_module3.Homework04.domain.Customer;
import com.example.homework_module3.Homework04.domain.dto.*;
import com.fasterxml.jackson.annotation.JsonView;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/customers")
@CrossOrigin(origins = "http://localhost:3000")
@Slf4j
@RequiredArgsConstructor
public class CustomerController {

    private final CustomerServiceImpl customerServiceImpl;
    private final CustomerDtoMapperRequest dtoMapperRequest;
    private final CustomerDtoMapperResponse dtoMapperResponse;
    private final AccountDtoMapperRequest accountDtoMapperRequest;
    private final AccountDtoMapperResponse accountDtoMapperResponse;
    @Autowired
    private PasswordEncoder passwordEncoder;

    @GetMapping
    public ResponseEntity<?> getAll(@RequestParam(defaultValue = "0") int pageNumber,
                                 @RequestParam(defaultValue = "10") int size) {

        Page<Customer> customersPage = customerServiceImpl.getAllPageble(pageNumber, size);
        List<Customer> customersList = customersPage.toList();
        Long totalCustomersAmount = customersPage.getTotalElements();
        return ResponseEntity.ok().body(List.of(customersList, totalCustomersAmount));
    }

    @JsonView(Views.Detailed.class)
    @GetMapping("/{id}")
    public ResponseEntity<?> getById(@PathVariable Long id) {
        Customer customer = customerServiceImpl.getOne(id);
        if (customer == null) {
            return ResponseEntity.badRequest().body(String.format("User with id %d was not found", id));
        }
        CustomerDtoResponse customerDtoResponse = dtoMapperResponse.convertToDto(customer);
        return ResponseEntity.ok(customerDtoResponse);
    }

    @PostMapping
    public ResponseEntity<?> createCustomer(@Valid @RequestBody CustomerDtoRequest customer) {
        Customer newCustomer = dtoMapperRequest.convertToEntity(customer);
        newCustomer.setEncryptedPassword(passwordEncoder.encode(customer.getPassword()));
        try {
            Customer currentCustomer = customerServiceImpl.save(newCustomer);
            CustomerDtoResponse createdCustomer = dtoMapperResponse.convertToDto(currentCustomer);
            return ResponseEntity.ok(createdCustomer);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Error during create customer: " + e);
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateCustomer(@PathVariable Long id, @Valid @RequestBody CustomerDtoRequest customerDtoRequest) {
        Customer updatedCustomerEntity = dtoMapperRequest.convertToEntity(customerDtoRequest);
        Customer customer = customerServiceImpl.getOne(id);
        updatedCustomerEntity.setId(id);
        updatedCustomerEntity.setAccounts(customer.getAccounts());
        try {
            Customer updatedCustomer = customerServiceImpl.update(updatedCustomerEntity);
            CustomerDtoResponse customerDtoResponse = dtoMapperResponse.convertToDto(updatedCustomer);
            return ResponseEntity.ok(customerDtoResponse);
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
    public ResponseEntity<?> createAccount(@PathVariable Long id, @Valid @RequestBody AccountDtoRequest accountDto) {

        try {
            Account createdAccount = accountDtoMapperRequest.convertToEntity(accountDto);
            Customer customer = customerServiceImpl.getOne(id);
            createdAccount.setCustomer(customer);
            Account currentAccount = customerServiceImpl.createAccount(createdAccount);
            AccountDtoResponse newAccount = accountDtoMapperResponse.convertToDto(currentAccount);
            return ResponseEntity.ok(newAccount);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body("Error during creating an account");
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
