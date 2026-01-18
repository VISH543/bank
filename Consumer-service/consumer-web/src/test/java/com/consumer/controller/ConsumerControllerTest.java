package com.consumer.controller;

import com.consumer.dto.AccountDTO;
import com.consumer.service.AccountConsumerService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(ConsumerController.class)
class ConsumerControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private AccountConsumerService service;

    @Test
    void fetchAccount_ShouldReturnAccount() throws Exception {
        AccountDTO account = new AccountDTO();
        account.setId(1L);
        account.setHolderName("John Doe");
        account.setBalance(1000.0);

        when(service.getAccount(1L, "valid-token")).thenReturn(account);

        mockMvc.perform(get("/consumer/account/1").param("token", "valid-token"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.holderName").value("John Doe"));
    }
}
