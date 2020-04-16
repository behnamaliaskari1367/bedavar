package com.behdavar.backservices.auth.controller;

import com.behdavar.backservices.auth.except.AuthException;
import com.behdavar.backservices.auth.except.AuthExceptionReason;
import com.behdavar.backservices.auth.model.ChangePasswordModel;
import com.behdavar.backservices.auth.model.UserModel;
import com.behdavar.backservices.auth.service.UserService;
import com.behdavar.backservices.common.model.backservice.BackServiceResponse;
import com.behdavar.backservices.common.model.backservice.ErrorType;
import com.behdavar.backservices.common.model.backservice.TableRequestModel;
import com.behdavar.backservices.common.model.backservice.TableResponseModel;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.message.EntryMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.security.Principal;
import java.util.Objects;
import java.util.Optional;


/**
 * @author Abbas Alishirvani
 */
@RestController
@RequestMapping(UserController.URL)
public class UserController extends BaseController {

    private static final Logger LOGGER = LogManager.getLogger(UserController.class);

    private UserService service;

    static final String URL = BASE_URL + "user/";
    private static final String URL_GET = "{id}";
    private static final String URL_FIND_ALL = "find";
    private static final String URL_SEARCH = "search";
    private static final String URL_ADD = "add";
    private static final String URL_EDIT = "edit";
    private static final String URL_RESET_PASSWORD = "reset/password";
    private static final String URL_CHANGE_PASSWORD = "change/password";
    private static final String URL_ASSIGNED_AUTHORITIES = "{id}/authorities";

    @GetMapping(URL_ASSIGNED_AUTHORITIES)
    @PreAuthorize("hasRole(T(com.behdavar.backservices.auth.enums.AuthRoleEnum).ROLE_BACK_SERVICE_USER_MANAGEMENT)")
    public BackServiceResponse<UserModel> getAssignedAuthorities(@PathVariable("id") Long id) {
        EntryMessage traceEntry = LOGGER.traceEntry("id: {}", id);
        if (Objects.isNull(id)) {
            return LOGGER.traceExit(BackServiceResponse.ofError(ErrorType.INTERNAL, "invalid id"));
        }
        UserModel result = this.service.findByUserIdAndAuthorityEnabled(id, true);
        return LOGGER.traceExit(traceEntry, BackServiceResponse.ofSuccess(result));
    }

    @GetMapping(URL_GET)
    @PreAuthorize("hasRole(T(com.behdavar.backservices.auth.enums.AuthRoleEnum).ROLE_BACK_SERVICE_USER_MANAGEMENT)")
    public BackServiceResponse<UserModel> getById(@PathVariable long id) throws AuthException {
        EntryMessage traceEntry = LOGGER.traceEntry("id: {}", id);

        Optional<UserModel> byId = service.findById(id);
        if (!byId.isPresent()) {
            return LOGGER.traceExit(traceEntry, BackServiceResponse.ofError(ErrorType.USER_DATA, "user not found"));
        }

        return LOGGER.traceExit(traceEntry, BackServiceResponse.ofSuccess(byId.get()));
    }

    @PostMapping(URL_FIND_ALL)
    @PreAuthorize("hasRole(T(com.behdavar.backservices.auth.enums.AuthRoleEnum).ROLE_BACK_SERVICE_USER_MANAGEMENT)")
    public TableResponseModel<UserModel> findAll(@RequestBody TableRequestModel<UserModel> request) {
        EntryMessage traceEntry = LOGGER.traceEntry("tableRequestModel: {}", request);

        Page<UserModel> page = service.findAll(getPageRequest(request));
        TableResponseModel<UserModel> result = service.convertToTableResponseModel(page);

        return LOGGER.traceExit(traceEntry, result);
    }

