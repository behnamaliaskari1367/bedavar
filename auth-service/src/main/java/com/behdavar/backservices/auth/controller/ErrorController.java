package com.behdavar.backservices.auth.controller;

import com.behdavar.backservices.common.model.backservice.BackServiceResponse;
import com.behdavar.backservices.common.model.backservice.ErrorType;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.RequestDispatcher;
import javax.servlet.http.HttpServletRequest;

@RestController
public class ErrorController implements org.springframework.boot.web.servlet.error.ErrorController {

    private static final String PATH = "/error";

    @RequestMapping(value = PATH)
    public ResponseEntity<BackServiceResponse<String>> error(HttpServletRequest request) {
        Object status = request.getAttribute(RequestDispatcher.ERROR_STATUS_CODE);

        String message = "Unknown";
        HttpStatus httpStatus = HttpStatus.INTERNAL_SERVER_ERROR;
        if (status != null) {
            Integer statusCode = Integer.valueOf(status.toString());

            for (HttpStatus s : HttpStatus.values()) {
                if (statusCode.equals(s.value())) {
                    httpStatus = s;
                    message = "Code: " + statusCode + ", ReasonPhrase is: " + s.getReasonPhrase();
                }
            }
        }

        BackServiceResponse<String> response = new BackServiceResponse<>();
        response.setHasError(1);
        response.setException(message);
        response.setType(ErrorType.INTERNAL);

        return new ResponseEntity<>(response, new HttpHeaders(), httpStatus);
    }

    @Override
    public String getErrorPath() {
        return PATH;
    }
}
