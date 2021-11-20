package com.devteam.module.account.controller;

import com.devteam.config.GeneralConfig;
import com.devteam.module.account.dto.AccountDto;
import com.devteam.module.account.entity.Account;
import com.devteam.module.account.service.AccountService;
import com.devteam.module.base.exception.AppException;
import com.devteam.module.base.exception.AppExceptionCode;
import com.devteam.module.base.exception.MessageResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/v1/user")
@Slf4j
public class AccountController {

    @Autowired
    GeneralConfig config;

    @Autowired
    @Qualifier("accountService")
    AccountService service;


    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody AccountDto user) throws AppException {
        log.info("Creating user : {}", user);
        if(!service.exists(user.getEmail())) {
            Account result = service.register(user);
            if(result!=null) {
                return ResponseEntity.ok(new MessageResponse("User registered successfully!"));
            } else {
                throw AppExceptionCode.USER_REGISTRATION_FAILED_500_4006;
            }
        } else {
            throw AppExceptionCode.USER_ALREADY_REGISTERED_400_4000;
        }
    }

}
