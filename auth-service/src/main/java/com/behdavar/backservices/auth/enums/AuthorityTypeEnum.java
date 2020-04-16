package com.behdavar.backservices.auth.enums;

import java.util.Objects;

public enum AuthorityTypeEnum {

    USER_ONLY("USR"),
    MACHINE_ONLY("MCH"),
    USER_AND_MACHINE("UAM"),
    ;

    private final String code;

    AuthorityTypeEnum(String code) {
        this.code = code;
    }

    public String getCode() {
        return code;
    }

    public static AuthorityTypeEnum findByCode(String code) {
        if (Objects.isNull(code)) return null;

        for (AuthorityTypeEnum authorityType : AuthorityTypeEnum.values()) {
            if (authorityType.code.equals(code)) return authorityType;
        }

        return null;
    }
}
