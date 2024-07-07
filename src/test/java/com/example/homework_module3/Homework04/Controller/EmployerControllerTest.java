package com.example.homework_module3.Homework04.Controller;

import com.example.homework_module3.Homework04.Service.EmployerServiceImpl;
import com.example.homework_module3.Homework04.Service.mappers.EmployerDtoMapperResponse;
import com.example.homework_module3.Homework04.domain.Employer;
import com.example.homework_module3.Homework04.domain.dto.EmployerDtoResponse;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.List;

import static org.mockito.Mockito.*;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@AutoConfigureMockMvc
class EmployerControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Mock
    private EmployerServiceImpl employerService;

    @InjectMocks
    private EmployerController employerController;

    @MockBean
    private EmployerDtoMapperResponse employerDtoMapperResponse;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    @WithMockUser(username = "testUser")
    void getAll() throws Exception {
        Employer employer = new Employer();

        EmployerDtoResponse employerDtoResponse = new EmployerDtoResponse();
        employerDtoResponse.setCompanyName("Comp 1");
        employerDtoResponse.setCompanyAddress("123 Tech Road");

        when(employerService.findAll()).thenReturn(List.of(employer));
        when(employerDtoMapperResponse.convertToDto(employer)).thenReturn(employerDtoResponse);

        mockMvc.perform(MockMvcRequestBuilders.get("/employers").contentType("application/json"))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].name", Matchers.is("Tech Corp")))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].address", Matchers.is("123 Tech Avenue")));
    }

}