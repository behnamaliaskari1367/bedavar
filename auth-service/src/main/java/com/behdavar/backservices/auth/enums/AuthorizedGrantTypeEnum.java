package com.behdavar.backservices.auth.enums;

import java.util.Objects;

public enum AuthorizedGrantTypeEnum {

    REFRESH_TOKEN("refresh_token"),
    PASSWORD("password"),
    CLIENT_CREDENTIALS("client_credentials"),
    IMPLICIT("implicit"),
    AUTHORIZATION_CODE("authorization_code");

    private final String grantType;

    AuthorizedGrantTypeEnum(String value) {
        this.grantType = value;
    }

    public String getGrantType() {
        return grantType;
    }

    public static AuthorizedGrantTypeEnum findByGrantType(String s) {
        if (Objects.isNull(s)) {
            return null;
        }

        for (AuthorizedGrantTypeEnum e : AuthorizedGrantTypeEnum.values()) {
            if (e.getGrantType().equals(s)) {
                return e;
            }
        }

        return null;
    }
}
