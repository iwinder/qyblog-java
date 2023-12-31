package com.windcoder.qycms.config;

import com.windcoder.qycms.utils.FileUploadProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurationSupport;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

@Configuration
public class ApplicationMvcConfig implements WebMvcConfigurer {
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler(FileUploadProperties.VIRTUALPATH + "/**")
                .addResourceLocations("file:" + FileUploadProperties.CONTENTPATH + "/");
//                .setCacheControl(CacheControl.maxAge(-1, TimeUnit.SECONDS).cachePublic());
    }

}
