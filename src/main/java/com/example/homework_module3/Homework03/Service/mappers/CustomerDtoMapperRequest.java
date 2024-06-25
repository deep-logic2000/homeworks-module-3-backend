package com.example.homework_module3.Homework03.Service.mappers;

import com.example.homework_module3.Homework03.Service.DtoMapperFacade;
import com.example.homework_module3.Homework03.domain.Customer;
import com.example.homework_module3.Homework03.domain.dto.CustomerDtoRequest;
import com.example.homework_module3.Homework03.domain.dto.CustomerDtoResponse;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class CustomerDtoMapperRequest extends DtoMapperFacade<Customer, CustomerDtoRequest> {
    public CustomerDtoMapperRequest() {
        super(Customer.class, CustomerDtoRequest.class);
    }

//    @Override
//    protected void decorateDto(CustomerDtoRequest dto, Customer entity) {
//        dto.setCreatedDate(LocalDateTime.now());
//        dto.setLastModifiedDate(LocalDateTime.now());
//    }
}
