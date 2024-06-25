package com.example.homework_module3.Homework03.Service.mappers;

import com.example.homework_module3.Homework03.Service.DtoMapperFacade;
import com.example.homework_module3.Homework03.domain.Account;
import com.example.homework_module3.Homework03.domain.dto.AccountDtoResponse;
import org.springframework.stereotype.Service;

@Service
public class AccountDtoMapperResponse extends DtoMapperFacade<Account, AccountDtoResponse> {
    public AccountDtoMapperResponse() {
        super(Account.class, AccountDtoResponse.class);
    }
}
