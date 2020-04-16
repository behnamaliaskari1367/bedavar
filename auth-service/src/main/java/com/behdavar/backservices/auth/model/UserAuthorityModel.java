package com.behdavar.backservices.auth.model;

import com.behdavar.backservices.auth.model.supper.AuthMappedSuperModel;
import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;

/**
 * @author Abbas Alishirvani
 */
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, scope = UserAuthorityModel.class, property = "id")
public class UserAuthorityModel extends AuthMappedSuperModel<Long> {

    private UserModel user;
    private AuthorityModel authority;

    public UserModel getUser() {
        return user;
    }

    public void setUser(UserModel user) {
        this.user = user;
    }

    public AuthorityModel getAuthority() {
        return authority;
    }

    public void setAuthority(AuthorityModel authority) {
        this.authority = authority;
    }
}

