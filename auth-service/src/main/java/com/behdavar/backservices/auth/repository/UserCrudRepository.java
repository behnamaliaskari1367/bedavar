package com.behdavar.backservices.auth.repository;

import com.behdavar.backservices.auth.entity.UserEntity;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

/**
 * @author Abbas Alishirvani
 */
public interface UserCrudRepository extends CrudRepository<UserEntity, Long> {
    UserEntity findByUsername(String userName);

    @Query("SELECT e " +
            "FROM UserEntity e " +
            "LEFT JOIN FETCH e.userAuthorities ua " +
            "LEFT JOIN FETCH ua.authority a " +
            "WHERE e.id = :userId " +
            "AND (a.enabled IS NULL OR a.enabled = :enabled)")
    UserEntity findByUserIdAndAuthorityEnabled(@Param("userId") Long userId, @Param("enabled") boolean enabled);
}
