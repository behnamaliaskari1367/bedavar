package com.behdavar.backservices.common.model.backservice;

/**
 * @author Abbas Alishirvani
 */
public class TableRequestModel<T> {

    private int pageSize = 5;
    private int currentPage = 0;
    private String sortIndex = "id";
    private String sortDirection = "ASC";
    private T value;

    public int getPageSize() {
        return pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }

    public int getCurrentPage() {
        return currentPage;
    }

    public void setCurrentPage(int currentPage) {
        this.currentPage = currentPage;
    }

    public String getSortIndex() {
        return sortIndex;
    }

    public void setSortIndex(String sortIndex) {
        this.sortIndex = sortIndex;
    }

    public String getSortDirection() {
        return sortDirection;
    }

    public void setSortDirection(String sortDirection) {
        this.sortDirection = sortDirection;
    }

    public T getValue() {
        return value;
    }

    public void setValue(T value) {
        this.value = value;
    }

    @Override
    public String toString() {
        return "TableRequestModel{" +
                "pageSize=" + pageSize +
                ", currentPage=" + currentPage +
                ", sortIndex='" + sortIndex + '\'' +
                ", sortDirection=" + sortDirection +
                ", value=" + value +
                '}';
    }
}
