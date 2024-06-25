package com.example.homework_module3.Homework03.Service.mappers;

import com.example.homework_module3.Homework03.Service.DtoMapperFacade;
import com.example.homework_module3.Homework03.domain.Customer;
import com.example.homework_module3.Homework03.domain.dto.CustomerDtoResponse;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class CustomerDtoMapperResponse extends DtoMapperFacade<Customer, CustomerDtoResponse> {
    public CustomerDtoMapperResponse() {
        super(Customer.class, CustomerDtoResponse.class);
    }

    @Override
    protected void decorateDto(CustomerDtoResponse dto, Customer entity) {
        dto.setAccounts(entity.getAccounts());
    }
}
