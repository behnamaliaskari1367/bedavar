package com.behdavar.backservices.auth;


import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.message.EntryMessage;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;

@EnableResourceServer
@EnableDiscoveryClient
@SpringBootApplication
@EnableAuthorizationServer
public class AuthServiceApplication extends SpringBootServletInitializer {

    private static final Logger LOGGER = LogManager.getLogger(AuthServiceApplication.class);

    public static void main(String... args) {
        EntryMessage traceEntry = LOGGER.traceEntry("args: {}", (Object[]) args);
        SpringApplication.run(AuthServiceApplication.class, args);
        LOGGER.traceExit(traceEntry);
    }
}
