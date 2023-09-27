package com.windcoder.qycms.core.system.config;

import com.windcoder.qycms.core.basis.mqTest.MqTestAdapter;
import com.windcoder.qycms.core.basis.mqTest.MqTestAdapterImpl;
import com.windcoder.qycms.core.system.entity.User;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.domain.AuditorAware;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

@Configuration
@EnableJpaAuditing
public class SystemConfig extends WebMvcConfigurerAdapter {

    @Bean
    public AuditorAware<User> auditorProvider(){
        return new AuditorAwareImpl();
    }

    @Bean
    @ConditionalOnMissingBean
    public MqTestAdapter mqTestAdapter(){
        return new MqTestAdapterImpl();
    }

}
