package com.devteam;

import com.devteam.module.account.AccountConfig;
import com.devteam.module.account.entity.Account;
import com.devteam.module.account.entity.Authority;
import com.devteam.module.account.repository.AccountDetailsRepository;
import com.devteam.security.WebSecurityConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.Import;
import org.springframework.security.crypto.password.PasswordEncoder;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.List;

@SpringBootApplication (
        scanBasePackages= {
                "com.devteam.*"
        },
        exclude = {
                SecurityAutoConfiguration.class
        })
@EnableConfigurationProperties
@Import(value = {
        WebSecurityConfig.class
})

public class DigitalApplication{

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private AccountDetailsRepository repo;

    static ConfigurableApplicationContext context;

    static public ApplicationContext run(String[] args, long wait) throws Exception {
        context = SpringApplication.run(DigitalApplication.class, args);
        isRunning(wait);
        return context;
    }

    static public boolean isRunning(long waitTime) {
        boolean running = false;
        if(waitTime <= 0) waitTime = 1;
        try {
            while(!running && waitTime > 0) {
                if(context == null) running = false;
                else running = context.isRunning();
                waitTime -= 100;
                if(!running && waitTime > 0) Thread.sleep(100);
            }
        } catch(Exception ex) {
            ex.printStackTrace();
        }
        return running;
    }

    static public void exit() {
        if(context != null) {
            SpringApplication.exit(context);
            context = null;
        }
    }



    public static void main(String[] args) throws Exception {
        run(args, 30000);
        Thread.currentThread().join();
    }

    @PostConstruct
    protected void init() {

        List<Authority> authorityList=new ArrayList<>();

        authorityList.add(createAuthority("USER","User role"));
        authorityList.add(createAuthority("ADMIN","Admin role"));

        Account user=new Account();

        user.setUserName("admin");
        user.setFirstName("admin");
        user.setLastName("admin");

        user.setPassword(passwordEncoder.encode("admin@123"));
        user.setEnabled(true);
        user.setAuthorities(authorityList);

        repo.save(user);

    }


    private Authority createAuthority(String roleCode,String roleDescription) {
        Authority authority=new Authority();
        authority.setRoleCode(roleCode);
        authority.setRoleDescription(roleDescription);
        return authority;
    }

}
