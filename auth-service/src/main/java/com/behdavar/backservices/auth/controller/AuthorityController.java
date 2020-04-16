package com.behdavar.backservices.auth.controller;

import com.behdavar.backservices.auth.enums.AuthorityTypeEnum;
import com.behdavar.backservices.auth.model.AuthorityModel;
import com.behdavar.backservices.auth.service.AuthorityService;
import com.behdavar.backservices.common.model.backservice.BackServiceResponse;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.message.EntryMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @author Abbas Alishirvani
 */
@RestController
@RequestMapping(AuthorityController.URL)
public class AuthorityController extends BaseController {

    private static final Logger LOGGER = LogManager.getLogger(AuthorityController.class);

    static final String URL = BASE_URL + "authority/";
    private static final String URL_USER_ACTIVE = "type/user/active";

    private AuthorityService service;

    @Autowired
    public void prepare(AuthorityService service) {
        LOGGER.trace("prepare Spring's dependency injection");
        this.service = service;
    }

    @PreAuthorize("hasRole(T(com.behdavar.backservices.auth.enums.AuthRoleEnum).ROLE_BACK_SERVICE_USER_MANAGEMENT)")
    @GetMapping(URL_USER_ACTIVE)
    public BackServiceResponse<List<AuthorityModel>> getActiveList() {
        EntryMessage traceEntry = LOGGER.traceEntry();
        List<AuthorityModel> result = this.service.findByTypeAndEnabled(true, AuthorityTypeEnum.USER_ONLY, AuthorityTypeEnum.USER_AND_MACHINE);
        return LOGGER.traceExit(traceEntry, BackServiceResponse.ofSuccess(result));
    }
}
