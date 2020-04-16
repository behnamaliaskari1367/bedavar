package com.behdavar.backservices.auth.service;

import com.behdavar.backservices.common.model.BaseEntity;
import com.behdavar.backservices.auth.except.AuthException;
import com.behdavar.backservices.auth.mapper.AuthBaseMapper;
import com.behdavar.backservices.common.model.BaseModel;
import com.behdavar.backservices.common.model.backservice.TableResponseModel;
import org.springframework.data.domain.Page;
import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.Optional;
import java.util.Set;

/**
 * @author Abbas Alishirvani
 */
public interface AuthBaseService<ENTITY extends BaseEntity, MODEL extends BaseModel, ID> {

    <S extends MODEL> MODEL save(MODEL model) throws AuthException;

    <S extends MODEL> Set<MODEL> saveAll(Iterable<MODEL> models) throws AuthException;

    Optional<MODEL> findById(ID id) throws AuthException;

    boolean existsById(ID id) throws AuthException;

    Set<MODEL> findAll() throws AuthException;

    Set<MODEL> findAllById(Set<ID> ids) throws AuthException;

    long count();

    void deleteById(ID id) throws AuthException;

    AuthBaseMapper<ENTITY, MODEL> getMapper();

    CrudRepository<ENTITY, ID> getCrudRepository();

    List<MODEL> convertEntitiesToModels(List<ENTITY> entities);

    MODEL convertEntityToModel(ENTITY entity);

    ENTITY convertModelToEntity(MODEL model);

    Page<MODEL> convertEntitiesToModels(Page<ENTITY> page);

    TableResponseModel<MODEL> convertToTableResponseModel(Page<MODEL> page);
}
