package com.behdavar.backservices.auth.config;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.message.EntryMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.preauth.AbstractPreAuthenticatedProcessingFilter;

@Order(1)
@Configuration
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    private static final Logger LOGGER = LogManager.getLogger(AuthorizationServerConfig.class);

    private UserDetailsService userDetailsService;
    private PasswordEncoder passwordEncoder;
    private AuthServiceProperties authServiceProperties;

    @Override
    protected void configure(AuthenticationManagerBuilder authenticationManagerBuilder) throws Exception {
        EntryMessage traceEntry = LOGGER.traceEntry("authenticationManagerBuilder: {}", authenticationManagerBuilder);
        authenticationManagerBuilder
                .userDetailsService(userDetailsService)
                .passwordEncoder(passwordEncoder);
        LOGGER.traceExit(traceEntry);
    }

    @Override
    protected void configure(HttpSecurity httpSecurity) throws Exception {
        EntryMessage traceEntry = LOGGER.traceEntry("httpSecurity: {}", httpSecurity);

        httpSecurity
                .addFilterBefore(new CorsFilter(), AbstractPreAuthenticatedProcessingFilter.class)
                .requestMatchers()
                .antMatchers("/login", "/logout", "/oauth/authorize", "/error",
                        "/principal", "/principal/**")
                .and()
                .formLogin().loginPage(authServiceProperties.getLogin().getPage().getUrl()).permitAll()
                .and()
                .authorizeRequests()
                .anyRequest().authenticated()
////                .and()
////                .logout()
////                .logoutRequestMatcher(new AntPathRequestMatcher("/signout"))
////                .invalidateHttpSession(true)
////                .deleteCookies("JSESSIONID")
////                .logoutSuccessUrl("/")
                .and()
                .httpBasic()
////                .and().formLogin().permitAll()
                .and()
////                .csrf().csrfTokenRepository(CookieCsrfTokenRepository.withHttpOnlyFalse());
                .csrf().disable();
        LOGGER.traceExit(traceEntry);
    }


    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return LOGGER.traceExit(super.authenticationManagerBean());
    }

    @Autowired
    public void prepare(@Qualifier("userDetailsServiceImpl") UserDetailsService userDetailsService,
                        PasswordEncoder passwordEncoder,
                        AuthServiceProperties authServiceProperties) {
        LOGGER.trace("prepare Spring's dependency injection");
        this.userDetailsService = userDetailsService;
        this.passwordEncoder = passwordEncoder;
        this.authServiceProperties = authServiceProperties;
    }
}
