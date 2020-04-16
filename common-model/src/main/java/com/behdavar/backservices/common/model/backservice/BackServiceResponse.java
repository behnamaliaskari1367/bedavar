package com.behdavar.backservices.common.model.backservice;

public class BackServiceResponse<T> {
    private T response;
    private Integer hasError = 0;
    private ErrorType type;
    private String exception;

    public T getResponse() {
        return response;
    }

    public void setResponse(T response) {
        this.response = response;
    }

    public Integer getHasError() {
        return hasError;
    }

    public void setHasError(Integer hasError) {
        this.hasError = hasError;
    }

    public ErrorType getType() {
        return type;
    }

    public void setType(ErrorType type) {
        this.type = type;
    }

    public String getException() {
        return exception;
    }

    public void setException(String exception) {
        this.exception = exception;
    }

    public static <T> BackServiceResponse<T> ofSuccess(T response) {
        BackServiceResponse<T> r = new BackServiceResponse<>();
        r.setResponse(response);
        r.setHasError(0);
        return r;
    }

    public static <T> BackServiceResponse<T> ofError(ErrorType type, String exception) {
        BackServiceResponse<T> r = new BackServiceResponse<>();
        r.setResponse(null);
        r.setHasError(1);
        r.setType(type);
        r.setException(exception);
        return r;
    }

    public static class BackServiceResponseBuilder<T> {

        public BackServiceResponse<T> buildSuccess(T response) {
            BackServiceResponse<T> r = new BackServiceResponse<>();
            r.setResponse(response);
            r.setHasError(0);
            return r;
        }

        public BackServiceResponse<T> buildError(ErrorType type, String exception) {
            BackServiceResponse<T> r = new BackServiceResponse<>();
            r.setResponse(null);
            r.setHasError(0);
            r.setType(type);
            r.setException(exception);
            return r;
        }
    }
}
