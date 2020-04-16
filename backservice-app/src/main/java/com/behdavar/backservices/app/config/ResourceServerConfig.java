package com.behdavar.backservices.app.config;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.message.EntryMessage;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.core.env.Environment;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.oauth2.client.OAuth2ClientContext;
import org.springframework.security.oauth2.client.OAuth2RestOperations;
import org.springframework.security.oauth2.client.OAuth2RestTemplate;
import org.springframework.security.oauth2.client.token.grant.client.ClientCredentialsAccessTokenProvider;
import org.springframework.security.oauth2.client.token.grant.client.ClientCredentialsResourceDetails;
import org.springframework.security.oauth2.client.token.grant.password.ResourceOwnerPasswordAccessTokenProvider;
import org.springframework.security.oauth2.client.token.grant.password.ResourceOwnerPasswordResourceDetails;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter;
import org.springframework.web.client.RestTemplate;

/**
 * @author Abbas Alishirvani
 */
@Order(2)
@Configuration
@EnableResourceServer
public class ResourceServerConfig extends ResourceServerConfigurerAdapter {

    private static final Logger LOGGER = LogManager.getLogger(ResourceServerConfig.class);

    private final Environment environment;
    private final OAuth2ClientContext oauth2ClientContext;

    public ResourceServerConfig(Environment environment,
                                @Qualifier("oauth2ClientContext") OAuth2ClientContext oauth2ClientContext) {
        this.environment = environment;
        this.oauth2ClientContext = oauth2ClientContext;
    }

    @Override
    public void configure(HttpSecurity http) throws Exception {
        EntryMessage traceEntry = LOGGER.traceEntry("HttpSecurity: {}", http);
        http.authorizeRequests().anyRequest().permitAll();
        LOGGER.traceExit(traceEntry);
    }

    @Bean
    public OAuth2RestOperations passwordRestTemplate() {
        EntryMessage traceEntry = LOGGER.traceEntry();
        OAuth2RestTemplate oAuth2RestTemplate = new OAuth2RestTemplate(resourceOwnerPasswordResourceDetails(), oauth2ClientContext);
        oAuth2RestTemplate.setAccessTokenProvider(new ResourceOwnerPasswordAccessTokenProvider());
        return LOGGER.traceExit(traceEntry, oAuth2RestTemplate);
    }

    @Bean
    public OAuth2RestOperations clientRestTemplate() {
        EntryMessage traceEntry = LOGGER.traceEntry();
        OAuth2RestTemplate oAuth2RestTemplate = new OAuth2RestTemplate(clientCredentialsResourceDetails(), oauth2ClientContext);
        oAuth2RestTemplate.setAccessTokenProvider(new ClientCredentialsAccessTokenProvider());
        return LOGGER.traceExit(traceEntry, oAuth2RestTemplate);
    }

    @Bean
    @ConfigurationProperties(prefix = "security.oauth2.client")
    public ClientCredentialsResourceDetails clientCredentialsResourceDetails() {
        return LOGGER.traceExit(new ClientCredentialsResourceDetails());
    }

    @Bean
    @ConfigurationProperties(prefix = "security.oauth2.password")
    protected ResourceOwnerPasswordResourceDetails resourceOwnerPasswordResourceDetails() {
        EntryMessage traceEntry = LOGGER.traceEntry();
        ResourceOwnerPasswordResourceDetails result = new ResourceOwnerPasswordResourceDetails();
        result.setClientId(environment.getRequiredProperty("security.oauth2.password.clientId"));
        result.setClientSecret(environment.getRequiredProperty("security.oauth2.password.clientSecret"));
        return LOGGER.traceExit(traceEntry, result);
    }

    @Bean
    public RestTemplate restTemplate() {
        EntryMessage traceEntry = LOGGER.traceEntry();
        RestTemplate result = new RestTemplate();
        return LOGGER.traceExit(traceEntry, result);
    }
}
