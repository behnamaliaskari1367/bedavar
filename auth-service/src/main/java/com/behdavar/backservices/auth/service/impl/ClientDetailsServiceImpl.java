package com.behdavar.backservices.auth.service.impl;

import com.behdavar.backservices.auth.enums.AuthorizedGrantTypeEnum;
import com.behdavar.backservices.auth.repository.ClientDetailsCrudRepository;
import com.behdavar.backservices.auth.entity.ClientEntity;
import com.behdavar.backservices.auth.mapper.ClientDetailsMapper;
import com.behdavar.backservices.auth.model.AuthorityModel;
import com.behdavar.backservices.auth.model.ClientAuthorityModel;
import com.behdavar.backservices.auth.model.ClientModel;
import com.behdavar.backservices.auth.service.AuthBaseService;
import com.behdavar.backservices.auth.service.ClientAuthorityService;
import com.behdavar.backservices.common.util.validation.NullValidator;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.message.EntryMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.CrudRepository;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.provider.ClientDetails;
import org.springframework.security.oauth2.provider.ClientRegistrationException;
import org.springframework.security.oauth2.provider.client.BaseClientDetails;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class ClientDetailsServiceImpl extends AuthBaseServiceImpl<ClientEntity, ClientModel, Long> implements AuthBaseService<ClientEntity, ClientModel, Long>, org.springframework.security.oauth2.provider.ClientDetailsService {

    private static final Logger LOGGER = LogManager.getLogger(ClientDetailsServiceImpl.class);
    private ClientDetailsCrudRepository repository;
    private ClientDetailsMapper mapper;
    private ClientAuthorityService clientAuthorityService;

    @Autowired
    public void prepare(ClientDetailsMapper mapper,
                        ClientAuthorityService clientAuthorityService,
                        ClientDetailsCrudRepository repository) {
        LOGGER.trace("prepare Spring's dependency injection");
        this.mapper = mapper;
        this.repository = repository;
        this.clientAuthorityService = clientAuthorityService;
    }

    @Override
    public ClientDetailsMapper getMapper() {
        return LOGGER.traceExit(mapper);
    }

    @Override
    public CrudRepository<ClientEntity, Long> getCrudRepository() {
        return LOGGER.traceExit(repository);
    }

    /**
     * Load a client by the client id. This method must not return null.
     *
     * @param clientId The client id.
     * @return The client details (never null).
     * @throws ClientRegistrationException If the client account is locked, expired, disabled, or invalid for any other reason.
     */
    @Override
    public ClientDetails loadClientByClientId(String clientId) throws ClientRegistrationException {
        EntryMessage traceEntry = LOGGER.traceEntry("clientId: {})", clientId);

        ClientEntity byClientId = repository.findByClientId(clientId);
        NullValidator.isNullThrow(byClientId, () -> new ClientRegistrationException(byClientId + " not found"));
        BaseClientDetails result = convert(byClientId);

        return LOGGER.traceExit(traceEntry, result);
    }

    /**
     * convert clientDetailsEntity to ClientDetails
     *
     * @param clientDetailsEntity to convert
     * @return BaseClientDetails
     */
    private BaseClientDetails convert(ClientEntity clientDetailsEntity) {
        EntryMessage traceEntry = LOGGER.traceEntry("clientDetailsEntity: {})", clientDetailsEntity);

        BaseClientDetails result = new BaseClientDetails();
        result.setClientId(clientDetailsEntity.getClientId());
        result.setClientSecret(clientDetailsEntity.getClientSecret());
        Set<AuthorizedGrantTypeEnum> authorizedGrantTypes = clientDetailsEntity.getAuthorizedGrantTypes();
        result.setAuthorizedGrantTypes(authorizedGrantTypes.stream().map(AuthorizedGrantTypeEnum::getGrantType).collect(Collectors.toSet()));
        result.setRegisteredRedirectUri(clientDetailsEntity.getRegisteredRedirectUris());
        Set<String> authorities = clientAuthorityService.findByClientId(clientDetailsEntity.getId())
                .stream()
                .map(ClientAuthorityModel::getAuthority)
                .map(AuthorityModel::getName)
                .collect(Collectors.toSet());
        result.setAutoApproveScopes(authorities);
        result.setScope(authorities);
        result.setAuthorities(getAuthorities(authorities));

        return LOGGER.traceExit(traceEntry, result);
    }

    private Set<GrantedAuthority> getAuthorities(Set<String> s) {
        EntryMessage traceEntry = LOGGER.traceEntry("authority: {})", s);

        if (Objects.isNull(s) || s.isEmpty()) {
            return Collections.emptySet();
        }

        Set<GrantedAuthority> result = new HashSet<>();

        for (String str : s) {
            result.add(new SimpleGrantedAuthority(str));
        }

        return LOGGER.traceExit(traceEntry, result);
    }
}
