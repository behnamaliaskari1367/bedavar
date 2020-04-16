package com.behdavar.backservices.cloud.config.config;

import com.behdavar.backservices.cloud.config.except.ConfigServerErrorController;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.message.EntryMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.security.oauth2.resource.ResourceServerProperties;
import org.springframework.boot.autoconfigure.security.oauth2.resource.UserInfoTokenServices;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.core.annotation.Order;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.client.OAuth2ClientContext;
import org.springframework.security.oauth2.client.OAuth2RestOperations;
import org.springframework.security.oauth2.client.OAuth2RestTemplate;
import org.springframework.security.oauth2.client.token.grant.client.ClientCredentialsResourceDetails;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.security.oauth2.provider.token.ResourceServerTokenServices;
import org.springframework.security.web.authentication.preauth.AbstractPreAuthenticatedProcessingFilter;

import javax.servlet.Filter;
import javax.servlet.http.HttpServletRequest;
import java.nio.file.AccessDeniedException;
import java.util.*;

@Order(1)
@Configuration
@EnableResourceServer
public class ResourceServerConfig extends ResourceServerConfigurerAdapter {

    private static final Logger LOGGER = LogManager.getLogger(ResourceServerConfig.class);
    private @Qualifier("oauth2ClientContext")
    OAuth2ClientContext oauth2ClientContext;
    private ResourceServerProperties resourceServerProperties;
    private static final String CONFIG_SERVER_SCOPE_PREFIX = "CONFIG_SERVER";

    @Override
    public void configure(HttpSecurity httpSecurity) throws Exception {
        EntryMessage traceEntry = LOGGER.traceEntry("httpSecurity: {}", httpSecurity);
        httpSecurity
                .addFilterAfter(filter(), AbstractPreAuthenticatedProcessingFilter.class)
                .authorizeRequests()
                .antMatchers("/registry-service*/**",
                        "/auth-service*/**",
                        "/encrypt",
                        "/decrypt").denyAll()
                .anyRequest().authenticated()
        ;
        LOGGER.traceExit(traceEntry);
    }

    private Filter filter() {
        return (request, response, chain) -> {
            EntryMessage traceEntry = LOGGER.traceEntry("ServletRequest: {}, ServletResponse: {}, FilterChain: {}", request, response, chain);

            HttpServletRequest httpServletRequest = (HttpServletRequest) request;
            String path = httpServletRequest.getServletPath();

            if (ConfigServerErrorController.PATH.equals(path) || isScopeValid(path)) {
                chain.doFilter(request, response);
            } else {
                throw new AccessDeniedException("Scope is invalid");
            }

            LOGGER.traceExit(traceEntry);
        };
    }

    private boolean isScopeValid(String path) throws AccessDeniedException {
        EntryMessage traceEntry = LOGGER.traceEntry();

        List<String> scopes = getScopes();
        if (scopes.isEmpty()) {
            throw new AccessDeniedException("Scope is invalid");
        }
        boolean result = false;
        String label = path.split("/")[1].split("\\.")[0];
        final String targetScope = CONFIG_SERVER_SCOPE_PREFIX + "-" + label.toUpperCase();
        for (String scope : scopes) {
            if (Objects.equals(scope.toUpperCase(), targetScope)) {
                result = true;
            }
        }

        if (!result) {
            LOGGER.warn("Scope is invalid: {}", targetScope);
        }
        return LOGGER.traceExit(traceEntry, result);
    }

    /**
     * extract scopes from OAuth2Authentication
     *
     * @return list of scope
     */
    @SuppressWarnings("unchecked")
    private List<String> getScopes() {
        //TODO: due to poorly written knowledge, it has to be replaced.
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication instanceof OAuth2Authentication) {
            OAuth2Authentication oAuth2Authentication = (OAuth2Authentication) authentication;
            Authentication userAuthentication1 = oAuth2Authentication.getUserAuthentication();
            if (Objects.nonNull(userAuthentication1) && userAuthentication1 instanceof UsernamePasswordAuthenticationToken) {
                UsernamePasswordAuthenticationToken userAuthentication = (UsernamePasswordAuthenticationToken) userAuthentication1;
                Object details = userAuthentication.getDetails();
                if (Objects.nonNull(details) && details instanceof LinkedHashMap) {
                    Object oauth2Request = ((LinkedHashMap) details).get("oauth2Request");
                    if (Objects.nonNull(oauth2Request) && oauth2Request instanceof LinkedHashMap) {
                        Object scope = ((LinkedHashMap) oauth2Request).get("scope");
                        if (Objects.nonNull(scope) && scope instanceof ArrayList) {
                            return (ArrayList<String>) scope;
                        }
                    }
                }
            }
        }
        return Collections.emptyList();
    }

    @Bean
    @ConfigurationProperties(prefix = "security.oauth2.client")
    public ClientCredentialsResourceDetails clientCredentialsResourceDetails() {
        return LOGGER.traceExit(new ClientCredentialsResourceDetails());
    }

    @Bean
    public OAuth2RestOperations restTemplate() {
        EntryMessage traceEntry = LOGGER.traceEntry("oauth2ClientContext: {}", oauth2ClientContext);
        return LOGGER.traceExit(traceEntry, new OAuth2RestTemplate(clientCredentialsResourceDetails(), oauth2ClientContext));
    }

    @Bean
    @Primary
    public ResourceServerTokenServices resourceServerTokenServices() {
        return new UserInfoTokenServices(resourceServerProperties.getUserInfoUri(), resourceServerProperties.getClientId());
    }

    @Autowired
    public void prepare(ResourceServerProperties resourceServerProperties,
                        @Qualifier("oauth2ClientContext") OAuth2ClientContext oauth2ClientContext) {
        this.oauth2ClientContext = oauth2ClientContext;
        this.resourceServerProperties = resourceServerProperties;
    }
}
