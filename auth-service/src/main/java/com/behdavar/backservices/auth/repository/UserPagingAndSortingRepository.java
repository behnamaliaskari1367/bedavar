package com.behdavar.backservices.auth.repository;

import com.behdavar.backservices.auth.entity.UserEntity;
import com.behdavar.backservices.auth.model.UserModel;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;

/**
 * @author Abbas Alishirvani
 */
public interface UserPagingAndSortingRepository extends PagingAndSortingRepository<UserEntity, Long> {

    @Query(value = "SELECT e " +
            "FROM UserEntity e " +
            "WHERE " +
            "(:#{#model.username} is null or e.username LIKE :#{#model.username}) " +
            "AND (:#{#model.firstName} is null or e.firstName LIKE :#{#model.firstName}) " +
            "AND (:#{#model.lastName} is null or e.lastName LIKE :#{#model.lastName}) " +
            "AND (:#{#model.branch} is null or e.branch = :#{#model.branch}) " +
            "AND (:#{#model.enabled} is null or e.enabled = :#{#model.enabled})",
            countQuery = "SELECT count(e) " +
                    "FROM UserEntity e " +
                    "WHERE " +
                    "(:#{#model.username} is null or e.username LIKE :#{#model.username}) " +
                    "AND (:#{#model.firstName} is null or e.firstName LIKE :#{#model.firstName}) " +
                    "AND (:#{#model.lastName} is null or e.lastName LIKE :#{#model.lastName}) " +
                    "AND (:#{#model.branch} is null or e.branch = :#{#model.branch}) " +
                    "AND (:#{#model.enabled} is null or e.enabled = :#{#model.enabled})")
    Page<UserEntity> findByParams(@Param("model") UserModel model, Pageable pageable);
}
