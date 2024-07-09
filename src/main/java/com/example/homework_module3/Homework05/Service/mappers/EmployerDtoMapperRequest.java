package com.example.homework_module3.Homework05.Service.mappers;

import com.example.homework_module3.Homework05.domain.Employer;
import com.example.homework_module3.Homework05.domain.dto.EmployerDtoRequest;
import com.example.homework_module3.Homework05.Service.DtoMapperFacade;
import org.springframework.stereotype.Service;

@Service
public class EmployerDtoMapperRequest extends DtoMapperFacade<Employer, EmployerDtoRequest> {

    public EmployerDtoMapperRequest() {
        super(Employer.class, EmployerDtoRequest.class);
    }
}
