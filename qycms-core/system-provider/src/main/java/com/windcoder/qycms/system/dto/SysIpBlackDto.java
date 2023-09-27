package com.windcoder.qycms.system.dto;

import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;

public class SysIpBlackDto {

    /**
     * id
     */
    private Long id;

    /**
     * 访客ip
     */
    private String visitorIp;

    /**
     * 访客客户端
     */
    private String visitorAgent;

    /**
     * 类型
     */
    private String type;

    /**
     * 备注
     */
    private String remarks;

    /**
     * 被封次数
     */
    private Integer blackNum;

    /**
     * 创建时间
     */
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
    private Date createdDate;

    /**
     * 更新时间
     */
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
    private Date lastModifiedDate;

    /**
     * 是否删除：0不删除， 1 删除
     */
    private String deleted;

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

    public String getDeleted() {
        return deleted;
    }

    public void setDeleted(String deleted) {
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