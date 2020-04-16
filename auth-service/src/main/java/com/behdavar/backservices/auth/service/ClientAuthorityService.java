package com.behdavar.backservices.auth.service;

import com.behdavar.backservices.auth.entity.ClientAuthorityEntity;
import com.behdavar.backservices.auth.model.ClientAuthorityModel;

import java.util.List;

/**
 * @author Abbas Alishirvani
 */
public interface ClientAuthorityService extends AuthBaseService<ClientAuthorityEntity, ClientAuthorityModel, Long> {

    List<ClientAuthorityModel> findByClientId(long id);
}

