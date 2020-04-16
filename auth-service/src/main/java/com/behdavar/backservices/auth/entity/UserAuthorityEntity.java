package com.behdavar.backservices.auth.entity;

import com.behdavar.backservices.auth.entity.constant.AuthorityEntityConstant;
import com.behdavar.backservices.auth.entity.constant.UserAuthorityEntityConstant;
import com.behdavar.backservices.auth.entity.constant.UserEntityConstant;

import javax.persistence.*;

/**
 * @author Abbas Alishirvani
 */
@Entity
@Table(name = UserAuthorityEntityConstant.TABLE_NAME, schema = UserAuthorityEntityConstant.SCHEMA)
public class UserAuthorityEntity extends AuthMappedSuperEntity<Long> {

    private UserEntity user;
    private AuthorityEntity authority;

    @Id
    @Override
    @Column(name = UserAuthorityEntityConstant.PRIMARY_KEY)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Long getId() {
        return super.getId();
    }

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = UserEntityConstant.FOREIGN_KEY, nullable = false)
    public UserEntity getUser() {
        return user;
    }

    public void setUser(UserEntity user) {
        this.user = user;
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

