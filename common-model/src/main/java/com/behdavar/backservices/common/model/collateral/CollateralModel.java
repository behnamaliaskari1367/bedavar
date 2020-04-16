package com.behdavar.backservices.common.model.collateral;

import com.behdavar.backservices.common.model.BaseModel;

import java.math.BigDecimal;

public class CollateralModel extends BaseModel<String> {

    private BigDecimal collateralAmount;
    private String collateralCode;
    private Integer rowNumber;

    public BigDecimal getCollateralAmount() {
        return collateralAmount;
    }

    public void setCollateralAmount(BigDecimal collateralAmount) {
        this.collateralAmount = collateralAmount;
    }

    public String getCollateralCode() {
        return collateralCode;
    }

    public void setCollateralCode(String collateralCode) {
        this.collateralCode = collateralCode;
    }

    public Integer getRowNumber() {
        return rowNumber;
    }

    public void setRowNumber(Integer rowNumber) {
        this.rowNumber = rowNumber;
    }
}
