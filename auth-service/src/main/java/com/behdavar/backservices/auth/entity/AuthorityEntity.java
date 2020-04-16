package com.behdavar.backservices.auth.entity;

import com.behdavar.backservices.auth.entity.constant.UserEntityConstant;
import com.behdavar.backservices.auth.entity.constant.AuthorityEntityConstant;
import com.behdavar.backservices.auth.entity.coverter.AuthorityTypeConverter;
import com.behdavar.backservices.auth.enums.AuthorityTypeEnum;
import org.hibernate.annotations.Type;

import javax.persistence.*;
import java.util.Set;

/**
 * @author Abbas Alishirvani
 */
@Entity
@Table(name = AuthorityEntityConstant.TABLE_NAME, schema = AuthorityEntityConstant.SCHEMA,
        indexes = {@Index(name = AuthorityEntityConstant.INDEX_NAME, columnList = AuthorityEntityConstant.COLUMN_NAME)},
        uniqueConstraints = {@UniqueConstraint(name = AuthorityEntityConstant.UC_NAME, columnNames = {AuthorityEntityConstant.COLUMN_NAME})})
public class AuthorityEntity extends AuthMappedSuperEntity<Long> {

    private String name;
    private String description;
    private Boolean enabled;
    private AuthorityTypeEnum type = AuthorityTypeEnum.USER_ONLY;
    private Set<UserAuthorityEntity> userAuthorities;
    private Set<ClientAuthorityEntity> clientAuthorities;

    @Id
    @Override
    @Column(name = AuthorityEntityConstant.PRIMARY_KEY)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Long getId() {
        return super.getId();
    }

    @Column(name = AuthorityEntityConstant.COLUMN_NAME, nullable = false)
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Type(type = UserEntityConstant.YES_NO_TYPE)
    @Column(name = "IS_ENABLED", length = 1, nullable = false)
    public Boolean getEnabled() {
        return enabled;
    }

    public void setEnabled(Boolean enabled) {
        this.enabled = enabled;
    }

    @Column(name = "TYPE", nullable = false)
    @Convert(converter = AuthorityTypeConverter.class)
    public AuthorityTypeEnum getType() {
        return type;
    }

    public void setType(AuthorityTypeEnum type) {
        this.type = type;
    }

    @Column(name = "DESCRIPTION")
    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @OneToMany(mappedBy = "authority")
    public Set<UserAuthorityEntity> getUserAuthorities() {
        return userAuthorities;
    }

    public void setUserAuthorities(Set<UserAuthorityEntity> userAuthorities) {
        this.userAuthorities = userAuthorities;
    }

    @OneToMany(mappedBy = "authority")
    public Set<ClientAuthorityEntity> getClientAuthorities() {
        return clientAuthorities;
    }

    public void setClientAuthorities(Set<ClientAuthorityEntity> clientAuthorities) {
        this.clientAuthorities = clientAuthorities;
    }
}
