package com.consumer.service;

import com.consumer.dto.AccountDTO;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;
import java.util.List;

@Service
public class AccountConsumerService {
    private final RestTemplate restTemplate;

    public AccountConsumerService(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    // Fetch account by ID
    public AccountDTO getAccount(Long id, String token) {
        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", "Bearer " + token);

        HttpEntity<Void> entity = new HttpEntity<>(headers);

        System.out.println("Forwarding to Producer: http://localhost:8889/api/accounts/user/" + id);
        System.out.println("Authorization header: Bearer " + token);

        return restTemplate
                .exchange("http://localhost:8889/api/accounts/user/" + id,
                          HttpMethod.GET,
                          entity,
                          AccountDTO.class)
                .getBody();
    }

    // Fetch all accounts (Admin only)
    public List<AccountDTO> getAllAccounts(String token) {
        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", "Bearer " + token);

        HttpEntity<Void> entity = new HttpEntity<>(headers);

        System.out.println("Forwarding to Producer: http://localhost:8889/api/accounts/admin/all");
        System.out.println("Authorization header: Bearer " + token);

        AccountDTO[] accounts = restTemplate
                .exchange("http://localhost:8889/api/accounts/admin/all",
                          HttpMethod.GET,
                          entity,
                          AccountDTO[].class)
                .getBody();

        return Arrays.asList(accounts);
    }
}
