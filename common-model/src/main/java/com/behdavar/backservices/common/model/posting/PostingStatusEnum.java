package com.behdavar.backservices.common.model.posting;

public enum PostingStatusEnum {

    WAIT("W"),
    SUCCESS("S"),;

    private String value;

    PostingStatusEnum(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }
}
