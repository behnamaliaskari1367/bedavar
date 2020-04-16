package com.behdavar.backservices.auth.service;

import com.behdavar.backservices.auth.entity.UserEntity;
import com.behdavar.backservices.auth.except.AuthException;
import com.behdavar.backservices.auth.model.ChangePasswordModel;
import com.behdavar.backservices.auth.model.UserModel;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.userdetails.User;

import java.util.Optional;

public interface UserService extends AuthBaseService<UserEntity, UserModel, Long> {

    Optional<UserModel> findByUsername(String username);

    boolean changePassword(ChangePasswordModel model) throws AuthException;

    boolean resetPassword(ChangePasswordModel model) throws AuthException;

    User loadUserByUsername(String username);

    UserModel add(UserModel model) throws AuthException;

    UserModel edit(UserModel model) throws AuthException;

    Page<UserModel> findAll(Pageable pageable);

    Page<UserModel> search(UserModel userModel, Pageable pageable);

    UserModel findByUserIdAndAuthorityEnabled(Long userId, boolean b);
}
