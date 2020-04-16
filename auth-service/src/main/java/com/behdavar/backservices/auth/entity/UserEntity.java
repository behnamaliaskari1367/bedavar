package com.behdavar.backservices.auth.entity;

import com.behdavar.backservices.auth.entity.constant.UserEntityConstant;
import org.hibernate.annotations.Type;

import javax.persistence.*;
import java.util.Set;

/**
 * @author Abbas Alishirvani
 */
@Entity
@Table(name = UserEntityConstant.TABLE_NAME, schema = UserEntityConstant.SCHEMA,
        indexes = {@Index(name = UserEntityConstant.INDEX_USER_NAME, columnList = UserEntityConstant.USER_NAME)},
        uniqueConstraints = {@UniqueConstraint(name = UserEntityConstant.UC_USER_NAME, columnNames = {UserEntityConstant.USER_NAME})})
public class UserEntity extends AuthMappedSuperEntity<Long> {

    private String username;
    private String password;
    private String email;
    private String firstName;
    private String lastName;
    private String branch;
    private Boolean accountExpired;
    private Boolean accountLocked;
    private Boolean credentialsExpired;
    private Boolean enabled;
    private Set<UserAuthorityEntity> userAuthorities;

    @Id
    @Override
    @Column(name = UserEntityConstant.PRIMARY_KEY)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Long getId() {
        return super.getId();
    }

    @Column(name = UserEntityConstant.USER_NAME, nullable = false)
    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    @Column(name = "PASSWORD", nullable = false)
    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Column(name = "EMAIL")
    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @Type(type = UserEntityConstant.YES_NO_TYPE)
    @Column(name = "IS_ACCOUNT_EXPIRED", length = 1)
    public Boolean getAccountExpired() {
        return accountExpired;
    }

    public void setAccountExpired(Boolean accountExpired) {
        this.accountExpired = accountExpired;
    }

    @Type(type = UserEntityConstant.YES_NO_TYPE)
    @Column(name = "IS_ACCOUNT_LOCKED", length = 1, nullable = false)
    public Boolean getAccountLocked() {
        return accountLocked;
    }

    public void setAccountLocked(Boolean accountLocked) {
        this.accountLocked = accountLocked;
    }

    @Type(type = UserEntityConstant.YES_NO_TYPE)
    @Column(name = "IS_CREDENTIAL_EXPIRED", length = 1, nullable = false)
    public Boolean getCredentialsExpired() {
        return credentialsExpired;
    }

    public void setCredentialsExpired(Boolean credentialsExpired) {
        this.credentialsExpired = credentialsExpired;
    }

    @Type(type = UserEntityConstant.YES_NO_TYPE)
    @Column(name = "IS_ENABLED", length = 1, nullable = false)
    public Boolean getEnabled() {
        return enabled;
    }

    public void setEnabled(Boolean enabled) {
        this.enabled = enabled;
    }

    @OneToMany(mappedBy = "user")
    public Set<UserAuthorityEntity> getUserAuthorities() {
        return userAuthorities;
    }

    public void setUserAuthorities(Set<UserAuthorityEntity> userAuthorities) {
        this.userAuthorities = userAuthorities;
    }

    @Column(name = "FIRST_NAME", length = 50)
    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    @Column(name = "LAST_NAME", length = 100)
    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    @Column(name = "BRANCH", length = 50)
    public String getBranch() {
        return branch;
    }

    public void setBranch(String branch) {
        this.branch = branch;
    }
}
