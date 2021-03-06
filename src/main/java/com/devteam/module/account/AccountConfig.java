package com.devteam.module.account;

import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@Configuration
@ComponentScan(
        basePackages = { "com.devteam.module.account" }
)
@EnableJpaRepositories(
        basePackages = { "com.devteam.module.account.repository" }
)
@EnableConfigurationProperties
@EnableTransactionManagement
public class AccountConfig {
}
