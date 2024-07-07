package com.example.homework_module3.Homework04.domain.dto;

import com.example.homework_module3.Homework04.domain.Currency;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

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
