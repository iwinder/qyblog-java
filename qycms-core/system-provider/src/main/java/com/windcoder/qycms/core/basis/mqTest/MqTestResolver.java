package com.windcoder.qycms.core.basis.mqTest;

import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Component;

@Component
public class MqTestResolver {

    @SuppressWarnings({ "unchecked", "rawtypes" })
    @JmsListener(destination = "MqTest.queue")
    public void process(String message){
        System.out.println("------------MQ测试star------------");
        System.out.println(message);
        System.out.println("------------MQ测试end------------");
    }


}
