package com.example.homework_module3.Homework04.Service.mappers;

import com.example.homework_module3.Homework04.Service.DtoMapperFacade;
import com.example.homework_module3.Homework04.domain.Account;
import com.example.homework_module3.Homework04.domain.dto.AccountDtoRequest;
import org.springframework.stereotype.Service;

@Service
public class AccountDtoMapperRequest extends DtoMapperFacade<Account, AccountDtoRequest> {

    public AccountDtoMapperRequest() {
        super(Account.class, AccountDtoRequest.class);
    }
}
