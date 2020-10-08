package com.windcoder.qycms.system.config;

import com.windcoder.qycms.config.DateConvert;
import com.windcoder.qycms.system.annotation.CurrentUserMethodArgumentResolver;
import com.windcoder.qycms.system.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.domain.AuditorAware;
import org.springframework.format.FormatterRegistry;

import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurationSupport;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.List;


//@EnableJpaAuditing
@Configuration
public class SystemConfig implements WebMvcConfigurer {

    @Autowired
    CurrentUserMethodArgumentResolver currentUserMethodArgumentResolver;
//    @Bean
//    public AuditorAware<User> auditorProvider(){
//        return new AuditorAwareImpl();
//    }



    @Override
    public void addArgumentResolvers(List<HandlerMethodArgumentResolver> argumentResolvers) {
//        CurrentUserMethodArgumentResolver ur = new CurrentUserMethodArgumentResolver();
//        argumentResolvers.add(ur);
        argumentResolvers.add(currentUserMethodArgumentResolver);
//        WebMvcConfigurer.super.addArgumentResolvers(argumentResolvers);
    }
    @Override
    public void addFormatters(FormatterRegistry registry) {
        registry.addConverter(new DateConvert());
    }


}
