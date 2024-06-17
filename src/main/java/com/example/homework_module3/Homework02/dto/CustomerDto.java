package com.example.homework_module3.Homework02.dto;

import com.example.homework_module3.Homework02.domain.Account;
import lombok.Data;

import java.util.List;

@Data
public class CustomerDto {
    private Long id;
    private String name;
    private String email;
    private Integer age;
    private List<Account> accounts;


    public CustomerDto(Long id, String name, String email, Integer age, List<Account> accounts) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.age = age;
        this.accounts = accounts;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }
}
