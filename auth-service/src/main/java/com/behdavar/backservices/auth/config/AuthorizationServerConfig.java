package com.behdavar.backservices.auth.config;

import com.behdavar.backservices.auth.service.impl.ClientDetailsServiceImpl;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.message.EntryMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.ClientDetailsService;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.oauth2.provider.token.store.InMemoryTokenStore;

@Configuration
@EnableAuthorizationServer
public class AuthorizationServerConfig extends AuthorizationServerConfigurerAdapter {

    private static final Logger LOGGER = LogManager.getLogger(AuthorizationServerConfig.class);

    private ClientDetailsService clientDetailsService;
    private UserDetailsService userDetailsService;
    private PasswordEncoder passwordEncoder;
    private AuthenticationManager authenticationManager;

    @Override
    public void configure(ClientDetailsServiceConfigurer clientDetailsServiceConfigurer) throws Exception {
        EntryMessage traceEntry = LOGGER.traceEntry("clientDetailsServiceConfigurer: {}", clientDetailsServiceConfigurer);
        clientDetailsServiceConfigurer.withClientDetails(clientDetailsService);
        LOGGER.traceExit(traceEntry);
    }

    @Override
    public void configure(AuthorizationServerEndpointsConfigurer authorizationServerEndpointsConfigurer) throws Exception {
        EntryMessage traceEntry = LOGGER.traceEntry("authorizationServerEndpointsConfigurer: {}", authorizationServerEndpointsConfigurer);
        authorizationServerEndpointsConfigurer
                .tokenStore(tokenStore())
                .authenticationManager(authenticationManager)
                .userDetailsService(userDetailsService);
        LOGGER.traceExit(traceEntry);
    }

    @Bean
    public TokenStore tokenStore() {
        EntryMessage traceEntry = LOGGER.traceEntry();
//        UaaTokenStore result = new UaaTokenStore();
        InMemoryTokenStore result = new InMemoryTokenStore();
        return LOGGER.traceExit(traceEntry, result);
    }

    @Override
    public void configure(AuthorizationServerSecurityConfigurer authorizationServerSecurityConfigurer) throws Exception {
        EntryMessage traceEntry = LOGGER.traceEntry("authorizationServerSecurityConfigurer: {}", authorizationServerSecurityConfigurer);
        authorizationServerSecurityConfigurer
                .tokenKeyAccess("permitAll()")
                .checkTokenAccess("isAuthenticated()")
                .passwordEncoder(passwordEncoder);
//                .allowFormAuthenticationForClients();
        LOGGER.traceExit(traceEntry);
    }

    @Autowired
    public void prepare(ClientDetailsServiceImpl clientDetailsService,
                        PasswordEncoder passwordEncoder,
                        @Qualifier("userDetailsServiceImpl") UserDetailsService userDetailsService,
                        AuthenticationManager authenticationManager) {
        LOGGER.trace("prepare Spring's dependency injection");
        this.clientDetailsService = clientDetailsService;
        this.passwordEncoder = passwordEncoder;
        this.authenticationManager = authenticationManager;
        this.userDetailsService = userDetailsService;
    }

}
