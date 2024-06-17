package com.example.homework_module3.Homework02.Service;

import com.example.homework_module3.Homework02.DAO.AccountDaoImpl;
import com.example.homework_module3.Homework02.DAO.CustomerDaoImpl;
import com.example.homework_module3.Homework02.domain.Account;
import com.example.homework_module3.Homework02.domain.Customer;
import com.example.homework_module3.Homework02.dto.AccountDto;
import com.example.homework_module3.Homework02.dto.CustomerDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Slf4j
@Transactional
public class CustomerServiceImpl implements CustomerService {

    CustomerDaoImpl customerDao;
    AccountDaoImpl accountDao;

    @Autowired
    public CustomerServiceImpl(CustomerDaoImpl customerDao, AccountDaoImpl accountDao) {
        this.customerDao = customerDao;
        this.accountDao = accountDao;
    }

    @Override
    public Customer save(Customer customer) {
        return customerDao.save(customer);
    }

    @Override
    public boolean delete(Customer customer) {
        return customerDao.delete(customer);
    }

    @Override
    @Transactional(readOnly = true)
    public void deleteAll(List<Customer> entities) {
        customerDao.deleteAll(entities);
    }

    @Override
    public void saveAll(List<Customer> entities) {
        customerDao.saveAll(entities);
    }

    @Override
    public List<Customer> findAll() {
        return customerDao.findAll();
    }

    @Override
    public boolean deleteById(long id) {
        return customerDao.deleteById(id);
    }

    @Override
    @Transactional(readOnly = true)
    public Customer getOne(Long id) {
        return customerDao.getOne(id);
    }

    @Override
    public Customer createCustomer(Customer customer) {
        return customerDao.save(customer);
    }

    @Override
    public Customer update(Long id, CustomerDto customerDto) {
        return customerDao.update(id, customerDto);
    }

    @Override
    public void deleteCustomer(Long id) {
        if (!customerDao.deleteById(id)) {
            throw new IllegalArgumentException("Customer not found with ID: " + id);
        }
    }
    @Override
    public Account createAccount(Long customerId, AccountDto accountDto) {
        return customerDao.addAccount(customerId, accountDto);
    }

    @Transactional
    public void deleteAccount (Long customerId, Long accountId) {
        Customer customer = customerDao.getOne(customerId);
        if (customer == null) {
            throw new IllegalArgumentException("Customer not found with ID: " + customerId);
        }

        Account account = accountDao.getOne(accountId);
        if (account == null || !account.getCustomer().getId().equals(customerId)) {
            throw new IllegalArgumentException("Account not found with ID: " + accountId + " for customer with ID: " + customerId);
        }

        customer.getAccounts().remove(account);

        if (!accountDao.deleteById(accountId)) {
            throw new RuntimeException("Account hasn't been deleted.");
        }
    }
}
