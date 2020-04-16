package com.behdavar.backservices.cloud.config;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.message.EntryMessage;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.config.server.EnableConfigServer;

@EnableDiscoveryClient
@EnableConfigServer
@SpringBootApplication
public class ConfigServerApplication extends SpringBootServletInitializer {

    private static final Logger LOGGER = LogManager.getLogger(ConfigServerApplication.class);

    public static void main(String... args) {
        EntryMessage traceEntry = LOGGER.traceEntry("args: {}", (Object[]) args);
        SpringApplication.run(ConfigServerApplication.class, args);
        LOGGER.traceExit(traceEntry);
    }
}
