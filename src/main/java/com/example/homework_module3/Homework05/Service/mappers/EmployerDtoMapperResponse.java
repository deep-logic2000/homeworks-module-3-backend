package com.example.homework_module3.Homework05.Service.mappers;

import com.example.homework_module3.Homework05.domain.Employer;
import com.example.homework_module3.Homework05.domain.dto.EmployerDtoResponse;
import com.example.homework_module3.Homework05.Service.DtoMapperFacade;
import org.springframework.stereotype.Service;

@Service
public class EmployerDtoMapperResponse extends DtoMapperFacade<Employer, EmployerDtoResponse> {
    public EmployerDtoMapperResponse() {
        super(Employer.class, EmployerDtoResponse.class);
    }
}
