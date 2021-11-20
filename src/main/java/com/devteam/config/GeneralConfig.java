package com.devteam.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

@Configuration
public class GeneralConfig {
    @Value("${app.errorlevel}")
    public String ERROR_LEVEL;

    @Value("${app.jwtSecret}")
    public String JWT_SECRET;

    @Value("${app.jwtExpirationMs}")
    public String JWT_EXPIRATIONMS;

    @Value("${app.dummyDataEnabled}")
    public boolean DUMMY_DATA_ENABLED;

    @Value("${app.username}")
    public String USERNAME;

    @Value("${app.password}")
    public String PASSWORD;

    @Value("${app.bypassAuthentication:false}")
    public boolean BYPASS_AUTHENTICATION;
}
