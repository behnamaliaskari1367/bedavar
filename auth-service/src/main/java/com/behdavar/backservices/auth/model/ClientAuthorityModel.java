package com.behdavar.backservices.auth.model;

import com.behdavar.backservices.auth.model.supper.AuthMappedSuperModel;
import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;

/**
 * @author Abbas Alishirvani
 */
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, scope = ClientAuthorityModel.class, property = "id")
public class ClientAuthorityModel extends AuthMappedSuperModel<Long> {

    private ClientModel clientDetails;
    private AuthorityModel authority;

    public ClientModel getClientDetails() {
        return clientDetails;
    }

    public void setClientDetails(ClientModel clientDetails) {
        this.clientDetails = clientDetails;
    }

    public AuthorityModel getAuthority() {
        return authority;
    }

    public void setAuthority(AuthorityModel authority) {
        this.authority = authority;
    }
}

