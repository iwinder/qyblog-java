package com.windcoder.qycms.entity;

import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Data
@Component
@ConfigurationProperties(prefix = "qy")
public class GlobalProperties {
    @Value("${token:windcoder}")
    private String token;
    @Value("${minaToken:windcoder}")
    private String minaToken;
    @Value("${xmlPath:/wdata/qy-xml}")
    private String xmlPath;


}
