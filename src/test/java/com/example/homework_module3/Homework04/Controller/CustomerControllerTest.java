package com.example.homework_module3.Homework04.Controller;

import com.example.homework_module3.Homework04.Service.CustomerServiceImpl;
import com.example.homework_module3.Homework04.Service.mappers.AccountDtoMapperRequest;
import com.example.homework_module3.Homework04.Service.mappers.AccountDtoMapperResponse;
import com.example.homework_module3.Homework04.Service.mappers.CustomerDtoMapperRequest;
import com.example.homework_module3.Homework04.Service.mappers.CustomerDtoMapperResponse;
import com.example.homework_module3.Homework04.domain.Account;
import com.example.homework_module3.Homework04.domain.Currency;
import com.example.homework_module3.Homework04.domain.Customer;
import com.example.homework_module3.Homework04.domain.dto.AccountDtoRequest;
import com.example.homework_module3.Homework04.domain.dto.AccountDtoResponse;
import com.example.homework_module3.Homework04.domain.dto.CustomerDtoRequest;
import com.example.homework_module3.Homework04.domain.dto.CustomerDtoResponse;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultMatcher;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.math.BigDecimal;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class CustomerControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private CustomerServiceImpl customerService;

    @MockBean
    private CustomerDtoMapperRequest dtoMapperRequest;

    @MockBean
    private CustomerDtoMapperResponse dtoMapperResponse;

    @MockBean
    private AccountDtoMapperRequest accountDtoMapperRequest;

    @MockBean
    private AccountDtoMapperResponse accountDtoMapperResponse;

    @MockBean
    private PasswordEncoder passwordEncoder;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void getAll() throws Exception {
        Page<Customer> customerPage = new PageImpl<>(List.of(new Customer()));
        Mockito.when(customerService.getAllPageble(0, 10)).thenReturn(customerPage);

        mockMvc.perform(MockMvcRequestBuilders.get("/customers"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].length()").value(2));
    }

    @Test
    void getById() throws Exception {
        Customer customer = new Customer();
        Mockito.when(customerService.getOne(1L)).thenReturn(customer);

        mockMvc.perform(MockMvcRequestBuilders.get("/customers/1"))
                .andExpect(status().isOk());
    }

    @Test
    void createCustomer() throws Exception {
        CustomerDtoRequest dto = new CustomerDtoRequest();
        Customer customer = new Customer();
        Mockito.when(dtoMapperRequest.convertToEntity(dto)).thenReturn(customer);
        Mockito.when(passwordEncoder.encode(dto.getPassword())).thenReturn("encodedPassword");
        Mockito.when(customerService.save(customer)).thenReturn(customer);
        Mockito.when(dtoMapperResponse.convertToDto(customer)).thenReturn(new CustomerDtoResponse());

        mockMvc.perform(MockMvcRequestBuilders.post("/customers")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(dto)))
                .andExpect(status().isOk());
    }

    @Test
    void updateCustomer() throws Exception {
        Long customerId = 1L;
        CustomerDtoRequest customerDtoRequest = new CustomerDtoRequest();
        Customer updatedCustomerEntity = new Customer();
        Customer updatedCustomer = new Customer();
        CustomerDtoResponse customerDtoResponse = new CustomerDtoResponse();

        Mockito.when(dtoMapperRequest.convertToEntity(customerDtoRequest)).thenReturn(updatedCustomerEntity);
        Mockito.when(customerService.getOne(customerId)).thenReturn(updatedCustomerEntity);
        Mockito.when(customerService.update(updatedCustomerEntity)).thenReturn(updatedCustomer);
        Mockito.when(dtoMapperResponse.convertToDto(updatedCustomer)).thenReturn(customerDtoResponse);

        mockMvc.perform(MockMvcRequestBuilders.put("/customers/{id}", customerId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(customerDtoRequest)))
                .andExpect(status().isOk())
                .andExpect((ResultMatcher) content().json(objectMapper.writeValueAsString(customerDtoResponse)));
    }

    @Test
    void deleteCustomer() throws Exception {
        Long customerId = 1L;

        mockMvc.perform(MockMvcRequestBuilders.delete("/customers/{id}", customerId))
                .andExpect(status().isOk())
                .andExpect((ResultMatcher) content().string("Customer deleted successfully."));

        Mockito.verify(customerService, Mockito.times(1)).deleteCustomer(customerId);
    }

    @Test
    void createAccount() throws Exception {
        Long customerId = 1L;
        String currency = "USD";
        double amount = 100.0;

        Customer customer = new Customer();
        customer.setId(customerId);

//        AccountDtoRequest accountDtoRequest = new AccountDtoRequest();
        Account account = new Account();
        account.setCurrency(Currency.valueOf(currency));
        account.setBalance(amount);
        account.setCustomer(customer);


        when(customerService.getOne(customerId)).thenReturn(customer);
        doNothing().when(customerService).createAccount(account);

        mockMvc.perform(MockMvcRequestBuilders.post("/customers/{id}/accounts", customerId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"currency\": \"" + currency + "\", \"balance\": " + amount + "}"))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    void deleteAccount() throws Exception {
        Long customerId = 1L;
        UUID accountNumber = UUID.fromString("123e4567-e89b-12d3-a456-426614174000");
        Long accountId = 1L;

        when(customerService.getOne(customerId)).thenReturn(new Customer());

        doThrow(new IllegalArgumentException("Account not found")).when(customerService).deleteAccount(customerId, accountId);

        mockMvc.perform(MockMvcRequestBuilders.delete("/customers/{customerId}/accounts/{accountNumber}", customerId, accountNumber))
                .andExpect(MockMvcResultMatchers.status().isBadRequest())
                .andExpect(MockMvcResultMatchers.content().string("Account with number " + accountNumber + " not found"));
    }
}