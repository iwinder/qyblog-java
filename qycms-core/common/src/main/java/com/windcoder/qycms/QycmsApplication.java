package com.windcoder.qycms;

import javafx.application.Application;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.support.SpringBootServletInitializer;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

/**
 * Description:
 * User: WindCoder
 * Date: 2018-03-21
 * Time: 23:50 下午
 */
@SpringBootApplication
@EnableJpaAuditing
public class QycmsApplication  extends SpringBootServletInitializer {
    public static void main(String[] args) {
        SpringApplication.run(QycmsApplication.class,args);
    }

    @Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
        return application.sources(QycmsApplication.class);
    }

}
