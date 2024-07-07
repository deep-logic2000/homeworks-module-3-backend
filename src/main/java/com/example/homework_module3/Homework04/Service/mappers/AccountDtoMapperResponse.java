package com.example.homework_module3.Homework04.Service.mappers;

import com.example.homework_module3.Homework04.Service.DtoMapperFacade;
import com.example.homework_module3.Homework04.domain.Account;
import com.example.homework_module3.Homework04.domain.dto.AccountDtoResponse;
import org.springframework.stereotype.Service;

@Service
public class AccountDtoMapperResponse extends DtoMapperFacade<Account, AccountDtoResponse> {
    public AccountDtoMapperResponse() {
        super(Account.class, AccountDtoResponse.class);
    }
}
