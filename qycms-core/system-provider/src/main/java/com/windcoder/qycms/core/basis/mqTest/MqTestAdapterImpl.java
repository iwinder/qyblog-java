package com.windcoder.qycms.core.basis.mqTest;

import org.apache.activemq.command.ActiveMQQueue;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.core.JmsTemplate;

import javax.jms.Destination;


public class MqTestAdapterImpl implements MqTestAdapter {

    @Autowired
    private JmsTemplate jmsTemplate;
    @Override
    public void process(int i, String str) {
        Destination destination = new ActiveMQQueue("MqTest,queue");
        String message = "这是第 " + i + " 条测试信息：" +str;
        jmsTemplate.convertAndSend(destination, message);
    }
}
