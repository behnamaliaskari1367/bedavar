package com.behdavar.backservices.common.model.backservice;

import java.util.Collections;
import java.util.List;
import java.util.Objects;

/**
 * @author Abbas Alishirvani
 */
public class TableResponseModel<T> {

    private boolean hasError;
    private String message;
    private int currentPage;
    private int totalPage;
    private long totalRecords;
    private List<T> response;

    public TableResponseModel(boolean hasError, String message) {
        this.hasError = hasError;
        this.message = message;
    }

    public TableResponseModel(int currentPage, int totalPage, int totalRecords) {
        this(currentPage, totalPage, totalRecords, Collections.emptyList());
    }

    public TableResponseModel(int currentPage, int totalPage, long totalRecords, List<T> response) {
        this.hasError = false;
        this.currentPage = currentPage;
        this.totalPage = totalPage;
        this.totalRecords = totalRecords;
        this.response = response;
    }

    public int getCurrentPage() {
        return currentPage;
    }

    public void setCurrentPage(int currentPage) {
        this.currentPage = currentPage;
    }

    public int getTotalPage() {
        return totalPage;
    }

    public void setTotalPage(int totalPage) {
        this.totalPage = totalPage;
    }

    public long getTotalRecords() {
        return totalRecords;
    }

    public void setTotalRecords(long totalRecords) {
        this.totalRecords = totalRecords;
    }

    public List<T> getResponse() {
        if (Objects.isNull(response)) {
            response = Collections.emptyList();
        }
        return response;
    }

    public void setResponse(List<T> response) {
        this.response = response;
    }

    public boolean getHasError() {
        return hasError;
    }

    public void setHasError(boolean hasError) {
        this.hasError = hasError;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
