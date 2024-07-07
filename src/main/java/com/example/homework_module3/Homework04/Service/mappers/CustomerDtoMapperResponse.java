package com.example.homework_module3.Homework04.Service.mappers;

import com.example.homework_module3.Homework04.Service.DtoMapperFacade;
import com.example.homework_module3.Homework04.domain.Customer;
import com.example.homework_module3.Homework04.domain.dto.CustomerDtoResponse;
import org.springframework.stereotype.Service;

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
