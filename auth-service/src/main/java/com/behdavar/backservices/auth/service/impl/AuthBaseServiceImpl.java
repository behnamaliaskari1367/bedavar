package com.behdavar.backservices.auth.service.impl;

import com.behdavar.backservices.auth.service.AuthBaseService;
import com.behdavar.backservices.common.model.BaseEntity;
import com.behdavar.backservices.common.model.backservice.TableResponseModel;
import com.behdavar.backservices.auth.except.AuthException;
import com.behdavar.backservices.auth.except.AuthExceptionReason;
import com.behdavar.backservices.auth.mapper.CycleAvoidingMappingContext;
import com.behdavar.backservices.common.model.BaseModel;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.message.EntryMessage;
import org.springframework.data.domain.Page;

import java.util.*;

/**
 * @author Abbas Alishirvani
 */
@SuppressWarnings("unchecked")
public abstract class AuthBaseServiceImpl<ENTITY extends BaseEntity, MODEL extends BaseModel, ID> implements AuthBaseService<ENTITY, MODEL, ID> {

    private static final Logger LOG = LogManager.getLogger(AuthBaseServiceImpl.class);

    @Override
    public Optional<MODEL> findById(ID id) throws AuthException {
        EntryMessage traceEntry = LOG.traceEntry("Parameters - id: {}", id);

        Optional<ENTITY> entity = getCrudRepository().findById(id);
        LOG.trace("entity: {} ", entity);

        if (!entity.isPresent()) {
            LOG.trace("entity not found");
            return Optional.empty();
        }

        LOG.trace("convert entityToModel started");
        MODEL baseModel = (MODEL) getMapper().entityToModel(entity.get(), new CycleAvoidingMappingContext());

        Optional<MODEL> result = Optional.ofNullable(baseModel);
        return LOG.traceExit(traceEntry, result);
    }

    @Override
    public boolean existsById(ID id) throws AuthException {
        EntryMessage traceEntry = LOG.traceEntry("Parameters - id: {}", id);
        boolean result = getCrudRepository().existsById(id);
        return LOG.traceExit(traceEntry, result);
    }

    @Override
    public Set<MODEL> findAll() throws AuthException {
        EntryMessage traceEntry = LOG.traceEntry();
        Set<MODEL> result = iterableToSet(getCrudRepository().findAll());
        return LOG.traceExit(traceEntry, result);
    }

    @Override
    public Set<MODEL> findAllById(Set<ID> ids) throws AuthException {
        EntryMessage traceEntry = LOG.traceEntry("Parameters - ids: {}", ids);

        Set<ID> idSet = Optional.of(ids).orElseThrow(() -> new AuthException(AuthExceptionReason.ERROR_IN_INPUT_PARAMS));

        Set<MODEL> result = iterableToSet(getCrudRepository().findAllById(idSet));
        return LOG.traceExit(traceEntry, result);
    }

    @Override
    public long count() {
        EntryMessage traceEntry = LOG.traceEntry();
        long result = getCrudRepository().count();
        return LOG.traceExit(traceEntry, result);
    }

    @Override
    public void deleteById(ID id) throws AuthException {
        EntryMessage traceEntry = LOG.traceEntry("Parameters - id: {}", id);

        ID nId = Optional.of(id).orElseThrow(() -> new AuthException(AuthExceptionReason.ERROR_IN_INPUT_PARAMS));

        try {
            LOG.trace("" + "start of delete for: " + nId);
            getCrudRepository().deleteById(nId);
            LOG.traceExit(traceEntry);
        } catch (Exception e) {
            LOG.error("error in deleteById for: " + nId + " - " + e.getMessage());
            throw new AuthException(AuthExceptionReason.ERROR_IN_DELETE, e);
        }
    }

    @Override
    public <S extends MODEL> MODEL save(MODEL model) throws AuthException {
        EntryMessage traceEntry = LOG.traceEntry("Parameters - model: {}", model);

        MODEL oModel = Optional.of(model).orElseThrow(() -> new AuthException(AuthExceptionReason.ERROR_IN_INPUT_PARAMS));

        try {
            LOG.trace("convert modelToEntity started");
            ENTITY entity = (ENTITY) getMapper().modelToEntity(oModel, new CycleAvoidingMappingContext());
            ENTITY save = getCrudRepository().save(entity);

            LOG.trace("convert entityToModel started");
            MODEL result = (MODEL) getMapper().entityToModel(save, new CycleAvoidingMappingContext());

            return LOG.traceExit(traceEntry, result);
        } catch (Exception e) {
            LOG.error("error in save - " + e.getMessage());
            throw new AuthException(AuthExceptionReason.ERROR_IN_DELETE, e);
        }
    }

