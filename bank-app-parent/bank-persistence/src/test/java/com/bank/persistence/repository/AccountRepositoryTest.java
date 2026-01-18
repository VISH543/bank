package com.bank.persistence.repository;

import com.bank.persistence.entity.Account;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
class AccountRepositoryTest {

    @Autowired
    private AccountRepository repository;

    @Test
    void save_ShouldPersistAccount() {
        Account account = new Account();
        account.setHolderName("Alice Smith");
        account.setBalance(2000.0);

        Account saved = repository.save(account);

        assertNotNull(saved.getId());
        assertEquals("Alice Smith", saved.getHolderName());
    }

    @Test
    void findById_ShouldReturnAccount() {
        Account account = new Account();
        account.setHolderName("Bob Jones");
        account.setBalance(3000.0);
        Account saved = repository.save(account);

        Optional<Account> found = repository.findById(saved.getId());

        assertTrue(found.isPresent());
        assertEquals("Bob Jones", found.get().getHolderName());
    }
}
