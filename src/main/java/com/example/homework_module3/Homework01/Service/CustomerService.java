package com.example.homework_module3.Homework01.Service;

import com.example.homework_module3.Homework01.domain.Account;
import com.example.homework_module3.Homework01.domain.Customer;
import com.example.homework_module3.Homework01.dto.AccountDto;
import com.example.homework_module3.Homework01.dto.CustomerDto;

import java.util.List;

public interface CustomerService {
    Customer save(Customer customer);
    boolean delete(Customer customer);
    void deleteAll(List<Customer> entities);
    void saveAll(List<Customer> entities);
    List<Customer> findAll();
    boolean deleteById(long id);
    Customer getOne(long id);
    Customer createCustomer(Customer customer);
    Customer update(Long id, CustomerDto customerDto);
    void deleteCustomer(Long id);
    Account createAccount(Long customerId, AccountDto accountDto);

}
