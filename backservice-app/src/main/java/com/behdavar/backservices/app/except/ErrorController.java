package com.behdavar.backservices.app.except;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.message.EntryMessage;
import org.springframework.boot.autoconfigure.web.servlet.error.AbstractErrorController;
import org.springframework.boot.web.servlet.error.ErrorAttributes;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

@RestController
public class ErrorController extends AbstractErrorController {
    private static final Logger LOGGER = LogManager.getLogger(ErrorController.class);

    private static final String PATH = "/error";

    public ErrorController(final ErrorAttributes errorAttributes) {
        super(errorAttributes);
    }

    @RequestMapping(value = PATH)
    public String error(HttpServletRequest request) {
        EntryMessage traceEntry = LOGGER.traceEntry("index");
        Map<String, Object> body = this.getErrorAttributes(request, false);
        return LOGGER.traceExit(traceEntry, "forward:/index.html");
    }

    @Override
    public String getErrorPath() {
        return PATH;
    }
}
