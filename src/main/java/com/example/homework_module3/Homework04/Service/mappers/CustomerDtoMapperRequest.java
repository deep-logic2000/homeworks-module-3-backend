package com.example.homework_module3.Homework04.Service.mappers;

import com.example.homework_module3.Homework04.Service.DtoMapperFacade;
import com.example.homework_module3.Homework04.domain.Customer;
import com.example.homework_module3.Homework04.domain.dto.CustomerDtoRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class CustomerDtoMapperRequest extends DtoMapperFacade<Customer, CustomerDtoRequest> {
@Autowired
    private PasswordEncoder passwordEncoder;

    public CustomerDtoMapperRequest() {
        super(Customer.class, CustomerDtoRequest.class);
    }

//    @Override
//    protected void decorateEntity(Customer entity, CustomerDtoRequest dto) {
//        entity.setEncryptedPassword(passwordEncoder.encode(entity.getEncryptedPassword()));

//    }
}
