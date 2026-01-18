package com.bank.api.controller;

import com.bank.common.dto.AccountDTO;
import com.bank.persistence.entity.Account;
import com.bank.service.AccountService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(AccountController.class)
class AccountControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private AccountService accountService;

    @Test
    @WithMockUser(authorities = { "SCOPE_ADMIN" })
    void createAccount_ShouldReturnCreatedAccount_WhenAdmin() throws Exception {
        Account account = new Account();
        account.setId(1L);
        account.setHolderName("Admin Created");
        account.setBalance(1000.0);

        when(accountService.createAccount(any(AccountDTO.class))).thenReturn(account);

        mockMvc.perform(post("/api/accounts/admin/create")
                .with(csrf())
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"holderName\":\"John\",\"balance\":1000.0}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.holderName").value("Admin Created"));
    }

    @Test
    @WithMockUser(authorities = { "SCOPE_USER" })
    void getAccountById_ShouldReturnAccount_WhenUser() throws Exception {
        Account account = new Account();
        account.setId(1L);
        account.setHolderName("User Info");
        account.setBalance(500.0);

        when(accountService.getAccountById(1L)).thenReturn(account);

        mockMvc.perform(get("/api/accounts/user/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.holderName").value("User Info"));
    }

    @Test
    @WithMockUser(authorities = { "SCOPE_USER" })
    void deposit_ShouldUpdateBalance() throws Exception {
        Account account = new Account();
        account.setId(1L);
        account.setBalance(600.0);

        when(accountService.deposit(anyLong(), any(Double.class))).thenReturn(account);

        mockMvc.perform(post("/api/accounts/user/1/deposit")
                .with(csrf())
                .param("amount", "100.0"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.balance").value(600.0));
    }
}
