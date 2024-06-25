package com.example.homework_module3.Homework03.domain.dto;

import com.example.homework_module3.Homework03.domain.Account;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CustomerDto {
    @NotNull
    private Long id;
    private String name;
    private String email;
    private Integer age;
    private List<Account> accounts;
    @JsonIgnore
    private String password;
    private String phoneNumber;
    private LocalDateTime createdDate;
    private LocalDateTime lastModifiedDate;
}
