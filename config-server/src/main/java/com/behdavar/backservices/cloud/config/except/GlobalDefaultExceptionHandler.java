package com.behdavar.backservices.cloud.config.except;

import com.behdavar.backservices.common.model.backservice.BackServiceResponse;
import com.behdavar.backservices.common.model.backservice.ErrorType;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.message.EntryMessage;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

/**
 * @author Abbas Alishirvani
 */
@ControllerAdvice
class GlobalDefaultExceptionHandler extends ResponseEntityExceptionHandler {
    private static final Logger LOG = LogManager.getLogger(GlobalDefaultExceptionHandler.class);

    @ExceptionHandler({Exception.class})
    public ResponseEntity<BackServiceResponse<String>> handleAll(Exception e) {
        EntryMessage traceEntry = LOG.traceEntry("exception: {} ", e);

        BackServiceResponse<String> response = BackServiceResponse.ofError(ErrorType.INTERNAL, e.getMessage());
        ResponseEntity<BackServiceResponse<String>> responseEntity = new ResponseEntity<>(response, new HttpHeaders(), HttpStatus.INTERNAL_SERVER_ERROR);
        return LOG.traceExit(traceEntry, responseEntity);
    }
}

