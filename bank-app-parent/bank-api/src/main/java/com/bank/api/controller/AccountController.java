package com.bank.api.controller;




import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import com.bank.common.dto.AccountDTO;
import com.bank.persistence.entity.Account;
import com.bank.service.AccountService;


@RestController
@RequestMapping("/api/accounts")
public class AccountController {

    private static final Logger logger = LoggerFactory.getLogger(AccountController.class);

    private final AccountService service;

    public AccountController(AccountService service) {
        this.service = service;
    }

    // ================= ADMIN APIs =================

    @PreAuthorize("hasAuthority('SCOPE_ADMIN')")
    @PostMapping("/admin/create")
    public Account createAccount(@RequestBody AccountDTO dto) {
        logger.info("ADMIN creating account");
        return service.createAccount(dto);
    }

    @PreAuthorize("hasAuthority('SCOPE_ADMIN')")
    @GetMapping("/admin/all")
    public List<Account> getAllAccountsForAdmin() {
        logger.info("ADMIN fetching all accounts");
        return service.getAllAccounts();
    }

    // ================= USER APIs =================

    @PreAuthorize("hasAnyAuthority('SCOPE_USER','SCOPE_ADMIN')")
    @GetMapping("/user/{id}")
    public Account getAccountById(@PathVariable Long id) {
        logger.info("USER fetching account with id {}", id);
        return service.getAccountById(id);
    }

    @PreAuthorize("hasAnyAuthority('SCOPE_USER','SCOPE_ADMIN')")
    @PostMapping("/user/{id}/deposit")
    public Account deposit(@PathVariable Long id, @RequestParam double amount) {
        logger.info("USER depositing amount {}", amount);
        return service.deposit(id, amount);
    }

    @PreAuthorize("hasAnyAuthority('SCOPE_USER','SCOPE_ADMIN')")
    @PostMapping("/user/{id}/withdraw")
    public Account withdraw(@PathVariable Long id, @RequestParam double amount) {
        logger.info("USER withdrawing amount {}", amount);
        return service.withdraw(id, amount);
    }
}
