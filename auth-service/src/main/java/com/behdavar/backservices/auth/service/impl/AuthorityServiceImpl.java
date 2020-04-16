package com.behdavar.backservices.auth.service.impl;

import com.behdavar.backservices.auth.mapper.AuthorityMapper;
import com.behdavar.backservices.auth.repository.AuthorityCrudRepository;
import com.behdavar.backservices.auth.service.AuthorityService;
import com.behdavar.backservices.auth.entity.AuthorityEntity;
import com.behdavar.backservices.auth.enums.AuthorityTypeEnum;
import com.behdavar.backservices.auth.model.AuthorityModel;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.message.EntryMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.Objects;

@Service
public class AuthorityServiceImpl extends AuthBaseServiceImpl<AuthorityEntity, AuthorityModel, Long> implements AuthorityService {

    private static final Logger LOGGER = LogManager.getLogger(AuthorityServiceImpl.class);

    private AuthorityMapper mapper;
    private AuthorityCrudRepository repository;

    @Autowired
    public void prepare(AuthorityMapper mapper,
                        AuthorityCrudRepository repository) {
        LOGGER.trace("prepare Spring's dependency injection");
        this.mapper = mapper;
        this.repository = repository;
    }

    @Override
    public AuthorityMapper getMapper() {
        return LOGGER.traceExit(mapper);
    }

    @Override
    public CrudRepository<AuthorityEntity, Long> getCrudRepository() {
        return LOGGER.traceExit(repository);
    }

    @Override
    public List<AuthorityModel> findByTypeAndEnabled(boolean enabled, AuthorityTypeEnum... types) {
        EntryMessage traceEntry = LOGGER.traceEntry("types: {}, enabled: {}", types, enabled);
        if (Objects.isNull(types) || types.length == 0) {
            return LOGGER.traceExit(traceEntry, Collections.emptyList());
        }

        List<AuthorityEntity> entities = repository.findByEnabledAndTypeIsIn(enabled, types);
        if (Objects.isNull(entities) || entities.isEmpty()) {
            return LOGGER.traceExit(traceEntry, Collections.emptyList());
        }
        List<AuthorityModel> result = convertEntitiesToModels(entities);
        return LOGGER.traceExit(traceEntry, result);
    }
}
