package com.behdavar.backservices.auth.service.impl;

import com.behdavar.backservices.auth.service.UserService;
import com.behdavar.backservices.common.util.validation.NullValidator;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.message.EntryMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    private static final Logger LOGGER = LogManager.getLogger(UserDetailsServiceImpl.class);
    private UserService userService;

    @Autowired
    public void prepare(UserService userService) {
        LOGGER.trace("prepare Spring's dependency injection");
        this.userService = userService;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        EntryMessage traceEntry = LOGGER.traceEntry("username: {}", username);

        User result = userService.loadUserByUsername(username);
        NullValidator.isNullThrow(result, () -> new UsernameNotFoundException(username + " not found"));

        return LOGGER.traceExit(traceEntry, result);
    }
}
