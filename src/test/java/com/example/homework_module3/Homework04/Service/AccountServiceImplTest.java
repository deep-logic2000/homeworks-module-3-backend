package com.example.homework_module3.Homework04.Service;

import com.example.homework_module3.Homework04.DAO.AccountJpaRepository;
import com.example.homework_module3.Homework04.domain.Account;
import com.example.homework_module3.Homework04.domain.Currency;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Collections;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

@ExtendWith(MockitoExtension.class)
class AccountServiceImplTest {

    @Mock
    private AccountJpaRepository accountJpaRepository;

    @InjectMocks
    private AccountServiceImpl accountServiceImpl;

    private Account account;

    @BeforeEach
    public void setUp() {
        account = new Account();
        account.setId(1L);
        account.setCurrency(Currency.EUR);
        account.setBalance(100.0);
    }

    @Test
    void save() {
        when(accountJpaRepository.save(any(Account.class))).thenReturn(account);

        Account savedAccount = accountServiceImpl.save(account);

        assertNotNull(savedAccount);
        assertEquals(1L, savedAccount.getId());
        verify(accountJpaRepository, times(1)).save(account);
    }

    @Test
    void delete() {
        when(accountJpaRepository.existsById(anyLong())).thenReturn(true);

        boolean isDeleted = accountServiceImpl.delete(account);

        assertTrue(isDeleted);
        verify(accountJpaRepository, times(1)).delete(account);
    }

    @Test
    void deleteAll() {
        List<Account> accounts = Collections.singletonList(account);
        doNothing().when(accountJpaRepository).deleteAll(accounts);

        accountServiceImpl.deleteAll(accounts);

        verify(accountJpaRepository, times(1)).deleteAll(accounts);
    }

    @Test
    void saveAll() {
        List<Account> accounts = Collections.singletonList(account);
        when(accountJpaRepository.saveAll(accounts)).thenReturn(accounts);

        accountServiceImpl.saveAll(accounts);

        verify(accountJpaRepository, times(1)).saveAll(accounts);
    }

    @Test
    void findAll() {
        List<Account> accounts = Collections.singletonList(account);
        when(accountJpaRepository.findAll()).thenReturn(accounts);

        List<Account> result = accountServiceImpl.findAll();

        assertNotNull(result);
        assertFalse(result.isEmpty());
        verify(accountJpaRepository, times(1)).findAll();
    }

    @Test
    void deleteById() {
        when(accountJpaRepository.existsById(anyLong())).thenReturn(true);
        doNothing().when(accountJpaRepository).deleteById(anyLong());

        boolean isDeleted = accountServiceImpl.deleteById(1L);

        assertTrue(isDeleted);
        verify(accountJpaRepository, times(1)).deleteById(1L);
    }

    @Test
    void getOne_accountExists() {
        Account mockAccount = new Account();
        mockAccount.setId(1L);
        when(accountJpaRepository.findById(anyLong())).thenReturn(Optional.of(mockAccount));

        Account foundAccount = accountServiceImpl.getOne(1L);

        assertNotNull(foundAccount);
        assertEquals(1L, foundAccount.getId());

        verify(accountJpaRepository, times(1)).findById(1L);
    }

    @Test
    void getOne_accountDoesNotExist() {
        when(accountJpaRepository.findById(anyLong())).thenReturn(Optional.empty());

        assertThrows(NoSuchElementException.class, () -> {
            accountServiceImpl.getOne(1L);
        });

        verify(accountJpaRepository, times(1)).findById(1L);
    }

    @Test
    void deposit() {
        Double depositAmount = 50.0;
        when(accountJpaRepository.findById(1L)).thenReturn(Optional.of(account));
        when(accountJpaRepository.save(any(Account.class))).thenReturn(account);

        Account updatedAccount = accountServiceImpl.deposit(1L, depositAmount);

        assertNotNull(updatedAccount);
        assertEquals(150.0, updatedAccount.getBalance());
        verify(accountJpaRepository, times(1)).save(any(Account.class));
    }

    @Test
    void withdraw() {
        Double withdrawAmount = 50.0;
        when(accountJpaRepository.findById(1L)).thenReturn(Optional.of(account));
        when(accountJpaRepository.save(any(Account.class))).thenReturn(account);

        Account updatedAccount = accountServiceImpl.withdraw(1L, withdrawAmount);

        assertNotNull(updatedAccount);
        assertEquals(50.0, updatedAccount.getBalance());
        verify(accountJpaRepository, times(1)).save(any(Account.class));
    }

    @Test
    public void testWithdrawInsufficientFunds() {
        Double withdrawAmount = 150.0;
        when(accountJpaRepository.findById(1L)).thenReturn(Optional.of(account));

        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            accountServiceImpl.withdraw(1L, withdrawAmount);
        });

        String expectedMessage = "Insufficient funds.";
        String actualMessage = exception.getMessage();

        assertTrue(actualMessage.contains(expectedMessage));
        verify(accountJpaRepository, never()).save(any(Account.class));
    }

    @Test
    public void testTransfer() {
        Account sourceAccount = new Account();
        sourceAccount.setId(1L);
        sourceAccount.setNumber("123");
        sourceAccount.setBalance(100.0);

        Account destinationAccount = new Account();
        destinationAccount.setId(2L);
        destinationAccount.setNumber("456");
        destinationAccount.setBalance(50.0);

        when(accountJpaRepository.findByNumber("123")).thenReturn(sourceAccount);
        when(accountJpaRepository.findByNumber("456")).thenReturn(destinationAccount);
        when(accountJpaRepository.save(any(Account.class))).thenReturn(sourceAccount, destinationAccount);

        Double transferAmount = 50.0;

        List<Account> updatedAccounts = accountServiceImpl.transfer("123", "456", transferAmount);

        assertNotNull(updatedAccounts);
        assertEquals(2, updatedAccounts.size());
        assertEquals(50.0, sourceAccount.getBalance());
        assertEquals(100.0, destinationAccount.getBalance());
        verify(accountJpaRepository, times(2)).save(any(Account.class));
    }
}