package com.example.homework_module3.Homework04.Service;

import com.example.homework_module3.Homework04.DAO.AccountJpaRepository;
import com.example.homework_module3.Homework04.DAO.CustomerJpaRepository;
import com.example.homework_module3.Homework04.domain.Account;
import com.example.homework_module3.Homework04.domain.Customer;
import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Slf4j
@Transactional
public class CustomerServiceImpl implements CustomerService {

    CustomerJpaRepository customerDao;
    AccountJpaRepository accountDao;

    @Autowired
    public CustomerServiceImpl(CustomerJpaRepository customerDao, AccountJpaRepository accountDao) {
        this.customerDao = customerDao;
        this.accountDao = accountDao;
    }

    @Override
    public Customer save(Customer customer) {
        return customerDao.save(customer);
    }

    @Override
    public void delete(Customer customer) {
        customerDao.delete(customer);
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
    public Page<Customer> getAllPageble(int pageNumber, int size) {
//        Sort sort = Sort.by(new Sort.Order(Sort.Direction.ASC, "name"));
        Pageable pageable = PageRequest.of(pageNumber, size);
        Page<Customer> customerPage = customerDao.findAll(pageable);
        return customerPage;
    }

    public Optional<Customer> getByLogin(@NonNull String login) {

        return customerDao.findCustomersByName(login);
    }

    @Override
    public void deleteById(long id) {
        customerDao.deleteById(id);
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
    public Customer update(Customer customer) {
        return customerDao.save(customer);
    }

    @Override
    public void deleteCustomer(Long id) {
        customerDao.deleteById(id);
    }

    @Override
    public Account createAccount(Account account) {
        return accountDao.save(account);
    }

    @Transactional
    public void deleteAccount(Long customerId, Long accountId) {
        Customer customer = customerDao.getOne(customerId);

        Account account = accountDao.getOne(accountId);
        if (account == null || !account.getCustomer().getId().equals(customerId)) {
            throw new IllegalArgumentException("Account not found with ID: " + accountId + " for customer with ID: " + customerId);
        }


        if (accountDao.existsById(accountId)) {
            accountDao.deleteById(accountId);
            customer.getAccounts().remove(account);
        } else {
            throw new RuntimeException("Account hasn't been deleted.");
        }
    }
}
