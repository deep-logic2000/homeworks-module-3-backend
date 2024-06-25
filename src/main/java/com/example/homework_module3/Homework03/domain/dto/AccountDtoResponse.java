package com.example.homework_module3.Homework03.domain.dto;

import com.example.homework_module3.Homework03.domain.Currency;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
public class AccountDtoResponse {
        private Long id;
        private String number;
        private Currency currency;
        private Long customerId;
        private BigDecimal balance;
}
