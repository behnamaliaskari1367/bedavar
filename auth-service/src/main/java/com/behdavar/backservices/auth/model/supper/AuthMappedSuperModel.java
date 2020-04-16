package com.behdavar.backservices.auth.model.supper;


import java.io.Serializable;
import java.util.Date;

/**
 * @author Abbas Alishirvani
 */
public abstract class AuthMappedSuperModel<ID extends Serializable> extends AuthBaseModel<ID> {

    private Date createDate;
    private Date lastModifiedDate;
    private String createBy;
    private String lastModifiedBy;
    private Integer version;

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    public String getCreateBy() {
        return createBy;
    }

    public void setCreateBy(String createBy) {
        this.createBy = createBy;
    }

    public Date getLastModifiedDate() {
        return lastModifiedDate;
    }

    public void setLastModifiedDate(Date lastModifiedDate) {
        this.lastModifiedDate = lastModifiedDate;
    }

    public String getLastModifiedBy() {
        return lastModifiedBy;
    }

    public void setLastModifiedBy(String lastModifiedBy) {
        this.lastModifiedBy = lastModifiedBy;
    }

    public Integer getVersion() {
        return version;
    }

    public void setVersion(Integer version) {
        this.version = version;
    }
}

