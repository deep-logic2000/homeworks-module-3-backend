package com.example.homework_module3.Homework05.domain.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class EmployerDtoRequest {
        @NotBlank(message = "Please provide a company name")
        @NotNull
        @Size(min = 3, message = "Company name must be at least 3 characters long")
        private String companyName;

        @NotBlank(message = "Please provide a company address")
        @NotNull
        @Size(min = 3, message = "Company address must be at least 3 characters long")
        private String companyAddress;
}