    @Override
    public <S extends MODEL> Set<MODEL> saveAll(Iterable<MODEL> models) throws AuthException {
        EntryMessage traceEntry = LOG.traceEntry("Parameters - models: {}", models);

        Iterable<MODEL> oModels = Optional.of(models).orElseThrow(() -> new AuthException(AuthExceptionReason.ERROR_IN_INPUT_PARAMS));

        try {
            Set<ENTITY> entities = new HashSet<>();
            for (MODEL model : oModels) {
                MODEL oModel = Optional.of(model).orElseThrow(() -> new AuthException(AuthExceptionReason.ERROR_IN_INPUT_PARAMS));

                LOG.trace("convert entityToModel started");
                ENTITY e = (ENTITY) getMapper().modelToEntity(oModel, new CycleAvoidingMappingContext());
                entities.add(e);
            }
            Iterable<ENTITY> entitiesResponse = getCrudRepository().saveAll(entities);

            Set<MODEL> result = new HashSet<>();
            for (ENTITY entity : entitiesResponse) {
                LOG.trace("convert entityToModel started");
                MODEL e = (MODEL) getMapper().entityToModel(entity, new CycleAvoidingMappingContext());
                result.add(e);
            }

            return LOG.traceExit(traceEntry, result);
        } catch (Exception e) {
            LOG.error("error in saveAll - " + e.getMessage());
            throw new AuthException(AuthExceptionReason.ERROR_IN_UPDATE, e);
        }

    }

    private Set<MODEL> iterableToSet(Iterable<ENTITY> entities) throws AuthException {
        EntryMessage traceEntry = LOG.traceEntry("Parameters - entities: {}", entities);

        MODEL model;
        Set<MODEL> result = new HashSet<>();
        try {
            for (ENTITY entity : entities) {
                LOG.trace("convert entityToModel started");
                model = (MODEL) getMapper().entityToModel(entity, new CycleAvoidingMappingContext());
                result.add(model);
            }
        } catch (Exception e) {
            LOG.error("error in iterableToSet - " + e.getMessage());
            throw new AuthException(AuthExceptionReason.ERROR_IN_DELETE, e);
        }

        return LOG.traceExit(traceEntry, result);
    }

    @Override
    public List<MODEL> convertEntitiesToModels(List<ENTITY> entities) {
        EntryMessage traceEntry = LOG.traceEntry("Parameters - entities: {}", entities);

        if (Objects.isNull(entities) || entities.isEmpty()) return Collections.emptyList();

        LOG.trace("convert entitiesToModels started");
        List<MODEL> result = getMapper().entitiesToModels(entities, new CycleAvoidingMappingContext());

        return LOG.traceExit(traceEntry, result);
    }

    @Override
    public MODEL convertEntityToModel(ENTITY entity) {
        EntryMessage traceEntry = LOG.traceEntry("Parameters - entity: {}", entity);

        if (Objects.isNull(entity)) return null;
        MODEL result = (MODEL) getMapper().entityToModel(entity, new CycleAvoidingMappingContext());

        return LOG.traceExit(traceEntry, result);
    }

    @Override
    public ENTITY convertModelToEntity(MODEL model) {
        EntryMessage traceEntry = LOG.traceEntry("Parameters - model: {}", model);

        if (Objects.isNull(model)) return null;
        ENTITY result = (ENTITY) getMapper().modelToEntity(model, new CycleAvoidingMappingContext());

        return LOG.traceExit(traceEntry, result);
    }

    @Override
    public Page<MODEL> convertEntitiesToModels(Page<ENTITY> page) {
        EntryMessage traceEntry = LOG.traceEntry("page: {}", page);

        Page<MODEL> result = page.map(this::convertEntityToModel);

        return LOG.traceExit(traceEntry, result);
    }

    @Override
    public TableResponseModel<MODEL> convertToTableResponseModel(Page<MODEL> page) {
        EntryMessage traceEntry = LOG.traceEntry("page: {}", page);

        TableResponseModel<MODEL> result = new TableResponseModel<>(
                page.getNumber(),
                page.getTotalPages(),
                page.getTotalElements(),
                page.getContent());

        return LOG.traceExit(traceEntry, result);
    }
}
