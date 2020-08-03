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
public class SystemConfig extends WebMvcConfigurationSupport {

    @Autowired
    CurrentUserMethodArgumentResolver currentUserMethodArgumentResolver;
//    @Bean
//    public AuditorAware<User> auditorProvider(){
//        return new AuditorAwareImpl();
//    }

   @Bean
   public String us(){
       System.out.println("us3333");
        return "ddd";
    }

    @Override
    public void addArgumentResolvers(List<HandlerMethodArgumentResolver> argumentResolvers) {
        System.out.println("addArgumentResolvers111111111");
//        CurrentUserMethodArgumentResolver ur = new CurrentUserMethodArgumentResolver();
//        argumentResolvers.add(ur);
        argumentResolvers.add(currentUserMethodArgumentResolver);
//        WebMvcConfigurer.super.addArgumentResolvers(argumentResolvers);
    }
    @Override
    public void addFormatters(FormatterRegistry registry) {
        System.out.println("addFormatters s111111111");
        registry.addConverter(new DateConvert());
    }


}
