package com.behdavar.backservices.auth.except;

import com.behdavar.backservices.common.model.except.BackServiceExceptionReason;

/**
 * @author Abbas Alishirvani
 */
public class AuthExceptionReason extends BackServiceExceptionReason {

    public static final BackServiceExceptionReason ERROR_IN_USER = new BackServiceExceptionReason("AU01", "خطای امنیتی - کاربر");
    public static final BackServiceExceptionReason ERROR_IN_AUTHENTICATION = new BackServiceExceptionReason("AU02", "نام کاربری یا رمز اشتباه است");

    protected AuthExceptionReason(String code, String description) {
        super(code, description);
    }
}
