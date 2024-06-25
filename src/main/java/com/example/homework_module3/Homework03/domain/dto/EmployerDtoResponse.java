package com.example.homework_module3.Homework03.domain.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class EmployerDtoResponse {
        private Long id;
        private String companyName;
        private String companyAddress;
}
