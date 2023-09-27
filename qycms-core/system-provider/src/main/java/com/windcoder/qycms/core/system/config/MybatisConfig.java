package com.windcoder.qycms.core.system.config;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@MapperScan(basePackages = {"com.windcoder.**.repository.mybatis"})
public class MybatisConfig {
}
