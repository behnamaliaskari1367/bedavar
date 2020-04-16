package com.behdavar.backservices.auth.config;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.message.EntryMessage;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter;

@Order(2)
@Configuration
@EnableResourceServer
public class ResourceServerConfig extends ResourceServerConfigurerAdapter {

    private static final Logger LOGGER = LogManager.getLogger(ResourceServerConfig.class);

    @Override
    public void configure(HttpSecurity httpSecurity) throws Exception {
        EntryMessage traceEntry = LOGGER.traceEntry("httpSecurity: {}", httpSecurity);
        httpSecurity
                .requestMatchers()
                .antMatchers("/api/v1/**", "/oauth2/principal", "/oauth2/principal/**")
                .and()
                .authorizeRequests()
                .anyRequest().authenticated();
        LOGGER.traceExit(traceEntry);
    }
}
