package com.example.homework_module3.Homework03.Service.mappers;

import com.example.homework_module3.Homework03.Service.DtoMapperFacade;
import com.example.homework_module3.Homework03.domain.Employer;
import com.example.homework_module3.Homework03.domain.dto.EmployerDtoResponse;
import org.springframework.stereotype.Service;

@Service
public class EmployerDtoMapperResponse extends DtoMapperFacade<Employer, EmployerDtoResponse> {
    public EmployerDtoMapperResponse() {
        super(Employer.class, EmployerDtoResponse.class);
    }
}
