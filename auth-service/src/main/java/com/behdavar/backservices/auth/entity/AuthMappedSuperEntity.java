package com.behdavar.backservices.auth.entity;


import com.behdavar.backservices.auth.entity.constant.AuthBaseEntityConstant;
import com.behdavar.backservices.common.model.BaseEntity;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

/**
 * @author Abbas Alishirvani
 */
@Access(AccessType.PROPERTY)
@MappedSuperclass
@EntityListeners(AuditingEntityListener.class)
public abstract class AuthMappedSuperEntity<ID extends Serializable> extends BaseEntity<ID> {

    private Date createDate;
    private Date lastModifiedDate;
    private String createBy;
    private String lastModifiedBy;
    private Integer version;

    @CreatedDate
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "CREATED_DATE", nullable = false)
    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    @CreatedBy
    @Column(name = "CREATED_BY", nullable = false, length = AuthBaseEntityConstant.USER_AUDIT_LENGTH)
    public String getCreateBy() {
        return createBy;
    }

    public void setCreateBy(String createBy) {
        this.createBy = createBy;
    }

    @LastModifiedDate
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "LAST_MODIFIED_DATE")
    public Date getLastModifiedDate() {
        return lastModifiedDate;
    }

    public void setLastModifiedDate(Date lastModifiedDate) {
        this.lastModifiedDate = lastModifiedDate;
    }

    @LastModifiedBy
    @Column(name = "LAST_MODIFIED_BY", length = AuthBaseEntityConstant.USER_AUDIT_LENGTH)
    public String getLastModifiedBy() {
        return lastModifiedBy;
    }

    public void setLastModifiedBy(String lastModifiedBy) {
        this.lastModifiedBy = lastModifiedBy;
    }

    @Version
    @Column(name = "VERSION", length = AuthBaseEntityConstant.VERSION_LENGTH, nullable = false)
    public Integer getVersion() {
        return version;
    }

    public void setVersion(Integer version) {
        this.version = version;
    }
}

