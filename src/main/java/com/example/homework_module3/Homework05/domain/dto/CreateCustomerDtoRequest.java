package com.example.homework_module3.Homework05.domain.dto;

import lombok.Data;

@Data
public class CreateCustomerDtoRequest {
    private Long id;
    private String name;
    private String email;
    private Integer age;
    private String password;
    private String phoneNumber;


    public CreateCustomerDtoRequest(Long id, String name, String email, Integer age, String password, String phoneNumber) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.age = age;
        this.password = password;
        this.phoneNumber = phoneNumber;
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

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }
}
