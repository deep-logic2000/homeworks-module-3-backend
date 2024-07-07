package com.example.homework_module3.Homework04.Service;

import com.example.homework_module3.Homework04.domain.Account;
import com.example.homework_module3.Homework04.domain.Customer;
import lombok.NonNull;
import org.springframework.data.domain.Page;

import java.util.List;
import java.util.Optional;

public interface CustomerService {
    Customer save(Customer customer);

    void delete(Customer customer);

    void deleteAll(List<Customer> entities);

    void saveAll(List<Customer> entities);

    List<Customer> findAll();

    //    Page<Customer> getAllPageble(Pageable pageable);
    Page<Customer> getAllPageble(int pageNumber, int size);
    Optional<Customer> getByLogin(String login);

    void deleteById(long id);

    Customer getOne(Long id);

    Customer createCustomer(Customer customer);

    Customer update(Customer customerDto);

    void deleteCustomer(Long id);

    Account createAccount(Account account);

}
