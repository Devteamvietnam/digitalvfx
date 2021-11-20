package com.devteam.module.account.service;

import com.devteam.module.account.dto.AccountDto;
import com.devteam.module.account.entity.Account;
import com.devteam.module.base.CRUDOperationService;
import com.devteam.module.base.exception.AppException;
import com.fasterxml.jackson.core.JsonProcessingException;

public interface AccountService extends CRUDOperationService<Account, AccountDto> {
    //check if the user has already registered
    boolean exists(String username);
    //find user and pass
    Account find(String username, String password);
    Account find(String username);

    Account register(AccountDto user) throws AppException;

    Account resetPassword(String username, String password) throws AppException;

    void initDummyData() throws JsonProcessingException;

    void init();


}
