package com.behdavar.backservices.auth.model;

import com.behdavar.backservices.auth.model.supper.AuthMappedSuperModel;
import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;

import java.util.Set;

/**
 * @author Abbas Alishirvani
 */
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, scope = UserModel.class, property = "id")
public class UserModel extends AuthMappedSuperModel<Long> {

    private String username;
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private String password;
    private String email;
    private String firstName;
    private String lastName;
    private String branch;
    private Boolean accountExpired;
    private Boolean accountLocked;
    private Boolean credentialsExpired;
    private Boolean enabled;
    private Set<UserAuthorityModel> userAuthorities;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Boolean getAccountExpired() {
        return accountExpired;
    }

    public void setAccountExpired(Boolean accountExpired) {
        this.accountExpired = accountExpired;
    }

    public Boolean getAccountLocked() {
        return accountLocked;
    }

    public void setAccountLocked(Boolean accountLocked) {
        this.accountLocked = accountLocked;
    }

    public Boolean getCredentialsExpired() {
        return credentialsExpired;
    }

    public void setCredentialsExpired(Boolean credentialsExpired) {
        this.credentialsExpired = credentialsExpired;
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

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getBranch() {
        return branch;
    }

    public void setBranch(String branch) {
        this.branch = branch;
    }
}
