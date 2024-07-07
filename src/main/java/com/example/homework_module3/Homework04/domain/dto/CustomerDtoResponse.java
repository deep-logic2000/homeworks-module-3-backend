package com.example.homework_module3.Homework04.domain.dto;

import com.example.homework_module3.Homework04.domain.Account;
import com.fasterxml.jackson.annotation.JsonView;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class CustomerDtoResponse {
    @JsonView(Views.Summary.class)
    private Long id;

    @JsonView(Views.Summary.class)
    private String name;

    @JsonView(Views.Detailed.class)
    private String email;

    @JsonView(Views.Detailed.class)
    private Integer age;

    @JsonView(Views.Summary.class)
    private List<Account> accounts;

    @JsonView(Views.Detailed.class)
    private String phoneNumber;

    @JsonView(Views.Detailed.class)
    private LocalDateTime createdDate;

    @JsonView(Views.Detailed.class)
    private LocalDateTime lastModifiedDate;
}
