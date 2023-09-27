package com.windcoder.qycms.file.entity;

import com.windcoder.qycms.system.entity.Auditable;
import java.util.Date;

public class FileLibConfig extends Auditable {
    private Long id;

    private String accessKey;

    private String secretKey;

    private String bucket;

    private String endpoint;

    private String prefix;

    private String domain;

    private Long typeId;


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getAccessKey() {
        return accessKey;
    }

    public void setAccessKey(String accessKey) {
        this.accessKey = accessKey;
    }

    public String getSecretKey() {
        return secretKey;
    }

    public void setSecretKey(String secretKey) {
        this.secretKey = secretKey;
    }

    public String getBucket() {
        return bucket;
    }

    public void setBucket(String bucket) {
        this.bucket = bucket;
    }

    public String getEndpoint() {
        return endpoint;
    }

    public void setEndpoint(String endpoint) {
        this.endpoint = endpoint;
    }

    public String getPrefix() {
        return prefix;
    }

    public void setPrefix(String prefix) {
        this.prefix = prefix;
    }

    public String getDomain() {
        return domain;
    }

    public void setDomain(String domain) {
        this.domain = domain;
    }

    public Long getTypeId() {
        return typeId;
    }

    public void setTypeId(Long typeId) {
        this.typeId = typeId;
    }


    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", id=").append(id);
        sb.append(", accessKey=").append(accessKey);
        sb.append(", secretKey=").append(secretKey);
        sb.append(", bucket=").append(bucket);
        sb.append(", endpoint=").append(endpoint);
        sb.append(", prefix=").append(prefix);
        sb.append(", domain=").append(domain);
        sb.append(", typeId=").append(typeId);
        sb.append(", createdBy=").append(super.getCreatedBy());
        sb.append(", lastModifiedBy=").append(super.getLastModifiedBy());
        sb.append(", createdDate=").append(super.getCreatedDate());
        sb.append(", lastModifiedDate=").append(super.getLastModifiedDate());
        sb.append("]");
        return sb.toString();
    }
}