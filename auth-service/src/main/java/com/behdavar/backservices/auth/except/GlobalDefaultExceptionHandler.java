package com.behdavar.backservices.auth.except;

import com.behdavar.backservices.common.model.backservice.BackServiceResponse;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.message.EntryMessage;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
class GlobalDefaultExceptionHandler extends ResponseEntityExceptionHandler {
    private final static Logger LOGGER = LogManager.getLogger(GlobalDefaultExceptionHandler.class);

//    @ExceptionHandler({AuthException.class})
//    public ResponseEntity handleAuthException(AuthException authException) {
//        EntryMessage traceEntry = LOGGER.traceEntry("authException{}", authException);
//
//        BackServiceResponse<String> response = new BackServiceResponse<>();
//        response.setHasError(1);
//        response.setException(authException.getMessage());
//        ResponseEntity<BackServiceResponse<String>> result = new ResponseEntity<>(response, new HttpHeaders(), HttpStatus.EXPECTATION_FAILED);
//
//        return LOGGER.traceExit(traceEntry, result);
//    }

    @ExceptionHandler({Exception.class})
    public ResponseEntity handleAll(Exception exception) {
        EntryMessage traceEntry = LOGGER.traceEntry("exception{}", exception);

        BackServiceResponse<String> response = new BackServiceResponse<>();
        response.setHasError(1);
        response.setException(exception.getMessage());
        ResponseEntity<BackServiceResponse<String>> result = new ResponseEntity<>(response, new HttpHeaders(), HttpStatus.EXPECTATION_FAILED);

        return LOGGER.traceExit(traceEntry, result);
    }
}

