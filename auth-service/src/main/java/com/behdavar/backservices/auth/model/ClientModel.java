package com.behdavar.backservices.auth.model;

import com.behdavar.backservices.auth.enums.AuthorizedGrantTypeEnum;
import com.behdavar.backservices.auth.model.supper.AuthMappedSuperModel;
import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;

import java.util.Set;

/**
 * @author Abbas Alishirvani
 */
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, scope = ClientModel.class, property = "id")
public class ClientModel extends AuthMappedSuperModel<Long> {

    private String clientId;
    private String clientSecret;
    private Set<AuthorizedGrantTypeEnum> authorizedGrantTypes;
    private Set<String> registeredRedirectUris;
    private Set<ClientAuthorityModel> scope;

    public String getClientId() {
        return clientId;
    }

    public void setClientId(String clientId) {
        this.clientId = clientId;
    }

    public String getClientSecret() {
        return clientSecret;
    }

    public void setClientSecret(String clientSecret) {
        this.clientSecret = clientSecret;
    }

    public Set<AuthorizedGrantTypeEnum> getAuthorizedGrantTypes() {
        return authorizedGrantTypes;
    }

    public void setAuthorizedGrantTypes(Set<AuthorizedGrantTypeEnum> authorizedGrantTypes) {
        this.authorizedGrantTypes = authorizedGrantTypes;
    }

    public Set<String> getRegisteredRedirectUris() {
        return registeredRedirectUris;
    }

    public void setRegisteredRedirectUris(Set<String> registeredRedirectUris) {
        this.registeredRedirectUris = registeredRedirectUris;
    }

    public Set<ClientAuthorityModel> getScope() {
        return scope;
    }

    public void setScope(Set<ClientAuthorityModel> scope) {
        this.scope = scope;
    }
}

