package com.example.homework_module3.Homework05.Service.mappers;

import com.example.homework_module3.Homework05.domain.Account;
import com.example.homework_module3.Homework05.domain.dto.AccountDtoResponse;
import com.example.homework_module3.Homework05.Service.DtoMapperFacade;
import org.springframework.stereotype.Service;

@Service
public class AccountDtoMapperResponse extends DtoMapperFacade<Account, AccountDtoResponse> {
    public AccountDtoMapperResponse() {
        super(Account.class, AccountDtoResponse.class);
    }
}
