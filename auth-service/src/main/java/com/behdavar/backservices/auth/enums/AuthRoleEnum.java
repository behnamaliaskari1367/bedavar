package com.behdavar.backservices.auth.enums;

public enum AuthRoleEnum {
    ROLE_BACK_SERVICE_USER_MANAGEMENT("مدیریت کاربران"),
    ;

    private final String description;

    AuthRoleEnum(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }
}
