package com.behdavar.backservices.auth.model;

import com.behdavar.backservices.auth.model.supper.AuthMappedSuperModel;
import com.behdavar.backservices.auth.enums.AuthorityTypeEnum;
import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;

import java.util.Set;

/**
 * @author Abbas Alishirvani
 */
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, scope = AuthorityModel.class, property = "id")
public class AuthorityModel extends AuthMappedSuperModel<Long> {

    private String name;
    private Boolean enabled;
    private String description;
    private AuthorityTypeEnum type;
    private Set<UserAuthorityModel> userAuthorities;
    private Set<ClientAuthorityModel> clientAuthorities;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Boolean getEnabled() {
        return enabled;
    }

    public void setEnabled(Boolean enabled) {
        this.enabled = enabled;
    }

    public Set<UserAuthorityModel> getUserAuthorities() {
        return userAuthorities;
    }

    public void setUserAuthorities(Set<UserAuthorityModel> userAuthorities) {
        this.userAuthorities = userAuthorities;
    }

    public Set<ClientAuthorityModel> getClientAuthorities() {
        return clientAuthorities;
    }

    public void setClientAuthorities(Set<ClientAuthorityModel> clientAuthorities) {
        this.clientAuthorities = clientAuthorities;
    }

    public AuthorityTypeEnum getType() {
        return type;
    }

    public void setType(AuthorityTypeEnum type) {
        this.type = type;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
