package com.devteam;

import com.devteam.module.account.entity.Role;
import com.devteam.module.account.entity.User;
import com.devteam.module.account.repository.UserRepository;
import com.devteam.module.account.service.UserService;
import com.devteam.security.WebSecurityConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Import;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
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

    @Bean
    public PasswordEncoder encoder() { return new BCryptPasswordEncoder(); }

    @Bean
    CommandLineRunner run (UserService service) {
        return args -> {
            service.saveRole(new Role("ROLE_USER"));
            service.saveRole(new Role("ROLE_ADMIN"));

            service.saveUser(new User("admin", "admin", "admin@123", "admin@gmail.com"));

            service.addRoleToUser("admin", "ROLE_ADMIN");

        };
    }

}
