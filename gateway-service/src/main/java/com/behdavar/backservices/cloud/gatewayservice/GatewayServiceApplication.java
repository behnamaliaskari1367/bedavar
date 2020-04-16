package com.behdavar.backservices.cloud.gatewayservice;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.message.EntryMessage;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.cloud.netflix.zuul.EnableZuulProxy;
import org.springframework.context.ConfigurableApplicationContext;

@EnableZuulProxy
@SpringBootApplication
public class GatewayServiceApplication extends SpringBootServletInitializer {

    private static final Logger LOGGER = LogManager.getLogger(GatewayServiceApplication.class);
    private static ConfigurableApplicationContext context;

    public static void main(String... args) {
        EntryMessage traceEntry = LOGGER.traceEntry("args: {}", (Object[]) args);
        context = SpringApplication.run(GatewayServiceApplication.class, args);
        LOGGER.traceExit(traceEntry);
    }

    public static void restart() {
        EntryMessage traceEntry = LOGGER.traceEntry("restart()");
        ApplicationArguments args = context.getBean(ApplicationArguments.class);

        Thread thread = new Thread(() -> {
            LOGGER.warn("context close started");
            context.close();
            LOGGER.warn("context restart started");
            context = SpringApplication.run(GatewayServiceApplication.class, args.getSourceArgs());
        });

        try {
            long time = 5L * 1000L;
            Thread.sleep(time);
        } catch (InterruptedException e) {
            LOGGER.error("error on sleep, message is: " + e.getMessage(), e);
            Thread.currentThread().interrupt();
        }
        thread.setDaemon(false);
        thread.start();
        LOGGER.traceExit(traceEntry);
    }
}
