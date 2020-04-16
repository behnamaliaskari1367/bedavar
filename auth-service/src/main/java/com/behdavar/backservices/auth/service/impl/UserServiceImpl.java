package com.behdavar.backservices.auth.service.impl;

import com.behdavar.backservices.auth.entity.UserAuthorityEntity;
import com.behdavar.backservices.auth.entity.UserEntity;
import com.behdavar.backservices.auth.except.AuthException;
import com.behdavar.backservices.auth.except.AuthExceptionReason;
import com.behdavar.backservices.auth.mapper.UserMapper;
import com.behdavar.backservices.auth.model.ChangePasswordModel;
import com.behdavar.backservices.auth.model.UserModel;
import com.behdavar.backservices.auth.repository.UserCrudRepository;
import com.behdavar.backservices.auth.repository.UserPagingAndSortingRepository;
import com.behdavar.backservices.auth.service.UserService;
import com.behdavar.backservices.common.util.validation.NullValidator;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.message.EntryMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.CrudRepository;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.*;

@Service
public class UserServiceImpl extends AuthBaseServiceImpl<UserEntity, UserModel, Long> implements UserService {

    private static final Logger LOGGER = LogManager.getLogger(UserServiceImpl.class);
    private UserCrudRepository repository;
    private UserPagingAndSortingRepository pagingAndSortingRepository;
    private UserMapper mapper;
    private PasswordEncoder passwordEncoder;

    @Autowired
    public void prepare(UserMapper mapper,
                        UserCrudRepository repository,
                        UserPagingAndSortingRepository pagingAndSortingRepository,
                        PasswordEncoder passwordEncoder) {
        LOGGER.trace("prepare Spring's dependency injection");
        this.mapper = mapper;
        this.repository = repository;
        this.passwordEncoder = passwordEncoder;
        this.pagingAndSortingRepository = pagingAndSortingRepository;
    }

    @Override
    public UserMapper getMapper() {
        return LOGGER.traceExit(mapper);
    }

    @Override
    public CrudRepository<UserEntity, Long> getCrudRepository() {
        return LOGGER.traceExit(repository);
    }

    @Override
    @Transactional
    public User loadUserByUsername(String username) throws UsernameNotFoundException {
        EntryMessage traceEntry = LOGGER.traceEntry("username: {})", username);

        UserEntity byUsername = repository.findByUsername(username);
        NullValidator.isNullThrow(byUsername, () -> new UsernameNotFoundException(username + " not found"));
        User result = convert(byUsername);

        return LOGGER.traceExit(traceEntry, result);
    }

    @Override
    public UserModel add(UserModel model) throws AuthException {
        EntryMessage traceEntry = LOGGER.traceEntry("UserModel: {})", model);


        Optional<UserModel> byUsername = findByUsername(model.getUsername());
        if (byUsername.isPresent()) {
            throw new AuthException(AuthExceptionReason.ERROR_IN_USER, "Duplicate user");
        }

        boolean enabled = false;
        if (Objects.nonNull(model.getEnabled())) {
            enabled = model.getEnabled();
        }

        model.setId(null);
        model.setEnabled(enabled);
        model.setAccountLocked(!enabled);
        model.setCredentialsExpired(!enabled);
        model.setAccountExpired(!enabled);

        UserModel result = save(model);

        return LOGGER.traceExit(traceEntry, result);
    }

    @Override
    public UserModel edit(UserModel model) throws AuthException {
        EntryMessage traceEntry = LOGGER.traceEntry("UserModel: {})", model);

        Optional<UserEntity> optionalUserEntity = repository.findById(model.getId());
        if (!optionalUserEntity.isPresent()) {
            throw new AuthException(AuthExceptionReason.ERROR_IN_USER, "invalid user");
        }
        UserEntity userEntity = optionalUserEntity.get();

        if (!Objects.equals(userEntity.getVersion(), model.getVersion())) {
            throw new AuthException(AuthExceptionReason.ERROR_IN_INPUT_PARAMS, "invalid version");
        }

        userEntity.setBranch(model.getBranch());
        userEntity.setFirstName(model.getFirstName());
        userEntity.setLastName(model.getLastName());

        boolean enabled = false;
        if (Objects.nonNull(model.getEnabled())) {
            enabled = model.getEnabled();
        }
        userEntity.setEnabled(enabled);
        userEntity.setAccountLocked(!enabled);
        userEntity.setCredentialsExpired(!enabled);
        userEntity.setAccountExpired(!enabled);

        UserEntity save = repository.save(userEntity);

        return LOGGER.traceExit(traceEntry, convertEntityToModel(save));
    }

