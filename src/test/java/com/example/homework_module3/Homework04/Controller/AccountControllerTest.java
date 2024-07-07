package com.example.homework_module3.Homework04.Controller;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.when;

import com.example.homework_module3.Homework04.Service.AccountServiceImpl;
import com.example.homework_module3.Homework04.Service.mappers.AccountDtoMapperResponse;
import com.example.homework_module3.Homework04.domain.Account;
import com.example.homework_module3.Homework04.domain.Currency;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.Collections;
import java.util.List;
import java.util.UUID;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@AutoConfigureMockMvc
class AccountControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private AccountServiceImpl accountService;

    @MockBean
    private AccountDtoMapperResponse accountDtoMapperResponse;

    private Account mockAccount;
    private Account mockAccount2;

    @Autowired
    private ObjectMapper objectMapper;

    @BeforeEach
    public void setUp() {
        mockAccount = new Account();
        mockAccount.setId(1L);
        mockAccount.setCurrency(Currency.GBP);
        mockAccount.setNumber(String.valueOf(UUID.randomUUID()));
        mockAccount.setBalance(1000.0);

        mockAccount2 = new Account();
        mockAccount2.setId(2L);
        mockAccount2.setCurrency(Currency.CHF);
        mockAccount2.setNumber(String.valueOf(UUID.randomUUID()));
        mockAccount2.setBalance(2000.0);
    }

    @Test
    @WithMockUser(username = "testUser")
    void getAll() throws Exception {
        when(accountService.findAll()).thenReturn(Collections.singletonList(mockAccount));

        mockMvc.perform(MockMvcRequestBuilders.get("/accounts"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.length()").value(1))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].id").value(mockAccount.getId()));

    }

    @Test
    @WithMockUser(username = "testUser")
    void getAccountById_accountExists() throws Exception {
        when(accountService.getOne(anyLong())).thenReturn(mockAccount);

        mockMvc.perform(MockMvcRequestBuilders.get("/accounts/{id}", 1L))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.id").value(mockAccount.getId()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.number").value(mockAccount.getNumber()));
    }

    @Test
    @WithMockUser(username = "testUser")
    void getAccountById_accountDoesNotExist() throws Exception {
        when(accountService.getOne(anyLong())).thenReturn(null);

        mockMvc.perform(MockMvcRequestBuilders.get("/accounts/{id}", 999L))
                .andExpect(MockMvcResultMatchers.status().isBadRequest())
                .andExpect(MockMvcResultMatchers.content().string("Account with id 999 was not found"));

    }

     @Test
    @WithMockUser(username = "testUser")
    void withdrawFromAccount_validAmount() throws Exception {
        // Mocking behavior

        mockMvc.perform(MockMvcRequestBuilders.post("/accounts/{id}/withdraw", 1L)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(String.valueOf(500.0)))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    @WithMockUser(username = "testUser")
    void withdrawFromAccount_invalidAmount() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.post("/accounts/{id}/withdraw", 1L)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(String.valueOf(-100.0)))
                .andExpect(MockMvcResultMatchers.status().isBadRequest())
                .andExpect(MockMvcResultMatchers.content().string("Amount must be greater than zero."));

    }

    @Test
    @WithMockUser(username = "testUser")
    void transfer_validTransfer() throws Exception {
        Account mockAccount = new Account();
        mockAccount.setId(1L);
        mockAccount.setNumber("123e4567-e89b-12d3-a456-556642440000");
        mockAccount.setCurrency(Currency.USD);
        mockAccount.setBalance(1000.0);

        Account mockTargetAccount = new Account();
        mockTargetAccount.setId(2L);
        mockTargetAccount.setNumber("123e4567-e89b-12d3-a456-556642440001");
        mockTargetAccount.setCurrency(Currency.USD);
        mockTargetAccount.setBalance(500.0);

        when(accountService.transfer(anyString(), anyString(), anyDouble()))
                .thenReturn(List.of(mockAccount, mockTargetAccount));

        Double requestBody = 500.0;
        mockMvc.perform(MockMvcRequestBuilders.patch("/accounts/transfer")
                        .contentType(MediaType.APPLICATION_JSON)
                        .param("fromAccountNumber", "123e4567-e89b-12d3-a456-556642440000")
                        .param("toAccountNumber", "123e4567-e89b-12d3-a456-556642440001")
                        .content(String.valueOf(requestBody)))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].balance").value(1000.0))
                .andExpect(MockMvcResultMatchers.jsonPath("$[1].balance").value(500.0));
    }

    @Test
    @WithMockUser(username = "testUser")
    void transfer_invalidAmount() throws Exception {
        when(accountService.transfer(anyString(), anyString(), anyDouble()))
                .thenThrow(new IllegalArgumentException("Amount must be greater than zero."));

        Double requestBody = 500.0;
        mockMvc.perform(MockMvcRequestBuilders.patch("/accounts/transfer")
                        .contentType(MediaType.APPLICATION_JSON)
                        .param("fromAccountNumber", "123e4567-e89b-12d3-a456-556642440000")
                        .param("toAccountNumber", "123e4567-e89b-12d3-a456-556642440001")
                        .content(String.valueOf(requestBody)))
                .andExpect(MockMvcResultMatchers.status().isBadRequest())
                .andExpect(MockMvcResultMatchers.content().string("Amount must be greater than zero."));
    }
}