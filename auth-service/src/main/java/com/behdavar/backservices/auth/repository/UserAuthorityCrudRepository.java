package com.behdavar.backservices.auth.repository;

import com.behdavar.backservices.auth.entity.UserAuthorityEntity;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;

/**
 * @author Abbas Alishirvani
 */
public interface UserAuthorityCrudRepository extends CrudRepository<UserAuthorityEntity, Long> {

    @Query("SELECT e " +
            "FROM UserAuthorityEntity e " +
            "WHERE e.user.id = :userId")
    List<UserAuthorityEntity> findByUserId(@Param("userId") long userId);
}
