package com.devteam.module.base.convert;

import com.devteam.module.account.dto.AccountDto;
import com.devteam.module.account.entity.Account;
import com.devteam.module.account.entity.ERole;

public class AccountConverter extends BaseConverter {

    private static AccountConverter instance;

    public static AccountConverter getInstance() {
        if(instance==null) {
            instance = new AccountConverter();
        }
        return instance;
    }

    private AccountConverter() {}

    public Account dtoToEntity(AccountDto dto) {
        return map(dto, Account.class);
    }

    public AccountDto entityToDto(Account e) {
        AccountDto u = map(e, AccountDto.class);
        u.getRoles().clear();
        e.getAccountRoles().stream().forEach(i -> {
            if(i.getName() == ERole.ROLE_ADMIN) {
                u.getRoles().add("admin");
            } else if(i.getName() == ERole.ROLE_MODERATOR) {
                u.getRoles().add("mod");
            } else {
                u.getRoles().add("user");
            }
        });
        return u;
    }
}

