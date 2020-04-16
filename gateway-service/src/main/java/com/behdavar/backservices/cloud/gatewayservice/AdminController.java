package com.behdavar.backservices.cloud.gatewayservice;

import com.behdavar.backservices.common.util.LogUtil;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.message.EntryMessage;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AdminController {

    private static final Logger LOGGER = LogManager.getLogger(AdminController.class);

    @PostMapping("/restart")
    public void restart() {
        EntryMessage traceEntry = LOGGER.traceEntry("restart()");
        GatewayServiceApplication.restart();
        LOGGER.traceExit(traceEntry);
    }

    @GetMapping("/loglevel/{level}")
    public void setLogLevel(@PathVariable("level") Level level) {
        EntryMessage traceEntry = LOGGER.traceEntry("setLogLevel(level: {})", level);
        LogUtil.setLogLevel(level, GatewayServiceApplication.class.getPackage().getName());
        LOGGER.traceExit(traceEntry);
    }
}
