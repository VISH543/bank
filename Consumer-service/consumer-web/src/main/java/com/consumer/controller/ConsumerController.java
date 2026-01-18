package com.consumer.controller;

import com.consumer.dto.AccountDTO;
import com.consumer.service.AccountConsumerService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/consumer")
public class ConsumerController {

    private final AccountConsumerService service;

    public ConsumerController(AccountConsumerService service) {
        this.service = service;
    }

    // Fetch account by ID (User/Admin)
    @GetMapping("/account/{id}")
    public AccountDTO fetchAccount(@PathVariable Long id,
                                   @RequestParam("token") String token) {
        System.out.println("Received token from client: " + token);
        return service.getAccount(id, token);
    }

    // Fetch all accounts (Admin only)
    @GetMapping("/accounts")
    public List<AccountDTO> fetchAllAccounts(@RequestParam("token") String token) {
        System.out.println("Received token from client: " + token);
        return service.getAllAccounts(token);
    }
}
