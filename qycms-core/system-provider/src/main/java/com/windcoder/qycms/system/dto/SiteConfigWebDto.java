package com.windcoder.qycms.system.dto;

public class SiteConfigWebDto {
    /**
     * id
     */
    private Long id;

    /**
     * 站点设置key
     */
    private String configKey;

    /**
     * 站点设置value
     */
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

    public String getConfigValue() {
        return configValue;
    }

    public void setConfigValue(String configValue) {
        this.configValue = configValue;
    }
}
