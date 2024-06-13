package com.example.homework_module3.Homework01.DAO;

import com.example.homework_module3.Homework01.domain.Account;
import com.example.homework_module3.Homework01.domain.Currency;
import com.example.homework_module3.Homework01.domain.Customer;
import com.example.homework_module3.Homework01.dto.AccountDto;
import com.example.homework_module3.Homework01.dto.CustomerDto;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;


@Repository
@Data
public class CustomerDaoImpl implements Dao<Customer> {
    private List<Customer> customers;
    private Long currentId = 0L;
    @JsonIgnore
    private Dao<Account> accountDao;

    @Autowired
    public CustomerDaoImpl(Dao<Account> accountDao) {
        this.accountDao = accountDao;
        this.customers = new ArrayList<>();
        init();
    }


    public void init() {
        createMockCustomers();
    }

    public void createMockCustomers() {
        Customer customer1 = new Customer("John Doe", "johndoe@gmail.com", 39);
        Customer customer2 = new Customer("Steven Red", "stevenred@gmail.com", 25);
        Customer customer3 = new Customer("Emily Clark", "emilyclark@gmail.com", 27);

        Account newAccount1 = new Account(Currency.USD, customer1);
        newAccount1.setBalance(Math.random() * 100000);

        Account newAccount2 = new Account(Currency.EUR, customer2);
        newAccount2.setBalance(Math.random() * 100000);

        Account newAccount3 = new Account(Currency.CHF, customer3);
        newAccount3.setBalance(Math.random() * 100000);

        customer1.addAccount(newAccount1);
        save(customer1);

        customer2.addAccount(newAccount2);
        save(customer2);

        customer3.addAccount(newAccount3);
        save(customer3);

        accountDao.save(newAccount1);
        accountDao.save(newAccount2);
        accountDao.save(newAccount3);

        System.out.println("All customers: " + customers);
        System.out.println("All accounts: " + accountDao.findAll());
    }

    public Customer createNewCustomer(Customer customer) {
        customer.setId(currentId + 1);
        customers.add(customer);
        incrementId();
        return customer;
    }

    @Override
    public Customer save(Customer customer) {
        Optional<Customer> existingCustomer = customers.stream()
                .filter(c -> c.equals(customer))
                .findFirst();

        if (existingCustomer.isPresent()) {
            return updateCustomer(customer, existingCustomer.get());
        } else {
            customer.setId(currentId + 1);
            customers.add(customer);
            incrementId();
            return customer;
        }
    }

    public Customer updateCustomer(Customer customer, Customer existingCustomer) {
        existingCustomer.setName(customer.getName());
        existingCustomer.setEmail(customer.getEmail());
        existingCustomer.setAge(customer.getAge());
        existingCustomer.setAccounts(customer.getAccounts());
        return existingCustomer;
    }

    public Customer update(Long id, CustomerDto customerDto) {
        Optional<Customer> existingCustomerOpt = customers.stream()
                .filter(c -> c.getId().equals(id))
                .findFirst();

        if (existingCustomerOpt.isPresent()) {
            Customer existingCustomer = existingCustomerOpt.get();
            existingCustomer.setName(customerDto.getName());
            existingCustomer.setEmail(customerDto.getEmail());
            existingCustomer.setAge(customerDto.getAge());
            return existingCustomer;
        } else {
            throw new IllegalArgumentException("Customer not found with ID: " + id);
        }
    }

    @Override
    public boolean delete(Customer customer) {
        return customers.contains(customer);
    }

    @Override
    public void deleteAll(List<Customer> entities) {
        customers.removeAll(entities);
    }

    @Override
    public void saveAll(List<Customer> entities) {
        for (Customer entity : entities) {
            if (!customers.contains(entity)) {
                save(entity);
            }
        }
    }

    @Override
    public List<Customer> findAll() {
        return customers;
    }

    @Override
    public boolean deleteById(long id) {
        Customer customer = getOne(id);
        if (customer != null) {
            customers.remove(customer);
            return true;
        }
        return false;
    }

    @Override
    public Customer getOne(long id) {
        return customers.stream()
                .filter(c -> c.getId() == id)
                .findFirst()
                .orElse(null);
    }

    public Account addAccount(Long customerId, AccountDto accountDto) {
        Customer customer = getOne(customerId);
        if (customer == null) {
            throw new IllegalArgumentException("Customer not found with ID: " + customerId);
        }
        if (accountDto.getBalance() < 0) {
            throw new IllegalArgumentException("Amount must be greater than zero.");
        }
        Account newAccount = new Account(accountDto.getCurrency(), customer);
        newAccount.setBalance(accountDto.getBalance());
        customer.addAccount(newAccount);
        accountDao.save(newAccount);
        return newAccount;
    }

    public void incrementId() {
        currentId += 1;
    }
}
