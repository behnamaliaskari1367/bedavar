package com.behdavar.backservices.common.model.except;

import java.util.Objects;

public abstract class BackServiceException extends Exception {

    private String code;
    private String description;
    private String additionMessage;
    private BackServiceExceptionReason reason;

    public <R extends BackServiceExceptionReason> BackServiceException(R reason) {
        super(createMessage(reason.getCode(), reason.getDescription()));
        this.reason = reason;
        this.code = reason.getCode();
        this.description = reason.getDescription();
    }

    public <R extends BackServiceExceptionReason> BackServiceException(R reason, Throwable cause) {
        super(createMessage(reason.getCode(), reason.getDescription()), cause);
        this.reason = reason;
        this.code = reason.getCode();
        this.description = reason.getDescription();
    }

    public <R extends BackServiceExceptionReason> BackServiceException(R reason, String additionMessage, Throwable cause) {
        super(createMessage(reason.getCode(), reason.getDescription(), additionMessage), cause);
        this.reason = reason;
        this.code = reason.getCode();
        this.description = reason.getDescription();
        this.additionMessage = additionMessage;
    }

    public <R extends BackServiceExceptionReason> BackServiceException(R reason, String additionMessage) {
        super(createMessage(reason.getCode(), reason.getDescription(), additionMessage));
        this.reason = reason;
        this.code = reason.getCode();
        this.description = reason.getDescription();
        this.additionMessage = additionMessage;
    }

    private static String createMessage(String code, String description, String additionMessage) {
        return "code: " + code + ", " +
                "description: " + description +
                (Objects.nonNull(additionMessage) ? ", additionMessage: " + additionMessage : "");
    }

    private static String createMessage(String code, String description) {
        return createMessage(code, description, null);
    }

    public String getCode() {
        return code;
    }

    public String getDescription() {
        return description;
    }

    public String getAdditionMessage() {
        return additionMessage;
    }

    public BackServiceExceptionReason getReason() {
        return reason;
    }
}
