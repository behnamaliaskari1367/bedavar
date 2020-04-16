package com.behdavar.backservices.app.controller;

import com.behdavar.backservices.app.model.CheckTokenModel;
import com.behdavar.backservices.app.model.LoggedModel;
import com.behdavar.backservices.app.model.LoginModel;
import com.behdavar.backservices.common.model.backservice.BackServiceResponse;
import com.behdavar.backservices.common.model.backservice.ErrorType;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.message.EntryMessage;
import org.springframework.boot.autoconfigure.security.oauth2.resource.ResourceServerProperties;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.security.oauth2.client.OAuth2RestOperations;
import org.springframework.security.oauth2.client.resource.OAuth2AccessDeniedException;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import javax.validation.Valid;
import java.util.Objects;
import java.util.Optional;

@RestController
public class UserController {
    private static final Logger LOGGER = LogManager.getLogger(UserController.class);

    private final RestTemplate restTemplate;
    private final OAuth2RestOperations passwordRestTemplate;
    private final ResourceServerProperties serverProperties;

    public UserController(RestTemplate restTemplate,
                          OAuth2RestOperations passwordRestTemplate,
                          ResourceServerProperties serverProperties) {
        this.restTemplate = restTemplate;
        this.passwordRestTemplate = passwordRestTemplate;
        this.serverProperties = serverProperties;
    }

    @PostMapping("/user/get_token")
    public ResponseEntity<BackServiceResponse<LoggedModel>> getPrinciapl(@Valid @RequestBody LoginModel model, BindingResult bindingResult) {
        EntryMessage traceEntry = LOGGER.traceEntry("LoginModel: {}", model);

        ResponseEntity<BackServiceResponse<LoggedModel>> result;
        Optional<ResponseEntity<BackServiceResponse<LoggedModel>>> optional = hasError(bindingResult);
        if (optional.isPresent()) {
            return LOGGER.traceExit(optional.get());
        }

        passwordRestTemplate.getOAuth2ClientContext().getAccessTokenRequest().set("username", model.getUsername());
        passwordRestTemplate.getOAuth2ClientContext().getAccessTokenRequest().set("password", model.getPassword());

        ResponseEntity<LoggedModel> response;
        try {
            response = passwordRestTemplate.getForEntity(serverProperties.getUserInfoUri(), LoggedModel.class);
            result = ResponseEntity.ok(BackServiceResponse.ofSuccess(response.getBody()));
        } catch (OAuth2AccessDeniedException e) {
            result = ResponseEntity.badRequest().body(BackServiceResponse.ofError(ErrorType.USER_DATA, "اطلاعات صحیح نمی باشد"));
        } catch (Exception e) {
            result = ResponseEntity.badRequest().body(BackServiceResponse.ofError(ErrorType.USER_DATA, e.getMessage()));
        }
        return LOGGER.traceExit(traceEntry, result);
    }

    @PostMapping("/user/check_token")
    public ResponseEntity<BackServiceResponse<CheckTokenModel>> checkToken(@Valid @RequestBody CheckTokenModel model, BindingResult bindingResult) {
        EntryMessage traceEntry = LOGGER.traceEntry();

        ResponseEntity<BackServiceResponse<CheckTokenModel>> result;
        Optional<ResponseEntity<BackServiceResponse<CheckTokenModel>>> optional = hasError(bindingResult);
        if (optional.isPresent()) {
            return LOGGER.traceExit(optional.get());
        }

        String uri = serverProperties.getTokenInfoUri() + "?token=" + model.getToken();
        HttpHeaders headers = new HttpHeaders();
        headers.setBasicAuth(serverProperties.getClientId(), serverProperties.getClientSecret());
        HttpEntity<String> entity = new HttpEntity<>(null, headers);

        ResponseEntity<CheckTokenModel> response = restTemplate.exchange(uri, HttpMethod.GET, entity, CheckTokenModel.class);
        result = ResponseEntity.ok(BackServiceResponse.ofSuccess(response.getBody()));
        return LOGGER.traceExit(traceEntry, result);
    }

    private <T> Optional<ResponseEntity<BackServiceResponse<T>>> hasError(BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            FieldError error = bindingResult.getFieldError();
            String errorMsg;
            if (Objects.nonNull(error)) {
                errorMsg = error.getField() + " " + error.getDefaultMessage();
            } else {
                errorMsg = bindingResult.getAllErrors().get(0).toString();
            }
            return Optional.of(ResponseEntity.badRequest().body(BackServiceResponse.ofError(ErrorType.USER_DATA, errorMsg)));
        }
        return Optional.empty();
    }
}
