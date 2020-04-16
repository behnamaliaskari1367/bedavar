package com.behdavar.backservices.common.model.ccy;

import java.math.BigDecimal;
import java.util.Date;

public class CurrencyRateModel {

    private String ccy;
    private Date date;
    private BigDecimal spotRate;
    private BigDecimal sellRate;
    private BigDecimal buyRate;

    public String getCcy() {
        return ccy;
    }

    public void setCcy(String ccy) {
        this.ccy = ccy;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public BigDecimal getSpotRate() {
        return spotRate;
    }

    public void setSpotRate(BigDecimal spotRate) {
        this.spotRate = spotRate;
    }

    public BigDecimal getSellRate() {
        return sellRate;
    }

    public void setSellRate(BigDecimal sellRate) {
        this.sellRate = sellRate;
    }

    public BigDecimal getBuyRate() {
        return buyRate;
    }

    public void setBuyRate(BigDecimal buyRate) {
        this.buyRate = buyRate;
    }
}
