package com.example.homework_module3.Homework05.Service.mappers;

import com.example.homework_module3.Homework05.domain.Account;
import com.example.homework_module3.Homework05.domain.dto.AccountDtoRequest;
import com.example.homework_module3.Homework05.Service.DtoMapperFacade;
import org.springframework.stereotype.Service;

@Service
public class AccountDtoMapperRequest extends DtoMapperFacade<Account, AccountDtoRequest> {

    public AccountDtoMapperRequest() {
        super(Account.class, AccountDtoRequest.class);
    }
}
