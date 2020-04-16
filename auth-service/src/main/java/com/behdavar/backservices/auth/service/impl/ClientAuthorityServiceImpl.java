package com.behdavar.backservices.auth.service.impl;

import com.behdavar.backservices.auth.entity.ClientAuthorityEntity;
import com.behdavar.backservices.auth.mapper.AuthBaseMapper;
import com.behdavar.backservices.auth.mapper.ClientAuthorityMapper;
import com.behdavar.backservices.auth.model.ClientAuthorityModel;
import com.behdavar.backservices.auth.repository.ClientAuthorityCrudRepository;
import com.behdavar.backservices.auth.service.ClientAuthorityService;
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
public class ClientAuthorityServiceImpl
        extends AuthBaseServiceImpl<ClientAuthorityEntity, ClientAuthorityModel, Long>
        implements ClientAuthorityService {

    private static final Logger LOGGER = LogManager.getLogger(ClientServiceImpl.class);
    private ClientAuthorityCrudRepository repository;
    private ClientAuthorityMapper mapper;

    @Autowired
    public void prepare(ClientAuthorityMapper mapper,
                        ClientAuthorityCrudRepository repository) {
        LOGGER.trace("prepare Spring's dependency injection");
        this.mapper = mapper;
        this.repository = repository;
    }

    @Override
    public AuthBaseMapper<ClientAuthorityEntity, ClientAuthorityModel> getMapper() {
        return LOGGER.traceExit(mapper);
    }

    @Override
    public CrudRepository<ClientAuthorityEntity, Long> getCrudRepository() {
        return LOGGER.traceExit(repository);
    }

    @Override
    public List<ClientAuthorityModel> findByClientId(long id) {
        EntryMessage traceEntry = LOGGER.traceEntry("findByClientId: {})", id);
        List<ClientAuthorityEntity> entities = repository.findByClientId(id);
        if (Objects.isNull(entities)) entities = Collections.emptyList();
        return LOGGER.traceExit(traceEntry, convertEntitiesToModels(entities));
    }
}
