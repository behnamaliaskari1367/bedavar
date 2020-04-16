package com.behdavar.backservices.auth.repository;

import com.behdavar.backservices.auth.entity.ClientAuthorityEntity;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;

/**
 * @author Abbas Alishirvani
 */
public interface ClientAuthorityCrudRepository extends CrudRepository<ClientAuthorityEntity, Long> {

    @Query("SELECT e " +
            "FROM ClientAuthorityEntity e " +
            "LEFT JOIN FETCH e.authority " +
            "WHERE e.clientDetails.id = :id")
    List<ClientAuthorityEntity> findByClientId(@Param("id") long id);
}
