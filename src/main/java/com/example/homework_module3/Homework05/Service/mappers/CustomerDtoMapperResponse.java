package com.example.homework_module3.Homework05.Service.mappers;

import com.example.homework_module3.Homework05.domain.Customer;
import com.example.homework_module3.Homework05.domain.dto.CustomerDtoResponse;
import com.example.homework_module3.Homework05.Service.DtoMapperFacade;
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