    @Override
    public Page<UserModel> findAll(Pageable pageable) {
        EntryMessage traceEntry = LOGGER.traceEntry("Pageable: {}", pageable);

        Page<UserEntity> entities = pagingAndSortingRepository.findAll(pageable);
        Page<UserModel> result = convertEntitiesToModels(entities);

        return LOGGER.traceExit(traceEntry, result);
    }

    @Override
    public Page<UserModel> search(UserModel userModel, Pageable pageable) {
        EntryMessage traceEntry = LOGGER.traceEntry("UserModel: {}, Pageable: {}", userModel, pageable);

        Page<UserEntity> entities = pagingAndSortingRepository.findByParams(userModel, pageable);
        Page<UserModel> result = convertEntitiesToModels(entities);

        return LOGGER.traceExit(traceEntry, result);
    }

    @Override
    public UserModel findByUserIdAndAuthorityEnabled(Long userId, boolean enabled) {
        EntryMessage traceEntry = LOGGER.traceEntry("userId: {}, enabled: {}", userId, enabled);
        UserEntity entity = this.repository.findByUserIdAndAuthorityEnabled(userId, enabled);
        UserModel result = convertEntityToModel(entity);
        return LOGGER.traceExit(traceEntry, result);
    }

    private User convert(UserEntity userEntity) {
        EntryMessage traceEntry = LOGGER.traceEntry("userEntity: {})", userEntity);

        User result = new User(
                userEntity.getUsername(),
                userEntity.getPassword(),
                userEntity.getEnabled(),
                !userEntity.getAccountExpired(),
                !userEntity.getCredentialsExpired(),
                !userEntity.getAccountLocked(),
                getAuthorities(userEntity));

        return LOGGER.traceExit(traceEntry, result);
    }

    private Set<GrantedAuthority> getAuthorities(UserEntity userEntity) {
        EntryMessage traceEntry = LOGGER.traceEntry("userEntity: {})", userEntity);
        if (Objects.isNull(userEntity.getUserAuthorities())) {
            return Collections.emptySet();
        }

        Set<GrantedAuthority> result = new HashSet<>();

        for (UserAuthorityEntity userAuthorityEntity : userEntity.getUserAuthorities()) {
            if (Objects.nonNull(userAuthorityEntity.getAuthority())) {
                String name = userAuthorityEntity.getAuthority().getName();
                if (Objects.nonNull(name)) result.add(new SimpleGrantedAuthority(name));
            }
        }

        return LOGGER.traceExit(traceEntry, result);
    }

    @Override
    public Optional<UserModel> findByUsername(String username) {
        EntryMessage traceEntry = LOGGER.traceEntry("username: {})", username);

        UserEntity byUsername = repository.findByUsername(username);
        if (Objects.isNull(byUsername)) {
            return Optional.empty();
        }

        return LOGGER.traceExit(traceEntry, Optional.of(convertEntityToModel(byUsername)));
    }

    @Override
    public boolean changePassword(ChangePasswordModel model) throws AuthException {
        EntryMessage traceEntry = LOGGER.traceEntry("changePasswordModel: {})", model);

        UserEntity entity = repository.findByUsername(model.getUsername());
        NullValidator.isNullThrow(entity, () -> new UsernameNotFoundException(model.getUsername() + " not found"));

        if (!passwordEncoder.matches(model.getOldPassword(), entity.getPassword())) {
            throw LOGGER.throwing(Level.TRACE, new AuthException(AuthExceptionReason.ERROR_IN_AUTHENTICATION));
        }

        String encodedPassword = passwordEncoder.encode(model.getNewPassword());
        entity.setPassword(encodedPassword);
        UserEntity save = repository.save(entity);

        boolean result = passwordEncoder.matches(model.getNewPassword(), save.getPassword());

        return LOGGER.traceExit(traceEntry, result);
    }

    @Override
    public boolean resetPassword(ChangePasswordModel model) throws AuthException {
        EntryMessage traceEntry = LOGGER.traceEntry("changePasswordModel: {})", model);

        UserEntity entity = repository.findByUsername(model.getUsername());
        NullValidator.isNullThrow(entity, () -> new UsernameNotFoundException(model.getUsername() + " not found"));

        String encodedPassword = passwordEncoder.encode(model.getNewPassword());
        entity.setPassword(encodedPassword);
        UserEntity saved = repository.save(entity);

        boolean result = passwordEncoder.matches(model.getNewPassword(), saved.getPassword());

        return LOGGER.traceExit(traceEntry, result);
    }

    @Override
    public UserModel save(UserModel model) throws AuthException {
        EntryMessage traceEntry = LOGGER.traceEntry("UserModel: {})", model);
        if (Objects.nonNull(model) && Objects.nonNull(model.getPassword())) {
            String encodedPassword = passwordEncoder.encode(model.getPassword());
            model.setPassword(encodedPassword);
        }
        return LOGGER.traceExit(traceEntry, super.save(model));
    }
}
