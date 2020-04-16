package com.behdavar.backservices.app;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;

@SpringBootApplication
public class BackServiceApplication extends SpringBootServletInitializer {

    private static final Logger LOG = LogManager.getLogger(BackServiceApplication.class);

    @Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
        LOG.debug("configure started");
        return application.sources(BackServiceApplication.class);
    }

    public static void main(String[] args) {
        LOG.debug("run started");
        SpringApplication.run(BackServiceApplication.class, args);
    }
}
