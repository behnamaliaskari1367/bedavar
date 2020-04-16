package com.behdavar.backservices.auth.repository;

import com.behdavar.backservices.auth.entity.AuthorityEntity;
import com.behdavar.backservices.auth.enums.AuthorityTypeEnum;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

/**
 * @author Abbas Alishirvani
 */
public interface AuthorityCrudRepository extends CrudRepository<AuthorityEntity, Long> {

    List<AuthorityEntity> findByEnabledAndTypeIsIn(Boolean enabled, AuthorityTypeEnum... types);
}
