package com.windcoder.qycms.system.entity;

import java.util.Date;

public class SysIpBlack extends Auditable {
    private Long id;

    private String visitorIp;

    private String visitorAgent;

    private String type;

    private String remarks;

    private Integer blackNum;

    private Date createdDate;

    private Date lastModifiedDate;

    private Boolean deleted;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getVisitorIp() {
        return visitorIp;
    }

    public void setVisitorIp(String visitorIp) {
        this.visitorIp = visitorIp;
    }

    public String getVisitorAgent() {
        return visitorAgent;
    }

    public void setVisitorAgent(String visitorAgent) {
        this.visitorAgent = visitorAgent;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getRemarks() {
        return remarks;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }

    public Integer getBlackNum() {
        return blackNum;
    }

    public void setBlackNum(Integer blackNum) {
        this.blackNum = blackNum;
    }

    public Date getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(Date createdDate) {
        this.createdDate = createdDate;
    }

    public Date getLastModifiedDate() {
        return lastModifiedDate;
    }

    public void setLastModifiedDate(Date lastModifiedDate) {
        this.lastModifiedDate = lastModifiedDate;
    }

    public Boolean getDeleted() {
        return deleted;
    }

    public void setDeleted(Boolean deleted) {
        this.deleted = deleted;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", id=").append(id);
        sb.append(", visitorIp=").append(visitorIp);
        sb.append(", visitorAgent=").append(visitorAgent);
        sb.append(", type=").append(type);
        sb.append(", remarks=").append(remarks);
        sb.append(", blackNum=").append(blackNum);
        sb.append(", createdDate=").append(createdDate);
        sb.append(", lastModifiedDate=").append(lastModifiedDate);
        sb.append(", deleted=").append(deleted);
        sb.append("]");
        return sb.toString();
    }
}