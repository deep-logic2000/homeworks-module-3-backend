package com.example.homework_module3.Homework04.Service;

import com.example.homework_module3.Homework04.DAO.AccountJpaRepository;
import com.example.homework_module3.Homework04.DAO.CustomerJpaRepository;
import com.example.homework_module3.Homework04.domain.Account;
import com.example.homework_module3.Homework04.domain.Currency;
import com.example.homework_module3.Homework04.domain.Customer;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class CustomerServiceImplTest {

    @Mock
    private CustomerJpaRepository customerJpaRepository;

    @Mock
    private AccountJpaRepository accountJpaRepository;

    @InjectMocks
    private CustomerServiceImpl customerService;

    @Captor
    private ArgumentCaptor<Customer> customerArgumentCaptor;

    @Test
    void save() {
        Customer customer = new Customer();
        Mockito.when(customerJpaRepository.save(customer)).thenReturn(customer);

        Customer savedCustomer = customerService.save(customer);
        assertEquals(customer, savedCustomer);
    }

    @Test
    void delete() {
        Mockito.doNothing().when(customerJpaRepository).deleteById(1L);

        customerService.deleteCustomer(1L);
        Mockito.verify(customerJpaRepository, Mockito.times(1)).deleteById(1L);
    }

    @Test
    void deleteAll() {
        Customer customer1 = new Customer("Jim", "jim@gmail.com", 35);
        Customer customer2 = new Customer("Jack", "jack@gmail.com", 28);
        List<Customer> customers = List.of(customer1, customer2);
        customerService.deleteAll(customers);
        verify(customerJpaRepository).deleteAll(customers);
    }

    @Test
    void saveAll() {
        Customer customer1 = new Customer("Jim", "jim@gmail.com", 35);
        Customer customer2 = new Customer("Jack", "jack@gmail.com", 28);


        customerService.saveAll(List.of(customer1, customer2));

        verify(customerJpaRepository).saveAll(List.of(customer1, customer2));
    }

    @Test
    void findAll() {
        Customer customer1 = new Customer();
        Customer customer2 = new Customer();
        List<Customer> customersExpected = List.of(customer1, customer2);
        when(customerJpaRepository.findAll())
                .thenReturn(new PageImpl<>(customersExpected).stream().toList());

        List<Customer> customersActual = customerService.findAll();
        assertNotNull(customersActual);
        assertFalse(customersActual.isEmpty());
        assertIterableEquals(customersExpected, customersActual);
    }

    @Test
    void getAllPageble() {
        Customer customer1 = new Customer("Jim", "jim@gmail.com", 35);
        Customer customer2 = new Customer("Jack", "jack@gmail.com", 28);
        when(customerJpaRepository.findAll(any(Pageable.class)))
                .thenReturn(new PageImpl<>(List.of(customer1, customer2)));
        List<Customer> customers = customerService.getAllPageble(1, 3).stream().toList();

        assertEquals(customer1, customers.get(0));
    }

    @Test
    void getByLogin() {
    }

    @Test
    void deleteById() {
        long testId = 1L;
        customerService.deleteById(testId);
        verify(customerJpaRepository).deleteById(testId);
    }

    @Test
    void getOne() {
        Customer customer = new Customer();
        Mockito.when(customerJpaRepository.getOne(1L)).thenReturn(customer);

        Customer foundCustomer = customerService.getOne(1L);
        assertEquals(customer, foundCustomer);
    }

    @Test
    void createCustomer() {
        Customer customer1 = new Customer("Jim", "jim@gmail.com", 35);

        customerService.createCustomer(customer1);

        verify(customerJpaRepository).save(customerArgumentCaptor.capture());
        Customer employeeActualArgument = customerArgumentCaptor.getValue();
        assertEquals(customer1, employeeActualArgument);
    }

    @Test
    void update() {
        Customer customer = new Customer();
        Mockito.when(customerJpaRepository.save(customer)).thenReturn(customer);

        Customer updatedCustomer = customerService.update(customer);
        assertEquals(customer, updatedCustomer);
    }

    @Test
    void deleteCustomer() {
        Mockito.doNothing().when(customerJpaRepository).deleteById(1L);

        customerService.deleteCustomer(1L);
        Mockito.verify(customerJpaRepository, Mockito.times(1)).deleteById(1L);
    }

    @Test
    void createAccount() {
        Account account = new Account();
        Mockito.when(accountJpaRepository.save(account)).thenReturn(account);

        Account createdAccount = customerService.createAccount(account);
        assertEquals(account, createdAccount);
    }

}