package com.behdavar.backservices.common.model.posting;

public enum PostingLockEnum {
    LOCK("L"),
    UNLOCK("U"),;

    private String value;

    PostingLockEnum(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }
}
