package com.example.homework_module3.Homework05.Service.mappers;

import com.example.homework_module3.Homework05.domain.Customer;
import com.example.homework_module3.Homework05.domain.dto.CustomerDtoRequest;
import com.example.homework_module3.Homework05.Service.DtoMapperFacade;
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
