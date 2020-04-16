package com.behdavar.backservices.auth.controller;

import com.behdavar.backservices.auth.model.UserModel;
import com.behdavar.backservices.auth.service.UserService;
import com.behdavar.backservices.common.model.backservice.BackServiceResponse;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.message.EntryMessage;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.Optional;

/**
 * @author Abbas Alishirvani
 */
@RestController
@RequestMapping(OAuth2Controller.URL)
public class OAuth2Controller extends BaseController {

    private static final Logger LOGGER = LogManager.getLogger(OAuth2Controller.class);
    static final String URL = "/oauth2/principal";

    private final UserService userService;
    private final TokenStore tokenStore;

    public OAuth2Controller(UserService userService, TokenStore tokenStore) {
        this.userService = userService;
        this.tokenStore = tokenStore;
    }

    @GetMapping
    public Principal user(Principal principal) {
        return LOGGER.traceExit(principal);
    }

    @GetMapping("/detail")
    public UserModel userDetails(Principal principal) {
        EntryMessage traceEntry = LOGGER.traceEntry("principal: {}", principal);
        Optional<UserModel> byUsername = userService.findByUsername(principal.getName());
        if (!byUsername.isPresent()) {
            throw new UsernameNotFoundException("invalid username");
        }
        return LOGGER.traceExit(traceEntry, byUsername.get());
    }

    @PostMapping("/revoke")
    public BackServiceResponse<Boolean> revokeToken(Authentication authentication) {
        EntryMessage traceEntry = LOGGER.traceEntry("authentication: {}", authentication);

        Optional.ofNullable(authentication).ifPresent(auth -> {
            Optional.ofNullable(tokenStore.getAccessToken((OAuth2Authentication) auth)).ifPresent(oAuth2AccessToken -> {
                Optional.ofNullable(oAuth2AccessToken.getRefreshToken()).ifPresent(tokenStore::removeRefreshToken);
                tokenStore.removeAccessToken(oAuth2AccessToken);
            });
        });

        return LOGGER.traceExit(traceEntry, BackServiceResponse.ofSuccess(true));
    }
}
