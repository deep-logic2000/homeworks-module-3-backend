package com.example.homework_module3.Homework04.Service.mappers;

import com.example.homework_module3.Homework04.Service.DtoMapperFacade;
import com.example.homework_module3.Homework04.domain.Employer;
import com.example.homework_module3.Homework04.domain.dto.EmployerDtoRequest;
import org.springframework.stereotype.Service;

@Service
public class EmployerDtoMapperRequest extends DtoMapperFacade<Employer, EmployerDtoRequest> {

    public EmployerDtoMapperRequest() {
        super(Employer.class, EmployerDtoRequest.class);
    }
}
