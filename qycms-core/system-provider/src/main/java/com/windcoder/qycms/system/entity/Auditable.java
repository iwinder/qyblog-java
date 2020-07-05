package com.windcoder.qycms.system.entity;


import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.format.annotation.DateTimeFormat;


import java.io.Serializable;
import java.util.Date;

/**
 * Description:
 * User: WindCoder
 * Date: 2017-12-12
 * Time: 0:15 上午
 */

public class Auditable implements Serializable {
    private static final long serialVersionUID = 1L;
    @LastModifiedDate
    @DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
    protected Date lastModifiedDate;

    @CreatedDate
    @DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")

    protected Date createdDate;

    @CreatedBy
    protected User createdBy;

    @LastModifiedBy
    protected User lastModifiedBy;


    public Date getLastModifiedDate() {
        return lastModifiedDate;
    }

    public void setLastModifiedDate(Date lastModifiedDate) {
        this.lastModifiedDate = lastModifiedDate;
    }

    public Date getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(Date createdDate) {
        this.createdDate = createdDate;
    }


    public User getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(User createdBy) {
        this.createdBy = createdBy;
    }

    public User getLastModifiedBy() {
        return lastModifiedBy;
    }

    public void setLastModifiedBy(User lastModifiedBy) {
        this.lastModifiedBy = lastModifiedBy;
    }
}
