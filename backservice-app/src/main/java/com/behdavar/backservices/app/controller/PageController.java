package com.behdavar.backservices.app.controller;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.message.EntryMessage;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class PageController {

    private static final Logger LOGGER = LogManager.getLogger(PageController.class);

    @GetMapping({"/", "/auth", "/dash",
            "/user-management", "/user/**", "/assign-authority/**",
            "/schedule-management", "/scheduler/**",
            "/samtak/**", "/samat/**"})
    public String index() {
        EntryMessage traceEntry = LOGGER.traceEntry("index");
        return LOGGER.traceExit(traceEntry, "forward:/index.html");
    }
}
