package com.devteam.module.account.controller;

import com.devteam.config.GeneralConfig;
import com.devteam.module.account.dto.AccountDto;
import com.devteam.module.account.dto.JwtResponse;
import com.devteam.module.account.dto.LoginRequest;
import com.devteam.module.account.entity.Account;
import com.devteam.module.account.service.AccountService;
import com.devteam.module.base.convert.AccountConverter;
import com.devteam.module.base.exception.AppException;
import com.devteam.module.base.exception.AppExceptionCode;
import com.devteam.module.base.exception.MessageResponse;
import com.devteam.security.jwt.JwtUtils;
import com.devteam.security.jwt.UserDetailsImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

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

    @Autowired
    AuthenticationManager authenticationManager;

    @Autowired
    JwtUtils jwtUtils;


    @PostMapping("/authenticate")
    public ResponseEntity<?> login(@Valid @RequestBody LoginRequest loginRequest) throws AppException {
        log.info("Authenticating user : {}", loginRequest.getUsername());
        try {

            Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(loginRequest.getUsername(), loginRequest.getPassword()));

            SecurityContextHolder.getContext().setAuthentication(authentication);
            String jwt = jwtUtils.generateJwtToken(authentication);

            UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
            List<String> roles = userDetails.getAuthorities().stream()
                    .map(item -> item.getAuthority())
                    .collect(Collectors.toList());

            return ResponseEntity.ok(new JwtResponse(jwt, "Bearer"));

        } catch (AuthenticationException ex) {
            throw AppExceptionCode.USER_LOGIN_FAILED_400_4003;
        }
    }

    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody AccountDto user) throws AppException {
        log.info("Creating user : {}", user);
        if (!service.exists(user.getEmail())) {
            Account result = service.register(user);
            if (result != null) {
                return ResponseEntity.ok(new MessageResponse("User registered successfully!"));
            } else {
                throw AppExceptionCode.USER_REGISTRATION_FAILED_500_4006;
            }
        } else {
            throw AppExceptionCode.USER_ALREADY_REGISTERED_400_4000;
        }
    }

    @PostMapping("/resetpassword")
    public ResponseEntity<?> resetPassword(@RequestParam String username, @RequestParam String password) throws AppException {
        log.info("Reset password for : {}", username);

        if (service.exists(username)) {
            AccountDto result = new AccountConverter().entityToDto(service.resetPassword(username, password));
            if (result != null) {
                return ResponseEntity.ok(new MessageResponse("Password changed successfully!"));
            } else {
                throw AppExceptionCode.USER_RESET_PASSWORD_FAILED_500_4007;
            }
        } else {
            throw AppExceptionCode.USER_NOTFOUND_400_4005;
        }
    }
}
