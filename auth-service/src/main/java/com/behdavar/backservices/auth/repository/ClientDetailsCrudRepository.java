package com.behdavar.backservices.auth.repository;

import com.behdavar.backservices.auth.entity.ClientEntity;
import org.springframework.data.repository.CrudRepository;

/**
 * @author Abbas Alishirvani
 */
public interface ClientDetailsCrudRepository extends CrudRepository<ClientEntity, Long> {
    ClientEntity findByClientId(String clientId);
}
