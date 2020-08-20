package com.windcoder.qycms;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

/**
 * Description:
 * User: WindCoder
 * Date: 2018-03-21
 * Time: 23:50 下午
 */
@SpringBootApplication
@EnableScheduling
//@EnableJpaAuditing
public class QycmsApplication   {
    public static void main(String[] args) {
        SpringApplication.run(QycmsApplication.class,args);
    }



}
