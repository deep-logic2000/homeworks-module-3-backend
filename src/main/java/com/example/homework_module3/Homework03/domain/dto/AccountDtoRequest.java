package com.example.homework_module3.Homework03.domain.dto;

import com.example.homework_module3.Homework03.domain.Currency;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Digits;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.Pattern;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.Value;

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
