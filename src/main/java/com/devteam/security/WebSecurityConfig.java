package com.devteam.security;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;



@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    @Override
    protected void configure(HttpSecurity httpSecurity) throws Exception {
        httpSecurity.headers().frameOptions().disable();
        httpSecurity.authorizeRequests().antMatchers("/", "/swagger-ui").permitAll();
        httpSecurity.authorizeRequests().antMatchers("/").permitAll()
                .and()
                .authorizeRequests().antMatchers("/h2-console/**").permitAll()
                .and()
                .headers().frameOptions().disable()
                .and()
                .csrf().ignoringAntMatchers("/h2-console/**")
                .and()
                .cors().disable();
        httpSecurity.csrf().disable();
        httpSecurity.authorizeRequests().anyRequest().authenticated();
    }

    @Bean
    @Override
    public AuthenticationManager authenticationManager() throws  Exception {
        return super.authenticationManager();
    }
}
