package com.windcoder.qycms.entity;

import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Data
@Component
@ConfigurationProperties(prefix = "spring.datasource")
public class DruidCommonProperties {
    @Value("${initialSize:5}")
    private String initialSize;
    @Value("${minIdle:5}")
    private String minIdle;
    @Value("${maxActive:20}")
    private String maxActive;
    @Value("${maxWait:60000}")
    private String maxWait;
    @Value("${timeBetweenEvictionRunsMillis:60000}")
    private String timeBetweenEvictionRunsMillis;
    @Value("${minEvictableIdleTimeMillis:300000}")
    private String minEvictableIdleTimeMillis;
    @Value("${validationQuery:SELECT 1}")
    private String validationQuery;
    @Value("${testWhileIdle:true}")
    private String testWhileIdle;
    @Value("${testOnBorrow:false}")
    private String testOnBorrow;
    @Value("${testOnReturn:false}")
    private String testOnReturn;
    @Value("${poolPreparedStatements:true}")
    private String poolPreparedStatements;
    @Value("${maxOpenPreparedStatements:20}")
    private String maxOpenPreparedStatements;
    @Value("${asyncInit:true}")
    private String asyncInit;
}
