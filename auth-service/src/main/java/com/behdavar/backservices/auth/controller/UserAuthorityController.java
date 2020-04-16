package com.behdavar.backservices.auth.controller;

import com.behdavar.backservices.auth.model.AuthorityModel;
import com.behdavar.backservices.common.model.backservice.BackServiceResponse;
import com.behdavar.backservices.auth.except.AuthException;
import com.behdavar.backservices.auth.service.UserAuthorityService;
import com.behdavar.backservices.common.model.backservice.ErrorType;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.message.EntryMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.List;
import java.util.Objects;

/**
 * @author Abbas Alishirvani
 */
@RestController
@RequestMapping(UserAuthorityController.URL)
public class UserAuthorityController extends BaseController {

    private static final Logger LOGGER = LogManager.getLogger(UserAuthorityController.class);

    static final String URL = BASE_URL + "user-authority/";
    private static final String URL_ASSIGN_AUTHORITIES = "user/{id}/assign/authorities";

    private UserAuthorityService service;

    @Autowired
    public void prepare(UserAuthorityService service) {
        LOGGER.trace("prepare Spring's dependency injection");
        this.service = service;
    }

    @PreAuthorize("hasRole(T(com.behdavar.backservices.auth.enums.AuthRoleEnum).ROLE_BACK_SERVICE_USER_MANAGEMENT)")
    @PutMapping(URL_ASSIGN_AUTHORITIES)
    public BackServiceResponse<Boolean> assignAuthorities(@PathVariable("id") Long id, @RequestBody List<AuthorityModel> list) throws AuthException {
        EntryMessage traceEntry = LOGGER.traceEntry("id: {}, list: {}", id, list);

        if (Objects.isNull(id)) {
            return LOGGER.traceExit(traceEntry, BackServiceResponse.ofError(ErrorType.USER_DATA, "invalid id"));
        }

        if (Objects.isNull(list)) {
            list = Collections.emptyList();
        }

        service.setAuthorities(id, list);

        return LOGGER.traceExit(traceEntry, BackServiceResponse.ofSuccess(true));
    }
}
