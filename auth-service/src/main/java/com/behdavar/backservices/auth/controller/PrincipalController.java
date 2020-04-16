package com.behdavar.backservices.auth.controller;

import com.behdavar.backservices.auth.model.UserModel;
import com.behdavar.backservices.auth.service.UserService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.message.EntryMessage;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpSession;
import java.security.Principal;
import java.util.Collections;
import java.util.Map;
import java.util.Optional;

/**
 * @author Abbas Alishirvani
 */
@RestController
@RequestMapping(PrincipalController.URL)
public class PrincipalController extends BaseController {

    private static final Logger LOGGER = LogManager.getLogger(PrincipalController.class);
    static final String URL = "/principal";

    private final UserService userService;

    public PrincipalController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping
    public Principal user(Principal principal) {
        return LOGGER.traceExit(principal);
    }

    @GetMapping("/detail")
    public UserModel userDetails(Principal principal) {
        EntryMessage traceEntry = LOGGER.traceEntry("principal: {}", principal);
        Optional<UserModel> byUsername = userService.findByUsername(principal.getName());
        if(!byUsername.isPresent()){
            throw new UsernameNotFoundException("invalid username");
        }
        return LOGGER.traceExit(traceEntry, byUsername.get());
    }

    @GetMapping("/token")
    public Map<String, String> token(HttpSession session) {
        return Collections.singletonMap("token", session.getId());
    }
}
