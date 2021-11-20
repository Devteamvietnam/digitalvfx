package com.devteam.module.account.controller;

import com.devteam.config.GeneralConfig;
import com.devteam.module.account.service.AccountService;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.PostConstruct;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/")
public class AppController {

    private static final Logger log = LoggerFactory.getLogger(AppController.class);


    public static String applicationInstanceName = "Digital back-end API";

    @Autowired
    GeneralConfig config;
    @Autowired
    AccountService service;

    @PostConstruct
    public void init() throws JsonProcessingException {
        log.info("Initialize data");

        service.init();

        if(config.DUMMY_DATA_ENABLED || config.BYPASS_AUTHENTICATION) {
            service.initDummyData();
        }

        log.info("Initialize data finished");
    }


}
