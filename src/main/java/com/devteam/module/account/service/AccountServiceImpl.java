package com.devteam.module.account.service;

import com.devteam.config.GeneralConfig;
import com.devteam.module.account.dto.AccountDto;
import com.devteam.module.account.entity.Account;
import com.devteam.module.account.entity.ERole;
import com.devteam.module.account.entity.Role;
import com.devteam.module.account.repository.AccountRepository;
import com.devteam.module.account.repository.RoleRepository;
import com.devteam.module.base.convert.AccountConverter;
import com.devteam.module.base.exception.AppException;
import com.devteam.util.Arrays;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service
@Transactional
@Qualifier("accountService")
public class AccountServiceImpl implements AccountService {

    @Autowired
    private AccountRepository accountRepository;
    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private PasswordEncoder encoder;

    @Autowired
    private GeneralConfig config;


    @Override
    public boolean exists(String username) {
        return accountRepository.existsByUsername(username);
    }

    @Override
    public Account find(String username, String password) {
        Account account = accountRepository.findByUsernameAndPassword(username, password);
        if(account != null) {
            return accountRepository.findByUsernameAndPassword(username, password);
        } else {
            return null;
        }
    }

    @Override
    public Account find(String username) {
        return accountRepository.findByUsername(username);
    }

    @Override
    public Account register(AccountDto user) throws AppException {
        user.setRoles(new HashSet<String>(Arrays.asList("user")));
        return save(user);
    }

    @Override
    public Account resetPassword(String username, String password) throws AppException {
        Account account = accountRepository.findByUsername(username);
        String pwd = encoder.encode(password);
        account.setPassword(pwd);
        return accountRepository.save(account);
    }


    @Override
    public List<Account> findAll() {
        return accountRepository.findAll();
    }

    @Override
    public Account findOneById(String id) {
        Optional<Account> user = accountRepository.findById((id));
        if(user.isPresent()) {
            return user.get();
        } else {
            return null;
        }
    }

    @Override
    public Account save(AccountDto dto) {
        Account act = new AccountConverter().dtoToEntity(dto);
        String pwd = encoder.encode(dto.getPassword());
        act.setPassword(pwd);
        act.setAccountRoles(getRoles(dto.getRoles()));
        return accountRepository.save(act);
    }

    private Set<Role> getRoles(Set<String> strRoles) {
        Set<Role> roles = new HashSet<>();

        if (strRoles == null) {
            Role userRole = roleRepository.findByName(ERole.ROLE_USER)
                    .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
            roles.add(userRole);
        } else {
            strRoles.forEach(role -> {
                switch (role) {
                    case "admin":
                        Role adminRole = roleRepository.findByName(ERole.ROLE_ADMIN)
                                .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
                        roles.add(adminRole);

                        break;
                    case "mod":
                        Role modRole = roleRepository.findByName(ERole.ROLE_MODERATOR)
                                .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
                        roles.add(modRole);

                        break;
                    default:
                        Role userRole = roleRepository.findByName(ERole.ROLE_USER)
                                .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
                        roles.add(userRole);
                }
            });
        }
        return roles;
    }

    @Override
    public void delete(String id) {
    accountRepository.deleteById(id);
    }

    @Override
    public void deleteAll() {
    accountRepository.deleteAll();
    }

    @Override
    public long count() {
        return accountRepository.count();
    }

    @Override
    public void initDummyData() throws JsonProcessingException {
        AccountDto dto = new AccountDto();
        dto.setUsername("tester@digital.com");
        dto.setEmail("tester@digital.com");
        dto.setPassword("tester@123");
        dto.setFullname("User tester");
        dto.setFirstname("tester");
        dto.setLastname("User");
        dto.setAddress("HCM");
        dto.setBirthdate("20/11/2021");
        dto.setCity("HCM");
        dto.setGender("Male");
        dto.setPhonenumber("0337303666");
        dto.setJob("Manager");
        dto.setRoles(new HashSet<String>(Arrays.asList("user")));
        save(dto);
    }

    @Override
    public void init() {
        if(roleRepository.count()==0) {
            Role role = new Role();
            role = new Role();
            role.setName(ERole.ROLE_USER);
            roleRepository.save(role);
            role = new Role();
            role.setName(ERole.ROLE_ADMIN);
            roleRepository.save(role);
            role = new Role();
            role.setName(ERole.ROLE_MODERATOR);
            roleRepository.save(role);
        }

        AccountDto dto = new AccountDto();
        dto.setUsername(config.USERNAME);
        dto.setPassword(config.PASSWORD);
        dto.setEmail("ddthien@gmail.com");
        dto.setFullname("Admin");
        dto.setFirstname("admin");
        dto.setLastname("ddthien");
        dto.setAddress("HCM");
        dto.setBirthdate("20/11/2021");
        dto.setCity("HCM");
        dto.setGender("Male");
        dto.setPhonenumber("0337303666");
        dto.setJob("IT");
        dto.setRoles(new HashSet<String>(Arrays.asList("user", "admin")));
        save(dto);
    }
}
