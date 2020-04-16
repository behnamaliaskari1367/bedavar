package com.behdavar.backservices.auth.except;

import com.behdavar.backservices.common.model.except.BackServiceException;
import com.behdavar.backservices.common.model.except.BackServiceExceptionReason;

/**
 * @author Abbas Alishirvani
 */
public class AuthException extends BackServiceException {
    public <R extends BackServiceExceptionReason> AuthException(R reason) {
        super(reason);
    }

    public <R extends BackServiceExceptionReason> AuthException(R reason, Throwable cause) {
        super(reason, cause);
    }

    public <R extends BackServiceExceptionReason> AuthException(R reason, String additionMessage, Throwable cause) {
        super(reason, additionMessage, cause);
    }

    public <R extends BackServiceExceptionReason> AuthException(R reason, String additionMessage) {
        super(reason, additionMessage);
    }
}
