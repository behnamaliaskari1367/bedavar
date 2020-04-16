package com.behdavar.backservices.auth.config;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.message.EntryMessage;
import org.springframework.data.domain.AuditorAware;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;

import java.util.Optional;

/**
 * implementation of AuditorAware, set current auditor of the application.
 *
 * @author Abbas Alishirvani
 */
public class AuditorAwareImpl implements AuditorAware<String> {

    private static final Logger LOGGER = LogManager.getLogger(AuditorAwareImpl.class);

    @Override
    public Optional<String> getCurrentAuditor() {
        EntryMessage traceEntry = LOGGER.traceEntry();
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Optional<String> result = Optional.of(user.getUsername());
        return LOGGER.traceExit(traceEntry, result);
    }
}