    @PostMapping(URL_SEARCH)
    @PreAuthorize("hasRole(T(com.behdavar.backservices.auth.enums.AuthRoleEnum).ROLE_BACK_SERVICE_USER_MANAGEMENT)")
    public TableResponseModel<UserModel> search(@RequestBody TableRequestModel<UserModel> request) {
        EntryMessage traceEntry = LOGGER.traceEntry("tableRequestModel: {}", request);

        Page<UserModel> page;
        if (Objects.isNull(request.getValue())) {
            page = service.findAll(getPageRequest(request));
        } else {
            page = service.search(request.getValue(), getPageRequest(request));
        }
        TableResponseModel<UserModel> result = service.convertToTableResponseModel(page);

        return LOGGER.traceExit(traceEntry, result);
    }

    @PostMapping(URL_CHANGE_PASSWORD)
    public Boolean changePassword(@Valid @RequestBody ChangePasswordModel changePasswordModel,
                                  Principal principal,
                                  BindingResult bindingResult) throws AuthException {
        EntryMessage traceEntry = LOGGER.traceEntry("changePasswordModel: {}, principal: {}, bindingResult:{}", principal, changePasswordModel, bindingResult);
        if (bindingResult.hasErrors() || Objects.isNull(changePasswordModel.getOldPassword())) {
            throw new AuthException(AuthExceptionReason.ERROR_IN_USER);
        }

        if (Objects.isNull(principal.getName()) || !Objects.equals(changePasswordModel.getUsername(), principal.getName())) {
            throw new AuthException(AuthExceptionReason.ERROR_IN_USER);
        }

        boolean result = service.changePassword(changePasswordModel);

        return LOGGER.traceExit(traceEntry, result);
    }

    @PostMapping(URL_RESET_PASSWORD)
    @PreAuthorize("hasRole(T(com.behdavar.backservices.auth.enums.AuthRoleEnum).ROLE_BACK_SERVICE_USER_MANAGEMENT)")
    public Boolean resetPassword(@Valid @RequestBody ChangePasswordModel changePasswordModel,
                                 BindingResult bindingResult) throws AuthException {
        EntryMessage traceEntry = LOGGER.traceEntry("changePasswordModel: {}, bindingResult:{}", changePasswordModel, bindingResult);
        if (bindingResult.hasErrors()) {
            throw new AuthException(AuthExceptionReason.ERROR_IN_USER);
        }

        boolean result = service.resetPassword(changePasswordModel);

        return LOGGER.traceExit(traceEntry, result);
    }

    @PostMapping(URL_ADD)
    @PreAuthorize("hasRole(T(com.behdavar.backservices.auth.enums.AuthRoleEnum).ROLE_BACK_SERVICE_USER_MANAGEMENT)")
    public BackServiceResponse<UserModel> add(@Valid @RequestBody UserModel model) throws AuthException {
        EntryMessage traceEntry = LOGGER.traceEntry("userModel: {}", model);

        if (Objects.isNull(model)
                || Objects.isNull(model.getUsername())
                || Objects.isNull(model.getPassword())) {
            return LOGGER.traceExit(traceEntry, BackServiceResponse.ofError(ErrorType.USER_DATA, "invalid params"));
        }

        UserModel result = service.add(model);

        return LOGGER.traceExit(traceEntry, BackServiceResponse.ofSuccess(result));
    }

    @PutMapping(URL_EDIT)
    @PreAuthorize("hasRole(T(com.behdavar.backservices.auth.enums.AuthRoleEnum).ROLE_BACK_SERVICE_USER_MANAGEMENT)")
    public BackServiceResponse<UserModel> edit(@Valid @RequestBody UserModel model) throws AuthException {
        EntryMessage traceEntry = LOGGER.traceEntry("userModel: {}", model);

        if (Objects.isNull(model) || Objects.isNull(model.getId())) {
            return LOGGER.traceExit(traceEntry, BackServiceResponse.ofError(ErrorType.USER_DATA, "invalid params"));
        }

        UserModel result = service.edit(model);

        return LOGGER.traceExit(traceEntry, BackServiceResponse.ofSuccess(result));
    }

    @Autowired
    public void prepare(UserService service) {
        LOGGER.trace("prepare Spring's dependency injection");
        this.service = service;
    }
}
