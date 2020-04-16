package com.behdavar.backservices.auth.entity;

import com.behdavar.backservices.auth.entity.constant.AuthorityEntityConstant;
import com.behdavar.backservices.auth.entity.constant.ClientDetailsAuthorityEntityConstant;
import com.behdavar.backservices.auth.entity.constant.ClientEntityConstant;

import javax.persistence.*;

/**
 * @author Abbas Alishirvani
 */
@Entity
@Table(name = ClientDetailsAuthorityEntityConstant.TABLE_NAME, schema = ClientDetailsAuthorityEntityConstant.SCHEMA)
public class ClientAuthorityEntity extends AuthMappedSuperEntity<Long>{

    private ClientEntity clientDetails;
    private AuthorityEntity authority;

    @Id
    @Override
    @Column(name = ClientDetailsAuthorityEntityConstant.PRIMARY_KEY)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Long getId() {
        return super.getId();
    }

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = ClientEntityConstant.FOREIGN_KEY, nullable = false)
    public ClientEntity getClientDetails() {
        return clientDetails;
    }

    public void setClientDetails(ClientEntity clientDetails) {
        this.clientDetails = clientDetails;
    }

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = AuthorityEntityConstant.FOREIGN_KEY, nullable = false)
    public AuthorityEntity getAuthority() {
        return authority;
    }

    public void setAuthority(AuthorityEntity authority) {
        this.authority = authority;
    }
}

