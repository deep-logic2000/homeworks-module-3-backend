package com.example.homework_module3.Homework03.Service.mappers;

import com.example.homework_module3.Homework03.Service.DtoMapperFacade;
import com.example.homework_module3.Homework03.domain.Account;
import com.example.homework_module3.Homework03.domain.Employer;
import com.example.homework_module3.Homework03.domain.dto.AccountDtoRequest;
import com.example.homework_module3.Homework03.domain.dto.EmployerDtoRequest;
import org.springframework.stereotype.Service;

@Service
public class EmployerDtoMapperRequest extends DtoMapperFacade<Employer, EmployerDtoRequest> {

    public EmployerDtoMapperRequest() {
        super(Employer.class, EmployerDtoRequest.class);
    }
}
