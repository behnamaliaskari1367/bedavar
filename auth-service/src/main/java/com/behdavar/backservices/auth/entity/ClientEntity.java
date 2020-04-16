package com.behdavar.backservices.auth.entity;

import com.behdavar.backservices.auth.entity.constant.ClientEntityConstant;
import com.behdavar.backservices.auth.entity.coverter.AuthorizedGrantTypeConverter;
import com.behdavar.backservices.auth.entity.coverter.StringToLinesConverter;
import com.behdavar.backservices.auth.enums.AuthorizedGrantTypeEnum;

import javax.persistence.*;
import java.util.Set;

/**
 * @author Abbas Alishirvani
 */
@Entity
@Table(name = ClientEntityConstant.TABLE_NAME, schema = ClientEntityConstant.SCHEMA,
        indexes = {@Index(name = ClientEntityConstant.INDEX_CLIENT_ID, columnList = ClientEntityConstant.CLIENT_ID)},
        uniqueConstraints = {@UniqueConstraint(name = ClientEntityConstant.UC_CLIENT_ID, columnNames = {ClientEntityConstant.CLIENT_ID})})
public class ClientEntity extends AuthMappedSuperEntity<Long> {

    private String clientId;
    private String clientSecret;
    private Set<AuthorizedGrantTypeEnum> authorizedGrantTypes;
    private Set<String> registeredRedirectUris;
    private Set<ClientAuthorityEntity> scope;

    @Id
    @Override
    @Column(name = ClientEntityConstant.PRIMARY_KEY)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Long getId() {
        return super.getId();
    }

    @Column(name = ClientEntityConstant.CLIENT_ID, nullable = false)
    public String getClientId() {
        return clientId;
    }

    public void setClientId(String clientId) {
        this.clientId = clientId;
    }

    @Column(name = "CLIENT_SECRET", nullable = false)
    public String getClientSecret() {
        return clientSecret;
    }

    public void setClientSecret(String clientSecret) {
        this.clientSecret = clientSecret;
    }

    @Column(name = "AUTHORIZE_GRANT_TYPES", nullable = false)
    @Convert(converter = AuthorizedGrantTypeConverter.class)
    public Set<AuthorizedGrantTypeEnum> getAuthorizedGrantTypes() {
        return authorizedGrantTypes;
    }

    public void setAuthorizedGrantTypes(Set<AuthorizedGrantTypeEnum> authorizedGrantTypes) {
        this.authorizedGrantTypes = authorizedGrantTypes;
    }

    @Column(name = "REDIRECT_URIS", length = 3000)
    @Convert(converter = StringToLinesConverter.class)
    public Set<String> getRegisteredRedirectUris() {
        return registeredRedirectUris;
    }

    public void setRegisteredRedirectUris(Set<String> registeredRedirectUris) {
        this.registeredRedirectUris = registeredRedirectUris;
    }

    @OneToMany(mappedBy = "clientDetails")
    public Set<ClientAuthorityEntity> getScope() {
        return scope;
    }

    public void setScope(Set<ClientAuthorityEntity> scope) {
        this.scope = scope;
    }
}

