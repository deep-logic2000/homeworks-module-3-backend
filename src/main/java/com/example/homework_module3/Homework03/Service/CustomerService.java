package com.example.homework_module3.Homework03.Service;

import com.example.homework_module3.Homework03.domain.Account;
import com.example.homework_module3.Homework03.domain.Customer;
import com.example.homework_module3.Homework03.domain.dto.AccountDto;
import com.example.homework_module3.Homework03.domain.dto.CustomerDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface CustomerService {
    Customer save(Customer customer);

    void delete(Customer customer);

    void deleteAll(List<Customer> entities);

    void saveAll(List<Customer> entities);

    List<Customer> findAll();

    //    Page<Customer> getAllPageble(Pageable pageable);
    Page<Customer> getAllPageble(int pageNumber, int size);

    void deleteById(long id);

    Customer getOne(Long id);

    Customer createCustomer(Customer customer);

    Customer update(Customer customerDto);

    void deleteCustomer(Long id);

    Account createAccount(Account account);

}
