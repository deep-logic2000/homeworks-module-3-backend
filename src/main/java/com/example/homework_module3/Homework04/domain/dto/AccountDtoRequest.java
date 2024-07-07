package com.example.homework_module3.Homework04.domain.dto;

import com.example.homework_module3.Homework04.domain.Currency;
import jakarta.validation.constraints.Min;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
@NoArgsConstructor
public class AccountDtoRequest {
        private Long customerId;
        private Currency currency;
        @Min(value = 0, message = "Balance can be negative")
        private BigDecimal balance;

}
