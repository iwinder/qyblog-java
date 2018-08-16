package com.windcoder.qycms.core.system;

import com.windcoder.qycms.core.basis.mqTest.MqTestAdapter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/test")
public class TestController {

    @Autowired
    private MqTestAdapter mqTestAdapter;

    @GetMapping("sayHello")
    public String sayHello (String name){
        return "Hello " + name;
    }

    @GetMapping("sayMq")
    public String sayMq (int i, String name){

        mqTestAdapter.process(i,name);
        return "sayMq:" + i +" ,Messages："+ name;
    }
}
