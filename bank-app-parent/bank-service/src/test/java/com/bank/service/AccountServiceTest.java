package com.bank.service;

import com.bank.common.dto.AccountDTO;
import com.bank.common.exception.ResourceNotFoundException;
import com.bank.persistence.entity.Account;
import com.bank.persistence.repository.AccountRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class AccountServiceTest {

    @Mock
    private AccountRepository repository;

    @InjectMocks
    private AccountService service;

    private AccountDTO accountDTO;
    private Account account;

    @BeforeEach
    void setUp() {
        accountDTO = new AccountDTO();
        accountDTO.setHolderName("John Doe");
        accountDTO.setBalance(1000.0);

        account = new Account();
        account.setId(1L);
        account.setHolderName("John Doe");
        account.setBalance(1000.0);
    }

    @Test
    void createAccount_ShouldReturnSavedAccount() {
        when(repository.save(any(Account.class))).thenReturn(account);

        Account created = service.createAccount(accountDTO);

        assertNotNull(created);
        assertEquals(account.getHolderName(), created.getHolderName());
        assertEquals(account.getBalance(), created.getBalance());
        verify(repository, times(1)).save(any(Account.class));
    }

    @Test
    void getAccountById_ShouldReturnAccount_WhenFound() {
        when(repository.findById(1L)).thenReturn(Optional.of(account));

        Account found = service.getAccountById(1L);

        assertNotNull(found);
        assertEquals(1L, found.getId());
    }

    @Test
    void getAccountById_ShouldThrowException_WhenNotFound() {
        when(repository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> service.getAccountById(1L));
    }

    @Test
    void deposit_ShouldIncreaseBalance() {
        when(repository.findById(1L)).thenReturn(Optional.of(account));
        when(repository.save(any(Account.class))).thenReturn(account);

        Account updated = service.deposit(1L, 500.0);

        assertEquals(1500.0, updated.getBalance());
        verify(repository, times(1)).save(account);
    }

    @Test
    void withdraw_ShouldDecreaseBalance() {
        when(repository.findById(1L)).thenReturn(Optional.of(account));
        when(repository.save(any(Account.class))).thenReturn(account);

        Account updated = service.withdraw(1L, 200.0);

        assertEquals(800.0, updated.getBalance());
        verify(repository, times(1)).save(account);
    }
}
