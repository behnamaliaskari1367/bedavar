package com.behdavar.backservices.common.model.customer;

import com.behdavar.backservices.common.model.BaseModel;

import java.math.BigDecimal;

public class CustomerRelationModel extends BaseModel<String> {

    private CustomerModel parentClient;
    private CustomerModel childClient;
    private CustomerRelationTypeEnum relationType;
    protected String historyOperation;
    protected String relationTypeTitle;
    private BigDecimal stocksPercent;

    public CustomerModel getChildClient() {
        return childClient;
    }

    public void setChildClient(CustomerModel childClient) {
        this.childClient = childClient;
    }

    public CustomerModel getParentClient() {
        return parentClient;
    }

    public void setParentClient(CustomerModel parentClient) {
        this.parentClient = parentClient;
    }

    public CustomerRelationTypeEnum getRelationType() {
        return relationType;
    }

    public void setRelationType(CustomerRelationTypeEnum relationType) {
        this.relationType = relationType;
    }

    public BigDecimal getStocksPercent() {
        return stocksPercent;
    }

    public void setStocksPercent(BigDecimal stocksPercent) {
        this.stocksPercent = stocksPercent;
    }

    public String getHistoryOperation() {
        return historyOperation;
    }

    public void setHistoryOperation(String historyOperation) {
        this.historyOperation = historyOperation;
    }

    public String getRelationTypeTitle() {
        return relationTypeTitle;
    }

    public void setRelationTypeTitle(String relationTypeTitle) {
        this.relationTypeTitle = relationTypeTitle;
    }
}
