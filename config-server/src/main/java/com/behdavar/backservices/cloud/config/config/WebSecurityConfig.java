package com.behdavar.backservices.cloud.config.config;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.message.EntryMessage;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@Order(0)
@Configuration
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    private static final Logger LOGGER = LogManager.getLogger(WebSecurityConfig.class);
    private static final String USER_ROLE = "USER";

    @Override
    protected void configure(AuthenticationManagerBuilder authenticationManagerBuilder) throws Exception {
        EntryMessage traceEntry = LOGGER.traceEntry("authenticationManagerBuilder: {}", authenticationManagerBuilder);
        authenticationManagerBuilder
                .inMemoryAuthentication()
                .withUser("backservice")
                .password(passwordEncoder().encode("backservices_P@ssw0rd"))
                .roles(USER_ROLE);
        LOGGER.traceExit(traceEntry);
    }

    /**
     * for main app do web security check
     *
     * @param httpSecurity to config
     * @throws Exception see {@link HttpSecurity}
     */
    @Override
    protected void configure(HttpSecurity httpSecurity) throws Exception {
        EntryMessage traceEntry = LOGGER.traceEntry("httpSecurity: {}", httpSecurity);
        httpSecurity
                .requestMatchers().antMatchers(
                "/registry-service*/**",
                "/auth-service*/**",
                "/gateway-service*/**",
                "/encrypt", "/decrypt")
                .and()
                .authorizeRequests().anyRequest().authenticated()
                .and()
                .httpBasic()
                .and()
                .csrf().disable().authorizeRequests()
                .and()
                .formLogin().disable();
        LOGGER.traceExit(traceEntry);
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        EntryMessage traceEntry = LOGGER.traceEntry();
        return LOGGER.traceExit(traceEntry, new BCryptPasswordEncoder());
    }
}
