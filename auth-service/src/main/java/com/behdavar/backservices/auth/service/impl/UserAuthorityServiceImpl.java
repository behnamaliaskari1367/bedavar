package com.behdavar.backservices.auth.service.impl;

import com.behdavar.backservices.auth.entity.AuthorityEntity;
import com.behdavar.backservices.auth.entity.UserEntity;
import com.behdavar.backservices.auth.except.AuthException;
import com.behdavar.backservices.auth.except.AuthExceptionReason;
import com.behdavar.backservices.auth.mapper.AuthBaseMapper;
import com.behdavar.backservices.auth.mapper.UserAuthorityMapper;
import com.behdavar.backservices.auth.model.AuthorityModel;
import com.behdavar.backservices.auth.model.UserAuthorityModel;
import com.behdavar.backservices.auth.repository.AuthorityCrudRepository;
import com.behdavar.backservices.auth.repository.UserCrudRepository;
import com.behdavar.backservices.auth.service.UserAuthorityService;
import com.behdavar.backservices.common.model.BaseModel;
import com.behdavar.backservices.auth.entity.UserAuthorityEntity;
import com.behdavar.backservices.auth.repository.UserAuthorityCrudRepository;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.message.EntryMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.*;
import java.util.stream.Collectors;

/**
 * @author Abbas Alishirvani
 */
@Service
public class UserAuthorityServiceImpl extends AuthBaseServiceImpl<UserAuthorityEntity, UserAuthorityModel, Long> implements UserAuthorityService {

    private static final Logger LOGGER = LogManager.getLogger(ClientServiceImpl.class);
    private UserAuthorityCrudRepository repository;
    private AuthorityCrudRepository authorityRepository;
    private UserCrudRepository userRepository;
    private UserAuthorityMapper mapper;

    @Autowired
    public void prepare(UserAuthorityMapper mapper,
                        UserAuthorityCrudRepository repository,
                        UserCrudRepository userRepository,
                        AuthorityCrudRepository authorityRepository) {
        LOGGER.trace("prepare Spring's dependency injection");
        this.mapper = mapper;
        this.repository = repository;
        this.authorityRepository = authorityRepository;
        this.userRepository = userRepository;
    }

    @Override
    public AuthBaseMapper<UserAuthorityEntity, UserAuthorityModel> getMapper() {
        return LOGGER.traceExit(mapper);
    }

    @Override
    public CrudRepository<UserAuthorityEntity, Long> getCrudRepository() {
        return LOGGER.traceExit(repository);
    }

    @Override
    @Transactional
    public void setAuthorities(long userId, List<AuthorityModel> authorityModels) throws AuthException {
        EntryMessage traceEntry = LOGGER.traceEntry("userId: {}, authorityModels: {}", userId, authorityModels);

        if (Objects.isNull(authorityModels)) {
            authorityModels = Collections.emptyList();
        }

        Optional<UserEntity> userRepositoryById = userRepository.findById(userId);
        if (!userRepositoryById.isPresent()) {
            throw new AuthException(AuthExceptionReason.ERROR_IN_INPUT_PARAMS, "Invalid userId");
        }
        UserEntity userEntity = userRepositoryById.get();

        Set<Long> requestAuthority = authorityModels.stream()
                .map(BaseModel::getId)
                .filter(Objects::nonNull)
                .collect(Collectors.toSet());

        List<UserAuthorityEntity> persist = this.repository.findByUserId(userId);

        Set<Long> persistAuthorities = new HashSet<>(persist.size());
        //delete unused
        for (UserAuthorityEntity e : persist) {
            AuthorityEntity authority = e.getAuthority();
            if (Objects.nonNull(authority)) {
                persistAuthorities.add(authority.getId());
                if (!requestAuthority.contains(authority.getId())) {
                    this.repository.delete(e);
                }
            }
        }

        //add new
        for (Long i : requestAuthority) {
            if (!persistAuthorities.contains(i)) {
                Optional<AuthorityEntity> byId = authorityRepository.findById(i);
                if (byId.isPresent()) {
                    UserAuthorityEntity entity = new UserAuthorityEntity();
                    entity.setUser(userEntity);
                    entity.setAuthority(byId.get());
                    this.repository.save(entity);
                }
            }
        }

        LOGGER.traceExit(traceEntry);
    }
}
