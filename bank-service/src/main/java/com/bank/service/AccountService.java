package com.bank.service;



import java.util.List;

import org.springframework.stereotype.Service;

import com.bank.common.dto.AccountDTO;
import com.bank.common.exception.ResourceNotFoundException;
import com.bank.persistence.entity.Account;
import com.bank.persistence.repository.AccountRepository;



@Service
public class AccountService {

    private final AccountRepository repository;

    public AccountService(AccountRepository repository) {
        this.repository = repository;
    }

    public Account createAccount(AccountDTO dto) {
        Account account = new Account();
        account.setHolderName(dto.getHolderName());
        account.setBalance(dto.getBalance());
        return repository.save(account);
    }

    public List<Account> getAllAccounts() {
        return repository.findAll();
    }

    public Account getAccountById(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Account not found"));
    }

    public Account deposit(Long id, double amount) {
        Account account = getAccountById(id);
        account.setBalance(account.getBalance() + amount);
        return repository.save(account);
    }

    public Account withdraw(Long id, double amount) {
        Account account = getAccountById(id);
        account.setBalance(account.getBalance() - amount);
        return repository.save(account);
    }
}
