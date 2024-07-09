package com.example.homework_module3.Homework05.domain;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.GrantedAuthority;

@RequiredArgsConstructor
public enum Roles implements GrantedAuthority {
    USER("USER"), ADMIN("ADMIN");
    private final String value;

    @Override
    public String getAuthority() {

        return value;
    }
}