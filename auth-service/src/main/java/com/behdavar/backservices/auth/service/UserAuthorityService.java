package com.behdavar.backservices.auth.service;

import com.behdavar.backservices.auth.model.AuthorityModel;
import com.behdavar.backservices.auth.entity.UserAuthorityEntity;
import com.behdavar.backservices.auth.except.AuthException;
import com.behdavar.backservices.auth.model.UserAuthorityModel;

import java.util.List;

/**
 * @author Abbas Alishirvani
 */
public interface UserAuthorityService extends AuthBaseService<UserAuthorityEntity, UserAuthorityModel, Long> {

    void setAuthorities(long userId, List<AuthorityModel> authorityModels) throws AuthException;
}
