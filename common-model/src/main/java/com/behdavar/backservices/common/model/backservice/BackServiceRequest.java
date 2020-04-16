package com.behdavar.backservices.common.model.backservice;

public class BackServiceRequest<T> {
    private T model;

    public T getModel() {
        return model;
    }

    public void setModel(T model) {
        this.model = model;
    }
}

