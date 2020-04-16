package com.behdavar.backservices.app.except;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.message.EntryMessage;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
class GlobalDefaultExceptionHandler extends ResponseEntityExceptionHandler {
    private final static Logger LOGGER = LogManager.getLogger(GlobalDefaultExceptionHandler.class);

    @ExceptionHandler({Exception.class})
    public String handleAll(Exception exception) {
        EntryMessage traceEntry = LOGGER.traceEntry("index");
        LOGGER.error(exception.getMessage());
        return LOGGER.traceExit(traceEntry, "forward:/index.html");
    }
}

