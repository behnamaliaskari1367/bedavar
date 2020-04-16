package com.behdavar.backservices.auth.service;

import com.behdavar.backservices.auth.entity.AuthorityEntity;
import com.behdavar.backservices.auth.enums.AuthorityTypeEnum;
import com.behdavar.backservices.auth.model.AuthorityModel;

import java.util.List;

/**
 * @author Abbas Alishirvani
 */
public interface AuthorityService extends AuthBaseService<AuthorityEntity, AuthorityModel, Long> {

    List<AuthorityModel> findByTypeAndEnabled(boolean enabled, AuthorityTypeEnum... types);
}
