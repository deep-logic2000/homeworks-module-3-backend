package com.example.homework_module3.Homework05.domain.dto;

import jakarta.validation.constraints.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class CustomerDtoRequest {
    @NotBlank
    @Size(min = 2, message = "Name must be at least 2 characters long")
    private String name;

    @Email(regexp = "^[\\w-\\.]+@([\\w-]+\\.)+[\\w-]{2,4}$", message = "Please provide a valid email address")
    private String email;

    @Min(value = 18, message = "Age must be at least 18")
    private Integer age;

    @ValidPassword
    @NotBlank(message = "Please provide a password")
    private String password;

    @Pattern(regexp = "^(\\+\\d{1,3}( )?)?\\d{10}$", message = "Phone number is invalid")
    private String phoneNumber;

}
