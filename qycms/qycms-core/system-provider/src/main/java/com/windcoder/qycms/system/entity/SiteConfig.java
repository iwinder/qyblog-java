package com.windcoder.qycms.system.entity;

import java.util.Date;

public class SiteConfig extends Auditable {
    private Long id;

    private String configKey;

    private String configName;

    private String configTip;

    private Integer type;

    private String configValue;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getConfigKey() {
        return configKey;
    }

    public void setConfigKey(String configKey) {
        this.configKey = configKey;
    }

    public String getConfigName() {
        return configName;
    }

    public void setConfigName(String configName) {
        this.configName = configName;
    }

    public String getConfigTip() {
        return configTip;
    }

    public void setConfigTip(String configTip) {
        this.configTip = configTip;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public String getConfigValue() {
        return configValue;
    }

    public void setConfigValue(String configValue) {
        this.configValue = configValue;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", id=").append(id);
        sb.append(", configKey=").append(configKey);
        sb.append(", configName=").append(configName);
        sb.append(", configTip=").append(configTip);
        sb.append(", type=").append(type);
        sb.append(", createdBy=").append(super.getCreatedBy());
        sb.append(", lastModifiedBy=").append(super.getLastModifiedBy());
        sb.append(", createdDate=").append(super.getCreatedDate());
        sb.append(", lastModifiedDate=").append(super.getLastModifiedDate());
        sb.append(", configValue=").append(configValue);
        sb.append("]");
        return sb.toString();
    }
}