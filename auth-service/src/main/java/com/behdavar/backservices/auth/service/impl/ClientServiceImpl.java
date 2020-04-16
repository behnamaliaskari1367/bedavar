package com.behdavar.backservices.auth.service.impl;

import com.behdavar.backservices.auth.repository.ClientDetailsCrudRepository;
import com.behdavar.backservices.auth.entity.ClientEntity;
import com.behdavar.backservices.auth.mapper.ClientDetailsMapper;
import com.behdavar.backservices.auth.model.ClientModel;
import com.behdavar.backservices.auth.service.ClientService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Service;

@Service
public class ClientServiceImpl extends AuthBaseServiceImpl<ClientEntity, ClientModel, Long> implements ClientService {

    private static final Logger LOGGER = LogManager.getLogger(ClientServiceImpl.class);
    private ClientDetailsCrudRepository repository;
    private ClientDetailsMapper mapper;

    @Autowired
    public void prepare(ClientDetailsMapper mapper,
                        ClientDetailsCrudRepository repository) {
        LOGGER.trace("prepare Spring's dependency injection");
        this.mapper = mapper;
        this.repository = repository;
    }

    @Override
    public ClientDetailsMapper getMapper() {
        return LOGGER.traceExit(mapper);
    }

    @Override
    public CrudRepository<ClientEntity, Long> getCrudRepository() {
        return LOGGER.traceExit(repository);
    }
}
